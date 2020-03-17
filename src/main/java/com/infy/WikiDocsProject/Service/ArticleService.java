package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;
import org.bson.types.ObjectId;

/**
 * Article Service Class 
 * Headers
 */
public interface ArticleService {
	
	//Method declarations
	Article createArticleByEmail(String email) throws Exception;

	List<Article> getAllArticlesByEmailId(String email);
	List<Article> getAllApprovedArticlesByEmailId(String email);
	List<Article> getAllBetaArticlesByEmailId(String email);
	List<Article> getAllInitialArticlesByEmailId(String email);
	List<Article> getAllRejectedArticlesByEmailId(String email);
	List<Article> getAllDiscardedArticlesByEmailId(String email);

	Article findById(String id) throws Exception;

	List<Article> getApprovedArticles();
	List<Article> getBetaArticles();

	Article submitArticle(ObjectId id) throws Exception;
	Article approveArticle(ObjectId id) throws Exception;
	Article rejectArticle(ObjectId id) throws Exception;

	Article saveArticle(String etherPadId);

	String getEtherPadUrl(String id);
}
