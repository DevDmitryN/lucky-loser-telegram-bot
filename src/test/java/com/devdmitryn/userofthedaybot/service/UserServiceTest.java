package com.devdmitryn.userofthedaybot.service;

import com.devdmitryn.userofthedaybot.UserOfTheDayBotApplication;
import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.entity.CustomUser;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;


@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void saveSuccessfully(){
        CustomUser user = new CustomUser();
        user.setId(5);
        user.setFirstName("name1");
        user.setLastName("test");
        user.setUsername("test");
        userService.saveOfUpdate(user);
        CustomUser savedUser = userService.getById(5);
        assertNotNull(savedUser);
    }
    @Test
    public void getExistedByIdSuccessfully(){

        CustomUser user = userService.getById(1);
        assertEquals("username1",user.getUsername());
    }
    @Test
    public void getUserWithChats(){

    }
}
