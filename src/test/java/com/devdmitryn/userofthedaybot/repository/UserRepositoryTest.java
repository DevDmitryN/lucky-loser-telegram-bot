package com.devdmitryn.userofthedaybot.repository;

import com.devdmitryn.userofthedaybot.entity.CustomUser;
import org.hibernate.Hibernate;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private CustomUser user;

    @Before
    public void createUser(){
        user = new CustomUser();
        user.setId(3);
        user.setFirstName("test first name");
        user.setLastName("test last name");
        user.setUsername("test username");
    }

    @Test
    public void saveCustomUserSuccessfully(){
        userRepository.save(user);
        Optional<CustomUser> userFromDb = userRepository.findById(user.getId());
        assertTrue(userFromDb.isPresent());
        assertEquals(user.getId(),userFromDb.get().getId());
        assertEquals(user.getFirstName(),userFromDb.get().getFirstName());
        assertEquals(user.getLastName(),userFromDb.get().getLastName());
        assertEquals(user.getUsername(),userFromDb.get().getUsername());
    }

    @Test
    public void updateUserSuccessfully(){
        user.setFirstName("new first name");
        userRepository.save(user);
        Optional<CustomUser> userFromDb = userRepository.findById(user.getId());
        assertEquals(user.getFirstName(),userFromDb.get().getFirstName());
    }

    @Test
    public void deleteUserByIdSuccessfully(){
        userRepository.delete(user);
        Optional<CustomUser> userFromDb = userRepository.findById(user.getId());
        assertFalse(userFromDb.isPresent());
    }

    @Test
    @Transactional
    public void getChatsForUser(){
        Optional<CustomUser> userFromBd = userRepository.findById(user.getId());
        assertTrue(userFromBd.isPresent());
        Hibernate.initialize(userFromBd.get().getChats());
        assertTrue(Hibernate.isInitialized(userFromBd.get().getChats()));
    }

}
