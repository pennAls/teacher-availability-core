package org.example.msteacher.academicDegree.domain;

import jakarta.persistence.*;
import org.example.msteacher.academicDegree.domain.types.DegreeCategory;
import org.hibernate.validator.constraints.UUID;
import teacher.domain.Teacher;

@Entity
@Table(name = "academic_degrees")
public class AcademicDegree {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @ManyToOne(optional = false)
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @Enumerated(EnumType.STRING)
    @Column(name = "category", nullable = false)
    private DegreeCategory category;

    @Column(nullable = false)
    private String institution;

    @Column(nullable = false)
    private String course;

    @Column(name = "degree_year", nullable = false)
    private Integer degreeYear;

    public AcademicDegree() {}

    public AcademicDegree(Teacher teacher, DegreeCategory category, String institution, String course, Integer degreeYear) {
        this.teacher = teacher;
        this.category = category;
        this.institution = institution;
        this.course = course;
        this.degreeYear = degreeYear;
    }

    public UUID getId() { return id; }
    public Teacher getTeacher() { return teacher; }
    public void setTeacher(Teacher teacher) { this.teacher = teacher; }
    public DegreeCategory getCategory() { return category; }
    public void setCategory(DegreeCategory category) { this.category = category; }
    public String getInstitution() { return institution; }
    public void setInstitution(String institution) { this.institution = institution; }
    public String getCourse() { return course; }
    public void setCourse(String course) { this.course = course; }
    public Integer getDegreeYear() { return degreeYear; }
    public void setDegreeYear(Integer degreeYear) { this.degreeYear = degreeYear; }
}

