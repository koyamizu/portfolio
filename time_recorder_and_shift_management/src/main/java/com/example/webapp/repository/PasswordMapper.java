package com.example.webapp.repository;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface PasswordMapper {

	void updateByEmployeeId(Integer employeeId,String newPassword);
}
