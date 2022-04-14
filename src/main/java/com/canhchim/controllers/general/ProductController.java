package com.canhchim.controllers.general;

import com.canhchim.models.PrdProduct;
import com.canhchim.models.dto.ProductDto;
import com.canhchim.payload.response.ResponseObject;
import com.canhchim.services.FileStorageService;
import com.canhchim.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping(path = "/product")
public class ProductController
{

    @Autowired
    ProductService productService;

    @Autowired
    FileStorageService fileStorageService;

    /**
     * Display all product
     *
     * @param page
     * @param itemPerPage
     * @return ResponseEntity<?>
     */
    @GetMapping("")
    public ResponseEntity<ResponseObject> getAllProduct (
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "itemPerPage", defaultValue = "12") int itemPerPage)
    {
        int actualPage = page <= 0 ? 0 : page - 1;
        Page<PrdProduct> result = productService.findAll(actualPage, itemPerPage);

        return result.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                404,
                "NOT FOUND",
                "Nothing to display."))
                : ResponseEntity.ok().body(new ResponseObject(
                200,
                "OK",
                result.map(this::convertToProductDto)
        ));
    }

    @GetMapping("/{shopId}/{catId}")
    public ResponseEntity<?> findByCatAndShop (@PathVariable long catId, @PathVariable long shopId)
    {
        List<PrdProduct> resultList = productService.findByCatAndShop(catId, shopId);
        return resultList.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                404,
                "NOT FOUND",
                "No product found."))
                : ResponseEntity.ok().body(new ResponseObject(
                200,
                "OK",
                resultList.stream().map(this::convertToProductDto)
        ));
    }

    /**
     * Search the product by qrcode, barcode or user-defined code, exact match required
     *
     * @param code
     * @return
     */
    @GetMapping("searchByCode")
    public ResponseEntity<?> findByCode (@RequestParam(name = "barcode") String code)
    {
        var foundProduct = productService.findByCode(code);
        return foundProduct != null
                ? ResponseEntity.ok()
                                .body(new ResponseObject(
                                        200,
                                        "OK",
                                        convertToProductDto(foundProduct)))
                : ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ResponseObject(
                                        404,
                                        "NOT FOUND",
                                        "No product found"));
    }

    /**
     * Search product by name or category
     *
     * @param name
     * @param page
     * @param itemPerPage
     * @return
     */
    @GetMapping("searchByName")
    public ResponseEntity<?> searchByName (
            @RequestParam(name = "name") String name,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "itemPerPage", defaultValue = "12") int itemPerPage)
    {
        var foundProduct = productService.searchByName(name, page, itemPerPage);
        return foundProduct.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND)
                                .body(new ResponseObject(404, "NOT FOUND", "No product found"))
                : ResponseEntity.ok()
                                .body(new ResponseObject(404, "NOT FOUND",
                                                         foundProduct.map(this::convertToProductDto)
                                ));
    }

    /**
     * Display products of a shop (by shop id):
     *
     * @param shopId
     * @param page
     * @param itemPerPage
     * @return
     */
    @GetMapping("shop")
    public ResponseEntity<ResponseObject> findAllByShop (
            @RequestParam(name = "shopId") int shopId,
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "itemPerPage", defaultValue = "0") int itemPerPage
    )
    {
        int countPrd = productService.countByShop(shopId);
        if ( itemPerPage == 0 ) {
            itemPerPage = countPrd;
        }
        Page<PrdProduct> result = productService.findByShop(shopId, page - 1, itemPerPage);

        return result.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                404,
                "N0T_FOUND",
                "Shop id does not exist."))
                : ResponseEntity.ok().body(new ResponseObject(
                200,
                "OK",
                result.map(this::convertToProductDto)
        ));
    }

    //Display product by Category
    @GetMapping("category")
    public ResponseEntity<ResponseObject> findAllByCategory (
            @RequestParam(name = "categoryId") int categoryId,
            @RequestParam(name = "shopId") int shopId,
            @RequestParam(name = "page", defaultValue = "0") int page,
            @RequestParam(name = "itemPerPage", defaultValue = "0") int itemPerPage)
    {
        if(itemPerPage == 0){
            itemPerPage = productService.countByCatAndShop(categoryId, shopId);
        }
        Page<PrdProduct> result = productService.findByCategory(categoryId, page, itemPerPage);
        System.out.println(result.isEmpty());
        return result.isEmpty()
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                404,
                "N0T_FOUND",
                "Category id does not matched."))
                : ResponseEntity.ok().body(new ResponseObject(
                200,
                "OK",
                result.map(this::convertToProductDto)
        ));
    }

    /**
     * Display an individual product by id:
     *
     * @param id
     * @return The response entity
     */
    @GetMapping("item")
    public ResponseEntity<ResponseObject> findById (@RequestParam("id") long id)
    {
        PrdProduct foundProduct = productService.findById(id).orElse(null);
        return foundProduct == null
                ? ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ResponseObject(
                404,
                "N0T_FOUND",
                "Product id does not matched."))
                : ResponseEntity.ok().body(new ResponseObject(
                200,
                "OK",
                convertToProductDto(foundProduct)
        ));
    }

    //DTO converter
    private ProductDto convertToProductDto (PrdProduct p)
    {
        return new ProductDto(p);
    }

    private PrdProduct convertToEntity (ProductDto p)
    {
        var product = new PrdProduct();
        product.setProductName(p.getProductName());
        product.setProductPrice(p.getProductPrice());
        return product;
    }
}
