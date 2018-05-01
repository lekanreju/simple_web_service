package com.dillonsoftware.webservices.mybatis.mapper;

import com.dillonsoftware.webservices.bean.User;

import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

	List<User> listWithQuery(@Param("query") String query);
	
	List<User> list();

	User fetch(@Param("userId") Integer userId);

	Integer add(@Param("user") User user);
	
	void delete(@Param("userId") Integer userId);
}
