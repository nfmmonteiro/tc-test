package com.travelcorporation.baskettest

import com.google.common.base.Preconditions

class Discount {
    private final Product product
    private final int numberOfDiscounts
    private final BigDecimal discountPercentage

    private Discount(Product product, int numberOfDiscounts, BigDecimal discountPercentage) {
        Preconditions.checkNotNull(product, 'product cannot be null')
        Preconditions.checkArgument(numberOfDiscounts > 0, 'number of discounts must be greater than zero')
        Preconditions.checkNotNull(discountPercentage, 'percentage of discount cannot be null')
        Preconditions.checkArgument(discountPercentage >= 0.0 && discountPercentage <= 1.0, 'percentage of discount must be between zero and one')

        this.product = product
        this.numberOfDiscounts = numberOfDiscounts
        this.discountPercentage = discountPercentage
    }

    public BigDecimal getValue() {
        return BigDecimal.valueOf(numberOfDiscounts)
                .multiply(discountPercentage)
                .multiply(product.getUnitCost())
    }

    static class DiscountFactory {
        public Discount create(Product product, int numberOfDiscounts, BigDecimal discountPercentage) {
            return new Discount(product, numberOfDiscounts, discountPercentage)
        }
    }
}
