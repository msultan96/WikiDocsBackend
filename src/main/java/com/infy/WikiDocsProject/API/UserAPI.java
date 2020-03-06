package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Service.ArticleService;
import com.infy.WikiDocsProject.Service.UserService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("user")
@CrossOrigin
public class UserAPI {

	private final UserService userService;
	private final ArticleService articleService;
	private final Environment environment;

	@Autowired
	public UserAPI(UserService userService, ArticleService articleService, Environment environment) {
		this.userService = userService;
		this.articleService = articleService;
		this.environment = environment;
	}

	@GetMapping("login/{name}")
	public ResponseEntity<User> loginUser(@PathVariable String name) throws Exception{
		try{
			User user = userService.findUserByName(name);
			List<Article> articleList = articleService.getAllArticlesByUser(name);
			user.setArticles(articleList);
			return new ResponseEntity<User>(user, HttpStatus.OK);
		}
		catch(Exception e){
			System.out.println(environment.getProperty(e.getMessage()));
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@GetMapping("getUsersArticles/{name}")
	public ResponseEntity<List<Article>> getUsersArticles(@PathVariable String name) throws Exception{
		try{
			List<Article> articles = articleService.getAllArticlesByUser(name);
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}

	}

	@PostMapping("createNewArticle/{name}/{channelId}")
	public ResponseEntity<Article> createNewArticle(@PathVariable String name, @PathVariable String channelId) throws Exception {
		try{
			Article article = userService.createArticleByUser(name, channelId);
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}
}
