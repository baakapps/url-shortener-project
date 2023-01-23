package com.baakapp.urlshortenerservice.service;

import com.baakapp.urlshortenerservice.entity.Url;
import com.baakapp.urlshortenerservice.model.response.ShortUrlResponse;

public interface UrlService {

    Url getLongUrlFromShortUrl(String shortenedUrl);

    ShortUrlResponse createShortUrlFromLongUrl(String longUrl);
}
