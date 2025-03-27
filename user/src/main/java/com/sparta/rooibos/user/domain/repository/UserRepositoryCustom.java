package com.sparta.rooibos.user.domain.repository;

import com.sparta.rooibos.user.application.dto.request.UserSearchRequest;
import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.model.Pagination;

public interface UserRepositoryCustom {
    Pagination<User> searchUsers(UserSearchRequest request);
}
