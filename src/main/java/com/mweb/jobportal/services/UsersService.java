package com.mweb.jobportal.services;

import com.mweb.jobportal.entity.JobSeekerProfile;
import com.mweb.jobportal.entity.RecruiterProfile;
import com.mweb.jobportal.entity.Users;
import com.mweb.jobportal.entity.UsersType;
import com.mweb.jobportal.repository.JobSeekerProfileRepository;
import com.mweb.jobportal.repository.RecruiterProfileRepository;
import com.mweb.jobportal.repository.UsersRepository;
import com.mweb.jobportal.repository.UsersTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class UsersService {

    private final UsersRepository usersRepository;
    private final JobSeekerProfileRepository jobSeekerProfileRepository;
    private final RecruiterProfileRepository recruiterProfileRepository;
    private final UsersTypeRepository usersTypeRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    public UsersService(
            UsersRepository usersRepository,
            JobSeekerProfileRepository jobSeekerProfileRepository,
            RecruiterProfileRepository recruiterProfileRepository,
            UsersTypeRepository usersTypeRepository,
            PasswordEncoder passwordEncoder
    ) {
        this.usersRepository = usersRepository;
        this.jobSeekerProfileRepository = jobSeekerProfileRepository;
        this.recruiterProfileRepository = recruiterProfileRepository;
        this.usersTypeRepository = usersTypeRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Users addNew(Users users) {

        UsersType type = usersTypeRepository.findById(users.getUserTypeId())
                .orElseThrow(() -> new RuntimeException("Invalid UsersType ID"));

        users.setUsersTypeId(type);

        users.setActive(true);
        users.setRegistrationDate(new Date(System.currentTimeMillis()));
        users.setPassword(passwordEncoder.encode(users.getPassword()));
        Users savedUser = usersRepository.save(users);
        int userTypeId = type.getUserTypeId();
        if (userTypeId == 1) {
            recruiterProfileRepository.save(new RecruiterProfile(savedUser));
        } else {
            jobSeekerProfileRepository.save(new JobSeekerProfile(savedUser));
        }

        return savedUser;
    }

    public Optional<Users> getUserByEmail(String email) {
        return usersRepository.findByEmail(email);
    }

    public Object getCurrentUserProfile() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String username = authentication.getName();
            Users users = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
            int userId = users.getUserId();
            if(authentication.getAuthorities().contains(new SimpleGrantedAuthority("Recruiter"))){
                RecruiterProfile recruiterProfile = recruiterProfileRepository.findById(userId).orElse(new RecruiterProfile());
                return recruiterProfile;
            }else {
                JobSeekerProfile jobSeekerProfile = jobSeekerProfileRepository.findById(userId).orElse(new JobSeekerProfile());
                return jobSeekerProfile;

            }
        }
        return null;
    }

    public Users getCurrentUser() {

       Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
       if (!(authentication instanceof AnonymousAuthenticationToken)){
           String username = authentication.getName();
           Users user = usersRepository.findByEmail(username).orElseThrow(() -> new UsernameNotFoundException("Could not found " + "user"));
           return user;
       }
       return null;
    }

    public Users findByEmail(String currentUsername) {
        return usersRepository.findByEmail(currentUsername).orElseThrow(()-> new UsernameNotFoundException("User not found"));
    }
}
