package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.enums.Status;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ArticleServiceImpl implements ArticleService {

	private final ArticleRepository articleRepository;

	@Autowired
	public ArticleServiceImpl(ArticleRepository articleRepository) {
		this.articleRepository = articleRepository;
	}

	public Flux<Article> getApprovedAndBetaArticles(){
		return articleRepository.findArticlesByStatusOrStatus(Status.APPROVED, Status.BETA);
	}

	public Mono<Article> getArticleById(){
		return null;
	}

	public Mono<Article> updateArticleById(){
		return null;
	}

	public Flux<Article> getAll() {
		return articleRepository.findAll();
	}
}
