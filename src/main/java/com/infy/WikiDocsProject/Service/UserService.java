package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;

public interface UserService {

	User findUserByName(String name) throws Exception;
	Article createArticleByUser(String name, String channelId) throws Exception;
}
