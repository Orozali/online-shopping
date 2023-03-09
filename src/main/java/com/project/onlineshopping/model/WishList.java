package com.project.onlineshopping.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.apache.catalina.User;

import java.util.Date;

@Entity
@Table(name = "wishlist")
@Getter
@Setter
@NoArgsConstructor
public class WishList {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "createdat")
    private Date createdAt;
    @ManyToOne(targetEntity = UserInfo.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private UserInfo userInfo;

    @ManyToOne(targetEntity = Product.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "product_id",referencedColumnName = "id")
    private Product product;

    public WishList(UserInfo user,Product product){
        this.userInfo = user;
        this.product = product;
        this.createdAt = new Date();
    }
}
