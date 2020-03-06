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
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@SpringBootApplication
@PropertySource(value={"classpath:messages.properties"})
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
			Article article1 = new Article();
			Article article2 = new Article();
			List<Article> articles = new ArrayList<>();

			user.setId(new ObjectId());
			user.setName("Muhammad Sultan");
			user.setEmail("muhammad.sultan96@gmail.com");
			user.setArticles(Collections.emptyList());
			user.setRole(Role.USER);

			article1.setId(new ObjectId());
			article1.setUserId(user.getId());
			article1.setEditable(true);
			article1.setName("Article 1");
			article1.setStatus(Status.BETA);

			article2.setId(new ObjectId());
			article2.setUserId(user.getId());
			article2.setEditable(true);
			article2.setName("Article 2");
			article2.setStatus(Status.BETA);

			articles.add(article1);
			articles.add(article2);

			user.setArticles(articles);

			articleRepository.insert(article1);
			articleRepository.insert(article2);
			userRepository.insert(user);
		};
	}
}
