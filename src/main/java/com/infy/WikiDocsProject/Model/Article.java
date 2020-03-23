package com.infy.WikiDocsProject.Model;

import com.infy.WikiDocsProject.enums.Status;
import lombok.*;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 
 * Article model class
 *
 */
@Document(collection = "articles")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Article {

	@Id
	private ObjectId id;
	private String emailId;
	private String name;
	private String content;
	private Status status;
	private int rejectedCount;
	private boolean readOnly;

}
