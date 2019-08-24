package com.devdmitryn.userofthedaybot.repository;

import com.devdmitryn.userofthedaybot.entity.CustomUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<CustomUser,Integer> {

}
