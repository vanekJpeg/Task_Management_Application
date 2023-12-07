package ru.vanek.task_management_application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.access.prepost.PreFilter;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.UserResponse;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.repositories.UsersRepository;
import ru.vanek.task_management_application.services.UserService;
import ru.vanek.task_management_application.utils.UserConverter;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class UserServiceImpl implements UserService {
    private final UsersRepository userRepository;
    private final UserConverter userConverter;

    @Override
    public List<UserResponse> findAll(int page) {
        return userRepository.findAll(PageRequest.of(page,10)).getContent()
                .stream().map(userConverter::convertUserToResponse).collect(Collectors.toList());
    }

    @Override
    public UserResponse findOne(int id) {
        return userConverter.convertUserToResponse(userRepository.findById(id).orElseThrow());//todo userNotfound
    }
    @Override
    public UserResponse findByEmail(String email) {
        return userConverter.convertUserToResponse(userRepository.findByEmail(email).orElseThrow());//todo userNotfound;
    }

    @Override
    @Transactional
    public User create(UserRequest userRequest) {
        User user = userConverter.convertToUser(userRequest);
       return  userRepository.save(user);
    }

    @Override
    @Transactional
    @PreFilter("@userServiceImpl.isEnoughRules(userId,principal.username)")
    public void update(int userId, UserRequest userRequest) {
        User userToUpdate = userRepository.findById(userId).orElseThrow();//todo userNotfound
        User changes = userConverter.convertToUser(userRequest);
        userToUpdate.setUsername(changes.getUsername());
        userToUpdate.setPassword(changes.getPassword());
        userToUpdate.setEmail(changes.getEmail());
    }

    @Override
    @Transactional
    @PreFilter("@userServiceImpl.isEnoughRules(userId,principal.username)")
    public void delete(int userId) {
        userRepository.delete(userRepository.findById(userId).orElseThrow());//add exception
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user =userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(
                "Пользователь %s не найден",email)));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.getCreatedTasks().stream().map(task -> new SimpleGrantedAuthority(task.getHeader())).collect(Collectors.toList()));
    }

    @Override
    public boolean isEnoughRules(int userId, String email){
       return email.equals(userRepository.findById(userId).orElseThrow().getEmail());
    }

}
