package com.newegg.autopricing.angular.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.newegg.autopricing.angular.domain.Products;
import com.newegg.autopricing.angular.persistence.ProductsMapper;


@Service
public class ProductsServiceImpl implements ProductsService {


    @Resource
    private ProductsMapper productMapper;
    
    @Override
    public List<Products> listProductsAll() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Products> listProductsCategoriesIn(int[] products) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    @Transactional(readOnly=true)
    public List<Products> listProductsCategoriesWhere(int productID, String categoryName) {
        Map<String, Object> params =new HashMap<String, Object>();
        params.put(ProductsMapper.BY_ProductID, productID);
        params.put(ProductsMapper.BY_CategoryName, categoryName);
        return productMapper.listProductsCategoriesWhere(params);
    }
}

