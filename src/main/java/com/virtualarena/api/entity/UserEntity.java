package com.virtualarena.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String email;

    @Column
    private String fullName;

    @Column
    private String phoneNumber;

    @Column
    private String profilePicture;

    @Column
    private String bio;

    @Column(nullable = false)
    private LocalDate createdAt;

    @ManyToMany(mappedBy = "participants")
    private Set<EventEntity> events;

}
