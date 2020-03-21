package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;

public interface ArticleService {

	Article findById(String id);
	Article createArticleByEmail(String email);

	List<Article> getAllArticlesByEmailId(String email);
	List<Article> getAllArticlesByEmailId(String email, int pageNumber, int pageSize);

	List<Article> getAllArticlesByStatus(Status status, int pageNumber, int pageSize);
	/**
	* Commented methods below have been replaced by the method above
	* */
//	List<Article> getApprovedArticles();
//	List<Article> getBetaArticles();

	List<Article> getAllArticlesByEmailIdAndStatus(String email, Status status, int pageNumber, int pageSize);
	/**
	 * Commented methods below have been replaced by the method above
	 */
//	List<Article> getAllApprovedArticlesByEmailId(String email);
//	List<Article> getAllBetaArticlesByEmailId(String email);
//	List<Article> getAllInitialArticlesByEmailId(String email);
//	List<Article> getAllRejectedArticlesByEmailId(String email);
//	List<Article> getAllDiscardedArticlesByEmailId(String email);

	Article submitArticle(String id);
	Article approveArticle(String id);
	Article rejectArticle(String id);

	Article saveArticle(String etherPadId);

	String getEtherPadUrl(String id);
}
