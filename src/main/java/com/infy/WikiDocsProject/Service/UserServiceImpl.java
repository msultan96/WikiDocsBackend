package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.enums.Status;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;
	private final ArticleRepository articleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ArticleRepository articleRepository) {
		this.userRepository = userRepository;
		this.articleRepository = articleRepository;
	}

	
	public Article createArticle(){
		Article article;
		return null;
	}

	public User login(String name){
		return userRepository.findUserByName(name);
		
	}

	
	@Override
	public void submit(Article arc) {
		
		//Check  .saveArticle return if success display message
		articleRepository.submitArticle(arc);
	}


	@Override
	public List<Article> displayArticles() {
		
		return articleRepository.findArticlesByStatusOrStatus(Status.APPROVED, Status.BETA);
	}


	@Override
	public void approve(Article arc) {
		articleRepository.approveArticle(arc);
		
		
	}


	@Override
	public void reject(Article arc) {
		articleRepository.rejectArticle(arc);
		
	}

}
