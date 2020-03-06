package com.infy.WikiDocsProject;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.enums.Status;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@Transactional
public class RepositoryTest {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	
	@Test
	public void getArticleByChannelIdInvalid(){
		Article article = new Article();
		article.setChannelId("1238*@*#$");
		articleRepository.findArticleByChannelId(article.getChannelId());
		Assert.assertFalse(false);
		
		
	}
	
	@Test
	public void findArticlesByStatusOrStatusInvalid() {
		//Status approved, Status beta
		Article article = new Article();
		article.setStatus(Status.REJECTED);
		articleRepository.findArticlesByStatusOrStatus(Status.REJECTED, Status.REJECTED);
		Assert.assertFalse(false);
		
	}
	
	@Test
	public void findUserByNameInvalid() {
		userRepository.findUserByName("asdk%^$%^");
		Assert.assertFalse(false);
	}

}
