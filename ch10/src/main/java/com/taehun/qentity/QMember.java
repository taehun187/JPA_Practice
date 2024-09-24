package com.taehun.qentity;

import com.querydsl.core.types.Path;
import com.querydsl.core.types.PathMetadata;
import com.querydsl.core.types.dsl.*;
import com.taehun.entity.AddressEntity;
import com.taehun.entity.FavoriteFood;
import com.taehun.entity.Member;

import javax.annotation.processing.Generated;

import static com.querydsl.core.types.PathMetadataFactory.forVariable;


/**
 * QMember is a Querydsl query type for Member
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QMember extends EntityPathBase<Member> {

    private static final long serialVersionUID = 243713709L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QMember member = new QMember("member1");

    public final QBaseEntity _super = new QBaseEntity(this);

    public final QAddress address;

    public final ListPath<AddressEntity, QAddressEntity> addressList = this.<AddressEntity, QAddressEntity>createList("addressList", AddressEntity.class, QAddressEntity.class, PathInits.DIRECT2);

    public final NumberPath<Integer> age = createNumber("age", Integer.class);

    //inherited
    public final StringPath createBy = _super.createBy;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> creationData = _super.creationData;

    public final ListPath<FavoriteFood, QFavoriteFood> favoriteFoods = this.<FavoriteFood, QFavoriteFood>createList("favoriteFoods", FavoriteFood.class, QFavoriteFood.class, PathInits.DIRECT2);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> lastModified = _super.lastModified;

    //inherited
    public final StringPath lastModifiedBy = _super.lastModifiedBy;

    public final StringPath name = createString("name");

    public final QTeam team;

    public QMember(String variable) {
        this(Member.class, forVariable(variable), INITS);
    }

    public QMember(Path<? extends Member> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QMember(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QMember(PathMetadata metadata, PathInits inits) {
        this(Member.class, metadata, inits);
    }

    public QMember(Class<? extends Member> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.address = inits.isInitialized("address") ? new QAddress(forProperty("address")) : null;
        this.team = inits.isInitialized("team") ? new QTeam(forProperty("team")) : null;
    }

}

