package com.infy.WikiDocsProject.Utility;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.Model.User;
import com.infy.WikiDocsProject.enums.Role;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class UserBuilder {

    private ObjectId id;
    private String email;
    private String name;
    private List<Article> articles = new ArrayList<Article>();
    private Role role;

    public UserBuilder(ObjectId id, String email, String name, List<Article> articles, Role role) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.articles = articles;
        this.role = role;
    }

    public UserBuilder(){}

    public UserBuilder id(ObjectId id) {
        this.id = id;
        return this;
    }

    public UserBuilder email(String email) {
        this.email = email;
        return this;
    }

    public UserBuilder name(String name) {
        this.name = name;
        return this;
    }

    public UserBuilder articles(List<Article> articles) {
        this.articles.addAll(articles);
        return this;
    }

    public UserBuilder articles(Article article){
        this.articles.add(article);
        return this;
    }

    public UserBuilder role(Role role) {
        this.role = role;
        return this;
    }

    public User build(){
        return new User(id, email, name, articles, role);
    }
}
