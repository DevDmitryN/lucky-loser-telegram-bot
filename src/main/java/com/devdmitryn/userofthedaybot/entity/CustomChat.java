package com.devdmitryn.userofthedaybot.entity;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "chats", schema = "telegram")
@Data
@NoArgsConstructor(access = AccessLevel.PUBLIC,force = true)
public class CustomChat {
    @Id
    private Integer id;
    @ManyToOne
    @JoinColumn(name = "lucky_id")
    private CustomUser lucky;
    @ManyToOne
    @JoinColumn(name = "looser_id")
    private CustomUser looser;
    @Column(name = "lucky_time")
    private LocalDateTime luckyTime;
    @Column(name = "looser_time")
    private LocalDateTime looserTime;
    @OneToMany(mappedBy = "chat")
    private List<UserInChat> users;

}
