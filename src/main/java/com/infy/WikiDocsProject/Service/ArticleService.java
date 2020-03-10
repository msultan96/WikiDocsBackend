package com.infy.WikiDocsProject.Service;

import java.util.List;
import java.util.Optional;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
/**
 * Article Service Class 
 * Headers
 */
public interface ArticleService {
	
	//Method declarations
	List<Article> getAllArticlesByUser(String name) throws Exception;
	List<Article> getApprovedArticles();
	List<Article> getBetaArticles();
	Article getArticleByChannelId(String channelId) throws Exception;

	Article submitArticle(String channelId) throws Exception;
	Article approveArticle(String channelId) throws Exception;
	Article rejectArticle(String channelId) throws Exception;

}
