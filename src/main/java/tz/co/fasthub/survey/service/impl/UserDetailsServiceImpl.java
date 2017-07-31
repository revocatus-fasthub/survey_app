package tz.co.fasthub.survey.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.repository.UserRepository;
import tz.co.fasthub.survey.repository.UserRolesRepository;

import java.util.Arrays;
import java.util.List;

/**
 * Created by root on 7/27/17.
 */
@Service("customUserDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = LoggerFactory.getLogger(UserDetailsServiceImpl.class);


    private final UserRepository userRepository;
    private final UserRolesRepository userRoleRepository;

    @Autowired
    public UserDetailsServiceImpl(UserRepository userRepository, UserRolesRepository userRoleRepository) {
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        try {
            User user = userRepository.findByUsername(username);
            if (user == null) {
                logger.debug("user not found with the provided username");
                throw new UsernameNotFoundException("User not found");
            }
            logger.debug(" user from username " + user.toString());
            return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), getAuthority());
        }
        catch (Exception e){
            throw new UsernameNotFoundException("User not found");
        }
  }

    private List getAuthority() {
        return Arrays.asList(new SimpleGrantedAuthority("ROLE_ADMIN"));
    }

}
