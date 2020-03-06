package com.infy.WikiDocsProject.Repository;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.enums.Status;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {

	Optional<Article> findArticleByChannelId(String channelId);
	List<Article> findAllArticlesByUserId(ObjectId userId);
	List<Article> findArticlesByStatusOrStatus(Status approved, Status beta);
}
