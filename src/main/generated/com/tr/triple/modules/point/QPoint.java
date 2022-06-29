package com.tr.triple.modules.point;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QPoint is a Querydsl query type for Point
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QPoint extends EntityPathBase<Point> {

    private static final long serialVersionUID = -1638722594L;

    public static final QPoint point1 = new QPoint("point1");

    public final NumberPath<Long> point = createNumber("point", Long.class);

    public final NumberPath<Long> pointId = createNumber("pointId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QPoint(String variable) {
        super(Point.class, forVariable(variable));
    }

    public QPoint(Path<? extends Point> path) {
        super(path.getType(), path.getMetadata());
    }

    public QPoint(PathMetadata metadata) {
        super(Point.class, metadata);
    }

}

