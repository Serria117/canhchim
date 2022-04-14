package com.canhchim.controllers.shopmanager;

import com.canhchim.models.PrdProduct;
import com.canhchim.models.dto.ProductDto;
import com.canhchim.payload.request.NewCategoryRequest;
import com.canhchim.payload.request.NewProductRequest;
import com.canhchim.payload.request.NewTableRequest;
import com.canhchim.payload.request.NewZoneRequest;
import com.canhchim.payload.response.ErrorResponse;
import com.canhchim.payload.response.ResponseObject;
import com.canhchim.securityconfig.customuserdetail.CustomUserDetails;
import com.canhchim.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@RestController
@RequestMapping(path = "/shop-admin")
public class AdminController
{

    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    FileStorageService fileStorageService;
    @Autowired
    UserService userService;
    @Autowired
    ShopService shopService;

    @GetMapping("display-action")
    public ResponseEntity<?> displayAllActions()
    {
        var action = userService.displayAllRole();
        return ResponseEntity.ok().body(new ResponseObject(
                200,
                "OK",
                action
        ));
    }

    @PostMapping("new-category")
    @Validated
    public ResponseEntity<?> addNewCategory(
            @RequestBody @Valid NewCategoryRequest newCategory,
            Errors errors, Authentication auth)
    {
        HttpStatus status;
        Map<String, String> errorMap = checkError(errors);
        if ( !errorMap.isEmpty() ) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    LocalDateTime.now(),
                    status,
                    status.value(),
                    errorMap
            ));
        }
        try {
            var user = (CustomUserDetails) auth.getPrincipal();
            var shops = user.getShopIds();
            if ( !shops.contains(newCategory.getShopId()) ) {
                throw new RuntimeException("Invalid shopID.");
            }
            categoryService.addNew(
                    newCategory.getShopId(), newCategory.getCategoryName(),
                    newCategory.getCode(), newCategory.getGroupId(),
                    newCategory.getImage(), newCategory.getDescription());
            status = HttpStatus.OK;
            return ResponseEntity.ok().body(new ResponseObject(
                    status.value(),
                    status.getReasonPhrase(),
                    newCategory.getCategoryName() + " added."
            ));
        }
        catch ( Exception e ) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(new ErrorResponse(
                    LocalDateTime.now(),
                    status,
                    status.value(),
                    e.getMessage()
            ));
        }
    }

    @PostMapping(path = "new-product", consumes = {"application/json"})
    @Validated
    public ResponseEntity<?> addProduct(@RequestBody @Valid NewProductRequest newProduct, Errors err, Authentication auth)
    {
        HttpStatus status;
        Map<String, String> errorMap = checkError(err);
        if ( !errorMap.isEmpty() ) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    LocalDateTime.now(),
                    status,
                    status.value(),
                    errorMap
            ));
        }
        try {
            var user = (CustomUserDetails) auth.getPrincipal();
            var shops = user.getShopIds();
            if ( !shops.contains(newProduct.getShopId()) ) {
                throw new RuntimeException("Invalid shop ID.");
            }

            if ( productService.existByName(newProduct.getName(), newProduct.getShopId()) ) {
                throw new RuntimeException("Product name has already existed");
            }

            PrdProduct product;
            if ( newProduct.getId() != null ) {
                product = productService.findById(newProduct.getId())
                                        .orElseThrow(() -> new RuntimeException("Product ID not found"));

                product.setProductName(newProduct.getName());
                product.setProductPrice(newProduct.getPrice());
                product.setProductPrice2(newProduct.getPrice2());
                product.setProductPrice3(newProduct.getPrice3());
                productService.save(product);
            }
            else {
                product = productService.add(
                        newProduct.getName(), newProduct.getCode(),
                        newProduct.getPrice(), newProduct.getPrice2(), newProduct.getPrice3(),
                        newProduct.getQuantity(),
                        newProduct.getCategoryId(), newProduct.getShopId(),
                        newProduct.getIsTopUp(), newProduct.getHasTopUp(),
                        newProduct.getIsMultiPrice(), newProduct.getImages());
            }

            status = HttpStatus.OK;
            return ResponseEntity.ok().body(new ResponseObject(
                    status.value(), status.getReasonPhrase(),
                    convertToProductDto(product)
            ));
        }
        catch ( RuntimeException e ) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(status).body(new ErrorResponse(
                    LocalDateTime.now(),
                    status,
                    status.value(),
                    e.getMessage()
            ));
        }
    }

    @PostMapping("new-table")
    public ResponseEntity<?> newTable(@RequestBody @Valid NewTableRequest newTable, Errors err, Authentication auth)
    {
        HttpStatus status;
        Map<String, String> errorMap = checkError(err);
        if ( !errorMap.isEmpty() ) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    LocalDateTime.now(),
                    status,
                    status.value(),
                    errorMap
            ));
        }
        try {
            var user = (CustomUserDetails) auth.getPrincipal();
            var shopIds = user.getShopIds();
            if ( !shopIds.contains(newTable.getShopId()) ) {
                throw new RuntimeException("The current user does not have authorization for the shop.");
            }

            shopService.newTable(newTable.getShopId(), newTable.getZoneId(),
                                 newTable.getTableName(), newTable.getNumberOfSeat());
            status = HttpStatus.OK;
            return ResponseEntity.ok().body(new ResponseObject(
                    status.value(), status.getReasonPhrase(),
                    "Table '" + newTable.getTableName() + "' added."
            ));
        }
        catch ( RuntimeException e ) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    LocalDateTime.now(),
                    status,
                    status.value(),
                    e.getMessage()
            ));
        }
    }

    @PostMapping("new-zone")
    public ResponseEntity<?> newZone(@RequestBody @Valid NewZoneRequest newZone, Errors err, Authentication auth)
    {
        HttpStatus status;
        Map<String, String> errorMap = checkError(err);
        if ( !errorMap.isEmpty() ) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    LocalDateTime.now(),
                    status,
                    status.value(),
                    errorMap
            ));
        }
        try {
            var user = (CustomUserDetails) auth.getPrincipal();
            var shopIds = user.getShopIds();
            if ( !shopIds.contains(newZone.getShopId()) ) {
                throw new RuntimeException("The current user does not have authorization for the shop.");
            }
            shopService.newZone(newZone.getShopId(), newZone.getZoneName());
            status = HttpStatus.OK;
            return ResponseEntity.ok().body(new ResponseObject(
                    status.value(), status.getReasonPhrase(),
                    "Zone '" + newZone.getZoneName() + "' added."
            ));
        }
        catch ( Exception e ) {
            status = HttpStatus.BAD_REQUEST;
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ErrorResponse(
                    LocalDateTime.now(),
                    status,
                    status.value(),
                    e.getMessage()
            ));
        }
    }

    private ProductDto convertToProductDto(PrdProduct p)
    {
        return new ProductDto(p);
    }

    private Map<String, String> checkError(Errors err)
    {
        Map<String, String> errorMessage = new HashMap<>();
        if ( err.hasErrors() ) {
            err.getFieldErrors().forEach(e -> {
                errorMessage.put(e.getField(), e.getDefaultMessage());
            });
        }
        return errorMessage;
    }

    @PostMapping("new-product1")
    public ResponseEntity<?> addProduct(
            @RequestParam(name = "productName") String name,
            @RequestParam(name = "productCode", required = false) String code,
            @RequestParam(name = "price") long price,
            @RequestParam(name = "price2", defaultValue = "0") long price2,
            @RequestParam(name = "price3", defaultValue = "0") long price3,
            @RequestParam(name = "quantity", defaultValue = "1") int quantity,
            @RequestParam(name = "isMultiPrice", defaultValue = "0") int isMultiPrice,
            @RequestParam(name = "categoryId") long catId,
            @RequestParam(name = "shopId") long shopId,
            @RequestParam(name = "hasTopUp", defaultValue = "0") int hasTopUp,
            @RequestParam(name = "isTopUp", defaultValue = "0") int isTopUp,
            @RequestParam(name = "files", required = false) MultipartFile[] files)
    {
        String newCode;
        if ( code == null || code.equals("") ) {
            newCode = Long.toString(Instant.now().toEpochMilli());
        }
        else {
            newCode = code;
        }
        List<String> fileName = new ArrayList<>();

        try {
            Arrays.asList(files).forEach(file -> fileName.add(fileStorageService.storeFile(file)));
        }
        catch ( Exception e ) {
            System.out.println(e.getMessage());
        }
        try {
            var savedProduct = productService.add(name, newCode,
                                                  price, price2, price3,
                                                  quantity,
                                                  catId, shopId,
                                                  isTopUp, hasTopUp, isMultiPrice,
                                                  fileName);
            return ResponseEntity.ok()
                                 .body(convertToProductDto(savedProduct));
        }
        catch ( RuntimeException e ) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
