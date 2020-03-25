package com.infy.WikiDocsProject.Utility;

import com.devskiller.jfairy.Fairy;
import com.devskiller.jfairy.producer.BaseProducer;
import com.devskiller.jfairy.producer.person.Person;
import com.devskiller.jfairy.producer.text.TextProducer;
import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.Model.Role;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.*;

public class TestDataCreator {

    private static final Fairy fairy = Fairy.create();
    private static final BaseProducer baseProducer = fairy.baseProducer();
    private static final TextProducer textProducer = fairy.textProducer();

    private static final BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

    /**
     * Creates 5 users with specific role using jFairy.
     * @return the list of users generated
     */
    public static List<User> createUsersWithRole(String role) {
        role = role.toUpperCase();
        List<User> users = new ArrayList<>();
        Person person;
        Role userRole = Role.builder().id(role).role(role).build();
        for (int i = 0; i <= 5; i++) {
            person = fairy.person();
            Map<Status, Article> articleMap = createArticles(person.getEmail());
            List<Article> articles = new ArrayList<>(articleMap.values());

            List<Article> collaboratingArticles = new ArrayList<>(createArticlesWithStatus("john@gmail.com", Status.INITIAL));
            List<ObjectId> collaboratingArticlesObjectId = new ArrayList<>();
            collaboratingArticles.forEach(article -> {
                        collaboratingArticlesObjectId.add(article.getId());
            });

            User user = User.builder()
                    .id(new ObjectId())
                    .email(person.getEmail())
                    .name(person.getFullName())
                    .password(bCryptPasswordEncoder.encode(person.getPassword()))
//                    .password(person.getPassword())
                    .roles(new HashSet<>(Arrays.asList(userRole)))
                    .articles(articles)
                    .collaboratingArticles(collaboratingArticlesObjectId)
                    .build();
            users.add(user);
        }
        return users;
    }

    /**
     * Creates a list of optional users.
     * Calls createUsers to create users and
     * uses a lambda expression to
     * convert them into optionals
     * @return The list of optional users generated
     */
    public static List<Optional<User>> createOptionalUsers(){
        List<Optional<User>> optionals = new ArrayList<>();
        List<User> users = createUsersWithRole("user");
        users.forEach(user -> optionals.add(Optional.of(user)));
        return optionals;
    }

    /**
     * Creates an Article with given email and status
     * @param email provided email to set article's emailId
     * @param status provided status to set article's status
     * @return generated article
     */
    public static Article createArticleByUserWithStatus(String email, Status status) {
        Article article = Article.builder()
                .id(new ObjectId())
                .emailId(email)
                .name(textProducer.sentence(1))
                .content(textProducer.paragraph(1))
                .status(status)
                .rejectedCount(baseProducer.randomBetween(0,2))
                .build();
        switch(status){
            case APPROVED:
            case BETA:
                article.setReadOnly(true);
                break;
            case DISCARDED:
                article.setReadOnly(true);
                article.setRejectedCount(4);
                break;
            case REJECTED:
                article.setReadOnly(false);
                break;
            case INITIAL:
                article.setReadOnly(false);
                article.setRejectedCount(0);
                break;
        }
        return article;
    }

    /**
     * Creates 5 articles reflecting the 5 Status enums
     * with a given users email for the article's emailId
     * @param email provided email to pass to createArticleByUserWithStatus()
     * @return the list of articles generated
     */
    public static Map<Status, Article> createArticles(String email) {
        Map<Status, Article> articleMap = new HashMap<>();

        Article approved = createArticleByUserWithStatus(email, Status.APPROVED);
        Article beta = createArticleByUserWithStatus(email, Status.BETA);
        Article initial = createArticleByUserWithStatus(email, Status.INITIAL);
        Article rejected = createArticleByUserWithStatus(email, Status.REJECTED);
        Article discarded = createArticleByUserWithStatus(email, Status.DISCARDED);

        articleMap.put(Status.APPROVED, approved);
        articleMap.put(Status.BETA, beta);
        articleMap.put(Status.INITIAL, initial);
        articleMap.put(Status.REJECTED, rejected);
        articleMap.put(Status.DISCARDED, discarded);

        return articleMap;
    }

    /**
     * Creates 5 articles reflecting the 5 Status enums
     * with a given users email for the article's emailId
     * @param email provided email to pass to createArticleByUserWithStatus()
     * @return the list of articles generated
     */
    public static List<Article> createArticlesWithStatus(String email, Status status) {
        List<Article> articles = new ArrayList<>();
        articles.add(createArticleByUserWithStatus(email, status));
        articles.add(createArticleByUserWithStatus(email, status));
        articles.add(createArticleByUserWithStatus(email, status));
        articles.add(createArticleByUserWithStatus(email, status));
        articles.add(createArticleByUserWithStatus(email, status));
        return articles;
    }

    /**
     * Creates a list of optional articles.
     * Calls createArticles to create articles.
     * Uses lambda expression to convert them to optionals.
     * @return List of optional articles
     */
    public static List<Optional<Article>> createOptionalArticles(){
        List<Optional<Article>> optionals = new ArrayList<>();
        Map<Status, Article> articleMap = createArticles("john@gmail.com");
        articleMap.values().forEach(article -> optionals.add(Optional.of(article)));
        return optionals;
    }

    public static Map<Status, Page<Article>> createPages() {
        Map<Status, Page<Article>> pageMap = new HashMap<>();

        List<Article> approved = createArticlesWithStatus("john@gmail.com", Status.APPROVED);
        List<Article> beta = createArticlesWithStatus("john@gmail.com", Status.BETA);
        List<Article> initial = createArticlesWithStatus("john@gmail.com", Status.INITIAL);
        List<Article> rejected = createArticlesWithStatus("john@gmail.com", Status.REJECTED);
        List<Article> discarded = createArticlesWithStatus("john@gmail.com", Status.DISCARDED);

        Page<Article> approvedPage = new PageImpl<>(approved);
        Page<Article> betaPage = new PageImpl<>(beta);
        Page<Article> initialPage = new PageImpl<>(initial);
        Page<Article> rejectedPage = new PageImpl<>(rejected);
        Page<Article> discardedPage = new PageImpl<>(discarded);

        pageMap.put(Status.APPROVED, approvedPage);
        pageMap.put(Status.BETA, betaPage);
        pageMap.put(Status.INITIAL, initialPage);
        pageMap.put(Status.REJECTED, rejectedPage);
        pageMap.put(Status.DISCARDED, discardedPage);

        return pageMap;
    }

    public static ObjectId createObjectId() {
        return new ObjectId();
    }

    public static Map<String, Role> createRoles() {
        Map<String, Role> map = new HashMap<>();

        Role user = Role.builder().id("USER").role("USER").build();
        Role admin = Role.builder().id("ADMIN").role("ADMIN").build();

        map.put("USER", user);
        map.put("ADMIN", admin);

        return map;
    }
}
