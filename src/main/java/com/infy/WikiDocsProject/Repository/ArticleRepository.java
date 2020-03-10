package com.infy.WikiDocsProject.Repository;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.enums.Status;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * 
 * Article Repository
 * @Repository - Declared this class as a Repository
 *
 */
@Repository
public interface ArticleRepository extends MongoRepository<Article, String> {
	
	/**
	 * @name findArticleByChannelId
	 * @Desciption Retrieve an article of given channelId
	 * @param channelId
	 * @return an article object
	 */
	Optional<Article> findArticleByChannelId(String channelId);
	/**
	 * @name findAllArticlesByUserId
	 * @Desciption Retrieve all articles of given userId
	 * @param userId
	 * @return list of article
	 */
	List<Article> findAllArticlesByUserId(ObjectId userId);
	/**
	 * @name findArticlesByStatus
	 * @Desciption Retrieve all articles of given status
	 * @param status
	 * @return list of article
	 */
	List<Article> findArticlesByStatus(Status status);
}
