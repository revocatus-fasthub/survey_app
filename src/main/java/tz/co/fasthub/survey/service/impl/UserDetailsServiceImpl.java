package tz.co.fasthub.survey.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.repository.UserRepository;
import tz.co.fasthub.survey.repository.UserRolesRepository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 7/27/17.
 */
@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;
    private final UserRolesRepository userRoleRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, UserRolesRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    //@Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);
        if(user==null){
            throw new UsernameNotFoundException("No user present with username: "+username);
        }else {
            List<GrantedAuthority> auths = new ArrayList<GrantedAuthority>();
            GrantedAuthority auth = new SimpleGrantedAuthority("ROLE_ADMIN");
            auths.add(auth);
         //   List<User> users = userRepository.findByUsername(username);
            List<String> userRoles = userRoleRepository.findRoleByUsername(username);
            return new CustomUserDetails(user,userRoles);
        }
  }

}
