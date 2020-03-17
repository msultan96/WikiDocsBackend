package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;
import org.bson.types.ObjectId;

public interface ArticleService {
	
	Article createArticleByEmail(String email);

	List<Article> getAllArticlesByEmailId(String email);
	List<Article> getAllApprovedArticlesByEmailId(String email);
	List<Article> getAllBetaArticlesByEmailId(String email);
	List<Article> getAllInitialArticlesByEmailId(String email);
	List<Article> getAllRejectedArticlesByEmailId(String email);
	List<Article> getAllDiscardedArticlesByEmailId(String email);

	Article findById(String id);

	List<Article> getApprovedArticles();
	List<Article> getBetaArticles();

	Article submitArticle(ObjectId id);
	Article approveArticle(ObjectId id);
	Article rejectArticle(ObjectId id);

	Article saveArticle(String etherPadId);

	String getEtherPadUrl(String id);
}
