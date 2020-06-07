package com.store.bookshopadmin.web.config;

import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.ArrayList;

public class MyUserDetailService implements UserDetailsService {

//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        if (StringUtils.equals(username, "zhangsan")) {
//            return new User("zhangsan", "111111", new ArrayList<GrantedAuthority>());
//        }
//        return null;
//    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserbyUername(username);

        User.UserBuilder builder = null;
        if (user != null) {
            builder = org.springframework.security.core.userdetails.User.withUsername(username);
            builder.password(new BCryptPasswordEncoder().encode(user.getPassword()));
            builder.authorities(user.getAuthorities());
        } else {
            throw new UsernameNotFoundException("User not found.");
        }

        return builder.build();
    }

    private User findUserbyUername(String username) {
        if(username.equalsIgnoreCase("zhangsan")) {
            return new User(username, "111222", new ArrayList<GrantedAuthority>());
        }
        return null;
    }
}
