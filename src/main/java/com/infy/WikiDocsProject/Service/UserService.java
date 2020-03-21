package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.User;

import java.util.List;

public interface UserService {

	User findByEmail(String email);
	User findByEmailAndPassword(String email, String password);
	User register(User user);

	String getNameByEmail(String email);

	List<String> getAllUserEmails();

}
