package com.demo.login.studylogin.domain.boards;

import lombok.*;

import javax.persistence.*;

@Builder
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "BOOKMARKTABLE")
public class Bookmark {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookmarkId;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "userNo")
//    private Long userNo;
//
//    @ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "postNo")
//    private Long postNo;

    @Column
    private Long userNo;

    @Column
    private Long postNo;
}
