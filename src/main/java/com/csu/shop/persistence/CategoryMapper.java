package com.csu.shop.persistence;

import com.csu.shop.domain.Category;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CategoryMapper {
    List<Category> getCategoryList();

    Category getCategory(String categoryId);
}
