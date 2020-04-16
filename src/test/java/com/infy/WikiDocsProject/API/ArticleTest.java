package com.infy.WikiDocsProject.Model;

import static org.junit.Assert.assertEquals;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;

import com.infy.WikiDocsProject.Utility.TestDataCreator;
import com.infy.WikiDocsProject.enums.Status;

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ArticleTest {
	
	/**
     * Test Data
     */
    private List<User> users;
    private List<User> admins;
    private List<Optional<User>> optionalUsers;
    private Map<Status, Article> articles;
    private List<Optional<Article>> optionalArticles;
    private Page<Article> page;
    private Map<Status, Page<Article>> pages;
    private ObjectId objectId;
    private String stringId;
    private Map<String, Role> roles;

    /**
     * Populates test data.
     * Should change data structure to Map.
     * Special note for articles, optionalArticles, and pages:
     *
     * Articles.get(x) | Status(x)
     *      0            APPROVED
     *      1            BETA
     *      2            INITIAL
     *      3            REJECTED
     *      4            DISCARDED
     * @see TestDataCreator Class
     */
    @Before
    public void populateTestData(){
        users = TestDataCreator.createUsersWithRole("user");
        admins = TestDataCreator.createUsersWithRole("admin");

        articles = TestDataCreator.createArticles("john@gmail.com");
        pages = TestDataCreator.createPages();

        optionalUsers = TestDataCreator.createOptionalUsers();
        optionalArticles = TestDataCreator.createOptionalArticles();

        page = pages.get(Status.APPROVED);

        objectId = TestDataCreator.createObjectId();
        stringId = objectId.toHexString();

        roles = TestDataCreator.createRoles();
    }
	
	@Test
	public void testGetEmailId() {

		Optional<Article> article = optionalArticles.get(0);
		String expectedArticleEmail = "john@gmail.com";
		
		//when
		String actualArticleEmail = article.get().getEmailId();
		
		//then
		assertEquals(expectedArticleEmail, actualArticleEmail);
		
	}
	
	@Test
	public void testSetEmailId() {
		//given
//		Article article = Article.builder()
//				.id(new ObjectId())
//				.emailId("email@email.com")
//				.name("New Article")
//				.content("")
//				.status(Status.INITIAL)
//				.rejectedCount(0)
//				.readOnly(true)
//				.build();
		
		Optional<Article> article = optionalArticles.get(0);
		String expectedEmail = "newEmail@gmail.com";
		
		//when
		article.get().setEmailId("newEmail@gmail.com");
		
		//then
		assertEquals(expectedEmail, article.get().getEmailId());
	}
	
	@Test
	public void testGetName() {
		Optional<Article> article = optionalArticles.get(0);
		String expectedName = "john";
		
		//when
		String actualArticleEmail = article.get().getName();
		
		//then
		assertEquals(expectedName, actualArticleEmail);
	}
	
	@Test
	public void testSetName() {
		Optional<Article> article = optionalArticles.get(0);
		String expectedName = "Johnathan";
		
		article.get().setName("Jahnathan");
		
		assertEquals(expectedName, article.get().getName());
		
	}
	
	@Test
	public void testSetID() {
		Optional<Article> article = optionalArticles.get(0);
		ObjectId expectedID = objectId;
		
		article.get().setId(objectId);
		assertEquals(expectedID, article.get().getId());
	}
	
	@Test
	public void testGetID() {
		Optional<Article> article = optionalArticles.get(0);
		ObjectId expectedId = objectId;
		
		//when
		ObjectId actualId = article.get().getId();
		
		//then
		assertEquals(expectedId, actualId);
	}

	@Test
	public void testGetStatus() {
		Optional<Article> article = optionalArticles.get(0);
		Status expectedStatus = Status.APPROVED;
		
		//when
		Status actualStatus = article.get().getStatus();
		
		//then
		assertEquals(expectedStatus, actualStatus);
	}
	
	@Test
	public void testSetStatus() {
		Optional<Article> article = optionalArticles.get(0);
		Status expectedStatus = Status.BETA;
		
		article.get().setStatus(Status.BETA);;
		assertEquals(expectedStatus, article.get().getStatus());
	}
	
	@Test
	public void testGetRejectedCount() {
		Optional<Article> article = optionalArticles.get(0);
		int expectedCount = 0;
		
		//when
		int actualCount = article.get().getRejectedCount();
		
		//then
		assertEquals(expectedCount, actualCount);
	}
	
	@Test
	public void testSetRejectedCount() {
		Optional<Article> article = optionalArticles.get(0);
		int expectedCount = 1;
		
		article.get().setRejectedCount(1);
		assertEquals(expectedCount, article.get().getRejectedCount());
		
		
	}
	
	@Test
	public void testGetRead() {
		Optional<Article> article = optionalArticles.get(0);
		boolean expectedRead= true;
		
		//when
		boolean actualRead = article.get().isReadOnly();
		
		//then
		assertEquals(expectedRead, actualRead);
	}
	
	@Test
	public void testSetRead() {
		Optional<Article> article = optionalArticles.get(0);
		boolean expectedRead= true;
		
		article.get().setReadOnly(true);
		assertEquals(expectedRead, article.get().isReadOnly());
	}
	
	@Test
    public void testGetContent() {
		Optional<Article> article = optionalArticles.get(0);
		String expectedContent = stringId;
		
		String actualContent = article.get().getContent();
		
		assertEquals(expectedContent, actualContent);
    }
    
    @Test
    public void testSetContent() {
    	Optional<Article> article = optionalArticles.get(0);
		String expectedContent = stringId;
		
		article.get().setContent(stringId);;
		assertEquals(expectedContent, article.get().getContent());
    }
}

	
	