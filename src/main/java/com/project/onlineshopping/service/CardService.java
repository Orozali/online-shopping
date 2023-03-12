package com.project.onlineshopping.service;

import com.project.onlineshopping.model.Cart;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.repository.CardRepository;
import com.project.onlineshopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;
    private final ProductService productService;
    @Transactional
    public void save(UserInfo userInfo, Product product) {
        Cart cart = new Cart(userInfo,product,new Date());
        cardRepository.save(cart);
    }

    public List<Product> findProductsByUserId(int id) {
        List<Cart> cartListByUserId = cardRepository.findByUserId(id);
        List<Product> productList = new ArrayList<>();
        for(Cart cart: cartListByUserId){
            productList.add(cart.getProduct());
        }
        return productList;
    }
}
