package com.dillonsoftware.webservices.service;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import com.dillonsoftware.webservices.bean.User;
import com.dillonsoftware.webservices.mybatis.mapper.UserMapper;
import com.dillonsoftware.webservices.test.MockHelper;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {

	@Mock
	private UserMapper userMapper;

	@InjectMocks
	private UserService userService;

	@Test
	public void should_list_users() {
		final List<User> expectedUsers = new ArrayList<>();

		when(userMapper.list()).thenReturn(expectedUsers);

		final List<User> users = userService.listUsers();

		verify(userMapper).list();
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(expectedUsers, users);
	}
	
	@Test
	public void should_list_users_with_query() {
		String query = "Larry";
		final List<User> expectedUsers = new ArrayList<>();

		when(userMapper.listWithQuery(query)).thenReturn(expectedUsers);

		final List<User> users = userService.listUsers(query);

		verify(userMapper).list();
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(expectedUsers, users);
	}


	@Test
	public void should_fetch_user() {
		final Integer userId = 1;
		final User expectedUser = new User();

		when(userMapper.fetch(userId)).thenReturn(expectedUser);

		final User user = userService.getUser(userId);

		verify(userMapper).fetch(userId);
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(expectedUser, user);
	}
	
	@Test
	public void should_add_user() {
		final Integer userId = 1;
		final User newUser = new User();

		when(userMapper.add(newUser)).thenReturn(userId);

		final Integer id = userService.addUser(newUser);

		verify(userMapper).add(newUser);
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

		assertEquals(id, userId);
	}
	
	@Test
	public void should_delete_user() {
		final Integer userId = 1;
		
		userService.deleteUser(userId);

		verify(userMapper).delete(userId);
		verifyNoMoreInteractions(MockHelper.allDeclaredMocks(this));

	}

}
