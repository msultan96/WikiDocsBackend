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

	User findUserByEmail(String email);
	User findUserByEmailAndPassword(String email, String encode);

}
