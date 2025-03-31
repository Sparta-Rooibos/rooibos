package com.sparta.rooibos.client.domain.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClientManager is a Querydsl query type for ClientManager
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClientManager extends EntityPathBase<ClientManager> {

    private static final long serialVersionUID = 743377123L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClientManager clientManager = new QClientManager("clientManager");

    public final QClient client;

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final StringPath userId = createString("userId");

    public QClientManager(String variable) {
        this(ClientManager.class, forVariable(variable), INITS);
    }

    public QClientManager(Path<? extends ClientManager> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClientManager(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClientManager(PathMetadata metadata, PathInits inits) {
        this(ClientManager.class, metadata, inits);
    }

    public QClientManager(Class<? extends ClientManager> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.client = inits.isInitialized("client") ? new QClient(forProperty("client")) : null;
    }

}

