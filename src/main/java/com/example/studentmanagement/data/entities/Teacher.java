package com.example.studentmanagement.data.entities;

import com.example.studentmanagement.data.entities.base.SchoolEntity;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.MapsId;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "teachers")
@Getter
@Setter
public class Teacher extends SchoolEntity {
    @Id
    @Column(name = "user_id")
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private Degree degree;

    @JsonIgnore
    @OneToMany(mappedBy = "teacher")
    private List<Course> courses;

    @OneToOne
    @MapsId
    @JoinColumn(name = "user_id")
    private User user;

    public Teacher() {
        this.courses = new ArrayList<>();
    }
}
