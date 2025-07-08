package com.likelion.springpractice.domain.food.entity;

import com.likelion.springpractice.domain.foodlike.entity.FoodLike;
import com.likelion.springpractice.domain.foodreview.entity.FoodReview;
import com.likelion.springpractice.global.common.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table(name = "foods")
public class Food extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description", nullable = false)
    private String description;

    @Column(name = "image", nullable = false)
    private String image;

    @Column(name = "score")
    private float score = 0.0f;

    @Column(name = "likes")
    private int likes = 0;

    @OneToMany(mappedBy = "food")
    private List<FoodReview> foodReviews = new ArrayList<>();

    @OneToMany(mappedBy = "food")
    private List<FoodLike> foodLikes = new ArrayList<>();

    public void update(String name, String description, String image) {
        this.name = name;
        this.description = description;
        this.image = image;
    }

    public void increaseLikes() {
        this.likes++;
    }

    public void decreaseLikes() {
        this.likes--;
    }

    public void updateScore(float score) {
        this.score = score;
    }
}
