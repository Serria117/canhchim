package com.canhchim.controllers.shopmanager;

import com.canhchim.models.*;
import com.canhchim.models.dto.CategoryDto;
import com.canhchim.models.dto.ProductDto;
import com.canhchim.payload.request.NewOrderRequest;
import com.canhchim.payload.response.Content;
import com.canhchim.payload.response.ResponseObject;
import com.canhchim.securityconfig.customuserdetail.CustomUserDetails;
import com.canhchim.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/shop")
public class ShopController
{
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    UserService userService;
    @Autowired
    OrderService orderService;
    @Autowired
    ShopService shopService;


    @GetMapping("category-all")
    public ResponseEntity<?> showAllCategories(
            @RequestParam(name = "shopId") long shopId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "0") int size)
    {
        if ( size == 0 ) {
            size = categoryService.countByShop(shopId);
        }

        var categories = categoryService.findByShop(shopId, page - 1, size);
        return categories.isEmpty()

                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ResponseObject(
                                        400,
                                        "NOT FOUND",
                                        "There is current no category in the shop."))

                : ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseObject(
                                        200,
                                        "OK",
                                        categories.map(this::convertToCategoryDto)));
    }

    @GetMapping("product-all")
    public ResponseEntity<?> showAllProduct(
            @RequestParam(name = "shopId") long shopId,
            @RequestParam(name = "page", required = false, defaultValue = "1") int page,
            @RequestParam(name = "size", required = false, defaultValue = "0") int size)
    {
        if ( size == 0 ) {
            size = productService.countByShop(shopId);
        }
        var products = productService.findByShop(shopId, page - 1, size);
        return products.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ResponseObject(
                                        404,
                                        "NOT FOUND",
                                        "There is current no product in the shop"))
                : ResponseEntity.status(HttpStatus.OK)
                                .body(new ResponseObject(
                                        200,
                                        "OK",
                                        products.map(this::convertToProductDto)));
    }

    @GetMapping(value = "display-all-tables")
    public ResponseEntity<?> getAllTables(@RequestParam(name = "shopId") long shopId, Authentication auth)
    {
        var user = (CustomUserDetails) auth.getPrincipal();
        if(!user.getShopIds().contains(shopId)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                    400, "BAD REQUEST",
                    "Invalid shop ID"
            ));
        }
        List<ShpZone> foundZone = shopService.findAllZone(shopId);
        if(foundZone.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                    404, "NOT FOUND",
                    "No zone found"
            ));
        }
        Content content = new Content(shopService.findAllZone(shopId));
        return ResponseEntity.ok().body(new ResponseObject(
                200, "OK", content

        ));
    }

    @GetMapping("product-search")
    public ResponseEntity<?> searchProductByCode(
            @RequestParam(name = "shopId") long shopId,
            @RequestParam(name = "keyword") String keyword)
    {
        try {
            var foundProduct = productService.searchByProductCode(shopId, keyword);
            return ResponseEntity.status(HttpStatus.OK).body(new ResponseObject(
                    200, "OK",
                    convertToProductDto(foundProduct)
            ));
        }
        catch ( Exception e ) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseObject(
                    400, "BAD REQUEST", e.getMessage()
            ));
        }
    }

    @PostMapping(path = "new-order", consumes = {"application/json"})
    public ResponseEntity<?> createOrder(@RequestBody @Valid NewOrderRequest newOrderRequest, Errors err, Authentication loginUser)
    {

        System.out.println(err.getNestedPath());
        if ( err.hasErrors() ) {
            Map<String, String> errorMap = new HashMap<>();
            err.getFieldErrors().forEach(e -> {
                errorMap.put(e.getField(), e.getDefaultMessage());
            });
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    new ResponseObject(400, "BAD REQUEST", errorMap));
        }
        if ( newOrderRequest != null ) {
            newOrderRequest.countProduct();
            newOrderRequest.countItem();
            var user = userService.findByName(loginUser.getName());
            newOrderRequest.setOrderTime(LocalDateTime.now());
            newOrderRequest.setUserId(user.getId());

            Set<PrdOrderDetail> orderList = new HashSet<>();
            newOrderRequest.getItems().forEach(item -> {
                PrdProduct product = productService.findById(item.getId()).orElse(null);
                if ( product != null ) {

                    PrdOrderDetail orderDetail = new PrdOrderDetail();
                    item.setProductName(product.getProductName());
                    item.setSubtotal(item.getPrice() * item.getQuantity());

                    orderDetail.setProduct(product);
                    orderDetail.setProductQuantity(item.getQuantity());
                    orderDetail.setProductSalePrice(item.getPrice());

                    orderList.add(orderDetail);
                }
            });

            newOrderRequest.setTotal(newOrderRequest.getItems().stream()
                                                    .mapToLong(item -> (long) item.getQuantity() * item.getPrice())
                                                    .sum());

            PrdOrder newOrder = new PrdOrder();
            newOrder.setOrderCode("Shp_" + newOrderRequest.getShopId() + "_" + Instant.now().toEpochMilli());
            newOrder.setOrderDate1(newOrderRequest.getOrderTime());
            newOrder.setShop(shopService.findById(newOrderRequest.getShopId()));
            newOrder.setUser(userService.findById(newOrderRequest.getUserId()));
            newOrder.setTotalPrice(newOrderRequest.getTotal());
            newOrder.setOrderType(newOrderRequest.getOrderType());
            newOrder.setPaymentType(newOrderRequest.getPaymentType());
            newOrder.setOrderList(orderList);
            newOrder.setTable(newOrderRequest.getTableId());

            orderService.saveOrder(newOrder, orderList);
        }
        return ResponseEntity.ok().body(newOrderRequest);
    }

    private ProductDto convertToProductDto(PrdProduct p)
    {
        return new ProductDto(p);
    }

    private CategoryDto convertToCategoryDto(PrdCategory c)
    {
        return new CategoryDto(c);
    }

    private PrdOrder convertToOrderEntity(NewOrderRequest newOrderRequest)
    {

        return null;
    }
}
