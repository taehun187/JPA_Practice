package com.taehun.entity;
import javax.persistence.*;

import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@DynamicInsert
@DynamicUpdate
@Entity
public class Comment extends BaseEntity{

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    @Column(name="COMMENT_ID")
    private Long id;

    @Lob
    private String text;

    @ManyToOne
    @JoinColumn(name = "POST_ID")
    private Post post;
}
