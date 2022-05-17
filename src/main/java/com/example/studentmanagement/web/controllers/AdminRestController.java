package com.example.studentmanagement.web.controllers;

import com.example.studentmanagement.data.dtos.UserAdminPageModel;
import com.example.studentmanagement.data.dtos.UserRolesModel;
import com.example.studentmanagement.data.entities.Course;
import com.example.studentmanagement.data.entities.RoleType;
import com.example.studentmanagement.services.security.UserService;
import com.example.studentmanagement.services.CourseService;
import com.example.studentmanagement.services.StudentService;
import com.example.studentmanagement.services.TeacherService;
import com.example.studentmanagement.web.payload.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/admin")
public class AdminRestController {

    private final CourseService courseService;
    private final UserService userService;

    @Autowired
    public AdminRestController(CourseService courseService, UserService userService, TeacherService teacherService, StudentService studentService) {
        this.courseService = courseService;
        this.userService = userService;
    }

    @GetMapping("/get-users")
    public ResponseEntity<List<UserAdminPageModel>> getUsersWithoutAdmins() {
        return new ResponseEntity<>(userService.getNonAdminUsers(), HttpStatus.OK);
    }

    @PostMapping("/set-teacher")
    public ResponseEntity<String> setTeacher(@RequestParam(name = "courseId") Long courseId,
                                             @RequestParam(name = "teacherId") Long teacherId) {
        courseService.setTeacher(courseId, teacherId);
        return ResponseEntity.ok("Teacher successfully added to course");
    }

    @PostMapping("/edit-role")
    public ResponseEntity<?> setRoles(@RequestBody UserRolesModel model) {
        List<RoleType> roles = model.getRoles()
                .stream()
                .map(RoleType::valueOf)
                .collect(Collectors.toList());

        userService.setRoles(model.getId(), roles);
        ResponseMessage response = new ResponseMessage();
        response.setMessage("User roles successfully changed");
        return ResponseEntity.ok(response);
    }

    @PostMapping("/add-course")
    public ResponseEntity<Course> addCourse(@RequestBody Course course) {
        courseService.add(course);
        return ResponseEntity.ok(course);
    }
}
