package com.sparta.rooibos.user.domain.repository;

import com.sparta.rooibos.user.application.dto.request.UserSearchRequest;
import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.model.Pagination;
import org.springframework.data.domain.Page;

public interface UserRepositoryCustom {
    Pagination<User> searchUsers(UserSearchRequest request);
}
