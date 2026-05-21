package com.k24.hospital.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "prescription_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PrescriptionDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer quantity;

    private String dosage;

    @ManyToOne
    @JoinColumn(name = "prescription_id")
    private Prescription prescription;

    @ManyToOne
    @JoinColumn(name = "medicine_id")
    private Medicine medicine;
}