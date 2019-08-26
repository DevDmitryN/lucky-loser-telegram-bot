package com.devdmitryn.userofthedaybot.service.impl;

import com.devdmitryn.userofthedaybot.entity.CustomUser;
import com.devdmitryn.userofthedaybot.repository.UserRepository;
import com.devdmitryn.userofthedaybot.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    private Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private UserRepository userRepository;

    @Override
    public void saveOfUpdate(CustomUser user) {
        userRepository.save(user);
    }

    @Override
    public CustomUser getById(int id) {
        return userRepository.findById(id).get();
    }
}
