package com.canhchim.services;

import com.canhchim.models.PrdCategory;
import com.canhchim.repositories.PrdCategoryGroupRepository;
import com.canhchim.repositories.PrdCategoryRepository;
import com.canhchim.repositories.ShpShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CategoryService implements IService<PrdCategory>
{
    @Autowired
    PrdCategoryRepository categoryRepository;
    @Autowired
    ShpShopRepository shopRepository;
    @Autowired
    PrdCategoryGroupRepository catGroupRepository;

    public Page<PrdCategory> findByShop (long shopId, int page, int size)
    {
        Pageable paging = PageRequest.of(page, size);
        return categoryRepository.findCategoryByShopId(shopId, paging);
    }

    public int countByShop (long shopId)
    {
        return categoryRepository.countByShop_Id(shopId);
    }

    @Override
    public Optional<PrdCategory> findById (Long id)
    {
        return categoryRepository.findById(id);
    }

    @Override
    public Page<PrdCategory> findAll (int page, int size)
    {
        Pageable paging = PageRequest.of(page, size);
        return categoryRepository.findAll(paging);
    }

    @Override
    public Page<PrdCategory> searchByName (String search, int page, int size)
    {
        return categoryRepository.findByName(search, PageRequest.of(page, size));
    }

    @Override
    public PrdCategory save (PrdCategory prdCategory)
    {
        return categoryRepository.save(prdCategory);
    }

    public void addNew (long shopId, String name, String code, long groupId, String imageUrl, String description)
    {
        if ( categoryRepository.existsByCategoryNameAndShop(name, shopId) ) {
            throw new RuntimeException("This shop has already had this category.");
        }
        var newCategory = new PrdCategory();
        newCategory.setShop(shopRepository.findById(shopId)
                                          .orElseThrow(() -> new RuntimeException("Invalid shop Id.")));
        newCategory.setCategoryName(name);
        newCategory.setCategoryGroup(catGroupRepository.findById(groupId)
                                                       .orElseThrow(() -> new RuntimeException("Category group not found.")));
        newCategory.setCategoryImage(imageUrl);
        newCategory.setCategoryCode(code);
        newCategory.setCategoryComment(description);

        if ( this.save(newCategory) == null ) {
            throw new RuntimeException("Failed to create new category.");
        }
    }

    @Override
    public void remove (PrdCategory prdCategory)
    {

    }
}
