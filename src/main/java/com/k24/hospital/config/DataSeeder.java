package com.k24.hospital.config;

import com.k24.hospital.entity.*;
import com.k24.hospital.enums.Gender;
import com.k24.hospital.enums.Role;
import com.k24.hospital.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final UserProfileRepository userProfileRepository;
    private final SpecialtyRepository specialtyRepository;
    private final DoctorRepository doctorRepository;
    private final MedicineRepository medicineRepository;
    private final TestTypeRepository testTypeRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        seedSpecialties();
        seedTestTypes();
        seedMedicines();

        seedAdmin();
        seedDoctor();
        seedPatient();

        System.out.println("=== SEEDED DATABASE SUCCESS ===");
    }

    private void seedAdmin() {
        if (userRepository.existsByUsername("admin")) {
            return;
        }

        User admin = User.builder()
                .username("admin")
                .email("admin@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.ADMIN)
                .enabled(true)
                .build();

        userRepository.save(admin);

        userProfileRepository.save(UserProfile.builder()
                .user(admin)
                .fullName("Quản trị viên")
                .phone("0900000000")
                .address("K24 Hospital")
                .gender(Gender.OTHER)
                .build());
    }

    private void seedDoctor() {
        if (userRepository.existsByUsername("doctor")) {
            return;
        }

        Specialty specialty = specialtyRepository.findAll().get(0);

        User doctor = User.builder()
                .username("doctor")
                .email("doctor@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.DOCTOR)
                .enabled(true)
                .build();

        userRepository.save(doctor);

        userProfileRepository.save(UserProfile.builder()
                .user(doctor)
                .fullName("Bác sĩ Nguyễn Văn A")
                .phone("0911111111")
                .address("Hà Nội")
                .gender(Gender.MALE)
                .build());

        doctorRepository.save(Doctor.builder()
                .user(doctor)
                .specialty(specialty)
                .degree("Thạc sĩ")
                .experience("5 năm kinh nghiệm")
                .build());
    }

    private void seedPatient() {
        if (userRepository.existsByUsername("patient")) {
            return;
        }

        User patient = User.builder()
                .username("patient")
                .email("patient@gmail.com")
                .password(passwordEncoder.encode("123456"))
                .role(Role.PATIENT)
                .enabled(true)
                .build();

        userRepository.save(patient);

        userProfileRepository.save(UserProfile.builder()
                .user(patient)
                .fullName("Trần Văn B")
                .phone("0922222222")
                .address("TP Hà Nội")
                .gender(Gender.MALE)
                .build());
    }

    private void seedSpecialties() {
        if (specialtyRepository.count() > 0) {
            return;
        }

        specialtyRepository.save(Specialty.builder()
                .name("Nội tổng quát")
                .description("Khám nội tổng quát")
                .build());

        specialtyRepository.save(Specialty.builder()
                .name("Tai mũi họng")
                .description("Khám tai mũi họng")
                .build());

        specialtyRepository.save(Specialty.builder()
                .name("Tim mạch")
                .description("Khám và tư vấn bệnh tim mạch")
                .build());
    }

    private void seedTestTypes() {
        if (testTypeRepository.count() > 0) {
            return;
        }

        testTypeRepository.save(TestType.builder()
                .name("Xét nghiệm máu")
                .description("Kiểm tra các chỉ số máu")
                .build());

        testTypeRepository.save(TestType.builder()
                .name("Xét nghiệm nước tiểu")
                .description("Kiểm tra chức năng thận")
                .build());

        testTypeRepository.save(TestType.builder()
                .name("Chụp X-quang")
                .description("Chẩn đoán hình ảnh bằng X-quang")
                .build());
    }

    private void seedMedicines() {
        if (medicineRepository.count() > 0) {
            return;
        }

        medicineRepository.save(Medicine.builder()
                .name("Paracetamol")
                .unit("Viên")
                .quantity(100)
                .price(1000.0)
                .description("Giảm đau, hạ sốt")
                .build());

        medicineRepository.save(Medicine.builder()
                .name("Amoxicillin")
                .unit("Viên")
                .quantity(50)
                .price(2000.0)
                .description("Kháng sinh")
                .build());

        medicineRepository.save(Medicine.builder()
                .name("Vitamin C")
                .unit("Viên")
                .quantity(120)
                .price(1500.0)
                .description("Tăng sức đề kháng")
                .build());
    }
}