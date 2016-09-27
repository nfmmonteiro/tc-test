package com.travelcorporation.baskettest.rules

import com.travelcorporation.baskettest.Basket
import com.travelcorporation.baskettest.Discount

class BuyTwoButterGetOneBreadHalfPrice extends DiscountRule {

    boolean accept(Basket basket) {
        return false
    }

    List<Discount> calculate(Basket basket) {
    }
}
