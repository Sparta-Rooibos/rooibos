package com.sparta.rooibos.delivery.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QDeliveryLog is a Querydsl query type for DeliveryLog
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QDeliveryLog extends EntityPathBase<DeliveryLog> {

    private static final long serialVersionUID = -886278118L;

    public static final QDeliveryLog deliveryLog = new QDeliveryLog("deliveryLog");

    public final StringPath arrival = createString("arrival");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath deletedBy = createString("deletedBy");

    public final ComparablePath<java.util.UUID> deliver = createComparable("deliver", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> deliveryId = createComparable("deliveryId", java.util.UUID.class);

    public final StringPath departure = createString("departure");

    public final StringPath expectedDistance = createString("expectedDistance");

    public final StringPath expectedTime = createString("expectedTime");

    public final ComparablePath<java.util.UUID> fromHubId = createComparable("fromHubId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final NumberPath<Integer> sequence = createNumber("sequence", Integer.class);

    public final EnumPath<com.sparta.rooibos.delivery.domain.model.DeliveryLogEnum> status = createEnum("status", com.sparta.rooibos.delivery.domain.model.DeliveryLogEnum.class);

    public final StringPath takenDistance = createString("takenDistance");

    public final StringPath takenTime = createString("takenTime");

    public final ComparablePath<java.util.UUID> toHubId = createComparable("toHubId", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public QDeliveryLog(String variable) {
        super(DeliveryLog.class, forVariable(variable));
    }

    public QDeliveryLog(Path<? extends DeliveryLog> path) {
        super(path.getType(), path.getMetadata());
    }

    public QDeliveryLog(PathMetadata metadata) {
        super(DeliveryLog.class, metadata);
    }

}

