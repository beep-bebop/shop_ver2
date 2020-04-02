package com.csu.shop.controller;

import com.csu.shop.domain.Item;
import com.csu.shop.domain.Category;
import com.csu.shop.domain.Product;
import com.csu.shop.service.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("/catalog")
public class CatalogController {

    @Autowired
    private CatalogService catalogService;

    @GetMapping("/main")
    public String view(){
        return "catalog/Main";
    }

    @GetMapping("/viewCategory")
    public String viewCategory(String categoryId, Model model){
        if(categoryId != null){
            Category category = catalogService.getCategory(categoryId);
            List<Product> productList = catalogService.getProductListByCategory(categoryId);
            model.addAttribute("category",category);
            model.addAttribute("productList",productList);
            return "catalog/Category";
        }
        return "catalog/Main";
    }

    @GetMapping("/viewProduct")
    public String viewProduct(String productId, Model model){
        Product product = catalogService.getProduct(productId);
        List<Item> itemList = catalogService.getItemListByProduct(productId);
        if(product != null) {
            model.addAttribute("product", product);
        }
        model.addAttribute("itemList",itemList);
        return "catalog/Product";
    }

    //这里注意
    @GetMapping("/viewItem")
    public String viewItem(String itemId, Model model){
        Item item = catalogService.getItem(itemId);
        Product product = item.getProduct();
        processProductDescription(product);
        System.out.println(product.getDescriptionText());
        model.addAttribute("item",item);
        model.addAttribute("product",product);
        return "catalog/Item";
    }

    @PostMapping("serchProducts")
    public String serchProducts(String keyword, Model model){
        if(keyword == null || keyword.length() < 1){
            String msg = "Please enter a keyword to search for, then press the search button.";
            model.addAttribute("msg",msg);
            return "common/Error";
        }else {
            List<Product> productList = catalogService.searchProductList(keyword.toLowerCase());
            processProductDescription(productList);
            model.addAttribute("productList",productList);
            return "catalog/SearchProducts";
        }
    }

    //待修改方法
    private void processProductDescription(Product product){
        String [] temp = product.getDescription().split("\"");
        product.setDescriptionImage(temp[1]);
        System.out.println(temp[1]);
        product.setDescriptionText(temp[2].substring(1));
        System.out.println(temp[2]);
    }

    private void processProductDescription(List<Product> productList){
        for(Product product : productList) {
            processProductDescription(product);
        }
    }
}
