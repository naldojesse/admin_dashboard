package com.naldojesse.loginreg.services;

import com.naldojesse.loginreg.models.User;
import com.naldojesse.loginreg.repositories.RoleRepository;
import com.naldojesse.loginreg.repositories.UserRepository;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    /**
     * Class constructor creating instances
     *
     * @param userRepository the user repository interface
     * @param bCryptPasswordEncoder module used to encode passwords
     * @param roleRepository the role repository interface
     */

    public UserService(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.roleRepository = roleRepository;
    }

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public void saveWithUserRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_USER"));
        userRepository.save(user);
    }

    public void saveUserWithAdminRole(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
        userRepository.save(user);
    }

    public void changeUserRoleToAdmin(Long id) {
        Optional<User> userExists = userRepository.findById(id);
        if (userExists.isPresent()) {
            User user = userExists.get();
            user.setRoles(roleRepository.findByName("ROLE_ADMIN"));
            userRepository.save(user);
        }

//        userRepository.save(user);
    }

//    public User findById(Long id) {
//        return userRepository.findById();
//    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void destroyUser(Long id) {
        userRepository.deleteById(id);
    }



}
