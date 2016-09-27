package com.travelcorporation.baskettest

import com.travelcorporation.baskettest.rules.BuyThreeMilkGetOneFree
import com.travelcorporation.baskettest.rules.BuyTwoButterGetOneBreadHalfPrice
import com.travelcorporation.baskettest.rules.DiscountRule
import spock.lang.Specification


class DiscountsEngineSpec extends Specification {

    private Basket mockBasket
    private DiscountRule mockMilkRule
    private DiscountRule mockButterRule
    private Discount mockMilkDiscount
    private Discount mockButterDiscount

    private DiscountsEngine discountsEngine

    def setup() {
        mockMilkRule = Mock(BuyThreeMilkGetOneFree)
        mockButterRule = Mock(BuyTwoButterGetOneBreadHalfPrice)
        mockMilkDiscount = Mock(Discount)
        mockButterDiscount = Mock(Discount)

        discountsEngine = new DiscountsEngine([mockMilkRule, mockButterRule])
    }

    def 'calculate the total discount applicable to the basket when no rules are accepted' () {

        given:
        mockButterRule.accept(mockBasket) >> false
        mockMilkRule.accept(mockBasket) >> false

        when:
        BigDecimal totalDiscount = discountsEngine.getDiscount(mockBasket)

        then:
        totalDiscount == BigDecimal.valueOf(0.0)
    }

    def 'calculate the total discount applicable to the basket when all the rules give no discounts' () {

        given:
        mockButterRule.accept(mockBasket) >> true
        mockMilkRule.accept(mockBasket) >> true
        mockButterRule.calculate(mockBasket) >> []
        mockMilkRule.calculate(mockBasket) >> []

        when:
        BigDecimal totalDiscount = discountsEngine.getDiscount(mockBasket)

        then:
        totalDiscount == BigDecimal.valueOf(0.0)
    }

    def 'calculate the total discount applicable to the basket when only one rule is accepted' () {

        given:
        mockButterRule.accept(mockBasket) >> false
        mockMilkRule.accept(mockBasket) >> true
        mockMilkRule.calculate(mockBasket) >> [ mockMilkDiscount, mockMilkDiscount ]
        mockMilkDiscount.getValue() >> BigDecimal.valueOf(0.575)

        when:
        BigDecimal totalDiscount = discountsEngine.getDiscount(mockBasket)

        then:
        totalDiscount == BigDecimal.valueOf(1.15)
    }

    def 'calculate the total discount applicable to the basket when all rules are accepted' () {

        given:
        mockButterRule.accept(mockBasket) >> true
        mockMilkRule.accept(mockBasket) >> true
        mockMilkRule.calculate(mockBasket) >> [mockMilkDiscount]
        mockButterRule.calculate(mockBasket) >> [mockButterDiscount]
        mockMilkDiscount.getValue() >> BigDecimal.valueOf(1.15)
        mockButterDiscount.getValue() >> BigDecimal.valueOf(0.5)

        when:
        BigDecimal totalDiscount = discountsEngine.getDiscount(mockBasket)

        then:
        totalDiscount == BigDecimal.valueOf(1.65)
    }
}