package com.k24.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "test_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TestType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;
}