package tz.co.fasthub.survey.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import tz.co.fasthub.survey.domain.User;
import tz.co.fasthub.survey.repository.UserRepository;
import tz.co.fasthub.survey.service.UserService;

/**
 * Created by root on 7/27/17.
 */
@Service
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;

    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);


    @Autowired
    public UserServiceImpl(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCpassword(bCryptPasswordEncoder.encode(user.getCpassword()));
        user.setRole("ADMIN");
       //user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public void update(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        user.setCpassword(bCryptPasswordEncoder.encode(user.getCpassword()));
        //user.setRoles(new HashSet<>(roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User findByUsernameAndPassword(String username, String password){
        return userRepository.findByUsernameAndPassword(username,password);
    }

    @Override
    public User validateUser(String username, String password) {
        User checkUser = findByUsernameAndPassword(username,password);//huyu yuko kwenye db
        logger.info("*******"+checkUser.getUsername());
        if(checkUser.getUsername()!=null){
            if(bCryptPasswordEncoder.encode(password).equals(checkUser.getPassword())){
                logger.info("checkUserNAme = "+checkUser.getUsername());
                logger.info(String.format("Auto login %s successfully! .. moving on", username));
                return userRepository.findByUsernameAndPassword(username, password);
            }
            else {
                return checkUser;
            }
        }
        return checkUser;
    }

    @Override
    public User encryptUserInput(String username, String password) {
        User user = new User();
        String encryptedUsername = bCryptPasswordEncoder.encode(username);
        String encryptedPassword = bCryptPasswordEncoder.encode(password);
        return null;
    }

    @Override
    public Iterable<User> listAllCustomers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public void deleteUser(Long id) {
        userRepository.delete(id);

    }

}
