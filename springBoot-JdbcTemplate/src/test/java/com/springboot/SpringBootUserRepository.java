package com.springboot;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springboot.Application;
import com.springboot.dao.UserRepository;
import com.springboot.entiy.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class)
// 指定spring-boot的启动类
public class SpringBootUserRepository {

	@Autowired
	private UserRepository userRepository;

	@Test
	public void findAllUsers() {
		List<User> users = userRepository.findAll();
		System.out.println(users);
	}

	@Test
	public void findUserById() {
		User user = userRepository.findUserById(2);
	}
	
	@Test
	public void delete() {
		userRepository.delete(4);
	}

	@Test
	public void updateById() {
		User newUser = new User().setId(2).setUserName("new").setPassword("new");
		userRepository.update(newUser);
		User newUser2 = userRepository.findUserById(newUser.getId());
	}

	@Test
	public void createUser() {
		User newUser = new User().setUserName("userName").setPassword("password");
		User savedUser = userRepository.create(newUser);
	}
}
