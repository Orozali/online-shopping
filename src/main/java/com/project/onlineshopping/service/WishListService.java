package com.project.onlineshopping.service;

import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.model.WishList;
import com.project.onlineshopping.repository.WishListRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListService {
    private final WishListRepo wishListRepo;
    @Transactional
    public void createWishList(WishList wishList) {
        wishListRepo.save(wishList);
    }
}
