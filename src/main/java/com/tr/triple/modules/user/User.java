package com.tr.triple.modules.user;

import lombok.*;

import javax.persistence.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId; //시스템상에서 자동으로 부여하는 ID

    private String userName; //사용자가 직접 입력하는 ID
    private String password;
    private Long point;
}
