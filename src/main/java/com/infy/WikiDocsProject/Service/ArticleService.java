package com.infy.WikiDocsProject.Service;

import java.util.List;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;

public interface ArticleService {

	List<Article> getApprovedAndBetaArticles();
	Article getArticleByChannelId(String channelId);

	void submitArticleForApproval(String channelId);
	void approveArticle(String channelId);
	void rejectArticle(String channelId);

}
