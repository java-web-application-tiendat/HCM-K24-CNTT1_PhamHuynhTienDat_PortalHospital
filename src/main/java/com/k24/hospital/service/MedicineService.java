package com.k24.hospital.service;

import com.k24.hospital.entity.Medicine;
import com.k24.hospital.repository.MedicineRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MedicineService {

    private final MedicineRepository medicineRepository;

    public List<Medicine> findAll() {
        return medicineRepository.findAll();
    }

    public Medicine findById(Long id) {
        return medicineRepository.findById(id).orElse(null);
    }

    public Medicine save(Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public void deleteById(Long id) {
        medicineRepository.deleteById(id);
    }
}
