package com.likelion.springpractice.global;


import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@MappedSuperclass //이걸 해줘야, 이 클래스를 상속받은 엔티티에서, 여기의 필드를 테이블 칼럼으로 인식함!!
//ex) Post엔티티가 상속받으면, createDate, modefiedDate가 Post테이블에 칼럼으로 생성된다!!
@EntityListeners(AuditingEntityListener.class) //JPA의 감시기능을 가능하게 하는 어노테이션!!
//이 클래스를 사용하는 엔티티에서, 저장/수정 시점에 자동으로 시간값을 채우도록 한다!!
public abstract class BaseTimeEntity { //클래스를 직접 생성할 수 없는 추상클래스!!
  //보통 다른 엔티티들이 extends BaseTimeEntity로 상속해서 이용함!

  @CreatedDate  //엔티티가 처음 저장될 때, 생성일을 자동으로 기록하게 함!!
  private LocalDateTime createdAt;

  @LastModifiedDate //엔티티가 수정될 때마다, 자동으로 갱신된다!! 즉 modefiedAt속성에 값 자동 저장됨!
  private LocalDateTime modifiedAt;

}
