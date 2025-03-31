package com.sparta.rooibos.route.domain.model;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QRoute is a Querydsl query type for Route
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRoute extends EntityPathBase<Route> {

    private static final long serialVersionUID = -1846731228L;

    public static final QRoute route = new QRoute("route");

    public final DateTimePath<java.time.LocalDateTime> createdAt = createDateTime("createdAt", java.time.LocalDateTime.class);

    public final StringPath createdBy = createString("createdBy");

    public final DateTimePath<java.time.LocalDateTime> deletedAt = createDateTime("deletedAt", java.time.LocalDateTime.class);

    public final StringPath deletedBy = createString("deletedBy");

    public final NumberPath<Integer> distance = createNumber("distance", Integer.class);

    public final ComparablePath<java.util.UUID> fromHubId = createComparable("fromHubId", java.util.UUID.class);

    public final StringPath fromHubName = createString("fromHubName");

    public final ComparablePath<java.util.UUID> routeId = createComparable("routeId", java.util.UUID.class);

    public final NumberPath<Integer> timeCost = createNumber("timeCost", Integer.class);

    public final ComparablePath<java.util.UUID> toHubId = createComparable("toHubId", java.util.UUID.class);

    public final StringPath toHubName = createString("toHubName");

    public final DateTimePath<java.time.LocalDateTime> updatedAt = createDateTime("updatedAt", java.time.LocalDateTime.class);

    public final StringPath updatedBy = createString("updatedBy");

    public QRoute(String variable) {
        super(Route.class, forVariable(variable));
    }

    public QRoute(Path<? extends Route> path) {
        super(path.getType(), path.getMetadata());
    }

    public QRoute(PathMetadata metadata) {
        super(Route.class, metadata);
    }

}

