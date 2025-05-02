package com.example.webapp.repository;

import org.apache.ibatis.annotations.Mapper;

import com.example.webapp.entity.Authentication;

@Mapper
public interface AuthenticationMapper {

	Authentication selectByEmployeeId(Integer employeeId);
}
