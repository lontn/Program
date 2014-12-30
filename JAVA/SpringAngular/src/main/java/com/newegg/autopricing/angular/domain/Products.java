package com.newegg.autopricing.angular.domain;

public class Products {
    private Long productID;
    private String categoryName;
    private String productName;
    private String quantityPerUnit;
    private String description;

    public Long getProductID() {
        return productID;
    }

    public void setProductID(Long productID) {
        this.productID = productID;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getQuantityPerUnit() {
        return quantityPerUnit;
    }

    public void setQuantityPerUnit(String quantityPerUnit) {
        this.quantityPerUnit = quantityPerUnit;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Products [productID=" + productID + ", categoryName="
                + categoryName + ", productName=" + productName
                + ", quantityPerUnit=" + quantityPerUnit + ", description="
                + description + "]";
    }

    
}
