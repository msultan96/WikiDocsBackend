package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Service.ArticleService;
import com.infy.WikiDocsProject.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("article")
@CrossOrigin
public class ArticleAPI {

	private final UserService userService;
	private final ArticleService articleService;

	@Autowired
	public ArticleAPI(UserService userService, ArticleService articleService) {
		this.userService = userService;
		this.articleService = articleService;
	}

	@GetMapping("getApprovedAndBetaArticles")
	@ResponseBody
	public Flux<Article> getApprovedAndBetaArticles() {

		return null;
	}

	@GetMapping("getAll")
	public Flux<Article> getAll(){
		return articleService.getAll();
	}

}
