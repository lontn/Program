package com.newegg.autopricing.angular.persistence;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.junit.Test;

import com.newegg.autopricing.SupportSpringTest;
import com.newegg.autopricing.angular.domain.Products;

public class ProductsMapperTest extends SupportSpringTest {
    @Resource
    private ProductsMapper mapper;

    @Test
    public void test(){
        Map<String, Object> params = new HashMap<>();
        params.put(ProductsMapper.BY_CategoryName, "點心");
        List<Products> lists = mapper.listProductsCategoriesWhere(params);
        System.out.println(lists.size());
    }
}
