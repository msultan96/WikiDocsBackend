package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;

import com.infy.WikiDocsProject.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.query.BasicQuery;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value="userService")
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final ArticleRepository articleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ArticleRepository articleRepository) {
		this.userRepository = userRepository;
		this.articleRepository = articleRepository;
	}

	public User findUserByName(String name){
		return userRepository.findUserByName(name);
	}
	
	public void createArticleByUser(String name, String channelId){
		Article article = new Article();
		User user = findUserByName(name);

		// change to get article by user from database
		List<Article> articleList = user.getArticles();

		article.setStatus(Status.INITIAL);
		article.setChannelId(channelId);
		articleList.add(article);

		user.setArticles(articleList);

		articleRepository.save(article);
		userRepository.save(user);
	}

	public List<Article> getAllArticlesByUser(String name){
//		return userRepository.getArticlesByUser();
		return null;
	}
}
