package com.k24.hospital.service;

import com.k24.hospital.entity.TestType;
import com.k24.hospital.repository.TestTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TestTypeService {

    private final TestTypeRepository testTypeRepository;

    public List<TestType> findAll() {
        return testTypeRepository.findAll();
    }
}
