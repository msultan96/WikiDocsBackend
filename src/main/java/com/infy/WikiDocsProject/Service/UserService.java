package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import reactor.core.publisher.Mono;

public interface UserService {

	User login();

	Article createArticle();
}
