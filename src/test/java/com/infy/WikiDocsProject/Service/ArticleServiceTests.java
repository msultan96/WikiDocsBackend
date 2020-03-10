package com.infy.WikiDocsProject.Service;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;
import com.infy.WikiDocsProject.Exception.*;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Utility.ArticleBuilder;
import com.infy.WikiDocsProject.Utility.UserBuilder;
import com.infy.WikiDocsProject.enums.Role;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
/**
 * 
 * Article Service Tests Class
 *
 */
@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ArticleServiceTests {

	// Mock repository and service objects declared
    @Mock
    ArticleRepository articleRepository;

    @Mock
    UserService userService;

    // Inject mock services
    @InjectMocks
    ArticleServiceImpl articleService;

    // Set Expected Exception rule
    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    /**
     * @Test
     * @Name testGetAllArticlesByUser
     * @Desciption Test get all articles of given user to be valid
     * @throws Exception
     */
    @Test
    public void testGetAllArticlesByUser() throws Exception{
    	// Auto generate new user using UserBuilder class
        Optional<User> expectedOptionalUser = Optional.of(
        		// create user with id, name, email,articles,role using builder design pattern
        		new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<Article>())
                .role(Role.USER)
                .build());
        // get all articles form new user and set it to a list of expectedArticles
        List<Article> expectedArticles = expectedOptionalUser.get().getArticles();

        // when findUserByName() is called with any string param then return expectedOptionalUser
        when(userService.findUserByName(anyString()))
                .thenReturn(expectedOptionalUser.get());
        // when findAllArticlesByUserId() is called with any param then return expectedArticles
        when(articleRepository.findAllArticlesByUserId(any()))
                .thenReturn(expectedArticles);

        // Actual call to getAllArticlesByUser() from articleService class give name = ""John"
        // receive back an actual list of articles
        List<Article> actualArticles = articleService.getAllArticlesByUser("John");
        // compare eexpectedArticles and actualArticles
        assertEquals(expectedArticles, actualArticles);
    }

    /**
     * @Test
     * @Name testGetAllArticlesByUser_UserNotFound
     * @Desciption Test get all articles of given user to be invalid
     * @throws Exception
     */
    @Test(expected = UserNotFoundException.class)
    public void testGetAllArticlesByUser_UserNotFound() throws Exception{
    	// when findUserByName() is called with any string param from userService
    	// then throw UserNotFoundException()
        when(userService.findUserByName(anyString()))
                .thenThrow(new UserNotFoundException());
        // Actual call to getAllArticlesByUser() from articleService class give name = ""John"
        articleService.getAllArticlesByUser("John");
    }

    /**
     * @Test
     * @Name testGetApprovedArticles
     * @Desciption Test get all approved articles to be valid
     * @throws Exception
     */
    @Test
    public void testGetApprovedArticles(){
    	// create new list of article named expectedArticles
        List<Article> expectedArticles = new ArrayList<>();
        // Set expectedArticles status to APPROVED
        expectedArticles.add(new ArticleBuilder().status(Status.APPROVED).build());

        // when findArticlesByStatus() is called from articleRepository class given any param
        // return expectedArticles
        when(articleRepository.findArticlesByStatus(any()))
                .thenReturn(expectedArticles);
        // Actual call to getApprovedArticles() from articleService class
        // Receive a list of actualArticles
        List<Article> actualArticles = articleService.getApprovedArticles();
        // compare expectedArticles and actualArticles
        assertEquals(expectedArticles, actualArticles);
    }

    /**
     * @Test
     * @Name testGetBetaArticles
     * @Desciption Test get all beta articles to be valid
     * @throws Exception
     */
    @Test
    public void testGetBetaArticles(){
    	// create new list of article named expectedArticles
        List<Article> expectedArticles = new ArrayList<>();
        // Set expectedArticles status to BETA
        expectedArticles.add(new ArticleBuilder().status(Status.BETA).build());
        // when findArticlesByStatus() is called from articleRepository class given any param
        // return expectedArticles
        when(articleRepository.findArticlesByStatus(any()))
                .thenReturn(expectedArticles);
        // Actual call to getBetaArticles() from articleService class
        // Receive a list of actualArticles
        List<Article> actualArticles = articleService.getBetaArticles();
        // compare expectedArticles and actualArticles
        assertEquals(expectedArticles, actualArticles);

    }

    /**
     * @Test
     * @Name testGetArticleByChannelId
     * @Desciption Test get all articles by channelId to be valid
     * @throws Exception
     */
    @Test
    public void testGetArticleByChannelId() throws Exception{
    	// Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(
        		// create user with id, name, email,articles,role using builder design pattern
        		new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call to getArticleByChannelId() from articleService class with given param "testArticle"
        // receive back actualArticle
        Article actualArticle = articleService.getArticleByChannelId("testArticle");
        // compare expectedArticle and actualArticle
        assertEquals(expectedArticle.get(), actualArticle);
    }

    /**
     * @Test
     * @Name testGetArticleByChannelId_ArticleNotFound
     * @Desciption Test get all articles by channelId to be invalid
     * @throws Exception
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testGetArticleByChannelId_ArticleNotFound() throws Exception{
    	// set expectedArticle to be empty
        Optional<Article> expectedArticle = Optional.empty();

        // when findArticleByChannelId() is called from articleRepository class with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId((anyString())))
                .thenReturn(expectedArticle);
        // actual call getArticleByChannelId() with "Invalid" param from articleService class
        articleService.getArticleByChannelId("Invalid");
    }

    /**
     * @Test
     * @Name testSubmitArticle_InitialArticle
     * @Desciption Test submit and initial article to be valid
     * @throws Exception
     */
    @Test
    public void testSubmitArticle_InitialArticle() throws Exception{
    	// Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(
        		// create user with id, name, email,articles,role using builder design pattern
        		new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                //set status to INITIAL
                .status(Status.INITIAL)
                .editable(true)
                .build());
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        //actual call to submitArticle() from articleService with "Invalid" param
        Article actualArticle = articleService.submitArticle("Invalid");
        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testSubmitArticle_BetaArticle
     * @Desciption Test submit and beta article to be valid
     * @throws Exception
     */
    @Test
    public void testSubmitArticle_BetaArticle() throws Exception{
    	// Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(
        		// create user with id, name, email,articles,role using builder design pattern
        		new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
              //set status to INITIAL
                .status(Status.INITIAL)
                .editable(true)
                .build());

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle("testArticle");
     // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testSubmitArticle_RejectedArticle
     * @Desciption Test submit and rejected article to be valid
     * @throws Exception
     */
    @Test
    public void testSubmitArticle_RejectedArticle() throws Exception{
    	// Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
              //set status to REJECTED
                .status(Status.REJECTED)
                .editable(true)
                .build());
        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param
        Article actualArticle = articleService.submitArticle("testArticle");
        // compare Status.BETA and actualArticle.getStatus()
        assertEquals(Status.BETA, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testSubmitArticle_ArticleApproved
     * @Desciption Test submit and approved article to be valid
     * @throws Exception
     */
    @Test(expected = SubmittingArticleIsApprovedException.class)
    public void testSubmitArticle_ArticleApproved() throws Exception{
    	// Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
              //set status to APPROVED
                .status(Status.APPROVED)
                .editable(true)
                .build());
     	// when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param	
        Article actualArticle = articleService.submitArticle("Invalid");
     // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testSubmitArticle_ArticleDiscarded
     * @Desciption Test submit and discarded article to be valid
     * @throws Exception
     */
    @Test(expected = SubmittingArticleIsDiscardedException.class)
    public void testSubmitArticle_ArticleDiscarded() throws Exception{
    	// Auto generate new user using UserBuilder class
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                //set status to DISCARDED
                .status(Status.DISCARDED)
                .editable(true)
                .build());

        // when findArticleByChannelId() is called from articleRepository with any string param
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        //actual call to submitArticle() from articleService with "testArticle" param		
        Article actualArticle = articleService.submitArticle("Invalid");
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testSubmitArticle_ArticleNotFound
     * @Desciption Test submit article to be invalid
     * @throws Exception
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testSubmitArticle_ArticleNotFound() throws Exception{
    	// Create expectedArticle set it to empty
        Optional<Article> expectedArticle = Optional.empty();
        // when findArticleByChannelId() with any string param from articleRepository
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual submitArticle() with "Invalid" param from articleService
        articleService.submitArticle("Invalid");
    }

    /**
     * @Test
     * @Name testApproveArticle_BetaArticle
     * @Desciption Test approved-beta article to be valid
     * @throws Exception
     */
    @Test
    public void testApproveArticle_BetaArticle() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
        		// set attributes
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.BETA)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
        // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
  }

    /**
     * @Test
     * @Name testApproveArticle_RejectedArticle
     * @Desciption Test approved-reject article to be valid
     * @throws Exception
     */
    @Test(expected = ApprovingArticleIsStillRejectedException.class)
    public void testApproveArticle_RejectedArticle() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
        		// set attributes
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.REJECTED)
                .editable(true)
                .build());
        
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
        // compare Status.REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @NametestApproveArticle_InitialArticle
     * @Desciption Test approved-initial article to be valid
     * @throws Exception
     */
    @Test(expected = ApprovingArticleIsInitialException.class)
    public void testApproveArticle_InitialArticle() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
        		// set attributes
                .id(new ObjectId()  )
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
        // compare Status.INITIALand actualArticle.getStatus()
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testApproveArticle_ApprovedArticle
     * @Desciption Test approved-approved article to be valid
     * @throws Exception
     */
    @Test(expected = ApprovingArticleIsApprovedException.class)
    public void testApproveArticle_ApprovedArticle() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.APPROVED)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
     // compare Status.APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testApproveArticle_DiscardedArticle
     * @Desciption Test approved-discarded article to be valid
     * @throws Exception
     */
    @Test(expected = ApprovingArticleIsDiscardedException.class)
    public void testApproveArticle_DiscardedArticle() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.DISCARDED)
                .editable(true)
                .build());
    	// when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call approveArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.approveArticle("testArticle");
     // compare Status.DISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testApproveArticle_ArticleNotFound
     * @Desciption Test approved article article not found to be invalid
     * @throws Exception
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testApproveArticle_ArticleNotFound() throws Exception{
    	// create expectedArticle set it to empty
        Optional<Article> expectedArticle = Optional.empty();
        // when findArticleByChannelId() is called with any string param from articleRepository class
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call to approveArticle() with "Invalid" param from articleService
        articleService.approveArticle("Invalid");
    }

    
    /**
     * @Test
     * @Name testRejectArticle_BetaArticle
     * @Desciption Test rejected article and beta article to be valid
     * @throws Exception
     */
    @Test
    public void testRejectArticle_BetaArticle() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.BETA)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
        // compare Status REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    
    /**
     * @Test
     * @Name testRejectArticle_BetaArticle
     * @Desciption Test rejected article and beta article becomes discarded to be valid
     * @throws Exception
     */
    @Test
    public void testRejectArticle_BetaArticleBecomesDiscarded() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
        		// set attributes
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(3)
                .status(Status.BETA)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
     // compare Status DISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testRejectArticle_InitialArticle
     * @Desciption Test rejected article and initial article to be valid
     * @throws Exception
     */
    @Test(expected = RejectingArticleIsInitialException.class)
    public void testRejectArticle_InitialArticle() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());
        // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
     // compare Status INITIAL and actualArticle.getStatus()
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testRejectArticle_ApprovedArticle
     * @Desciption Test rejected article and approved article to be valid
     * @throws Exception
     */
    @Test(expected = RejectingArticleIsApprovedException.class)
    public void testRejectArticle_ApprovedArticle() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.APPROVED)
                .editable(true)
                .build());

     // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
     // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
     // compare Status APPROVED and actualArticle.getStatus()
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testRejectArticle_RejectedArticle
     * @Desciption Test rejected article and rejected article to be valid
     * @throws Exception
     */
    @Test(expected = RejectingArticleIsStillRejectedException.class)
    public void testRejectArticle_RejectedArticle() throws Exception{
    	// Generate expectedArticle using ArticleBuilder()
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(2)
                .status(Status.REJECTED)
                .editable(true)
                .build());
     // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
     // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
     // compare Status REJECTED and actualArticle.getStatus()
        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }
    /**
     * @Test
     * @Name testRejectArticle_RejectedArticle
     * @Desciption Test rejected article and discarded article to be valid
     * @throws Exception
     */
    @Test(expected = RejectingArticleIsDiscardedException.class)
    public void testRejectArticle_DiscardedArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.DISCARDED)
                .editable(true)
                .build());
     // when findArticleByChannelId() with any string param from articleRepository
        // return expectedArticle 
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
     // actual call rejectArticle() with "testArticle" param from articleService
        // return actualArticle
        Article actualArticle = articleService.rejectArticle("testArticle");
     // compare Status RDISCARDED and actualArticle.getStatus()
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    /**
     * @Test
     * @Name testRejectArticle_ArticleNotFound
     * @Desciption Test rejected article article not found to be invalid
     * @throws Exception
     */
    @Test(expected = ArticleNotFoundException.class)
    public void testRejectArticle_ArticleNotFound() throws Exception{
    	//create  expectedArticle set it to empty
        Optional<Article> expectedArticle = Optional.empty();
        // when findArticleByChannelId() is called with any string param from articleRepository
        // then return expectedArticle
        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);
        // actual call rejectArticle() from articleService with "Invalid" param
        articleService.rejectArticle("Invalid");
    }
}
