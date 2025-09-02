package com.example.webapp.controller;

import java.util.List;

import jakarta.servlet.http.HttpSession;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.Employee;
import com.example.webapp.exception.DuplicateEmployeeException;
import com.example.webapp.exception.ForeignKeyConstraintViolationException;
import com.example.webapp.exception.InvalidEmployeeIdException;
import com.example.webapp.exception.NoDataFoundException;
import com.example.webapp.exception.TooLongDataException;
import com.example.webapp.form.EmployeeForm;
import com.example.webapp.form.PasswordForm;
import com.example.webapp.service.EmployeesManagementService;
import com.example.webapp.utility.PasswordGenerator;

import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping("/employees")
@RequiredArgsConstructor
public class EmployeesManagementController {

	private final EmployeesManagementService service;

	@GetMapping
	public String showEmployeesList(Model model) throws NoDataFoundException {
		List<Employee> employees = service.getAllEmployees();
		model.addAttribute("employees", employees);
		return "employees/list";
	}

	@GetMapping("form")
	public String registerNewEmployee(EmployeeForm form) {
		form.setIsNew(true);
		return "employees/form";
	}
	
	@PostMapping("register")
	public String updatePassword(@Validated PasswordForm passwordForm, BindingResult bindingResult,
			RedirectAttributes attributes,HttpSession session) throws DuplicateEmployeeException, TooLongDataException {
		if (bindingResult.hasErrors()) {
			return "password/form";
		}
		String newBcryptPassword = PasswordGenerator.generatePassword(passwordForm.getNewRawPassword());
		EmployeeForm newEmployee=(EmployeeForm) session.getAttribute("newEmployee");
		session.removeAttribute("newEmployee");
		newEmployee.setPassword(newBcryptPassword);
		service.insertEmployee(newEmployee);
		attributes.addFlashAttribute("message", "新規従業員の登録が完了しました");
		return "redirect:/employees";
	}

	@GetMapping("edit/{employee-id}")
	public String editEmployee(@PathVariable("employee-id") Integer employeeId, Model model,
			RedirectAttributes attributes) throws InvalidEmployeeIdException {
		EmployeeForm form = service.getEmployeeForm(employeeId);
		model.addAttribute("employeeForm", form);
		return "employees/form";
	}

	@PostMapping("update")
	public String updateEmployee(@Validated EmployeeForm form, BindingResult bindingResult,
			RedirectAttributes attributes) throws DuplicateEmployeeException, TooLongDataException {
		if (bindingResult.hasErrors()) {
			return "employees/form";
		}
		service.updateEmployee(form);
		attributes.addFlashAttribute("message", "従業員情報が更新されました");
		return "redirect:/employees";
	}

	@PostMapping("delete/{employee-id}")
	public String delete(@PathVariable("employee-id") Integer employeeId, RedirectAttributes attributes
			,HttpSession session)
					throws InvalidEmployeeIdException, ForeignKeyConstraintViolationException {
		session.setAttribute("employeeIdToDelete", employeeId);
		service.deleteEmployee(employeeId);
		attributes.addFlashAttribute("message","従業員ID:" + employeeId+" の従業員情報が削除されました");
		session.removeAttribute("employeeIdToDelete");
		return "redirect:/employees";
	}
	
	@PostMapping("all-erase")
	@ResponseStatus(HttpStatus.TEMPORARY_REDIRECT)
	public String eraseAllEmployeeInformation(@RequestParam("employee-id") Integer employeeId) {
		service.eraseAbsenceApplicationsWorkHistoriesShiftRequestsAndShiftSchedules(employeeId);
		return "redirect:/employees/delete/"+employeeId;
	}
	
	@GetMapping("detail/{employee-id}")
	public String showEmployeeDetail(@PathVariable("employee-id") Integer employeeId, Model model) 
			throws InvalidEmployeeIdException {
		Employee employee = service.getEmployee(employeeId);
		model.addAttribute("employee", employee);
		return "employees/detail";
	}

	@ExceptionHandler(ForeignKeyConstraintViolationException.class)
	public String confirmAllErase(ForeignKeyConstraintViolationException e, RedirectAttributes attributes
			,HttpSession session) {
		Integer targetEmployeeId=(Integer) session.getAttribute("employeeIdToDelete");
		attributes.addFlashAttribute("confirmMessage", e.getMessage()
				+"\n「はい」を押すと、シフト情報と勤怠履歴が全て削除されますがよろしいでしょうか？（この操作は取り消せません）");
		attributes.addFlashAttribute("targetEmployeeId",targetEmployeeId);
		return "redirect:/employees";
	}
	
	@ExceptionHandler({NoDataFoundException.class,InvalidEmployeeIdException.class,DuplicateEmployeeException.class})
	public String redirectToEmployeesListPage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/employees";
	}
	
	@ExceptionHandler(TooLongDataException.class)
	public String showOriginOfTooLongDataException(Exception e, RedirectAttributes attributes) {
		//TODO 原因を抜き出すロジック
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/employees";
	}
}
