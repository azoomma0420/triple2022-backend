package com.tr.triple.modules.image;

import lombok.*;
import org.hibernate.envers.Audited;

import javax.persistence.*;
import java.sql.Blob;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="image")
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;
    private Long serviceId;
    private Long serviceTypeId;
    private String imageName;
    @Lob
    private Blob image;
}
