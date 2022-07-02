package com.tr.triple.modules.image;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.Generated;
import com.querydsl.core.types.Path;


/**
 * QImage is a Querydsl query type for Image
 */
@Generated("com.querydsl.codegen.EntitySerializer")
public class QImage extends EntityPathBase<Image> {

    private static final long serialVersionUID = 273839924L;

    public static final QImage image = new QImage("image");

    public final NumberPath<Long> imageId = createNumber("imageId", Long.class);

    public final StringPath imageName = createString("imageName");

    public final StringPath imagePath = createString("imagePath");

    public final NumberPath<Long> serviceId = createNumber("serviceId", Long.class);

    public final NumberPath<Long> serviceTypeId = createNumber("serviceTypeId", Long.class);

    public final NumberPath<Long> userId = createNumber("userId", Long.class);

    public QImage(String variable) {
        super(Image.class, forVariable(variable));
    }

    public QImage(Path<? extends Image> path) {
        super(path.getType(), path.getMetadata());
    }

    public QImage(PathMetadata metadata) {
        super(Image.class, metadata);
    }

}

