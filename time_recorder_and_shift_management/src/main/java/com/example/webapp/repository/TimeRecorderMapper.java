package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.WorkTime;

@Mapper
public interface TimeRecorderMapper {

	List<Employee> selectTodaysEmployees();
	
	WorkTime selectWorkTimeById(@Param("id") String employee_id);
	
	void start(@Param("id") String employee_id);
	
	void end(@Param("id") String employee_id);
}
