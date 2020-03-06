package com.infy.WikiDocsProject.Repository;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import org.springframework.data.annotation.QueryAnnotation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends MongoRepository<User, String> {

	 User findUserByName(String name);

//	 @Query("{ '$ref : articles, $id : _id, $db : user' }")
//	 List<Article> getArticlesByUser();
}
