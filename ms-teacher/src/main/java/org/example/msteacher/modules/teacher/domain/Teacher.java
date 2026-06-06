package org.example.msteacher.modules.teacher.domain;

import jakarta.persistence.*;
import org.example.msteacher.modules.academicDegree.domain.AcademicDegree;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "user_id", nullable = false, unique = true)
    private UUID userId;

    @Column(name = "school_id", nullable = false)
    private UUID schoolId;

    @Column(name = "institutional_email", nullable = false)
    private String institutionalEmail;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String registration;

    @Column(nullable = false)
    private String phone;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcademicDegree> degrees = new ArrayList<>();

    public Teacher() {}

    public Teacher(UUID userId, UUID schoolId, String fullName, String registration, String phone, String institutionalEmail) {
        this.userId = userId;
        this.schoolId = schoolId;
        this.fullName = fullName;
        this.registration = registration;
        this.phone = phone;
        this.institutionalEmail = institutionalEmail;
    }

    public UUID getId() { return id; }
    public UUID getUserId() { return userId; }
    public UUID getSchoolId() { return schoolId; }
    public String getFullName() { return fullName; }
    public String getRegistration() { return registration; }
    public String getPhone() { return phone; }
    public String getInstitutionalEmail() { return institutionalEmail; }
    public List<AcademicDegree> getDegrees() { return degrees; }
}
