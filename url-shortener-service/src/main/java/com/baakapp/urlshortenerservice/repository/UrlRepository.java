package com.baakapp.urlshortenerservice.repository;

import com.baakapp.urlshortenerservice.entity.Url;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface UrlRepository extends MongoRepository<Url, Long> {

      Optional<Url> findByShortUrl(String shortUrl);

}
