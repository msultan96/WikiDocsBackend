package com.infy.WikiDocsProject.Repository;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.enums.Status;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

	List<Article> findArticlesByStatusOrStatus(Status approved, Status beta);
	Article findArticleByChannelId(String channelId);
}
