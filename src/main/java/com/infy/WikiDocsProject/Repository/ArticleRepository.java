package com.infy.WikiDocsProject.Repository;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.enums.Status;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

@Repository
public interface ArticleRepository extends ReactiveMongoRepository<Article, String> {

	Flux<Article> findArticlesByStatusOrStatus(Status status1, Status status2);
}
