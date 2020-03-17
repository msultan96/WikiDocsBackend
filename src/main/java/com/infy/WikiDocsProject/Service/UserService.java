package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
/**
 * 
 * User Service Class
 * Headers
 *
 */
public interface UserService {

	User findByEmail(String email);
	User findByEmailAndPassword(String email, String password);
	User register(User user);

}
