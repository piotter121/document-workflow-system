package pl.edu.pw.ee.pyskp.documentworkflow.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.edu.pw.ee.pyskp.documentworkflow.domain.User;
import pl.edu.pw.ee.pyskp.documentworkflow.service.UserService;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;

/**
 * Created by piotr on 20.12.16.
 */
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private static final Logger logger = Logger.getLogger(UserDetailsServiceImpl.class);

    private final UserService userService;

    @Autowired
    public UserDetailsServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        logger.debug("Wyszukuję użytkownika o nazwie " + username);
        Optional<User> userOptional = userService.getUserByLogin(username);
        if (!userOptional.isPresent()) {
            throw new UsernameNotFoundException("Nie znaleziono użytkownika o nazwie " + username);
        }
        User user = userOptional.get();
        Collection<GrantedAuthority> authorities
                = Collections.singleton(new SimpleGrantedAuthority(user.getRole().name()));
        return new org.springframework.security.core.userdetails.User(
                user.getLogin(), user.getPassword(), authorities
        );
    }
}
