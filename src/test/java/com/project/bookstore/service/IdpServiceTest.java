package com.project.bookstore.service;

import com.project.bookstore.dto.User;
import com.project.bookstore.entity.UserEntity;
import com.project.bookstore.exceptions.UserNameAlreadyTaken;
import com.project.bookstore.exceptions.UserNotRegistered;
import com.project.bookstore.repository.UserRepository;
import com.project.bookstore.response.UserResponse;
import com.project.bookstore.service.IdpService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class IdpServiceTest {
    @Mock
    UserRepository userRepository;

    @Test
    void shouldCallSaveOnRepositoryWithUserEntity() throws Exception {
        Mockito.when(userRepository.save(new UserEntity(Mockito.any(), "vin-450", "Vineeth R", "phNo", "pwd")))
                .thenReturn(new UserEntity("00000000-0000-0000-0000-000000000000", "vin-450", "Vineeth R", "phNo", "pwd"));

        IdpService idpService = new IdpService(userRepository);
        UserResponse serviceResponse = idpService.createUser(new User("00000000-0000-0000-0000-000000000000", "vin-450", "Vineeth R", "phNo", "pwd"));

        Assertions.assertThat(serviceResponse).isEqualTo(new UserResponse("00000000-0000-0000-0000-000000000000", "vin-450", "Vineeth R", "phNo"));
    }

    @Test
    void createUserShouldReturnExceptionWhenUsernameAlreadyTaken() {
        Mockito.when(userRepository.existsByUsername("vin-450"))
                .thenReturn(true);

        IdpService idpService = new IdpService(userRepository);

        Assertions.assertThatThrownBy(() -> {
            idpService.createUser(new User("00000000-0000-0000-0000-000000000000", "vin-450", "Vineeth R", "phNo", "pwd"));
        }).isInstanceOf(UserNameAlreadyTaken.class);
    }

    @Test
    void getUserShouldReturnUserAsExpected() throws Exception {
        Mockito.when(userRepository.getByUsername("vin-450@g.com"))
                .thenReturn(new UserEntity("1234", "vin-450@g.com", "Vineeth R", "7411419248", ""));

        IdpService idpService = new IdpService(userRepository);
        UserResponse serviceResponse = idpService.getUserByUsername("vin-450@g.com");

        Assertions.assertThat(serviceResponse).isEqualTo(new UserResponse("1234", "vin-450@g.com", "Vineeth R", "7411419248"));
    }

    @Test
    void getUserShouldThrowExceptionWhenUserNotFound() throws Exception {
        Mockito.when(userRepository.getByUsername("vin-450@g.com"))
                .thenReturn(null);

        IdpService idpService = new IdpService(userRepository);

        Assertions.assertThatThrownBy(() -> {
            idpService.getUserByUsername("vin-450@g.com");
        }).isInstanceOf(UserNotRegistered.class);
    }
}
