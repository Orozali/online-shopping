package com.project.onlineshopping.service;

import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.model.WishList;
import com.project.onlineshopping.repository.WishListRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class WishListService {
    private final WishListRepo wishListRepo;
    private final ProductService productService;
    private final UserService userService;
    @Transactional
    public void createWishList(WishList wishList) {
        wishListRepo.save(wishList);
    }
    @Transactional
    public void save(int userId, int productId) {
        Product product = productService.findById(productId).get();
        UserInfo user = userService.findById(userId).get();
        var wishList = WishList.builder()
                .product(product)
                .userInfo(user)
                .createdAt(new Date())
                .build();
        wishListRepo.save(wishList);
    }

    public List<Product> findProducts(int id) {
        List<WishList> wishLists = wishListRepo.findWishListsByUserInfoId(id);
        List<Product> products = new ArrayList<>();
        for(WishList wishList: wishLists){
            products.add(wishList.getProduct());
        }
        return products;
    }
    @Transactional
    public void delete(int userId, int productId) {
        wishListRepo.deleteByUserIdAndProductId(userId,productId);
    }
}
