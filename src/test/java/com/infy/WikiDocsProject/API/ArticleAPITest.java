package com.infy.WikiDocsProject.API;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.infy.WikiDocsProject.Configuration.JwtTokenProvider;
import com.infy.WikiDocsProject.Configuration.WebSecurityConfig;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.Role;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Repository.ArticleRepository;
import com.infy.WikiDocsProject.Repository.RoleRepository;
import com.infy.WikiDocsProject.Repository.UserRepository;
import com.infy.WikiDocsProject.Utility.TestDataCreator;
import com.infy.WikiDocsProject.WikiDocsProjectApplication;
import com.infy.WikiDocsProject.enums.Status;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT, classes = WikiDocsProjectApplication.class)
@ContextConfiguration(classes = WebSecurityConfig.class)
@PropertySource(value={"classpath:application.properties"})
public class ArticleAPITest {

    @Autowired
    WebApplicationContext context;

    MockMvc mockMvc;

    /**
     * We autowire the repositories because we must populate the data.
     */
    @Autowired UserRepository userRepository;
    @Autowired ArticleRepository articleRepository;
    @Autowired RoleRepository roleRepository;

    /**
     * We autowire jwtTokenProvider because we need authentication tokens
     * to securely access the endpoints and verify that they are indeed secured
     */
    @Autowired JwtTokenProvider jwtTokenProvider;

    /**
     * Test data
     */
    List<User> users;
    List<User> admins;
    Map<Status, Article> articles;
    Map<String, Role> roles;
    Map<User, String> tokens;
    String pageNumberAndPageSize;
    //String email;

    /**
     * We set up the mockMvc here in order to
     * apply Spring Security to our requests.
     * Otherwise, our requests will be tested without security
     */
    @Before
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity()) // We apply spring security here
                .build();
    }

    /**
     * We populate test data before each method
     * but avoid repopulating if database already contains test data.
     */
    @Before
    public void populateTestData(){

        roleRepository.deleteAll();
        userRepository.deleteAll();
        articleRepository.deleteAll();

        users = TestDataCreator.createUsersWithRole("user");
        admins = TestDataCreator.createUsersWithRole("admin");
        articles = TestDataCreator.createArticles(users.get(0).getEmail());
        roles = TestDataCreator.createRoles();
        tokens = new HashMap<>();
        pageNumberAndPageSize = "0/5";
        //email = "john@gmail.com";

        roleRepository.insert(roles.get("USER"));
        roleRepository.insert(roles.get("ADMIN"));

        //Stream a combined list of Users and Admins
        //Add each person to the User repository
        //Add each article belonging to that person to the article repository
        Stream.concat(users.stream(), admins.stream()).forEach(person ->{
            userRepository.insert(person);
            person.getArticles().forEach(article -> articleRepository.insert(article));
        });

        //Stream a combined list of Users and Admins
        //Create tokens for each person
        //Add their tokens to tokens map
        Stream.concat(users.stream(), admins.stream()).forEach(person ->{
            String token = "Bearer " + jwtTokenProvider.createToken(person.getEmail(), person.getRoles());
            tokens.put(person, token);
        });

        //For each Key Value pair in the articles Map,
        //insert the value into the database
        articles.forEach((key, value) -> {
            articleRepository.insert(value);
        });

    }

    @Test
    public void testGetAllApprovedArticles_User() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllApprovedArticles/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(users.get(0))))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllApprovedArticles_Admin() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllApprovedArticles/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(admins.get(0))))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetAllApprovedArticles_Unauthorized() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllApprovedArticles/"+pageNumberAndPageSize)
                        .header("Authorization", "") // No token provided in the Authorization header
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized());
    }

    @Test
    public void testGetAllBetaArticles_Admin() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllBetaArticles/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(admins.get(0))))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testGetAllArticlesByEmail_User() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllArticlesByEmail/"+users.get(0).getEmail()+"/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(users.get(0))))
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void testGetAllApprovedArticlesByEmail_User() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllApprovedArticlesByEmail/"+users.get(0).getEmail()+"/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(users.get(0))))
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void testGetAllBetaArticlesByEmail_User() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllBetaArticlesByEmail/"+users.get(0).getEmail()+"/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(users.get(0))))
                .andExpect(status().isOk());
    }
   
    
    @Test
    public void testGetAllInitialArticlesByEmail_User() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllInitialArticlesByEmail/"+users.get(0).getEmail()+"/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(users.get(0))))
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void testGetAllRejectedArticlesByEmail_User() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllRejectedArticlesByEmail/"+users.get(0).getEmail()+"/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(users.get(0))))
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void testGetAllDiscardedArticlesByEmail_User() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllDiscardedArticlesByEmail/"+users.get(0).getEmail()+"/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(users.get(0))))
                .andExpect(status().isOk());
    }
    
    
    @Test
    public void testGetAllInvitedArticlesByEmail_User() throws Exception {
        this.mockMvc.perform(
                get("/ArticleAPI/getAllInvitedArticlesByEmail/"+users.get(1).getEmail()+"/"+pageNumberAndPageSize)
                        .header("Authorization", tokens.get(users.get(1))))
                .andExpect(status().isOk());
    }
  
    
    @Test
    public void testApproveArticle() throws Exception {
        String articleId = articles.get(Status.BETA).getId().toHexString();
        this.mockMvc.perform(get("/ArticleAPI/approveArticle/"+articleId)
                .header("Authorization", tokens.get(admins.get(0))))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testSubmitArticleForApproval() throws Exception {
        String articleId = articles.get(Status.INITIAL).getId().toHexString();
        this.mockMvc.perform(post("/ArticleAPI/submitArticleForApproval/")
                .header("Authorization", tokens.get(users.get(0)))
                .characterEncoding("utf-8")
                .contentType(MediaType.APPLICATION_JSON)
                .content(articleId))
                .andExpect(status().isOk());
    }
    
    @Test
    public void testSaveArticle() throws Exception{
    	this.mockMvc.perform(
                get("/ArticleAPI/saveArticle/")
                        .header("Authorization", tokens.get(users.get(0))))
                .andExpect(status().isOk());
    }

    public static String asJsonString(final Object obj) throws JsonProcessingException {
        final ObjectMapper mapper = new ObjectMapper();
        final String json = mapper.writeValueAsString(obj);
        return json;
    }
}