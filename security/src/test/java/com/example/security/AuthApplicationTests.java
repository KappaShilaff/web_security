//package com.example.security;
//
//
//import com.example.security.config.WebSecurityConfig;
//import com.example.security.model.AuthenticationImpl;
//import com.example.security.model.Role;
//import com.example.security.model.User;
//import com.example.security.repos.UserRepository;
//import com.google.gson.Gson;
//import org.apache.tomcat.util.codec.binary.Base64;
//import org.junit.Assert;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import static org.mockito.Mockito.*;
//
//import org.mockito.Mock;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.context.annotation.Import;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.MediaType;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
//
////нужно сделать datajpatest
//
//@RunWith(SpringJUnit4ClassRunner.class)
////@Import(MockMvc.class)
//@WebMvcTest(MockMvc.class)
//@Import(WebSecurityConfig.class)
//@AutoConfigureMockMvc
//public class AuthApplicationTests {
//
//    @MockBean
//    UserRepository userRepository;
//
//    @MockBean
//    PasswordEncoder encoder;
//
//    @Autowired
//    private MockMvc mockMvc;
//
//    @Test
//    public void getAuthentication() throws Exception {
//
//        String password = "testPassword";
//        User user = new User("testUsername", encoder.encode(password), Role.ADMIN, "test@test.test");
//
//        String encodedCredentials =
//                new String(Base64.encodeBase64((user.getUsername() + ":" + password).getBytes()));
//
//        HttpHeaders httpHeaders = new HttpHeaders();
//
//        httpHeaders.add("Authorization", "Basic " + encodedCredentials);
//
//        doReturn(user)
//                .when(userRepository)
//                .findByUsername(user.getUsername());
//
//        this.mockMvc.perform(get("/auth")
//                .headers(httpHeaders)
//                .contentType(MediaType.APPLICATION_JSON))
//                .andDo(result -> {
//                    Gson gson = new Gson();
//                    String responseJsonToString = result.getResponse().getContentAsString();
//
//                    AuthenticationImpl authentication = gson.fromJson(responseJsonToString, AuthenticationImpl.class);
//                    Assert.assertEquals(Role.ADMIN.getAuthority(), authentication.getAuthorities());
//                });
//    }
//}
