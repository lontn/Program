package com.newegg.autopricing.angular.persistence;

import java.util.List;
import java.util.Map;

import com.newegg.autopricing.angular.domain.Products;


public interface ProductsMapper {
    public static final String BY_ProductID = "productID";
    public static final String BY_CategoryName = "categoryName";
    public static final String BY_ProductName = "productName";
    public static final String BY_ProductIDList = "Products";

    public List<Products> listProductsAll();

    public List<Products> listProductsCategoriesIn(Map<String, Object> params);

    public List<Products> listProductsCategoriesWhere(Map<String, Object> map);

}
