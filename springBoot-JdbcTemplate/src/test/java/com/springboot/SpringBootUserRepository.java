package com.springboot;

import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.springboot.Application;
import com.springboot.dao.UserRepository;
import com.springboot.domain.User;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = Application.class) // 指定spring-boot的启动类
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
	public void updateById() {
		User newUser = new User(3, "JackChen", "JackChen@qq.com");
		userRepository.update(newUser);
		User newUser2 = userRepository.findUserById(newUser.getId());
	}

	@Test
	public void createUser() {
		User user = new User(0, "tom", "tom@gmail.com");
		User savedUser = userRepository.create(user);

	}
}
