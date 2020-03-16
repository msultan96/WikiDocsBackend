package com.infy.WikiDocsProject;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.BaseProducer;
import com.devskiller.jfairy.producer.person.Person;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Utility.ArticleBuilder;
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
//			articleRepository.deleteAll();
//			userRepository.deleteAll();
//
//			Fairy fairy = Fairy.create();
//			Person person;
//			BaseProducer baseProducer;
//			TextProducer textProducer;
//
//			for(int i=0; i<5; i++){
//				person = fairy.person();
//				baseProducer = fairy.baseProducer();
//				textProducer = fairy.textProducer();
//
//				List<Article> articles = new ArrayList<>();
//				Article article;
//				for(int j=0; j<5; j++){
//					article = Article
//							.builder()
//							.id(new ObjectId())
//							.emailId(person.getEmail())
//							.name(textProducer.sentence(baseProducer.randomBetween(3,8)))
//							.content(textProducer.paragraph(baseProducer.randomBetween(5,25)))
//							.status(baseProducer.randomElement(Status.class))
//							.readOnly(baseProducer.trueOrFalse())
//							.rejectedCount(baseProducer.randomBetween(0,2))
//							.build();
//					if(article.getStatus() == Status.APPROVED || article.getStatus() == Status.DISCARDED)
//						article.setReadOnly(true);
//					if(article.getStatus() == Status.DISCARDED) article.setRejectedCount(4);
//					if(article.getStatus() == Status.INITIAL) article.setRejectedCount(0);
//					articles.add(article);
//					articleRepository.insert(article);
//				}
//
//				User user = User
//						.builder()
//						.id(new ObjectId())
//						.email(person.getEmail())
//						.name(person.getFullName())
//						.password(bCryptPasswordEncoder.encode(person.getPassword()))
//						.role(Role.USER)
//						.articles(articles)
//						.build();
//				userRepository.insert(user);
//
//				System.out.println(person.getFullName());
//				System.out.println(person.getEmail());
//				System.out.println(person.getPassword());
//				System.out.println(user.getRole());
//			}
		};
	}
}
