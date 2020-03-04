package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;
import reactor.core.publisher.Flux;

public interface ArticleService {

	List<Article> getAll();
}
