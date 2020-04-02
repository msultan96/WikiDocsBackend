package com.infy.WikiDocsProject.Model;

import lombok.Data;

@Data
public class AuthBody implements Cloneable{

    private String email;
    private String password;

    @Override
    public AuthBody clone() throws CloneNotSupportedException {
        return (AuthBody) super.clone();
    }
}
