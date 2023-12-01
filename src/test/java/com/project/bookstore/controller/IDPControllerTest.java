package com.project.bookstore.controller;

import com.project.bookstore.dto.User;
import com.project.bookstore.exceptions.UserNameAlreadyTaken;
import com.project.bookstore.response.UserResponse;
import com.project.bookstore.service.IdpService;
import com.project.bookstore.testutils.TestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Profile;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(IdpController.class)
@AutoConfigureMockMvc
public class IDPControllerTest {
    @Autowired
    MockMvc mockMvc;

    @MockBean
    private IdpService idpService;

    @Test
    void testCreateUser() throws Exception {
        Mockito.when(idpService.createUser(new User("", "vinhruc@gmail", "Vineeth R", "7411419248", "pwd")))
                .thenReturn(new UserResponse("1234", "vinhruc@gmail", "Vineeth R", "7411419248"));

        User request = new User("", "vinhruc@gmail", "Vineeth R", "7411419248", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.userid").isString())
                .andExpect(jsonPath("$.username").isString())
                .andExpect(jsonPath("$.name").isString())
                .andExpect(jsonPath("$.phNo").isString());
    }

    @Test
    void testCreateUserShouldReturnBadRequestAndUSERNAME_ALREADY_TAKENErrorCodeInResponse() throws Exception {
        Mockito.when(idpService.createUser(new User("", "vinhruc@gmail", "Vineeth R", "7411419248", "pwd")))
                .thenThrow(UserNameAlreadyTaken.class);

        User request = new User("", "vinhruc@gmail", "Vineeth R", "7411419248", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errCode").value("USERNAME_ALREADY_TAKEN"));
    }

    private record UserWithoutUsername(String name, String phNo, String password) { }

    @Test
    void createUserShouldReturnBadRequestWhenUsernameIsMissingInRequest() throws Exception {
        // Null Username
        UserWithoutUsername request = new UserWithoutUsername("Vineeth R", "7411419248", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Blank Username
        User request1 = new User("", "", "Vineeth R", "7411419248", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request1)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserShouldReturnBadRequestWhenUsernameIsNotMatchingEmailIdFormat() throws Exception {
        User request1 = new User("", "abcd", "Vineeth R", "7411419248", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request1)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    private record UserWithoutName(String username, String phNo, String password) { }

    @Test
    void createUserShouldReturnBadRequestWhenNameIsMissingInRequest() throws Exception {
        // Null Name
        UserWithoutName request = new UserWithoutName("Vineeth R", "7411419248", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Blank Name
        User request1 = new User("", "vin@gmail.com", "", "7411419248", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request1)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    private record UserWithoutPhoneNumber(String username, String name, String password) { }

    @Test
    void createUserShouldReturnBadRequestWhenPhoneNumberIsMissingInRequest() throws Exception {
        // Null Phone number
        UserWithoutPhoneNumber request = new UserWithoutPhoneNumber("vin-450", "Vineeth R", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        // Blank Phone number
        User request1 = new User("", "vin@gmail.com", "Vineeth R", "", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request1)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserShouldReturnBadRequestWhenPhoneNumberIsNot10Digits() throws Exception {
        User request1 = new User("", "vin@gmail.com", "Vineeth R", "1234", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request1)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void createUserShouldReturnBadRequestWhenPhoneNumberContainsNotNumeric() throws Exception {
        User request1 = new User("", "vin@gmail.com", "Vineeth R", "a741141924", "pwd");
        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request1)).contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void getUserShouldReturnUnauthorizedWhenAccessTokenNotPassedInHeader() throws Exception {
       mockMvc.perform(get("/idp/user"))
                .andExpect(status().isUnauthorized());
    }

    // Get User unable to test due to interceptor
//    @Test
//    void getUserShouldReturnUnauthorizedWhenAccessTokenIsPassedAndIsNotAValidJWT() throws Exception {
////        Mockito.when(tokenIntrospection.validateTokenAndGetUsername("abcd"))
////                .thenThrow(Exception.class);
//
//        mockMvc.perform(get("/idp/user").header("accessToken", "abcd"))
//                .andExpect(status().isUnauthorized());
//    }

    // Commenting as we are unable to mock interceptor dependencies.
//    @Test
//    void getUserShouldReturnUserWhenUsernameFoundInHeader() throws Exception {
//        Mockito.when(idpService.getUserByUsername("vineeth@gmail.com"))
//                .thenReturn(new UserResponse("1234", "vineeth@gmail.com", "Vineeth R", "7411419248"));
//
//        Mockito.when(tokenIntrospection.validateTokenAndGetUsername("abcd"))
//                .thenReturn("vineeth@gmail.com");
//
//        mockMvc.perform(get("/idp/user").header("accessToken", "abcd"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.userid").value("1234"))
//                .andExpect(jsonPath("$.username").value("vineeth@gmail.com"))
//                .andExpect(jsonPath("$.name").value("Vineeth R"))
//                .andExpect(jsonPath("$.phNo").value("7411419248"));
//    }

//    private record UserWithoutPassword(String username, String name, String phNo) { }
//
//    @Test
//    void createUserShouldReturnBadRequestWhenPasswordIsMissingInRequest() throws Exception {
//        Mockito.when(idpService.createUser(new User("", "vinhruc@gmail", "Vineeth R", "7411419248", "")))
//                .thenReturn(new UserResponse("1234", "vinhruc@gmail", "Vineeth R", "7411419248"));
//
//        UserWithoutPassword request = new UserWithoutPassword("vin-450", "Vineeth R", "7411419248");
//        mockMvc.perform(post("/idp/user").content(TestUtils.asJsonString(request)).contentType(MediaType.APPLICATION_JSON)
//                        .accept(MediaType.APPLICATION_JSON))
//                .andExpect(status().isBadRequest());
//    }
}
