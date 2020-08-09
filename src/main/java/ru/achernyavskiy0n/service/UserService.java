package ru.achernyavskiy0n.service;

import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import ru.achernyavskiy0n.model.StuffUser;
import ru.achernyavskiy0n.repository.StuffUserRepository;

import java.util.Collections;
import java.util.List;

/**
 * 09.08.2020
 *
 * @author a.chernyavskiy0n
 */
@Service
@Slf4j
@FieldDefaults(makeFinal=true, level= AccessLevel.PRIVATE)
@RequiredArgsConstructor
public class UserService implements UserDetailsService {

    @NonNull StuffUserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        List<SimpleGrantedAuthority> dummyAuthorityForExample =
                Collections.singletonList(new SimpleGrantedAuthority("ROLE_ADMIN"));
        StuffUser mongoUser = repository.findByStuffUserName(s);
        return new User(mongoUser.getUsername(), mongoUser.getPassword(), dummyAuthorityForExample);
    }
}
