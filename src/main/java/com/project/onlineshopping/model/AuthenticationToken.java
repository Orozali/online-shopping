package com.project.onlineshopping.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "authenticationtoken")
public class AuthenticationToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Integer id;
    @Column(name = "token")
    private String token;
    @Column(name = "createdat")
    private Date createdAt;
    @OneToOne(targetEntity = UserInfo.class,fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserInfo user;

    public AuthenticationToken(UserInfo userInfo){
        this.user = userInfo;
        this.createdAt = new Date();
        this.token = UUID.randomUUID().toString();
    }

}
