package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Role;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	private ObjectId id;
	private String authorId;
	private String email;
	private String name;
	private String password;
	/**
	 * Reference to Article in database
	 */
	@DBRef(db = "article")
	private List<Article> articles;
	private Role role;

}
