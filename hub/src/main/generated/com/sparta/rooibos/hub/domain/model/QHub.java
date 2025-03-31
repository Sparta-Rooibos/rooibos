package com.sparta.rooibos.hub.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHub is a Querydsl query type for Hub
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHub extends EntityPathBase<Hub> {

    private static final long serialVersionUID = 323742628L;

    public static final QHub hub = new QHub("hub");

    public final StringPath address = createString("address");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath deletedBy = createString("deletedBy");

    public final ComparablePath<java.util.UUID> hubId = createComparable("hubId", java.util.UUID.class);

    public final ListPath<HubManager, QHubManager> hubManagers = this.<HubManager, QHubManager>createList("hubManagers", HubManager.class, QHubManager.class, PathInits.DIRECT2);

    public final StringPath latitude = createString("latitude");

    public final StringPath longitude = createString("longitude");

    public final StringPath name = createString("name");

    public final StringPath region = createString("region");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public QHub(String variable) {
        super(Hub.class, forVariable(variable));
    }

    public QHub(Path<? extends Hub> path) {
        super(path.getType(), path.getMetadata());
    }

    public QHub(PathMetadata metadata) {
        super(Hub.class, metadata);
    }

}

