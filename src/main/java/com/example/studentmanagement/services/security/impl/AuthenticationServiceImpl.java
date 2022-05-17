package com.example.studentmanagement.services.security.impl;

import com.example.studentmanagement.data.dtos.StudentRegisterModel;
import com.example.studentmanagement.data.dtos.TeacherRegisterModel;
import com.example.studentmanagement.data.dtos.UserLoginModel;
import com.example.studentmanagement.data.dtos.UserRegisterModel;
import com.example.studentmanagement.data.dtos.UserRolesModel;
import com.example.studentmanagement.data.entities.Role;
import com.example.studentmanagement.data.entities.RoleType;
import com.example.studentmanagement.data.entities.Student;
import com.example.studentmanagement.data.entities.Teacher;
import com.example.studentmanagement.data.entities.User;
import com.example.studentmanagement.data.repositories.RoleRepository;
import com.example.studentmanagement.data.repositories.StudentRepository;
import com.example.studentmanagement.data.repositories.TeacherRepository;
import com.example.studentmanagement.data.repositories.UserRepository;
import com.example.studentmanagement.data.security.UserDetailsImpl;
import com.example.studentmanagement.services.security.AuthenticationService;
import com.example.studentmanagement.services.security.HashingService;
import com.example.studentmanagement.web.payload.JwtResponse;
import com.example.studentmanagement.web.security.utils.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationServiceImpl implements AuthenticationService {
    private static final RoleType DEFAULT_ROLE = RoleType.USER;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final HashingService hashingService;
    private final StudentRepository studentRepository;
    private final TeacherRepository teacherRepository;
    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtUtils;

    public AuthenticationServiceImpl(UserRepository userRepository,
                                     RoleRepository roleRepository,
                                     HashingService hashingService,
                                     StudentRepository studentRepository,
                                     TeacherRepository teacherRepository, AuthenticationManager authenticationManager, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.hashingService = hashingService;
        this.studentRepository = studentRepository;
        this.teacherRepository = teacherRepository;
        this.authenticationManager = authenticationManager;
        this.jwtUtils = jwtUtils;
    }

    @Override
    public JwtResponse login(UserLoginModel model) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(model.getUsername(), model.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication);

        UserDetailsImpl user = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = user.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.toList());

        return new JwtResponse(jwt, new UserRolesModel(user.getId(), roles));
    }

    @Override
    public void registerTeacher(TeacherRegisterModel model) {
        User user = createUser(model);
        Teacher teacher = new Teacher();
        teacher.setName(model.getName());
        teacher.setDegree(model.getDegree());
        teacher.setUser(user);
        teacherRepository.save(teacher);
    }

    @Override
    public void registerStudent(StudentRegisterModel model) {
        User user = createUser(model);
        Student student = new Student();
        student.setName(model.getName());
        student.setAge(model.getAge());
        student.setUser(user);
        studentRepository.save(student);
    }

    private User createUser(UserRegisterModel model) {
        User user = new User();
        user.setUsername(model.getUsername());
        user.setPassword(hashingService.hash(model.getPassword()));
        Set<Role> userRoles = new HashSet<>();
        userRoles.add(createDefaultRole());
        user.setRoles(userRoles);
        userRepository.save(user);
        return user;
    }

    private Role createDefaultRole() {
        Role role = roleRepository.findByName(DEFAULT_ROLE.name());
        if (role == null) {
            role = roleRepository.save(new Role(DEFAULT_ROLE));
        }

        return role;
    }
}
