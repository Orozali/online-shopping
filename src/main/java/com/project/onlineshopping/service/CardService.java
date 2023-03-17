package com.project.onlineshopping.service;

import com.project.onlineshopping.dto.CartDTO;
import com.project.onlineshopping.dto.CartGet;
import com.project.onlineshopping.exceptions.ItemLessThanZeroException;
import com.project.onlineshopping.exceptions.ProductNotFoundException;
import com.project.onlineshopping.model.Cart;
import com.project.onlineshopping.model.Product;
import com.project.onlineshopping.model.UserInfo;
import com.project.onlineshopping.repository.CardRepository;
import com.project.onlineshopping.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CardService {
    private final CardRepository cardRepository;
    private final ProductService productService;
    private final UserService userService;
    @Transactional
    public void save(CartDTO cartDTO,int user_id) {
        Optional<Product> product = productService.findById(cartDTO.getProduct_id());
        Optional<UserInfo> user = userService.findById(user_id);
        if(product.isEmpty()){
            throw new ProductNotFoundException("Товар не найден!");
        }
        if(user.isEmpty()){
            throw new ProductNotFoundException("Пользователь не найден!");
        }
        Integer quantity = product.get().getQuantity();
        if(quantity-cartDTO.getQuantity()<0){
            throw new ItemLessThanZeroException("У нас есть только "+quantity+" количество товаров!");
        }
        Optional<Cart> cartUser = cardRepository.findCartByUserId(user_id);
//        Optional<Cart> cartUser = cardRepository.findByUserId(user_id);
        if(cartUser.isPresent() && Objects.equals(cartUser.get().getProduct().getId(), cartDTO.getProduct_id())){
            if(quantity-(cartUser.get().getQuantity()+cartDTO.getQuantity())<0){
                throw new ItemLessThanZeroException("У нас есть только "+quantity+" количество товаров!");
            }
            else {
                cartUser.get().setQuantity(cartUser.get().getQuantity()+cartDTO.getQuantity());
            }
        }else {
            Cart cart = new Cart(user.get(),product.get(),new Date(),cartDTO.getQuantity());
            cardRepository.save(cart);
        }

    }

    public List<Product> findProductsByUserId(int id) {
        List<Cart> cartListByUserId = cardRepository.findByUserId(id);
        List<Product> productList = new ArrayList<>();
        for(Cart cart: cartListByUserId){
            productList.add(cart.getProduct());
        }
        return productList;
    }
    public List<CartGet> getCart(int id){
        List<Cart> carts = cardRepository.findByUserId(id);
        List<CartGet> cartGets = new ArrayList<>();
        for(Cart cart:carts){
            CartGet cartGet = new CartGet(cart.getProduct(),cart.getQuantity());
            cartGets.add(cartGet);
        }
        return cartGets;
    }
    @Transactional
    public void deleteProductById(UserInfo userInfo, Product product) {
        cardRepository.deleteProduct(userInfo.getId(),product.getId());
    }

//    public Integer productQuantity() {
//        return cardRepository.
//    }
}
