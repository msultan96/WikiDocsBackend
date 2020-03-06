package com.infy.WikiDocsProject;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Service.ArticleService;
import com.infy.WikiDocsProject.Service.ArticleServiceImpl;
import com.infy.WikiDocsProject.Service.UserService;
import com.infy.WikiDocsProject.Service.UserServiceImpl;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ServiceTest {
	
	@Mock
	private ArticleRepository articleRepository;
	
	@Mock
	private UserRepository userRepository;
	
	@InjectMocks
	private UserService userService = new UserServiceImpl(userRepository, articleRepository);
	
	@InjectMocks
	private ArticleService articleService = new ArticleServiceImpl(userService, articleRepository);
	
	@Rule
	public ExpectedException expectedException = ExpectedException.none();

	
	@Test
	public void findUserByNameInvalid() throws Exception {
		String name = "da@#$";
		Mockito.when(userRepository.findUserByName(Mockito.anyString())).thenReturn(null);
		expectedException.expect(Exception.class);
		expectedException.expectMessage("");
		userService.findUserByName(name);
		
	}
	@Test
	public void createArticleByUserInvalid() throws Exception {
		String name = "Tom";
		String channelId = "123423#";
		Mockito.when(userRepository.findUserByName(Mockito.anyString())).thenReturn(null);
		expectedException.expect(Exception.class);
		expectedException.expectMessage("");
		userService.createArticleByUser(name, channelId);
		
	}
	
	@Test
	public void getArticleByChannelIdInvalid() throws Exception {
		String channelId = "123423#";
		Mockito.when(articleRepository.findArticleByChannelId(Mockito.anyString())).thenReturn(null);
		expectedException.expect(Exception.class);
		expectedException.expectMessage("");
		articleService.getArticleByChannelId(channelId);
	}
	
	@Test
	public void submitArticleForApproval() throws Exception {
		String channelId = "123$%$";
		Mockito.when(articleRepository.findArticleByChannelId(Mockito.anyString())).thenReturn(null);
		expectedException.expect(Exception.class);
		expectedException.expectMessage("");
		articleService.submitArticleForApproval(channelId);
	}
	
	@Test
	public void approveArticleInvalid() throws Exception{
		String channelId = "123$*%";
		Mockito.when(articleRepository.findArticleByChannelId(Mockito.anyString())).thenReturn(null);
		expectedException.expect(Exception.class);
		expectedException.expectMessage("");
		articleService.approveArticle(channelId);
	}
	
	@Test
	public void rejectArticleInvalid()throws Exception{
		String channelId = "34536$*%";
		Mockito.when(articleRepository.findArticleByChannelId(Mockito.anyString())).thenReturn(null);
		expectedException.expect(Exception.class);
		expectedException.expectMessage("");
		articleService.rejectArticle(channelId);
	}
}
