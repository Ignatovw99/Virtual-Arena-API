package com.virtualarena.api.entity;

import com.virtualarena.api.domain.enums.EventCategory;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "events")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class EventEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String description;

    private String imageUrl;

    @Column(nullable = false)
    private LocalDateTime startDateTime;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private EventCategory category;

    @ManyToOne
    @JoinColumn
    private UserEntity organizer;
}
