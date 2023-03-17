package com.project.onlineshopping.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "authenticationtoken")
public class AuthenticationToken {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "token",unique = true)
    private String token;

    @Enumerated(EnumType.STRING)
    @Column(name = "token_type")
    public TokenType tokenType = TokenType.BEARER;
    @Column(name = "revoked")
    public boolean revoked;
    @Column(name = "createdat")
    private Date createdAt;
    @Column(name = "expired")
    public boolean expired;
    @ManyToOne
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserInfo user;

    public AuthenticationToken(UserInfo userInfo){
        this.user = userInfo;
        this.createdAt = new Date();
        this.token = UUID.randomUUID().toString();
    }

}
