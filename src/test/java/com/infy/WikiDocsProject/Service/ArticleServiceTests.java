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

@RunWith(MockitoJUnitRunner.class)
@SpringBootTest
public class ArticleServiceTests {

    @Mock
    ArticleRepository articleRepository;

    @Mock
    UserService userService;

    @InjectMocks
    ArticleServiceImpl articleService;

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void testGetAllArticlesByUser() throws Exception{
        Optional<User> expectedOptionalUser = Optional.of(new UserBuilder()
                .id(new ObjectId())
                .name("John")
                .email("John@gmail.com")
                .articles(new ArrayList<Article>())
                .role(Role.USER)
                .build());
        List<Article> expectedArticles = expectedOptionalUser.get().getArticles();

        when(userService.findUserByName(anyString()))
                .thenReturn(expectedOptionalUser.get());

        when(articleRepository.findAllArticlesByUserId(any()))
                .thenReturn(expectedArticles);

        List<Article> actualArticles = articleService.getAllArticlesByUser("John");

        assertEquals(expectedArticles, actualArticles);
    }

    @Test(expected = UserNotFoundException.class)
    public void testGetAllArticlesByUser_UserNotFound() throws Exception{

        when(userService.findUserByName(anyString()))
                .thenThrow(new UserNotFoundException());

        articleService.getAllArticlesByUser("John");
    }

    @Test
    public void testGetApprovedArticles(){
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new ArticleBuilder().status(Status.APPROVED).build());

        when(articleRepository.findArticlesByStatus(any()))
                .thenReturn(expectedArticles);

        List<Article> actualArticles = articleService.getApprovedArticles();

        assertEquals(expectedArticles, actualArticles);
    }

    @Test
    public void testGetBetaArticles(){
        List<Article> expectedArticles = new ArrayList<>();
        expectedArticles.add(new ArticleBuilder().status(Status.BETA).build());

        when(articleRepository.findArticlesByStatus(any()))
                .thenReturn(expectedArticles);

        List<Article> actualArticles = articleService.getBetaArticles();

        assertEquals(expectedArticles, actualArticles);

    }

    @Test
    public void testGetArticleByChannelId() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.getArticleByChannelId("testArticle");

        assertEquals(expectedArticle.get(), actualArticle);
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testGetArticleByChannelId_ArticleNotFound() throws Exception{
        Optional<Article> expectedArticle = Optional.empty();

        when(articleRepository.findArticleByChannelId((anyString())))
                .thenReturn(expectedArticle);

        articleService.getArticleByChannelId("Invalid");
    }

    @Test
    public void testSubmitArticle_InitialArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.submitArticle("Invalid");
        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    @Test
    public void testSubmitArticle_BetaArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.submitArticle("testArticle");

        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    @Test
    public void testSubmitArticle_RejectedArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.REJECTED)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.submitArticle("testArticle");

        assertEquals(Status.BETA, actualArticle.getStatus());
    }

    @Test(expected = SubmittingArticleIsApprovedException.class)
    public void testSubmitArticle_ArticleApproved() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.APPROVED)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.submitArticle("Invalid");
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    @Test(expected = SubmittingArticleIsDiscardedException.class)
    public void testSubmitArticle_ArticleDiscarded() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.DISCARDED)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.submitArticle("Invalid");
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testSubmitArticle_ArticleNotFound() throws Exception{
        Optional<Article> expectedArticle = Optional.empty();

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        articleService.submitArticle("Invalid");
    }

    @Test
    public void testApproveArticle_BetaArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.BETA)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.approveArticle("testArticle");
        assertEquals(Status.APPROVED, actualArticle.getStatus());
  }

    @Test(expected = ApprovingArticleIsStillRejectedException.class)
    public void testApproveArticle_RejectedArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.REJECTED)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.approveArticle("testArticle");

        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    @Test(expected = ApprovingArticleIsInitialException.class)
    public void testApproveArticle_InitialArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.approveArticle("testArticle");
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    @Test(expected = ApprovingArticleIsApprovedException.class)
    public void testApproveArticle_ApprovedArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.APPROVED)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.approveArticle("testArticle");
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    @Test(expected = ApprovingArticleIsDiscardedException.class)
    public void testApproveArticle_DiscardedArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.DISCARDED)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.approveArticle("testArticle");
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testApproveArticle_ArticleNotFound() throws Exception{
        Optional<Article> expectedArticle = Optional.empty();

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        articleService.approveArticle("Invalid");
    }

    @Test
    public void testRejectArticle_BetaArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.BETA)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.rejectArticle("testArticle");

        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

    @Test
    public void testRejectArticle_BetaArticleBecomesDiscarded() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(3)
                .status(Status.BETA)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.rejectArticle("testArticle");

        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    @Test(expected = RejectingArticleIsInitialException.class)
    public void testRejectArticle_InitialArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.INITIAL)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.rejectArticle("testArticle");
        assertEquals(Status.INITIAL, actualArticle.getStatus());
    }

    @Test(expected = RejectingArticleIsApprovedException.class)
    public void testRejectArticle_ApprovedArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId()  )
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(0)
                .status(Status.APPROVED)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.rejectArticle("testArticle");
        assertEquals(Status.APPROVED, actualArticle.getStatus());
    }

    @Test(expected = RejectingArticleIsStillRejectedException.class)
    public void testRejectArticle_RejectedArticle() throws Exception{
        Optional<Article> expectedArticle = Optional.of(new ArticleBuilder()
                .id(new ObjectId())
                .userId(new ObjectId())
                .name("Article")
                .channelId("testArticle")
                .rejectedCount(2)
                .status(Status.REJECTED)
                .editable(true)
                .build());

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.rejectArticle("testArticle");

        assertEquals(Status.REJECTED, actualArticle.getStatus());
    }

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

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        Article actualArticle = articleService.rejectArticle("testArticle");
        assertEquals(Status.DISCARDED, actualArticle.getStatus());
    }

    @Test(expected = ArticleNotFoundException.class)
    public void testRejectArticle_ArticleNotFound() throws Exception{
        Optional<Article> expectedArticle = Optional.empty();

        when(articleRepository.findArticleByChannelId(anyString()))
                .thenReturn(expectedArticle);

        articleService.rejectArticle("Invalid");
    }
}
