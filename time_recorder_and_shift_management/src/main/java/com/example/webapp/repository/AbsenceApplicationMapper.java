package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Absence;
import com.example.webapp.entity.AbsenceApplication;

@Mapper
public interface AbsenceApplicationMapper {
	
	List<AbsenceApplication> selectAll();
	
	List<AbsenceApplication> selectAllByEmployeeId(Integer employeeId);
	
	List<Absence> selectAllReasons();
	
	void insert(AbsenceApplication application);
	
	void update(Integer shiftId,Boolean decision);
	
	void delete(Integer applicationId);
}
