package com.baakapp.urlshortenerservice.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.io.Serializable;
import java.time.LocalDateTime;

@Document(collection = "url")
@Data
public class Url implements Serializable {

    public Url(String shortUrl, String longUrl) {
        this.shortUrl = shortUrl;
        this.longUrl = longUrl;
    }

    @Id
    private String id;
    @Indexed(unique = true)
    private String shortUrl;
    private String longUrl;
    private LocalDateTime createdDate = LocalDateTime.now();
}
