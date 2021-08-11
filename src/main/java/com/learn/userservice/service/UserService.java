package com.learn.userservice.service;

import com.learn.userservice.ValueObjects.Department;
import com.learn.userservice.ValueObjects.ResponseTemplateVO;
import com.learn.userservice.entity.User;
import com.learn.userservice.exception.ResourceNotFoundException;
import com.learn.userservice.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class UserService {

//    @Value("${department-service-url}")
//    private String DEPARTMENT_SERVICE_URL;

    @Autowired
    private UserRepository repository;

    @Autowired
    private RestTemplate restTemplate;

    public User saveUser(User user) {
        log.info("Saving single User info");
        return repository.save(user);
    }

    public List<User> saveUsers(List<User> userList) {
        log.info("Saving multiple {} User's Info", userList.size());
        return repository.saveAll(userList);
    }

    public ResponseTemplateVO getUserWithDepartmentById(Long id) {
        log.info("Inside getUserWithDepartmentById -> Fetching User With Department By Id: " + id);
        ResponseTemplateVO vo = new ResponseTemplateVO();
        User user = repository.findById(id).
                orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" + id));
        String deptUrl = null;
        if (user != null) {
//            deptUrl =  "http://"+DEPARTMENT-SERVICE + "/fetch/" + user.getDepartmentId();
            log.info("User found, with Dept ID: {}", user.getDepartmentId());
        }
        //since we have service-registry we can directly use DEPARTMENT-SERVICE in the url, instead of localhost:9001
        Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/department/fetch/" + user.getDepartmentId(), Department.class);
        vo.setUser(user);
        vo.setDepartment(department);
        return vo;
    }

    public List<ResponseTemplateVO> findAllUsers() {
        List<ResponseTemplateVO> voList = new ArrayList<>();
        List<User> userList = repository.findAll();
        userList.forEach(user -> {
            Department department = restTemplate.getForObject("http://DEPARTMENT-SERVICE/department/fetch/" + user.getDepartmentId(), Department.class);
            voList.add(new ResponseTemplateVO(user, department));
        });
        return voList;
    }

//    public User findUserById(Long id) throws ResourceNotFoundException {
//        log.info("Searching for User with ID : {}", id);
//        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("User not found with ID:" + id));
//    }
//
//    public Page<User> findUsers(int pageNo) {
//        log.info("Fetching all Users list with Pagination, Page : {} sorted by FirstName and then LastName", pageNo);
//        Pageable find20RecordsSortedByFirstAndLastName = PageRequest.of(pageNo, 20, Sort.by("firstName")
//                .and(Sort.by("lastName")));
//        return repository.findAll(find20RecordsSortedByFirstAndLastName);
//    }
}
