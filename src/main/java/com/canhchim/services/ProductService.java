package com.canhchim.services;

import com.canhchim.models.PrdImage;
import com.canhchim.models.PrdProduct;
import com.canhchim.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Service
public class ProductService implements IService<PrdProduct>
{

    @Autowired
    PrdProductRepository productRepository;
    @Autowired
    PrdCategoryRepository categoryRepository;
    @Autowired
    ShpShopRepository shopRepository;
    @Autowired
    PrdImageRepository imgRepository;
    @Autowired
    ProductTypeRepository ProductTypeRepository;

    @Override
    public Optional<PrdProduct> findById(Long id)
    {
        return productRepository.findById(id);
    }

    @Override
    public Page<PrdProduct> findAll(int page, int size)
    {
        Pageable paging = PageRequest.of(page, size);
        return productRepository.findAll(paging);
    }

    public Page<PrdProduct> findByCategory(long id, int page, int size)
    {
        Pageable paging = PageRequest.of(page, size);
        return productRepository.findByCategory(id, paging);
    }

    @Override
    public Page<PrdProduct> searchByName(String search, int page, int size)
    {
        Pageable paging = PageRequest.of(page, size);
        return productRepository.searchByNameOrCategory(search, paging);
    }

    public PrdProduct searchByProductCode(long shopId, String keyword) throws RuntimeException
    {
        var foundProduct = productRepository.searchByProductCode(shopId, keyword);
        return foundProduct.orElseThrow(() -> new RuntimeException("Product code does not exist in the database."));
    }

    public List<PrdProduct> findByCatAndShop(long catId, long shopId)
    {
        return productRepository.findProductByCatAndShop(catId, shopId);
    }

    @Override
    public PrdProduct save(PrdProduct saveProduct)
    {
        return productRepository.save(saveProduct);
    }

    public Page<PrdProduct> findByShop(long shopId, int page, int size)
    {
        Pageable paging = PageRequest.of(page, size);
        return productRepository.findByShopId(shopId, paging);
    }


    @Transactional(rollbackOn = {SQLException.class, RuntimeException.class})
    public PrdProduct add(
            String name, String code,
            long price, long price2, long price3,
            int quantity,
            long catId, long shopId, int isTopUp, int hasTopUp, int isMultiPrice,
            List<String> fileName)
            throws RuntimeException
    {
        var newProduct = new PrdProduct();
        newProduct.setProductName(name);
        newProduct.setProductCode(code);
        newProduct.setProductPrice(price);
        newProduct.setProductQuantity(quantity);
        newProduct.setIsTopUp(isTopUp);
        newProduct.setHasTopUp(hasTopUp);
        newProduct.setIsMultiPrice(isMultiPrice);
        newProduct.setProductPrice2(price2);
        newProduct.setProductPrice3(price3);

        newProduct.setShpShop(shopRepository.findById(shopId)
                .orElseThrow(() -> new RuntimeException("Invalid shop ID")));

        newProduct.setPrdCategories(categoryRepository.findById(catId)
                .orElseThrow(() -> new RuntimeException("Invalid category ID")));

        var savedProduct = productRepository.save(newProduct);

        if(!fileName.isEmpty()){
            fileName.forEach(file -> {
                var img = new PrdImage();
                img.setImageUrl(file);
                img.setProduct(savedProduct);
                imgRepository.save(img);
            });
        }

        return savedProduct;
    }

    public int countByShop(long shopId){
        return productRepository.countByShopId(shopId);
    }
    public int countByCatAndShop(long catId, long shopId){
        return productRepository.countByCatAndShop(catId, shopId);
    }

    public PrdProduct findByCode(String code)
    {
        return productRepository.findByCode(code).orElse(null);
    }

    @Override
    public void remove(PrdProduct prdProduct)
    {

    }

    public boolean existByName(String name, long shopId){
        return productRepository.existsByProductName(name, shopId);
    }
}
