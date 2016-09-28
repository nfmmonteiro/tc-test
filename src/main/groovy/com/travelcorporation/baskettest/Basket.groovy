package com.travelcorporation.baskettest

import com.google.common.base.Preconditions

class Basket {

    private DiscountsEngine discountsEngine
    private BasketRowFactory basketRowFactory

    private final Map<ProductType, BasketRow> basketRows = [ : ]

    public Basket(BasketRowFactory basketRowFactory, DiscountsEngine discountsEngine) {
        this.discountsEngine = discountsEngine
        this.basketRowFactory = basketRowFactory
    }

    public void addProduct(Product product, int quantity) {
        Preconditions.checkNotNull(product, 'product cannot be null')
        Preconditions.checkArgument(quantity >= 0, 'quantity must be positive')
        basketRows.put(product.productType, basketRowFactory.create(product, quantity))
    }

    public BasketRow getBasketRow(ProductType productType) {
        Preconditions.checkNotNull(productType, 'productType cannot be null')
        return basketRows.get(productType)
    }

    public BigDecimal getTotalCost() {
        BigDecimal cost = getCostWithoutDiscouts()
        BigDecimal discount = discountsEngine.getDiscount(this)
        return cost.subtract(discount)
    }

    private BigDecimal getCostWithoutDiscouts() {
        BigDecimal cost = BigDecimal.ZERO
        basketRows.values().each({ basketRow ->
            cost = cost.add(basketRow.getCost())
        })
        return cost
    }

}
