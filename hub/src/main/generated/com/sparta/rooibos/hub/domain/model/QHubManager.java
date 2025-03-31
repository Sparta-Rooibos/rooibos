package com.sparta.rooibos.hub.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QHubManager is a Querydsl query type for HubManager
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QHubManager extends EntityPathBase<HubManager> {

    private static final long serialVersionUID = -2016928919L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QHubManager hubManager = new QHubManager("hubManager");

    public final ComparablePath<java.util.UUID> belongingHubId = createComparable("belongingHubId", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath email = createString("email");

    public final QHub hub;

    public final ComparablePath<java.util.UUID> hubManagerId = createComparable("hubManagerId", java.util.UUID.class);

    public final ComparablePath<java.util.UUID> userId = createComparable("userId", java.util.UUID.class);

    public QHubManager(String variable) {
        this(HubManager.class, forVariable(variable), INITS);
    }

    public QHubManager(Path<? extends HubManager> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QHubManager(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QHubManager(PathMetadata metadata, PathInits inits) {
        this(HubManager.class, metadata, inits);
    }

    public QHubManager(Class<? extends HubManager> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.hub = inits.isInitialized("hub") ? new QHub(forProperty("hub")) : null;
    }

}

