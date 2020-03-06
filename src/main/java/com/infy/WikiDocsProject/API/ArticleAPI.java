package com.infy.WikiDocsProject.API;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Service.ArticleService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

@RestController
@RequestMapping("article")
@CrossOrigin
public class ArticleAPI {

	private final ArticleService articleService;
	private final Environment environment;

	@Autowired
	public ArticleAPI(ArticleService articleService, Environment environment) {
		this.articleService = articleService;
		this.environment = environment;
	}

	@GetMapping("getAllArticlesByUser/{name}")
	public ResponseEntity<List<Article>> getAllArticlesByUser(@PathVariable String name) throws Exception{
		try{
			List<Article> articles = articleService.getAllArticlesByUser(name);
			return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@GetMapping("getApprovedAndBetaArticles")
	public ResponseEntity<List<Article>> getApprovedAndBetaArticles(){
		List<Article> articles = articleService.getApprovedAndBetaArticles();
		return new ResponseEntity<List<Article>>(articles, HttpStatus.OK);
	}

	@PostMapping("submitArticleForApproval/{channelId}")
	public ResponseEntity<Article> submitArticleForApproval(@PathVariable String channelId) throws Exception{
		try{
			Article article = articleService.submitArticleForApproval(channelId);
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@PostMapping("approveArticle/{channelId}")
	public ResponseEntity<Article> approveArticle(@PathVariable String channelId) throws Exception {
		try{
			Article article = articleService.approveArticle(channelId);
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

	@PostMapping("rejectArticle/{channelId}")
	public ResponseEntity<Article> rejectArticle(@PathVariable String channelId) throws Exception{
		try{
			Article article = articleService.rejectArticle(channelId);
			return new ResponseEntity<Article>(article, HttpStatus.OK);
		}
		catch(Exception e){
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, environment.getProperty(e.getMessage()));
		}
	}

}
