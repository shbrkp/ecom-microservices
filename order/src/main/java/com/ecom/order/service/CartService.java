package com.ecom.order.service;

import com.ecom.order.clients.ProductServiceClient;
import com.ecom.order.clients.UserServiceClient;
import com.ecom.order.dto.CartItemRequest;
import com.ecom.order.dto.ProductResponse;
import com.ecom.order.dto.UserResponse;
import com.ecom.order.model.CartItem;
import com.ecom.order.repository.CartItemRepository;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Transactional
public class CartService {
    /*public final UserRepository userRepository;
    public final ProductRepository productRepository;*/
    public final CartItemRepository cartItemRepository;
    public final ProductServiceClient productServiceClient;
    public final UserServiceClient userServiceClient;
    public boolean addToCart(String userId, CartItemRequest cartItemRequest){
        // checking whether the user is valid
        ProductResponse productResponse = productServiceClient.getProductDetails(cartItemRequest.getProductId());
        if(productResponse == null ) {
            System.out.println("CartService::addToCart()----Product Response is null---");
            return false;
        }
        System.out.println("CartService::addToCart()----Product Response is ---"+productResponse.getId());
        if(productResponse.getStockQuantity()  < cartItemRequest.getQuantity() )
            return false;

        UserResponse userResponse = userServiceClient.getUserDetails(userId);
        if(userResponse == null ) {
            System.out.println("CartService::addToCart()----User Response is null---");
            return false;
        }
        System.out.println("CartService::addToCart()----User Response is ---"+userResponse.getId());
//       Optional<User> userOpt =  userRepository.findById(Long.valueOf(userId));
//        if(userOpt.isEmpty())
//            return false;
//
//        User user = userOpt.get();
//        // Checking whether product is valid
//        Optional<Product> productOpt = productRepository.findById(cartItemRequest.getProductId());
//        if(productOpt.isEmpty())
//            return false;
//        Product product = productOpt.get();
//
//        // Check whether the requested quantity is available
//        if(product.getStockQuantity() < cartItemRequest.getQuantity())
//            return false;

        //Check whether the item is already picked by the user in the cart
        com.ecom.order.model.CartItem existingCartItem = cartItemRepository.findByUserIdAndProductId(userId, String.valueOf(cartItemRequest.getProductId()));
        if(existingCartItem != null){
            // update the quantity
            existingCartItem.setQuantity(existingCartItem.getQuantity() + cartItemRequest.getQuantity());
            existingCartItem.setPrice(BigDecimal.valueOf(1000.00));
            cartItemRepository.save(existingCartItem);
        }else{
            // create a cart item
            CartItem cartItem = new CartItem();
            cartItem.setProductId(cartItemRequest.getProductId());
            cartItem.setUserId(userId);
            cartItem.setQuantity(cartItemRequest.getQuantity());
            cartItem.setPrice(BigDecimal.valueOf(1001.00));
            cartItemRepository.save(cartItem);
        }
        return true;
    }

    public boolean deleteCartItem(String userId, String productId) {
        /*Optional<User> userOpt =  userRepository.findById(Long.valueOf(userId));
        Optional<Product> productOpt = productRepository.findById(Long.valueOf(productId));*/
        CartItem cartItem = cartItemRepository.findByUserIdAndProductId(userId, productId);
        if(cartItem != null){
            cartItemRepository.delete(cartItem);
            return true;
        }
        return false;
    }

    public List<CartItem> getCart(String userId) {
       /* Optional<User> userOpt =  userRepository.findById(Long.valueOf(userId));
        if(userOpt.isPresent())
            return cartItemRepository.findByUser(userOpt.get()) ;
        return new ArrayList<CartItem>();*/


       /* return userRepository.findById(Long.valueOf(userId))
                .map(cartItemRepository::findByUserId)
                .orElseGet(List::of);*/
        return cartItemRepository.findByUserId(userId);


    }

    public void clearCart(String userId) {
        //userRepository.findById(Long.valueOf(userId)).ifPresent(cartItemRepository::deleteByUser);
        cartItemRepository.deleteByUserId(userId);
    }
}
