package com.newegg.autopricing.angular.service;

import java.util.List;

import com.newegg.autopricing.angular.domain.Products;


public interface ProductsService {
    public List<Products> listProductsAll();

    public List<Products> listProductsCategoriesIn(int[] products);

    public List<Products> listProductsCategoriesWhere(int productID,
            String categoryName);
}
