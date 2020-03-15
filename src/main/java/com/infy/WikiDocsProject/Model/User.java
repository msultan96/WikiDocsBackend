package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Role;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Document
@Getter @Setter @NoArgsConstructor
public class User {

	@Id
	private ObjectId id;
	private String email;
	private String name;
	private String password;
	/**
	 * Reference to Article in database
	 */
	@DBRef(db = "article")
	private List<Article> articles;
	private Role role;

	/**
	 * All Args constructor
	 *
	 */
	public User(ObjectId id, String email, String password, String name, List<Article> articles, Role role) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.articles = articles;
		this.role = role;
	}
}
