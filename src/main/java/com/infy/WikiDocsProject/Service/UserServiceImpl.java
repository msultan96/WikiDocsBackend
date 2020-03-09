package com.infy.WikiDocsProject.Service;

import com.infy.WikiDocsProject.Exception.UserNotFoundException;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;

import com.infy.WikiDocsProject.Utility.ArticleBuilder;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service(value="userService")
public class UserServiceImpl implements UserService {

	private final UserRepository userRepository;

	private final ArticleRepository articleRepository;

	@Autowired
	public UserServiceImpl(UserRepository userRepository, ArticleRepository articleRepository) {
		this.userRepository = userRepository;
		this.articleRepository = articleRepository;
	}

	public User findUserByName(String name) throws Exception{
		Optional<User> optionalUser = userRepository.findUserByName(name);
		if(optionalUser.isPresent()){
			return optionalUser.get();
		}
		else{
			throw new UserNotFoundException();
		}
	}
	
	public Article createArticleByUser(String name, String channelId) throws Exception{
		User user;
		List<Article> articles;

		try {
			user = findUserByName(name);
			articles = articleRepository.findAllArticlesByUserId(user.getId());

			Article newArticle = new ArticleBuilder()
					.id(new ObjectId())
					.userId(user.getId())
					.status(Status.INITIAL)
					.channelId(channelId)
					.editable(true)
					.build();

			articles.add(newArticle);

			user.setArticles(articles);
			articleRepository.save(newArticle);
			userRepository.save(user);
			return newArticle;
		}
		catch(UserNotFoundException e){
			throw new UserNotFoundException();
		}
	}
}
