package com.springboot.service;

import java.util.List;
import com.springboot.entiy.User;

public interface UserService {

	public List<User> getUserList();

	public User findUserById(int id);

	public void save(User user);

	public void edit(User user);

	public void delete(int id);

}
