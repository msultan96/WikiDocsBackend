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

	User findUserByEmail(String email) throws Exception;
	User findUserByEmailAndPassword(String email, String encode) throws Exception;

	Article createArticleByEmail(String email, String channelId) throws Exception;//Fix

}
