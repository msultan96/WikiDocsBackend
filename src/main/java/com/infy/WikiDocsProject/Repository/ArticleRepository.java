package com.infy.WikiDocsProject.Repository;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.enums.Status;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

	Optional<Article> findById(ObjectId id);

	Page<Article> findArticlesByStatus(Status status, Pageable pageable);

	Page<Article> findAllArticlesByEmailIdAndStatus(String email, Status approved, Pageable pageable);

	Page<Article> findAllArticlesByEmailId(String email, Pageable pageable);

	List<Article> findAllArticlesByEmailId(String email);
}
