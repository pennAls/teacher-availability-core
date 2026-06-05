package org.example.msteacher.modules.teacher.domain;

import jakarta.persistence.*;
import org.example.mssecurity.modules.users.domain.User;
import org.example.msteacher.modules.academicDegree.domain.AcademicDegree;
import org.hibernate.mapping.List;
import org.hibernate.validator.constraints.UUID;

@Entity
@Table(name = "teachers")
public class Teacher {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private UUID userid;

    @Column(name = "institutionalEmail", nullable = false)
    private String institutionalEmail;

    @Column(name = "full_name", nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String registration;

    @Column(nullable = false)
    private String phone;

    @Column
    @JoinColumn(name = "school_id", nullable = false)
    private UUID schoolid;

    @OneToMany(mappedBy = "teacher", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AcademicDegree> degrees = new ArrayList<>();

    public Teacher() {}

    public Teacher(User user, String fullName, String registration, String phone, School school, String institutionalEmail) {
        this.user = user;
        this.fullName = fullName;
        this.registration = registration;
        this.phone = phone;
        this.school = school;
        this.institutionalEmail = institutionalEmail;
    }

    public UUID getId() { return id; }
    public User getUser() { return user; }
    public String getFullName() { return fullName; }
    public String getRegistration() { return registration; }
    public String getPhone() { return phone; }
    public School getSchool() { return school; }
    public String getInstitutionalEmail() { return institutionalEmail; }

    public List<AcademicDegree> getDegrees(){return this.degrees;}
}
