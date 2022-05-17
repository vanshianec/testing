package com.example.studentmanagement.data.entities;

import com.example.studentmanagement.data.entities.base.SchoolEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
@Getter
@Setter
public class Student extends SchoolEntity {
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column
    private int age;

    @JsonIgnore
    @OneToMany(mappedBy = "student")
    private List<AcademicRecord> courseGrades;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Student() {
        this.courseGrades = new ArrayList<>();
    }
}
