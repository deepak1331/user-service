package com.learn.userservice.controller;

import com.learn.userservice.entity.User;
import com.learn.userservice.exception.ResourceNotFoundException;
import com.learn.userservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.xml.ws.Response;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService service;

    @PostMapping(path = "save", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUser(@RequestBody User user) {
        return new ResponseEntity<>(service.saveUser(user), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.POST, path = "/saveAll", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> saveUsers(@RequestBody List<User> UserList) {
        return new ResponseEntity(service.saveUsers(UserList), HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/fetch/{id}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getUserWithDepartmentById(@PathVariable Long id) throws ResourceNotFoundException {
        return new ResponseEntity(service.getUserWithDepartmentById(id), HttpStatus.OK);
    }

    @RequestMapping(method = RequestMethod.GET, path = "/fetchAll",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> findAllUsers() throws ResourceNotFoundException {

        return new ResponseEntity(service.findAllUsers(), HttpStatus.OK);
    }

//    @RequestMapping(method = RequestMethod.GET, path = "/fetchAll/page/{pageNo}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getAllUsers(@PathVariable int pageNo) throws ResourceNotFoundException {
//
//        return new ResponseEntity(service.findUsers(pageNo), HttpStatus.OK);
//    }
//
//    @RequestMapping(method = RequestMethod.GET, path = "/fetch/{id}",
//            produces = MediaType.APPLICATION_JSON_VALUE)
//    public ResponseEntity<?> getUserById(@PathVariable Long id) throws ResourceNotFoundException {
//
//        return new ResponseEntity(service.findUserById(id), HttpStatus.OK);
//    }

}
