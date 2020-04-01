package com.csu.shop.persistence;

import com.csu.shop.domain.Category;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CategoryMapper {
    List<Category> getCategoryList();

    Category getCategory(String categoryId);
}
