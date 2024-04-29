package com.mutualfunds.backend.mutualfundapi.services;

import com.mutualfunds.backend.mutualfundapi.pojo.entity.User;
import com.mutualfunds.backend.mutualfundapi.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;

    public synchronized User getUserWithPhoneNumber(Long phoneNumber){
        // return userRepository.findByPhoneNumber(phoneNumber);
        log.info("---->" + Thread.currentThread().getName());
        Optional<User> obj = userRepository.findByPhoneNumber(phoneNumber);
        if(obj.isPresent()) {
            User user = obj.get();
            log.debug("User is "+ user.getUsername() + "with Number" + user.getPhoneNumber());
            return user;
        } else {
            User user = createRandomUserWithPhoneNumber(phoneNumber);
            log.debug("New User is "+ user.getUsername() + "with Number" + user.getPhoneNumber());
            return user;
        }
    }

    public User createRandomUserWithPhoneNumber(Long phoneNumber){
        return userRepository.save(new User(
                "User-"+ UUID.randomUUID().toString().substring(5),
                phoneNumber)
        );
    }

    public User currentUser(){
        return (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
