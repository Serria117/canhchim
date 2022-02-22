package com.canhchim.controllers;

import com.canhchim.models.PrdProduct;
import com.canhchim.models.dto.OrderItem;
import com.canhchim.response.CartResponse;
import com.canhchim.services.OrderService;
import com.canhchim.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;

@RestController
@RequestMapping(path = "/cart")
@Validated
public class OrderController {

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;

    @GetMapping("list")
    public ResponseEntity<?> listItem() {
        return ResponseEntity.ok().body(new CartResponse(
                orderService.getOrderList(),
                orderService.sumTotal(),
                orderService.productCount(),
                orderService.itemCount()
        ));
    }

    @PostMapping("add/{id}")
    public ResponseEntity<?> add(
            @PathVariable long id,
            @RequestParam("quantity") @Min(value = 1, message = "Quantity must be greater than 0.") int quantity) {
        PrdProduct product = productService.findById(id).orElse(null);
        if (product != null) {
            OrderItem item = new OrderItem(product, quantity);
            orderService.addToCart(item);
            return ResponseEntity.ok().body(
                    new CartResponse(
                            orderService.getOrderList(),
                            orderService.sumTotal(),
                            orderService.productCount(),
                            orderService.itemCount()
                    )
            );
        }
        return ResponseEntity.badRequest().build();
    }

    @PostMapping("update/{id}")
    public ResponseEntity<?> update(
            @PathVariable long id,
            @RequestParam @Min(value = 0, message = "Quantity to be updated must be greater than or equal to 0.") int quantity) {
        return orderService.updateCart(id, quantity) == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok().body(
                        new CartResponse(
                                orderService.getOrderList(),
                                orderService.sumTotal(),
                                orderService.productCount(),
                                orderService.itemCount()
                        )
                );
    }

    @PostMapping("remove/{id}")
    public ResponseEntity<?> remove(@PathVariable long id) {
        return orderService.remove(id) == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok().body(
                        new CartResponse(
                                orderService.getOrderList(),
                                orderService.sumTotal(),
                                orderService.productCount(),
                                orderService.itemCount()
                        )
                );
    }
}
