package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.Article;
import reactor.core.publisher.Flux;

public interface ArticleService {

	Flux<Article> getAll();
}
