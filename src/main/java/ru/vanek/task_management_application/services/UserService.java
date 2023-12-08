package ru.vanek.task_management_application.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.UserResponse;
import ru.vanek.task_management_application.models.User;

import java.util.List;
@Service
public interface UserService extends UserDetailsService {
    public List<UserResponse> findAll(int page);
    public UserResponse findOne(int id);
    public User create(UserRequest userRequest);
    public void update(int userId, UserRequest userRequest, String userEmail);
    public void delete(int id, String userEmail);
    public User findByEmail(String email);
    public UserDetails loadUserByUsername(String username);
    public boolean isEnoughRules(int userId, String username);
}
