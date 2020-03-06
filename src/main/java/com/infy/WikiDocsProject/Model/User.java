package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Role;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Document
public class User {

	@Id
	private String id;
	private String email;
	private String name;
	@DBRef(db = "article")
	private List<Article> articles;
	private Role role;

	public User(String id, String email, String name, List<Article> articles, Role role) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.articles = articles;
		this.role = role;
	}

	public User() { }

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Article> getArticles() {
		return articles;
	}

	public void setArticles(List<Article> articles) {
		this.articles = articles;
	}

	public Role getRole() {
		return role;
	}

	public void setRole(Role role) {
		this.role = role;
	}
}
