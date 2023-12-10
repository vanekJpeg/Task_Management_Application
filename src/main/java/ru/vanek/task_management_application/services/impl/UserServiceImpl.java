package ru.vanek.task_management_application.services.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.vanek.task_management_application.dtos.requests.UserRequest;
import ru.vanek.task_management_application.dtos.responses.UserResponse;
import ru.vanek.task_management_application.exceptions.NotEnoughRulesException;
import ru.vanek.task_management_application.models.User;
import ru.vanek.task_management_application.repositories.RoleRepository;
import ru.vanek.task_management_application.repositories.UsersRepository;
import ru.vanek.task_management_application.services.UserService;
import ru.vanek.task_management_application.utils.UserConverter;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)

public class UserServiceImpl implements UserService {
    private final UsersRepository userRepository;
    private final UserConverter userConverter;
    private final RoleRepository roleRepository;
    @Override
    public List<UserResponse> findAll(int page) {
        return userRepository.findAll(PageRequest.of(page,10)).getContent()
                .stream().map(userConverter::convertUserToResponse).collect(Collectors.toList());
    }
    @Override
    public UserResponse findOne(int id) {
        return userConverter.convertUserToResponse(userRepository.findById(id)
                .orElseThrow(()->new NoSuchElementException("Пользователя с идентификатором "+id+" - не существует")));
    }
    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email).orElse(null);//так надо
    }
    @Override
    @Transactional
    public User create(UserRequest userRequest) {
        User user = userConverter.convertToUser(userRequest);
        user.setRoles(List.of(roleRepository.findByName("ROLE_USER").get()));
       return  userRepository.save(user);
    }
    @Override
    @Transactional
    public void update(int userId, UserRequest userRequest, String userEmail) {
        if(isEnoughRules(userId,userEmail)){
            User userToUpdate = userRepository.findById(userId).
                    orElseThrow(()->new NoSuchElementException("Пользователя с идентификатором "+userId+" - не существует"));
            User changes = userConverter.convertToUser(userRequest);
            userToUpdate.setUsername(changes.getUsername());
            userToUpdate.setPassword(changes.getPassword());
            userToUpdate.setEmail(changes.getEmail());
            userRepository.save(userToUpdate);
        }

    }
    @Override
    @Transactional
    public void delete(int userId, String userEmail) {
        if(isEnoughRules(userId,userEmail)){
            userRepository.delete(userRepository.findById(userId).orElseThrow());
        }else throw new NotEnoughRulesException("Удалять и изменять пользователя может только сам пользоваетль");
    }

    @Override
    public UserDetails loadUserByUsername(String email) {
        User user =userRepository.findByEmail(email).orElseThrow(()->new UsernameNotFoundException(String.format(
                "Пользователь %s не найден",email)));
        return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(),user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList()));
    }

    @Override
    public boolean isEnoughRules(int userId, String email){
       return email.equals(userRepository.findById(userId)
               .orElseThrow(()->new NoSuchElementException("Пользователя с идентификатором "+userId+" - не существует")).getEmail());
    }

}
