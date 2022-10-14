package com.io.realworld.domain.aggregate.profile.service;

import com.io.realworld.domain.aggregate.profile.repository.ProfileRepository;
import com.io.realworld.domain.aggregate.user.dto.UserAuth;
import com.io.realworld.domain.aggregate.user.dto.UserUpdate;
import com.io.realworld.domain.aggregate.user.repository.UserRepository;
import com.io.realworld.exception.CustomException;
import com.io.realworld.exception.Error;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProfileServiceImplTest {

    @InjectMocks
    private ProfileServiceImpl profileService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProfileRepository profileRepository;

    @Test
    @DisplayName("프로필조회 팔로워 못 찾을때")
    void getProfile_userNotFound() {
        UserAuth userAuth = UserAuth.builder().id(1L).build();
        when(userRepository.findByUsername(any(String.class))).thenThrow(new CustomException(Error.USER_NOT_FOUND));
        try{
            profileService.getProfile(userAuth,"username");
        }catch (CustomException e){
            assertThat(e.getError().equals(Error.USER_NOT_FOUND));
            assertThat(e.getError().getMessage().equals(Error.USER_NOT_FOUND.getMessage()));
        }

    }

    @Test
    void followUser() {
    }



    private static Stream<String> foundNotUsers(){
        return Stream.of("username1","username2","username3");
    }
}