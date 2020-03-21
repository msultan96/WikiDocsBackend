package com.infy.WikiDocsProject;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Utility.TestDataCreator;
import com.infy.WikiDocsProject.enums.Role;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
@PropertySource(value={"classpath:messages.properties"})
public class WikiDocsProjectApplication {

	@Autowired
	ArticleRepository articleRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(WikiDocsProjectApplication.class, args);
	}

	@Bean
	ApplicationRunner init(){
		return args ->{
			articleRepository.deleteAll();
			userRepository.deleteAll();

			List<User> users = TestDataCreator.createUsers();
			List<Article> articles = TestDataCreator.createArticles("muh@gmail.com");
			User muh = User.builder()
					.id(new ObjectId())
					.name("Muhammad Sultan")
					.email("muh@gmail.com")
					.password(bCryptPasswordEncoder.encode("111111"))
					.articles(articles)
					.role(Role.USER)
					.build();

			User admin = User.builder()
					.id(new ObjectId())
					.name("admin")
					.email("admin@gmail.com")
					.password(bCryptPasswordEncoder.encode("admin"))
					.articles(new ArrayList<>())
					.role(Role.ADMIN)
					.build();

			users.add(muh);
			users.add(admin);

			users.forEach(user ->{
				user.getArticles().forEach(article ->{
					articleRepository.insert(article);
				});
				userRepository.insert(user);
			});
		};
	}

}
