package com.example.webapp.repository;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Shift;

@Mapper
public interface TimeRecorderMapper {

	List<Shift> selectTodaysEmployees();
	
	void start(Shift shift);
	
	void end(Shift shift);
}
