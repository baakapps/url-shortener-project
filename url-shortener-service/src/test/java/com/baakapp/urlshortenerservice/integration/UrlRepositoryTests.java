package com.baakapp.urlshortenerservice.integration;

import com.baakapp.urlshortenerservice.entity.Url;
import com.baakapp.urlshortenerservice.repository.UrlRepository;
import com.baakapp.urlshortenerservice.container.MongoContainer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.dao.DuplicateKeyException;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;

@DataMongoTest
@Testcontainers
class UrlRepositoryTests  extends MongoContainer {

    @Autowired
    UrlRepository urlRepository;

    @DisplayName("Find by short url")
    @Test
    void whenSavedDataThenFindsByShortUrl(){
        Url savedUrl = new Url("1", "www.google.com");
        urlRepository.save(savedUrl);

        Url fetchedUrl = urlRepository.findByShortUrl("1").orElse(null);
        assertNotNull(fetchedUrl);
        assertEquals(savedUrl.getShortUrl(), fetchedUrl.getShortUrl());
        assertEquals(savedUrl.getLongUrl(), fetchedUrl.getLongUrl());
        assertNotNull(fetchedUrl.getCreatedDate());
    }

    @DisplayName("Short url is unique")
    @Test
    void whenSavedDataThenChecksUniqueShortUrl(){
        Url firstSavedUrl = new Url("2", "www.google.com");
        urlRepository.save(firstSavedUrl);

        Url secondSavedUrl = new Url("2", "www.apple.com");
        assertThrows(DuplicateKeyException.class, () -> urlRepository.save(secondSavedUrl));
    }
}
