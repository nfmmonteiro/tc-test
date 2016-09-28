package com.travelcorporation.baskettest.rules

import com.travelcorporation.baskettest.Basket
import com.travelcorporation.baskettest.BasketRow
import com.travelcorporation.baskettest.Discount
import com.travelcorporation.baskettest.DiscountFactory
import com.travelcorporation.baskettest.Product
import com.travelcorporation.baskettest.ProductType

class BuyTwoButterGetOneBreadHalfPrice extends DiscountRule {

    private static final int MIN_QUANTITY = 2
    private static final BigDecimal PERCENTAGE_OF_DISCOUNT = BigDecimal.valueOf(0.5)

    public BuyTwoButterGetOneBreadHalfPrice(DiscountFactory discountFactory) {
        super(discountFactory)
    }

    @Override
    boolean accept(Basket basket) {
        return hasMinQuantityOfButter(basket) && hasBreadInBasket(basket)
    }

    @Override
    List<Discount> calculate(Basket basket) {
        List<Discount> discounts = []

        int numberOfDiscounts = getNumberOfDiscounts(basket)
        if (numberOfDiscounts > 0) {
            Product bread = basket.getBasketRow(ProductType.BREAD).getProduct()
            discounts.add(discountFactory.create(bread, numberOfDiscounts, PERCENTAGE_OF_DISCOUNT))
        }

        return discounts
    }

    private int getNumberOfDiscounts(Basket basket) {
        BasketRow butterBasketRow = basket.getBasketRow(ProductType.BUTTER)
        int maxNumberOfDiscounts = butterBasketRow.getQuantity() / MIN_QUANTITY
        return  Math.min(maxNumberOfDiscounts, getQuantityOfBread(basket))
    }

    private boolean hasMinQuantityOfButter(Basket basket) {
        return getQuantityOfButter(basket) >= MIN_QUANTITY
    }

    private boolean hasBreadInBasket(Basket basket) {
        return  getQuantityOfBread(basket) != 0
    }

    private Integer getQuantityOfBread(Basket basket) {
        return basket.getBasketRow(ProductType.BREAD)?.getQuantity()
    }

    private Integer getQuantityOfButter(Basket basket) {
        return basket.getBasketRow(ProductType.BUTTER)?.getQuantity()
    }
}
