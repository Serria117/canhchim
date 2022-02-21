package com.canhchim.services;

import com.canhchim.models.PrdProduct;
import com.canhchim.repositories.PrdProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService implements IService<PrdProduct> {

    @Autowired
    PrdProductRepository productRepository;


    @Override
    public Optional<PrdProduct> findById(Long id) {
        return productRepository.findById(id);
    }

    @Override
    public Page<PrdProduct> findAll(int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return productRepository.findAll(paging);
    }

    public Page<PrdProduct> findByCategory(long id, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return productRepository.findByCategory(id, paging);
    }

    @Override
    public Page<PrdProduct> searchByName(String search, int page, int size) {
        Pageable paging = PageRequest.of(page, size);
        return productRepository.searchByNameOrCategory(search, paging);
    }

    public Page<PrdProduct> findByShop(long shopId, int page, int size){
        Pageable paging = PageRequest.of(page, size);
        return productRepository.findByShopId(shopId, paging);
    }

    @Override
    public PrdProduct save(PrdProduct savingProduct) {
        return productRepository.save(savingProduct);
    }

    @Override
    public void remove(PrdProduct prdProduct) {

    }
}
