package com.naldojesse.loginreg.services;

import com.naldojesse.loginreg.models.User;
import com.naldojesse.loginreg.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import org.springframework.stereotype.Service;

//import javax.management.relation.Role;
import com.naldojesse.loginreg.models.Role;
import java.util.ArrayList;
import java.util.List;


/** A custom class is created to implement UserDetailsService interface in order to authenticate and authorize a user
 * We will use this class to load the user according to the username that is passed from the form.
 */

@Service
public class UserDetailsServiceImplementation implements UserDetailsService {
    private UserRepository userRepository;

    /**
     * Injecting the user repository for use in the class
     * @param userRepository the user repository
     */
    public UserDetailsServiceImplementation(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Finds the user by their username.
     * @param username the username string passed in to be checked in the database via the repository
     * @return the user object with it's username, password, and the roles that the user has
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found");
        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthorities(user));
    }

    /**
     * Returns a list of authorities/permissions for a specific user.
     * Using Spring Security for authorization, we need to get the name of the possible roles for current
     * user from db and create a new 'SimpleGrantedAuthority' object with the user's roles.
     * @param user
     * @return
     */

    private List<GrantedAuthority> getAuthorities(User user) {
        List<GrantedAuthority> authorities = new ArrayList<>();
        for (Role role : user.getRoles()) {
            GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(role.getName());
            authorities.add(grantedAuthority);
        }
        return authorities;
    }


}


