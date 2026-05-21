package com.k24.hospital.service;

import com.k24.hospital.entity.Medicine;
import com.k24.hospital.entity.Prescription;
import com.k24.hospital.entity.PrescriptionDetail;
import com.k24.hospital.enums.PrescriptionStatus;
import com.k24.hospital.repository.MedicineRepository;
import com.k24.hospital.repository.PrescriptionRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrescriptionService {

    private final PrescriptionRepository prescriptionRepository;
    private final MedicineRepository medicineRepository;

    @Transactional
    public void dispensePrescription(Long prescriptionId) {

        Prescription prescription = prescriptionRepository
                .findById(prescriptionId)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy đơn thuốc"));

        if (prescription.getStatus() == PrescriptionStatus.DISPENSED) {
            throw new RuntimeException("Đơn thuốc đã được cấp phát");
        }


        for (PrescriptionDetail detail : prescription.getDetails()) {

            Medicine medicine = detail.getMedicine();

            if (medicine.getQuantity() < detail.getQuantity()) {
                throw new RuntimeException(
                        "Không đủ thuốc trong kho: " + medicine.getName()
                );
            }
        }


        for (PrescriptionDetail detail : prescription.getDetails()) {

            Medicine medicine = detail.getMedicine();

            medicine.setQuantity(
                    medicine.getQuantity() - detail.getQuantity()
            );

            medicineRepository.save(medicine);
        }


        prescription.setStatus(PrescriptionStatus.DISPENSED);

        prescriptionRepository.save(prescription);
    }
}