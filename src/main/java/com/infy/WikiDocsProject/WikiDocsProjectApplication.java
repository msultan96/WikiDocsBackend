package com.infy.WikiDocsProject;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.enums.Role;
import com.infy.WikiDocsProject.enums.Status;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class WikiDocsProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WikiDocsProjectApplication.class, args);
	}

	@Bean
	ApplicationRunner init(ArticleRepository articleRepository, UserRepository userRepository){
		return args -> {
			articleRepository.deleteAll();
			userRepository.deleteAll();
//			User user = new User();
//			Article article = new Article();

//
//			user.builder()
//					.name("Muhammad Sultan")
//					.email("muhammad.sultan96@gmail.com")
//					.role(Role.ADMIN)
//					.build();
//
//			article.builder()
//					.author(user)
//					.name("Article 1")
//					.content(" ")
//					.status(Status.BETA)
//					.editable(true)
//					.build();
//
//			user.builder().article(article).build();
//
//			userRepository.save(user).subscribe(System.out::println);
//			articleRepository.save(article).subscribe(System.out::println);

		};
	}
}
