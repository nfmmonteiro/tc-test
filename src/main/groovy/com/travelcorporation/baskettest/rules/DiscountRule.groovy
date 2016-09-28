package com.travelcorporation.baskettest.rules

import com.travelcorporation.baskettest.Basket
import com.travelcorporation.baskettest.Discount
import com.travelcorporation.baskettest.DiscountFactory

abstract class DiscountRule {

    protected DiscountFactory discountFactory

    protected DiscountRule(DiscountFactory discountFactory) {
        this.discountFactory = discountFactory
    }

    public abstract boolean accept(Basket basket)
    public abstract List<Discount> calculate(Basket basket)
}
