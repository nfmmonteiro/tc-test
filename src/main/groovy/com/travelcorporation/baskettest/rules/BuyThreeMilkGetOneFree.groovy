package com.travelcorporation.baskettest.rules

import com.travelcorporation.baskettest.Basket
import com.travelcorporation.baskettest.BasketRow
import com.travelcorporation.baskettest.Discount
import com.travelcorporation.baskettest.Discount.DiscountFactory
import com.travelcorporation.baskettest.ProductType

class BuyThreeMilkGetOneFree extends DiscountRule {

    private static final int MIN_QUANTITY = 4
    private static final BigDecimal PERCENTAGE_OF_DISCOUNT = BigDecimal.valueOf(1)

    public BuyThreeMilkGetOneFree(DiscountFactory discountFactory) {
        super(discountFactory)
    }

    @Override
    boolean accept(Basket basket) {
        return hasMinQuantityOfMilk(basket)
    }

    @Override
    List<Discount> calculate(Basket basket) {
        List<Discount> discounts = []
        BasketRow basketRow = basket.getBasketRow(ProductType.MILK)

        int numberOfDiscounts = getNumberOfDiscounts(basketRow)
        if (numberOfDiscounts > 0) {
            discounts.add(discountFactory.create(basketRow.getProduct(), numberOfDiscounts, PERCENTAGE_OF_DISCOUNT))
        }

        return discounts
    }

    private int getNumberOfDiscounts(BasketRow basketRow) {
        return basketRow.getQuantity() / MIN_QUANTITY
    }

    private hasMinQuantityOfMilk(Basket basket) {
        return basket.getBasketRow(ProductType.MILK)?.getQuantity() >= MIN_QUANTITY
    }
}
