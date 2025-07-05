package com.likelion.springpractice.domain.mission.dto.response;

import com.likelion.springpractice.domain.mission.entity.Country;
import com.likelion.springpractice.domain.mission.entity.Role;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserSummaryResponse {

  private Long userId;
  private String username;
  private String introduction;
  private Role role;
  private Country country;
  private int reviewCount;
  private int likeCount;
  private List<String> badges;
}