package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Status;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;

@Document
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Article {

	@Id
	private String id;
	private User author;
	@Singular
	private List<User> currentCollaborators;
	private String name;
	private String content;
	private Status status;
	private Status[] edits = {Status.BETA, Status.BETA, Status.BETA};
	private boolean editable;


}
