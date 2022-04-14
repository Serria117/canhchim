package com.canhchim.controllers.general;

import com.canhchim.models.PrdProduct;
import com.canhchim.models.dto.CartDto;
import com.canhchim.models.dto.OrderItem;
import com.canhchim.payload.response.ResponseObject;
import com.canhchim.services.CustomerService;
import com.canhchim.services.OrderService;
import com.canhchim.services.ProductService;
import com.canhchim.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.Min;
import java.security.Principal;

@RestController
@RequestMapping(path = "/cart")
@Validated
public class OrderController
{

    @Autowired
    OrderService orderService;
    @Autowired
    ProductService productService;
    @Autowired
    CustomerService customerService;
    @Autowired
    UserService userService;

    @GetMapping("list")
    public ResponseEntity<?> listItem (Principal principal)
    {
        return ResponseEntity.ok().body(new CartDto(
                orderService.getOrderList(),
                orderService.sumTotal(),
                orderService.productCount(),
                orderService.itemCount()
        ));
    }

    /**
     * Add item to cart
     *
     * @param principal represent the current customer's account
     * @param id
     * @param quantity
     * @return CardDto
     */
    @PostMapping("add/{id}")
    @PreAuthorize("hasRole('EMPLOYEE')")
    public ResponseEntity<?> add (
            Principal principal,
            @PathVariable long id,
            @RequestParam("quantity") @Min(value = 1, message = "Quantity must be greater than 0.") int quantity)
    {
        PrdProduct product = productService.findById(id).orElse(null);
        if ( product != null && principal != null ) {
            OrderItem item = new OrderItem(product, quantity);
            orderService.addToCart(item);
            return ResponseEntity.ok().body(
                    new CartDto(
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
        public ResponseEntity<?> update (
            Principal principal,
            @PathVariable long id,
            @RequestParam @Min(
                    value = 0,
                    message = "Quantity to be updated must be greater than or equal to 0."
            ) int quantity)
    {
        if ( principal == null ) {
            return ResponseEntity.badRequest().body(new ResponseObject(
                    400, "Unauthorized", "You has been signed out or token is expired, please sign in again."
            ));
        }

        return orderService.updateCart(id, quantity) == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok().body(
                        new CartDto(
                                orderService.getOrderList(),
                                orderService.sumTotal(),
                                orderService.productCount(),
                                orderService.itemCount()
                        )
                );
    }

    @PostMapping("remove/{id}")
    public ResponseEntity<?> remove (@PathVariable long id, Principal principal)
    {
        return orderService.remove(id) == null ?
                ResponseEntity.badRequest().build() :
                ResponseEntity.ok().body(
                        new CartDto(
                                orderService.getOrderList(),
                                orderService.sumTotal(),
                                orderService.productCount(),
                                orderService.itemCount()
                        )
                );
    }
}
