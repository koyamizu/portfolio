package com.example.webapp.controller;

import java.util.List;
import java.util.Map;

import jakarta.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.webapp.entity.AbsenceApplication;
import com.example.webapp.entity.AbsenceReason;
import com.example.webapp.entity.ShiftSchedule;
import com.example.webapp.exception.DuplicateApplicationException;
import com.example.webapp.exception.InvalidAccessException;
import com.example.webapp.exception.InvalidEditException;
import com.example.webapp.form.AbsenceApplicationForm;
import com.example.webapp.service.AbsenceApplicationService;
import com.example.webapp.service.ShiftManagementService;

import lombok.RequiredArgsConstructor;

/**
 * 欠勤申請関連のリクエストを受け付ける
 */
@Controller
@RequiredArgsConstructor
@RequestMapping("/absence-application")

public class AbsenceApplicationController {

	private final AbsenceApplicationService absenceApplicationService;
	private final ShiftManagementService shiftManagementService;

	/**
	 * 欠勤申請一覧を表示。<br>管理者メニュー（/admin）から遷移してきたときは全従業員の欠勤申請一覧を表示し、
	 * 従業員メニュー（/user）から遷移してきたときは、その従業員の欠勤申請一覧を表示する。<br>
	 * 管理者メニューと従業員メニュー以外から遷移したとき、例外放出をする。
	 * @param model
	 * @param auth
	 * @param session
	 * @return 欠勤申請一覧のビュー
	 * @throws InvalidAccessException
	 */
	@GetMapping
	public String showList(Model model, Authentication auth,HttpSession session) throws InvalidAccessException {
		String from = (String) session.getAttribute("from");
		Integer employeeId = Integer.parseInt(auth.getName());
		List<AbsenceApplication> applications=absenceApplicationService.get(from,employeeId);
		
		model.addAttribute("from", from);
		model.addAttribute("applications", applications);
		return "absence-application/list";
	}

	/**
	 * 欠勤申請のフォームを表示。
	 * @param model
	 * @param auth
	 * @return フォームのビュー
	 */
	@GetMapping("request")
	public String showForm(Model model, Authentication auth) {
		Integer employeeId = Integer.parseInt(auth.getName());
		List<ShiftSchedule> shiftSchedules = shiftManagementService.getAllShiftsAfterToday(employeeId);
		List<AbsenceReason> absenceReasons = absenceApplicationService.getAllReasons();
		model.addAllAttributes(
				Map.of(
						"shiftSchedules", shiftSchedules
						, "absenceReasons", absenceReasons
						, "employeeId", employeeId
						, "form", new AbsenceApplicationForm()));
		return "absence-application/form";
	}
	
	/**
	 * 欠勤申請一覧にある「承認」「不承認」を押した時に実行。
	 * 「承認」でtrue、「不承認」でfalseがdecisionに格納されてポスト送信される。
	 * @param shiftId
	 * @param decision
	 * @param attributes
	 * @return
	 */
	@PostMapping("decision")
	public String updateApprove(@RequestParam Integer shiftId, @RequestParam Boolean decision,
			RedirectAttributes attributes) {
		absenceApplicationService.updateApprove(shiftId, decision);
		attributes.addFlashAttribute("message", "承認処理が完了しました");
		return "redirect:/absence-application";
	}

	/**
	 * 欠勤申請フォーム（/absence-application/request）で「送信」を押したら実行される。
	 * フォームをアプリケーション層に渡す。申請が重複していたら例外放出。
	 * @param form
	 * @param bindingResult
	 * @param attributes
	 * @return
	 * @throws DuplicateApplicationException
	 */
	@PostMapping("submit")
	public String submitApplication(@Validated AbsenceApplicationForm form,BindingResult bindingResult
			, RedirectAttributes attributes) throws DuplicateApplicationException {
		if(bindingResult.hasErrors()) {
			return "redirect:/absence-application/request";
		}
		absenceApplicationService.submitApplication(form);
		attributes.addFlashAttribute("message", "申請が完了しました");
		return "redirect:/absence-application";
	}

	/**
	 * 従業員ページ（/user）から欠勤申請一覧に遷移したときのみ、「取消」ボタンが表示される。
	 * それを押すと実行。<br>application-idはREST方式で動的に生成される。
	 * 昨日以前の申請を削除をすると例外放出。
	 * @param applicationId
	 * @param attributes
	 * @return
	 * @throws InvalidEditException
	 */
	@GetMapping("delete/{application-id}")
	public String deleteApplication(@PathVariable("application-id") Integer applicationId, RedirectAttributes attributes) throws InvalidEditException {
		absenceApplicationService.deleteApplication(applicationId);
		attributes.addFlashAttribute("message", "申請を削除しました");
		return "redirect:/absence-application";
	}
	
	/**
	 * deleteApplicationメソッドから放出された例外の処理。欠勤申請一覧ページに推移してエラーメッセージを表示する。
	 * @param e
	 * @param attributes
	 * @return
	 */
	@ExceptionHandler({InvalidEditException.class})
	public String redirectToApplicationsPage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/absence-application";
	}
	
	/**
	 * submitApplicationメソッドから放出された例外の処理。欠勤申請フォームに推移してエラーメッセージを表示する。
	 */
	@ExceptionHandler({DuplicateApplicationException.class})
	public String redirectToRequestPage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/absence-application/request";
	}
	
	/**
	 * showListメソッドから放出された例外の処理。欠勤申請一覧に推移してエラーメッセージを表示する。
	 * @param e
	 * @param attributes
	 * @return
	 */
	@ExceptionHandler({InvalidAccessException.class})
	public String redirectToIndexPage(Exception e, RedirectAttributes attributes) {
		attributes.addFlashAttribute("errorMessage", e.getMessage());
		return "redirect:/";
	}
}
