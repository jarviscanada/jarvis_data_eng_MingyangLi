package ca.jrvs.apps.twitter.model;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class Hashtag {
    private Integer[] indices;
    private String text;

    public Integer[] getIndices() {
        return indices;
    }

    public void setIndices(Integer[] indices) {
        this.indices = indices;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
