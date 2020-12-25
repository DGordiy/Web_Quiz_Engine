package engine;

//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;


@RestController
public class RegistrationUserController {
    //final static Logger logger = LoggerFactory.getLogger(User.class);
    @Autowired
    UserRepository userRepository;

    //Register a user
    @PostMapping("/api/register")
    public boolean addUser(@RequestBody @Valid UserEntity newUser)
            throws ResponseStatusException {
        if (userRepository.findByEmail(newUser.getEmail()) != null) {
            //logger.info(String.format("This user %s already exists. returning 401", newUser.getEmail()));
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "This user already exists");
        }
        //logger.info(String.format("Saving user %s !", newUser.getEmail()));
        //logger.info(String.format("Is the password already encrypted?: %s ", newUser.getPassword()));
        newUser.encryptPassword();
        //logger.info(String.format("Is the password now encrypted?: %s ", newUser.getPassword()));
        userRepository.save(newUser);
        return true;
    }

}