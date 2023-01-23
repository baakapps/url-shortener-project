package com.baakapp.urlshortenerservice.model;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access= AccessLevel.PRIVATE)
public class Base62Converter {

    private static final String BASE_62 = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ";

    public static String encodeUniqueNumberToUniqueString(long number) {
        StringBuilder sb = new StringBuilder();
        while (number != 0) {
            sb.append(BASE_62.charAt((int)(number % 62)));
            number /= 62;
        }
        return sb.toString();
    }
}
