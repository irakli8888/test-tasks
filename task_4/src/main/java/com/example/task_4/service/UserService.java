package com.example.task_4.service;

import com.example.task_4.model.User;
import com.example.task_4.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {

    @Autowired
    private final UserRepository userRepository;

    @Bean
    public BCryptPasswordEncoder passwordEncoder2() {
        return new BCryptPasswordEncoder();
    }

    @Transactional
    public List<User> findAll(){
        return userRepository.findAll();
    }

    @Transactional
    public ResponseEntity saveUser(User user){
        if(userRepository.findByUsername(user.getUsername())==null) {
            User usr = new User();
            usr.setActive(user.isActive());
            usr.setUsername(user.getUsername());
            usr.setPassword(passwordEncoder2().encode(user.getPassword()));
            usr.setRole(user.getRole());
            usr.setPerson(user.getPerson());
            userRepository.save(usr);
            return new ResponseEntity(HttpStatus.OK);
        }
        else return ResponseEntity.status(HttpStatus.NOT_MODIFIED).body(null);
    }

}
