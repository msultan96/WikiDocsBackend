package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.User;

public interface UserService {

	User findByEmail(String email);
	User findByEmailAndPassword(String email, String password);
	User register(User user);

	String getNameByEmail(String email);

}
