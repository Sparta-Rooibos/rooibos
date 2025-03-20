package com.sparta.rooibos.message.infrastuct.repository.impl;

import com.querydsl.core.types.Predicate;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.sparta.rooibos.message.domain.critria.MessageCriteria;
import com.sparta.rooibos.message.domain.entity.Message;
import com.sparta.rooibos.message.domain.entity.QMessage;
import com.sparta.rooibos.message.domain.model.Pagination;
import com.sparta.rooibos.message.domain.repository.MessageRepositoryCustom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class JpaMessageRepositoryCustom implements MessageRepositoryCustom {
    private final JPAQueryFactory query;
    private final QMessage message = QMessage.message;

    @Override
    public Pagination<Message> searchMessages(MessageCriteria criteria) {
        List<Message> content = query.select(message)
                .where(
                        nullIdCheck(criteria.id()),
                        nullReceiverIdCheck(criteria.receiverId()),
                        nullSenderCheck(criteria.sender()),
                        nullTextCheck(criteria.text())
                )
                .from(message)
                .offset((long) (criteria.page() - 1) * criteria.size())
                .limit(criteria.size())
                .fetch();

        Long total = query.select(message.count())
                .where(
                        nullIdCheck(criteria.id()),
                        nullReceiverIdCheck(criteria.receiverId()),
                        nullSenderCheck(criteria.sender()),
                        nullTextCheck(criteria.text())
                )
                .from(message).fetchOne();


        return Pagination.of(criteria.page(), criteria.size(), total != null ? total : 0, content);
    }

    private Predicate nullIdCheck(UUID id) {
        return id == null ? null : message.id.eq(id);
    }

    private Predicate nullReceiverIdCheck(String receiverId) {
        return receiverId == null ? null : message.recipient.like(receiverId);
    }

    private Predicate nullSenderCheck(String sender) {
        return sender == null ? null : message.sender.like(sender);
    }

    private Predicate nullTextCheck(String text) {
        return text == null ? null : message.text.like(text);
    }



}
