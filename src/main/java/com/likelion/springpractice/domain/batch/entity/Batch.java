package com.likelion.springpractice.domain.batch.entity;

import com.likelion.springpractice.domain.userbatch.entity.UserBatch;
import com.likelion.springpractice.global.common.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "batch")
public class Batch extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "condition", nullable = false)
    private String condition;

    @OneToMany(mappedBy = "batch")
    private List<UserBatch> userBatches = new ArrayList<>();

}
