package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Service.ArticleService;
import com.infy.WikiDocsProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserAPI {

	private final UserService userService;
	private final ArticleService articleService;

	@Autowired
	public UserAPI(UserService userService, ArticleService articleService) {
		this.userService = userService;
		this.articleService = articleService;
	}

	@GetMapping("{name}")
	@ResponseBody
	public Mono<User> loginUser(@PathVariable String name) {

		return null;
	}

	public Mono<Article> createArticle(){
		return null;
	}

	public Mono<Article> readArticle(){
		return null;
	}

	public Mono<Article> editArticle(){
		return null;
	}

	public Mono<Article> submitArticle(){
		return null;
	}

}
