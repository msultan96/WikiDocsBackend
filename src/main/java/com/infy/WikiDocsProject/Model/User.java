package com.infy.WikiDocsProject.Model;

import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;
import java.util.Set;

@Document(collection = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	private ObjectId id;

	private String email;

	private String password;

	private String name;

	// TODO: Change List<Article> to Set<Article> to handle duplicates
	private @DBRef(db = "article") List<Article> articles;

	// TODO: Change List<ObjectId> to Set<ObjectId> to handle duplicates
	private List<ObjectId> collaboratingArticles;

	@Field("roles")
	private @DBRef Set<Role> roles;
	private boolean enabled;

}
