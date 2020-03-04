package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import reactor.core.publisher.Mono;

public interface UserService {

	public User login(String name);
	public void submit(Article arc);
	public List<Article> displayArticles();
	public void approve(Article arc);
	public void reject(Article arc);
	

	Article createArticle();
}
