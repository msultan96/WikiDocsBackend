package com.infy.WikiDocsProject;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.enums.Role;
import com.infy.WikiDocsProject.enums.Status;
import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBObject;
import org.bson.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
public class WikiDocsProjectApplication {

	@Autowired
	public ArticleRepository articleRepository;

	@Autowired
	public UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(WikiDocsProjectApplication.class, args);
	}

	@Bean
	ApplicationRunner init(){
		return args -> {
			articleRepository.deleteAll();
			userRepository.deleteAll();

			User user = new User();
			Article article = new Article();
			List<Article> articles = new ArrayList<>();

			user.setName("Muhammad Sultan");
			user.setEmail("muhammad.sultan96@gmail.com");
			user.setArticles(Collections.emptyList());
			user.setRole(Role.USER);

			article.setEditable(true);
			article.setName("No name");
			article.setStatus(Status.BETA);

			articles.add(article);

			user.setArticles(articles);

			articleRepository.insert(article);
			userRepository.insert(user);

		};
	}
}
