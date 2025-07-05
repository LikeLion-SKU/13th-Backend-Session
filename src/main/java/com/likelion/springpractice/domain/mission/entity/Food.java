package com.likelion.springpractice.domain.mission.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Food {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column(nullable = false)
  private String content;

  @Column(name = "like_count", nullable = false)
  @ColumnDefault("0")
  private int likeCount;

  @Builder.Default
  @OneToMany(mappedBy = "food", cascade = CascadeType.ALL, orphanRemoval = true)
  private List<Likes> likesList = new ArrayList<>();

  public void increaseLikeCount() {
    this.likeCount++;
  }

  public void decreaseLikeCount() {
    if (this.likeCount > 0) {
      this.likeCount--;
    }
  }

}
