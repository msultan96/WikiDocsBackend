package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Role;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	private String id;
	private String email;
	private String name;
	@Singular
	private List<Article> articles;
	private Role role;

}
