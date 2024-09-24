package com.taehun.qentity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.DateTimePath;
import com.querydsl.core.types.dsl.EntityPathBase;
import com.querydsl.core.types.dsl.StringPath;
import com.taehun.entity.BaseEntity;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QBaseEntity is a Querydsl query type for BaseEntity
 */
@Generated("com.querydsl.codegen.DefaultSupertypeSerializer")
public class QBaseEntity extends EntityPathBase<BaseEntity> {

    private static final long serialVersionUID = 1381806247L;

    public static final QBaseEntity baseEntity = new QBaseEntity("baseEntity");

    public final StringPath createBy = createString("createBy");

    public final DateTimePath<java.time.LocalDateTime> creationData = createDateTime("creationData", java.time.LocalDateTime.class);

    public final DateTimePath<java.time.LocalDateTime> lastModified = createDateTime("lastModified", java.time.LocalDateTime.class);

    public final StringPath lastModifiedBy = createString("lastModifiedBy");

    public QBaseEntity(String variable) {
        super(BaseEntity.class, forVariable(variable));
    }

    public QBaseEntity(Path<? extends BaseEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QBaseEntity(PathMetadata metadata) {
        super(BaseEntity.class, metadata);
    }

}

