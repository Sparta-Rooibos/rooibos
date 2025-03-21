package com.sparta.rooibus.delivery.infrastructure.user;

import com.sparta.rooibus.delivery.application.dto.request.feign.user.GetUserRequest;
import com.sparta.rooibus.delivery.application.dto.response.feign.user.GetUserResponse;
import com.sparta.rooibus.delivery.application.service.feign.UserService;
import org.springframework.stereotype.Component;

@Component
public class UserClientSub implements UserService {

    @Override
    public GetUserResponse getUser(GetUserRequest request) {
        String userName = "임시";
        String email= "임시";
        String phone= "임시";
        String slackAccount= "임시";
        String role= "임시";
        return new GetUserResponse(userName,email,phone,slackAccount,role);
    }
}
