package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Service.ArticleService;
import com.infy.WikiDocsProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

	@GetMapping("login/{name}")
	@ResponseBody
	public User loginUser(@PathVariable String name) {
		User user = userService.findUserByName(name);
		List<Article> articleList = userService.getAllArticlesByUser(name);
		user.setArticles(articleList);
		return user;
	}

	@GetMapping("getUsersArticles/{name}")
	public List<Article> getUsersArticles(@PathVariable String name){
		return null;
	}

	@PostMapping("createNewArticle/{name}/{channelId}")
	public void createNewArticle(@PathVariable String name, @PathVariable String channelId){
		userService.createArticleByUser(name, channelId);
	}

}
