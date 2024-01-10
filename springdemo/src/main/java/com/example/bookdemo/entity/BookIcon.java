package com.example.bookdemo.entity;




import jakarta.persistence.Basic;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

@Document(collection = "books")
public class BookIcon {
    @Id
    private int id;
    @Field("icon_url")
    private String icon_url;

    public BookIcon(int id, String icon_url) {
        this.id = id;
        this.icon_url = icon_url;
    }

    public BookIcon() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getIcon_url() {
        return icon_url;
    }

    public void setIcon_url(String icon_url) {
        this.icon_url = icon_url;
    }

}
