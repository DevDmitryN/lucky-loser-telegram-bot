package com.devdmitryn.userofthedaybot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Table(name = "chats", schema = "telegram")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class CustomChat {
    @Id
    private Long id;
    @ManyToOne
    @JoinColumn(name = "lucky_id")
    private CustomUser lucky;
    @ManyToOne
    @JoinColumn(name = "loser_id")
    private CustomUser loser;
    @Column(name = "lucky_time")
    private LocalDateTime luckyTime;
    @Column(name = "loser_time")
    private LocalDateTime loserTime;
    @OneToMany(mappedBy = "chat")
    private List<UserInChat> users;

    @PrePersist
    private void setDefaultValues(){
        if(luckyTime == null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            luckyTime = LocalDateTime.parse("1962-06-21 00:00:00",formatter);
        }
        if(loserTime == null){
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            loserTime = LocalDateTime.parse("1991-12-26 00:00:00",formatter);
        }
    }


}
