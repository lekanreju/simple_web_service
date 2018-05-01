package com.dillonsoftware.webservices.mybatis.mapper;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.dillonsoftware.webservices.bean.User;
import com.dillonsoftware.webservices.spring.DataConfiguration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.annotation.DirtiesContext.ClassMode;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@DirtiesContext(classMode = ClassMode.AFTER_CLASS)
@ContextConfiguration(classes = {DataConfiguration.class})
@Rollback
@Transactional
@Component
public class UserMapperTest {

	@Autowired
	private UserMapper userMapper;

	@Test
	public void should_list_users() {
		final List<User> users = userMapper.list();

		assertEquals(4, users.size());
	}
	
	@Test
	public void should_list_users_with_query() {
		String query = "Han";
		final List<User> users = userMapper.listWithQuery(query);

		final User firstUser = users.get(0);

		final User user = userMapper.fetch(firstUser.getId());

		assertEquals(firstUser.getId(), user.getId());
		assertEquals(firstUser.getName(), user.getName());
		assertEquals(1, users.size());
	}

	@Test
	public void should_fetch_user() {
		final List<User> users = userMapper.list();
		final User firstUser = users.get(0);

		final User user = userMapper.fetch(firstUser.getId());

		assertEquals(firstUser.getId(), user.getId());
		assertEquals(firstUser.getName(), user.getName());
	}
	
	@Test
	public void should_add_user() {
		List<User> users = userMapper.list();
		final int initialCount = users.size();
		User newUser = new User();
        newUser.setName("Johnson");
		userMapper.add(newUser);
		
		users = userMapper.list();
		final int finalCount = users.size();
		
		assertTrue(finalCount > initialCount);
	}
	
	@Test
	public void should_delete_user() {
		List<User> users = userMapper.list();
		final User user = users.get(0);
		final Integer initialCount = users.size();
		userMapper.delete(user.getId());
		users = userMapper.list();
		final Integer finalCount = users.size();
		
		assertTrue(initialCount > finalCount);
	}
	
}
