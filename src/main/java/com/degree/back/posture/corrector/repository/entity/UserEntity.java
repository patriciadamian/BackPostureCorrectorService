package com.degree.back.posture.corrector.repository.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(schema = "back_posture.corrector", name = "users")
@NamedQuery(name = "UserEntity.getAll", query = "Select u from UserEntity u")
@NamedQuery(name = "UserEntity.findByEmail", query = "Select u from UserEntity u where u.email =:EMAIL")
@NamedQuery(name = "UserEntity.updateProfile", query = "Update UserEntity u set u.age = :AGE, u.height = :HEIGHT, u.weight = :WEIGHT where u.id = :ID")
@NoArgsConstructor
@AllArgsConstructor
public class UserEntity {

  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE)
  private Long id;

  @Column(name = "first_name")
  private String firstName;

  @Column(name = "last_name")
  private String lastName;

  @Column(name = "email")
  private String email;

  @Column(name = "password")
  private String password;

  @Column(name = "age")
  private short age;

  @Column(name = "height")
  private short height;

  @Column(name = "weight")
  private short weight;

  public UserEntity(String firstName, String lastName, String email, String password) {
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    this.password = password;
    this.age = 0;
    this.height = 0;
    this.weight = 0;
  }

}
