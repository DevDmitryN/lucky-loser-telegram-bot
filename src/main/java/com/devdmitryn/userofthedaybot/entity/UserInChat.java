package com.devdmitryn.userofthedaybot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "chat_user",schema = "telegram")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class UserInChat {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "chat_id")
    private CustomChat chat;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private CustomUser user;
    @Column(name = "lucky_counter")
    private int luckyCounter;
    @Column(name = "loser_counter")
    private int loserCounter;

    public void incrementLuckyCounter(){
        luckyCounter++;
    }
    public void incrementLoserCounter(){
        loserCounter++;
    }

}
