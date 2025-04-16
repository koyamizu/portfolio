package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.example.webapp.entity.Employee;
import com.example.webapp.entity.TimeStamp;

@Mapper
public interface TimeRecorderMapper {

	List<Employee> selectTodaysEmployees();
	
	TimeStamp selectWorkTimeById(@Param("employee_id") String employee_id);
	
	void start(@Param("employee_id") String employee_id);
	
	void end(@Param("employee_id") String employee_id);
}
