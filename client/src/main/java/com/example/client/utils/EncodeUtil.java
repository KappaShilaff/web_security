package com.example.client.utils;

import com.example.client.dto.UserDtoReqAuth;
import com.example.client.dto.UserDtoReqSave;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.http.HttpHeaders;

public class EncodeUtil {
    public static HttpHeaders getHttpHeaders(UserDtoReqAuth userDtoReqAuth) {
        HttpHeaders httpHeaders = new HttpHeaders();
        String userCredentials = userDtoReqAuth.getUsername() + ":" + userDtoReqAuth.getPassword();
        String encodedCredentials =
                new String(Base64.encodeBase64(userCredentials.getBytes()));
        httpHeaders.add("Authorization", "Basic " + encodedCredentials);
        return httpHeaders;
    }
}
