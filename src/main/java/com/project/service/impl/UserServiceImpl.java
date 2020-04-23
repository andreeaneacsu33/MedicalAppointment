package com.project.service.impl;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.Doctor;
import com.project.model.Patient;
import com.project.model.User;
import com.project.model.dto.UserDTO;
import com.project.persistence.impl.DoctorRepository;
import com.project.persistence.impl.PatientRepository;
import com.project.persistence.impl.SpecialtyRepository;
import com.project.persistence.impl.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessageRemovedException;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service(value = "service")
public class UserServiceImpl implements com.project.service.UserService, UserDetailsService {

    private AbstractLogger logger = Logger.getLogger();

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

    @Autowired
    public JavaMailSender emailSender;

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
        User saved=repoUser.save(user1);
        sendEmail(user);
        return saved;
    }

    private void sendEmail(UserDTO user){
        try {
            MimeMessage message=emailSender.createMimeMessage();
            MimeMessageHelper helper=new MimeMessageHelper(message,true);
            helper.setTo(user.getEmail());
            helper.setSubject("User successfully created");
            String text="<h2>\n" +
                    "Welcome to MyDOC!\n" +
                    "</h2>\n" +
                    "<span>\n" +
                    "Thank you for signing up to MyDOC! Hi "+ user.getFirstName()+" "+user.getLastName()+ ", we’re glad you’re here! You can book new appointments, review your experience and many more. Let's make a difference in the medical appointment system!\n" +
                    "</span>\n" +
                    "<br/>\n" +
                    "<h4>\n" +
                    "MyDOC team\n" +
                    "</h4>";
            message.setText(text);
            emailSender.send(message);
            System.out.println("Email successfully sent!");
        } catch (MessagingException exception ) {
            exception.printStackTrace();
        }
    }

    @Override
    public User findUser(String email) {
        return repoUser.getByEmail(email);
    }

}
