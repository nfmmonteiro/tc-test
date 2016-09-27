package com.travelcorporation.baskettest.rules

import com.travelcorporation.baskettest.Basket
import com.travelcorporation.baskettest.Discount

abstract class DiscountRule {

    abstract boolean accept(Basket basket)
    abstract List<Discount> calculate(Basket basket)
}
