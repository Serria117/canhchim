package com.canhchim.controllers;

import com.canhchim.models.PrdImage;
import com.canhchim.models.PrdProduct;
import com.canhchim.models.dto.ProductDto;
import com.canhchim.response.ResponseObject;
import com.canhchim.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@CrossOrigin
@RequestMapping(path = "/product")
public class ProductController {

    @Autowired
    ProductService productService;

    //Display all product (maybe in the home page)
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllProduct(
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "itemPerPage", defaultValue = "12") int itemPerPage) {
        Page<PrdProduct> result = productService.findAll(page, itemPerPage);

        return result.isEmpty() ?
                ResponseEntity.status(HttpStatus.NO_CONTENT).body(new ResponseObject(
                        204,
                        "N0_CONTENT",
                        "Nothing to display.")) :
                ResponseEntity.ok().body(new ResponseObject(
                        200,
                        "OK",
                        result.map(this::convertToProductDto)
                ));
    }

    //Display products of a shop (by shop id):
    @GetMapping("shop")
    public ResponseEntity<ResponseObject> findAllByShop(
            @RequestParam(name = "shopId") int shopId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "itemPerPage", defaultValue = "12") int itemPerPage
    ) {
        Page<PrdProduct> result = productService.findByShop(shopId, page, itemPerPage);

        return result.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        404,
                        "N0T_FOUND",
                        "Shop id does not exist.")) :
                ResponseEntity.ok().body(new ResponseObject(
                        200,
                        "OK",
                        result.map(this::convertToProductDto)
                ));
    }

    //Display product by Category
    @GetMapping("category")
    public ResponseEntity<ResponseObject> findAllByCategory(
            @RequestParam(name = "categoryId") int categoryId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "itemPerPage", defaultValue = "12") int itemPerPage) {
        Page<PrdProduct> result = productService.findByCategory(categoryId, page, itemPerPage);
        System.out.println(result.isEmpty());
        return result.isEmpty() ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        404,
                        "N0T_FOUND",
                        "Category id does not matched.")) :
                ResponseEntity.ok().body(new ResponseObject(
                        200,
                        "OK",
                        result.map(this::convertToProductDto)
                ));
    }


    //Display an individual product by id:
    @GetMapping("item")
    public ResponseEntity<ResponseObject> findById(@RequestParam("id") long id) {
        PrdProduct foundProduct = productService.findById(id).orElse(null);
        return foundProduct == null ?
                ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                        404,
                        "N0T_FOUND",
                        "Product id does not matched.")) :
                ResponseEntity.ok().body(new ResponseObject(
                        200,
                        "OK",
                        convertToProductDto(foundProduct)
                ));
    }


    //DTO converter
    private ProductDto convertToProductDto(PrdProduct p) {
        return new ProductDto(p);
    }
}
