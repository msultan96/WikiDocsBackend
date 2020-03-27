package com.infy.WikiDocsProject.Service;

import java.util.List;
import java.util.Map;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.enums.Status;

public interface ArticleService {

	Article findById(String id);
	Article createArticleByEmail(String email, String articleName);

	List<Article> getAllArticlesByEmailId(String email, int pageNumber, int pageSize);

	List<Article> getAllArticlesByStatus(Status status, int pageNumber, int pageSize);

	List<Article> getAllArticlesByEmailIdAndStatus(String email, Status status, int pageNumber, int pageSize);

	Article submitArticle(String id);
	Article approveArticle(String id);
	Article rejectArticle(String id);

	Article saveArticle(String etherPadId);

	String getEtherPadUrl(String id);

	List<Article> getAllInvitedArticlesByEmail(String email, int pageNumber, int pageSize);
	String inviteUserToCollaborateByEmail(Map<String, String> map);

}
