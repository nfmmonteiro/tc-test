package com.travelcorporation.baskettest

class Product {
    private final ProductType productType
    private final BigDecimal unitCost

    Product(ProductType productType, BigDecimal unitCost) {
        this.productType = productType
        this.unitCost = unitCost
    }

    ProductType getProductType() {
        return productType
    }

    BigDecimal getUnitCost() {
        return unitCost
    }
}
