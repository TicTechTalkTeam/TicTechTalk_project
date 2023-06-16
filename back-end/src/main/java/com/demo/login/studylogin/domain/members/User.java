package com.demo.login.studylogin.domain.members;

import lombok.*;


import javax.persistence.*;


@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Table(name = "USERTABLE")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userNo")
    private Long userNo;

    @Column(nullable = false, unique = true)
    private String userEmail;

    @Column(nullable = false)
    private String password;

    @Column(name = "userNick", nullable = false, unique = true)
    private String userNick;

    @Column
    private String userInfo;

    @Column
    private String userPicOriginFileName;

    @Column
    private String userPicStoredFileName;

    @Column
    private Long point;

    @PrePersist
    private void prePersist() {
        if (point == null) {
            point = 50L;
        }
    }

    //TOKENTABLE의 userNo를 참조하며 1대1 관계에 있다. 해당 유저 삭제 시 토큰도 함께 삭제
    @OneToOne(mappedBy = "user", fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    private RefreshToken refreshToken;


}
