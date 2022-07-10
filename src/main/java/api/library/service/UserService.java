package api.library.service;

import api.library.model.User;
import api.library.model.request.UserCreateRequest;
import api.library.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;

    public User readUserByUsername(String username){
        return userRepository.findByUsername(username).orElseThrow(EntityNotFoundException::new);
    }

    public void createUser(UserCreateRequest userCreateRequest){
        User user = new User();
        Optional<User> byUsername = userRepository.findByUsername(userCreateRequest.getUsername());

        if(byUsername.isPresent()){
            throw new RuntimeException("User already registered.");
        }

        user.setUsername(userCreateRequest.getUsername());
        user.setPassword(passwordEncoder.encode(userCreateRequest.getPassword()));
        user.setRole(userCreateRequest.getRole());
        userRepository.save(user);
    }
}
