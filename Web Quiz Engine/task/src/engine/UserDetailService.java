package engine;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailService implements UserDetailsService {
    //final static Logger logger = LoggerFactory.getLogger(UserDataService.class);
    @Autowired
    UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        UserEntity user = userRepository.findByEmail(email);
        //logger.info(String.format("Finding user by email %s", user.getEmail()));
        if (user == null) {
            throw new UsernameNotFoundException(String.format("User was not found by email %s!", email));
        }
        //logger.info(String.format("Found user with password '%s'!", user.getPassword()));
        return new UserData(user);
    }
}