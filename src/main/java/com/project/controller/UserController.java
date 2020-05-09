package com.project.controller;

import com.project.logging.AbstractLogger;
import com.project.logging.Logger;
import com.project.model.User;
import com.project.model.dto.UserDTO;
import com.project.security.AuthToken;
import com.project.security.TokenProvider;
import com.project.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.text.MessageFormat;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class UserController {

    private AbstractLogger logger = Logger.getLogger();

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private TokenProvider jwtTokenUtil;

    @Autowired
    private UserServiceImpl service;

    @PreAuthorize("hasRole('PACIENT')")
    ////@PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @RequestMapping(value = "/users", method = RequestMethod.GET)
    public List<User> getAll(){
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved users",UserController.class));
        return service.findUsers();
    }

    @RequestMapping(value = "/user/{email}", method = RequestMethod.GET)
    public ResponseEntity<?> getUser(@PathVariable String email){
        User user= service.findUser(email);
        if(user==null){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve user failed",UserController.class));
            return new ResponseEntity<>("Not found!",HttpStatus.NOT_FOUND);
        }
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Retrieved user",UserController.class));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }

    @RequestMapping(value="/signup", method = RequestMethod.POST)
    public ResponseEntity<?> saveUser(@RequestBody UserDTO userDTO){
        try{
            User user=service.save(userDTO);
            if(user==null){
                logger.log(AbstractLogger.ERROR, "User sign up failed due to email already in use.");
                return new ResponseEntity<>("Email already in use!", HttpStatus.BAD_REQUEST);
            }
            final Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            userDTO.getEmail(),
                            userDTO.getPassword()
                    )
            );
            SecurityContextHolder.getContext().setAuthentication(authentication);
            final String token = jwtTokenUtil.generateToken(authentication);
            logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - User signed up",UserController.class));
            return ResponseEntity.ok(new AuthToken(token));
        }catch(Exception ex){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - User sign up failed with message {1}",UserController.class,ex.getMessage()));
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<?> register(@RequestBody User loginUser) throws AuthenticationException {

        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginUser.getEmail(),
                        loginUser.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        final String token = jwtTokenUtil.generateToken(authentication);
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - User registered",UserController.class));
        return ResponseEntity.ok(new AuthToken(token));
    }

    @RequestMapping(value = "/user", method = RequestMethod.PUT)
    public ResponseEntity<?> changePassword(@RequestBody UserDTO userToModify){
        User user=service.updateUser(userToModify);
        if(user==null){
            logger.log(AbstractLogger.ERROR, MessageFormat.format("{0} - Retrieve user with email {1} failed",UserController.class,userToModify.getEmail()));
            return new ResponseEntity<>("Incorrect password!", HttpStatus.BAD_REQUEST);
        }
        logger.log(AbstractLogger.INFO, MessageFormat.format("{0} - Changed user\'s password",UserController.class));
        return new ResponseEntity<>(user,HttpStatus.OK);
    }
}
