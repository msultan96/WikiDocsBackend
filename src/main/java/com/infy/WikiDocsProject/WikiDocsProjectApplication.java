package com.infy.WikiDocsProject;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Utility.ArticleBuilder;
import com.infy.WikiDocsProject.Utility.UserBuilder;
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
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@PropertySource(value={"classpath:messages.properties"})
public class WikiDocsProjectApplication {

	@Autowired
	public ArticleRepository articleRepository;

	@Autowired
	public UserRepository userRepository;

	@Autowired
	public BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(WikiDocsProjectApplication.class, args);
	}

	@Bean
	ApplicationRunner init(){
		return args -> {
			articleRepository.deleteAll();
			userRepository.deleteAll();

			User user = new UserBuilder()
					.id(new ObjectId())
					.email("muhammad@gmail.com")
					.password(bCryptPasswordEncoder.encode("111111"))
					.articles(Collections.emptyList())
					.role(Role.USER)
					.build();

			User admin = new UserBuilder()
					.id(new ObjectId())
					.email("daniel@gmail.com")
					.password(bCryptPasswordEncoder.encode("222222"))
					.articles(Collections.emptyList())
					.role(Role.ADMIN)
					.build();

			Article initialArtcile = new ArticleBuilder()
					.id(new ObjectId())
					.emailId("muhammad@gmail.com")
					.name("Article Initial")
					.status(Status.INITIAL)
					.rejectedCount(0)
					.editable(true)
					.build();

			Article betaArticle = new ArticleBuilder()
					.id(new ObjectId())
					.emailId("muhammad@gmail.com")
					.name("Article Beta")
					.status(Status.BETA)
					.rejectedCount(0)
					.editable(true)
					.build();

			Article approvedArticle = new ArticleBuilder()
					.id(new ObjectId())
					.emailId("muhammad@gmail.com")
					.name("Article Approved")
					.status(Status.APPROVED)
					.rejectedCount(0)
					.editable(true)
					.build();

			Article rejectedArticle = new ArticleBuilder()
					.id(new ObjectId())
					.emailId("muhammad@gmail.com")
					.name("Article Rejected")
					.status(Status.REJECTED)
					.rejectedCount(0)
					.editable(true)
					.build();

			Article discardedArticle = new ArticleBuilder()
					.id(new ObjectId())
					.emailId("muhammad@gmail.com")
					.name("Article Discarded")
					.status(Status.DISCARDED)
					.rejectedCount(0)
					.editable(true)
					.build();

			List<Article> articles = new ArrayList<>();

			articles.add(initialArtcile);
			articles.add(betaArticle);
			articles.add(approvedArticle);
			articles.add(rejectedArticle);
			articles.add(discardedArticle);

			user.setArticles(articles);

			articleRepository.insert(initialArtcile);
			articleRepository.insert(approvedArticle);
			articleRepository.insert(betaArticle);
			articleRepository.insert(rejectedArticle);
			articleRepository.insert(discardedArticle);

			userRepository.insert(user);
			userRepository.insert(admin);
		};
	}

	@Bean
	public CorsFilter corsFilter() {
		final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

		final CorsConfiguration config = new CorsConfiguration();
		config.setAllowCredentials(true);
		config.setAllowedOrigins(Collections.singletonList("*"));
		config.setAllowedHeaders(Arrays.asList("Origin", "Content-Type", "Accept"));
		config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS"));

		source.registerCorsConfiguration("/**", config);
		return new CorsFilter(source);
	}

	@Bean
	public BCryptPasswordEncoder bCryptPasswordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
