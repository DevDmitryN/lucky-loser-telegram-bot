package com.devdmitryn.userofthedaybot.repository;

import com.devdmitryn.userofthedaybot.entity.CustomChat;
import com.devdmitryn.userofthedaybot.entity.CustomUser;
import com.devdmitryn.userofthedaybot.entity.UserInChat;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserInChatRepository extends CrudRepository<UserInChat, Long> {
    Optional<UserInChat> findByChatAndUser(CustomChat chat, CustomUser user);
    Optional<UserInChat> findByChatIdAndUserId(long chatId, int userId);
    List<UserInChat> findAllByChatIdOrderByLuckyCounterDesc(long chatId);
    List<UserInChat> findAllByChatIdOrderByLoserCounterDesc(long chatId);
}
