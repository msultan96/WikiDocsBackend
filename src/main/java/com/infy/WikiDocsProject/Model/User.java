package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Role;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.List;

@Document
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class User {

	@Id
	private ObjectId id;

	@Email
	private String email;

	@NotBlank
	private String name;

	@NonNull
	private String password;

	private @DBRef(db = "article") List<Article> articles;
	private List<ObjectId> collaboratingArticles;
	private Role role;

}
