package com.devdmitryn.userofthedaybot.service;

import com.devdmitryn.userofthedaybot.entity.CustomUser;

public interface UserService {
    void saveOfUpdate(CustomUser user);
    CustomUser getById(int id);
    boolean isExist(int id);
}
