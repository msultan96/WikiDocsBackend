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
    private String channelId;
    private String name;
    private String content;
    private Status status;
    private int rejectedCount;
    private boolean editable;

    public ArticleBuilder(ObjectId id, String emailId, String channelId, String name, String content, Status status, int rejectedCount, boolean editable) {
        this.id = id;
        this.emailId = emailId;
        this.channelId = channelId;
        this.name = name;
        this.content = content;
        this.status = status;
        this.rejectedCount = rejectedCount;
        this.editable = editable;
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

    public ArticleBuilder channelId(String channelId) {
        this.channelId = channelId;
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

    public ArticleBuilder editable(boolean editable) {
        this.editable = editable;
        return this;
    }

    /**
     * @name build
     * @Desciption Build article with attributes in params
     * @return article object
     */
    public Article build(){
        return new Article(id, emailId, channelId, name, content, status, rejectedCount, editable);
    }
}
