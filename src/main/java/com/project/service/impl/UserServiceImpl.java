package com.project.service.impl;

import com.project.model.Doctor;
import com.project.model.Patient;
import com.project.model.User;
import com.project.model.dto.UserDTO;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.PatientRepository;
import com.project.persistence.impl.SpecialtyRepository;
import com.project.persistence.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;

@Service(value = "service")
public class UserServiceImpl implements com.project.service.UserService, UserDetailsService {

    final static Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

    @Autowired
    private DoctorRepository repoDoctor;

    @Autowired
    private UserRepository repoUser;

    @Autowired
    private PatientRepository repoPatient;

    @Autowired
    private SpecialtyRepository repoSpecialty;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
       User user=repoUser.getByEmail(email);
       if(user==null){
           throw new UsernameNotFoundException("Invalid username or password.");
       }
       return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),getAuthority(user));
    }


    private Set<SimpleGrantedAuthority> getAuthority(User user) {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        authorities.add(new SimpleGrantedAuthority("ROLE_" + user.getRole()));
        return authorities;
    }

    @Override
    public List<User> findUsers() {
        List<User> list=new ArrayList<>();
        repoUser.getAll().forEach(list::add);
        return list;
    }

    @Override
    public User save(UserDTO user) {
        User user1=new User();
        user1.setEmail(user.getEmail());
        user1.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user1.setRole(user.getRole());
        switch (user.getRole().toUpperCase()){
            case "DOCTOR":
                Doctor doctor=new Doctor();
                doctor.setEmail(user.getEmail());
                doctor.setFirstName(user.getFirstName());
                doctor.setLastName(user.getLastName());
                doctor.setSpecialty(repoSpecialty.findOne(user.getSpecialty()));
                doctor.setGender(user.getGender().toLowerCase());
                repoDoctor.save(doctor);
                System.out.println(doctor.toString()+" saved");
                break;
            case "PATIENT":
                Patient patient=new Patient();
                patient.setFirstName(user.getFirstName());
                patient.setLastName(user.getLastName());
                patient.setEmail(user.getEmail());
                patient.setGender(user.getGender().toLowerCase());
                repoPatient.save(patient);
                System.out.println(patient.toString()+" saved");
                break;
                default:
                    break;
        }
        return repoUser.save(user1);
    }

    @Override
    public User findUser(String email) {
        return repoUser.getByEmail(email);
    }

}
