package com.example.e_commerce.service;

import com.example.e_commerce.model.Roles;
import com.example.e_commerce.model.User;
import com.example.e_commerce.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;


//part 1 regarding  security this is the first class created after the basic cladsses. here i have created load user by username anad mapped roles to authorities
//



@Service
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    public CustomUserDetailService(UserRepository userRepository){
        this.userRepository= userRepository;
    }

    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       User user;
       user = userRepository.findByUsername(username).orElseThrow(()-> new UsernameNotFoundException("User not found"));

       return org.springframework.security.core.userdetails.User
               .builder().username(user.getUsername())
               .password(user.getPassword()).authorities(mapRolesToAuthorities(user.getRoles())).build();


    }


    private Collection<GrantedAuthority> mapRolesToAuthorities(List<Roles> roles){
        if (roles==null){
            return List.of();
        }

        return roles.stream().map(role-> (GrantedAuthority) new SimpleGrantedAuthority("ROLE_"+ role.getRoleName()))
                .toList();

    }

}
