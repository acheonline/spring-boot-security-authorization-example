package ru.achernyavskiy0n.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

/**
 * 09.08.2020
 *
 * @author a.chernyavskiy0n
 */
public interface UserDetailsService {
    UserDetails loadUserByUsername (String s) throws UsernameNotFoundException;
}
