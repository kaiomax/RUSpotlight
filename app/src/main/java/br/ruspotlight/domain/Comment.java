package br.ruspotlight.domain;

import java.util.Map;

public class Comment {

    public String content;
    public Map<String, Comment> responses;

    public Comment() {
    }

    public Comment(String content) {
        this.content = content;
    }

}
