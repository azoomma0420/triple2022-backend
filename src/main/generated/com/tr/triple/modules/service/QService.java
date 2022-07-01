package com.tr.triple.modules.service;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QService is a Querydsl query type for Service
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QService extends EntityPathBase<Service> {

    private static final long serialVersionUID = -1952283992L;

    public static final QService service = new QService("service");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final StringPath serviceName = createString("serviceName");

    public final NumberPath<Integer> serviceType = createNumber("serviceType", Integer.class);

    public QService(String variable) {
        super(Service.class, forVariable(variable));
    }

    public QService(Path<? extends Service> path) {
        super(path.getType(), path.getMetadata());
    }

    public QService(PathMetadata metadata) {
        super(Service.class, metadata);
    }

}

