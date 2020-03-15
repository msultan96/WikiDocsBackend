package com.infy.WikiDocsProject.Utility;

import com.infy.WikiDocsProject.Model.Article;
import com.infy.WikiDocsProject.enums.Status;
import org.bson.types.ObjectId;

/**
 * Article Builder Class
 * Desc: Utility to create articles with ease
 * using the Builder pattern.
 */
public class ArticleBuilder {

    private ObjectId id;
    private String emailId;
    private String name;
    private String content;
    private Status status;
    private int rejectedCount;
    private boolean readOnly;

    public ArticleBuilder(ObjectId id, String emailId, String name, String content, Status status, int rejectedCount, boolean readOnly) {
        this.id = id;
        this.emailId = emailId;
        this.name = name;
        this.content = content;
        this.status = status;
        this.rejectedCount = rejectedCount;
        this.readOnly = readOnly;
    }

    public ArticleBuilder() {}

    public ArticleBuilder id(ObjectId id) {
        this.id = id;
        return this;
    }

    public ArticleBuilder emailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public ArticleBuilder name(String name) {
        this.name = name;
        return this;
    }

    public ArticleBuilder content(String content) {
        this.content = content;
        return this;
    }


    public ArticleBuilder status(Status status) {
        this.status = status;
        return this;
    }

    public ArticleBuilder rejectedCount(int rejectedCount) {
        this.rejectedCount = rejectedCount;
        return this;
    }

    public ArticleBuilder readOnly(boolean readOnly) {
        this.readOnly = readOnly;
        return this;
    }

    /**
     * @name build
     * @Desciption Build article with attributes in params
     * @return article object
     */
    public Article build(){
        return new Article(id, emailId, name, content, status, rejectedCount, readOnly);
    }
}
