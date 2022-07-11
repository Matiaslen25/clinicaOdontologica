package com.example.ClinicaOdontologica.configuration;

import com.example.ClinicaOdontologica.model.AppUser;
import com.example.ClinicaOdontologica.model.AppUserRoles;
import com.example.ClinicaOdontologica.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements ApplicationRunner {

    private UserRepository userRepository;
    @Autowired
    public DataLoader(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String password = passwordEncoder.encode("lalala123");

        userRepository.save(new AppUser("Matias", "mati25", "matiaslen25@gmail.com", password, AppUserRoles.ADMIN));
    }
}
