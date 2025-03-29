package com.sparta.rooibos.user.domain.repository;


import com.sparta.rooibos.user.domain.critria.UserSearchCriteria;
import com.sparta.rooibos.user.domain.entity.User;
import com.sparta.rooibos.user.domain.model.Pagination;

public interface UserRepositoryCustom {
    Pagination<User> searchUsers(UserSearchCriteria criteria);
}
