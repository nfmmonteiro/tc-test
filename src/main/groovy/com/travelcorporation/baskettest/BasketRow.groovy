package com.travelcorporation.baskettest

class BasketRow {

    private final Product product
    private final int quantity

    private BasketRow(Product product, int quantity) {
        this.product = product
        this.quantity = quantity
    }

    Product getProduct() {
        return product
    }

    int getQuantity() {
        return quantity
    }

    BigDecimal getCost() {
        return product.unitCost.multiply(BigDecimal.valueOf(quantity))
    }

    static class BasketRowFactory {
        public BasketRow create(Product product, int quantity) {
            return new BasketRow(product, quantity)
        }
    }
}
