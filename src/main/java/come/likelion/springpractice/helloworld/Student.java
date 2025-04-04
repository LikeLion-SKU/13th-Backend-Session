package come.likelion.springpractice.helloworld;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "students")
public class Student {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String name;
  private Long age;

  public Long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public Long getAge() {
    return age;
  }
}
