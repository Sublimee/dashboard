package com.dashboard.service;


import com.dashboard.domain.Role;
import com.dashboard.domain.User;
import com.dashboard.exception.UserAlreadyExistsException;
import com.dashboard.exception.UserNotFoundException;
import com.dashboard.repository.RoleRepository;
import com.dashboard.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UserService implements UserDetailsService {

    private static final Long USER_ROLE_ID = 1L;

    private final PasswordEncoder encoder;

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepository.findByUsername(username);
    }

    public User findById(Long userId) {
        return userRepository.findById(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }

    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    public User save(final User user) {
        User userByUsername = userRepository.findByUsername(user.getUsername());
        if (userByUsername != null) {
            throw new UserAlreadyExistsException(user.getUsername());
        }

        Role role = roleRepository.findById(USER_ROLE_ID).get();
        user.setRole(role);
        user.setPassword(encoder.encode(user.getPassword()));
        return userRepository.save(user);
    }
}