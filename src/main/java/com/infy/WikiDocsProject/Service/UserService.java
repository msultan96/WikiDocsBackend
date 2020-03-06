package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;

public interface UserService {

	User findUserByName(String name);

	void createArticleByUser(String name, String channelId);

	List<Article> getAllArticlesByUser(String name);
}
