package com.sparta.rooibos.message.domain.repository;

import com.sparta.rooibos.message.domain.critria.MessageCriteria;
import com.sparta.rooibos.message.domain.entity.Message;
import com.sparta.rooibos.message.domain.model.Pagination;

public interface MessageRepositoryCustom {
    Pagination<Message> searchMessages(MessageCriteria criteria);
}
