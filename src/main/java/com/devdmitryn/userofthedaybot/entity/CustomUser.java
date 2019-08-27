package com.devdmitryn.userofthedaybot.entity;

import lombok.*;
import org.checkerframework.checker.units.qual.C;

import javax.persistence.*;
import java.util.List;
@Entity
@Table(name = "users",schema = "telegram")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class CustomUser {
    @Id
    private Integer id;
    @Column(name = "username")
    private String userName;
    @Column(name = "first_name")
    private String firstName;
    @Column(name = "last_name")
    private String lastName;
    @Column(name = "is_bot")
    private boolean isBot;
    @OneToMany(mappedBy = "user")
    private List<UserInChat> chats;
    @OneToMany(mappedBy = "lucky")
    private List<CustomChat> luckyChats;
    @OneToMany(mappedBy = "loser")
    private List<CustomChat> unluckyChats;

    public String getNotNullName(){
        if(userName != null){
            return userName;
        }
        if(firstName != null){
            return firstName;
        }
        return lastName;
    }

}
