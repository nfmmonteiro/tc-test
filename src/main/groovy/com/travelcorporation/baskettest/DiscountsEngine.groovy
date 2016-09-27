package com.travelcorporation.baskettest

import com.travelcorporation.baskettest.rules.DiscountRule

class DiscountsEngine {

    private final List<DiscountRule> rules

    public DiscountsEngine(List<DiscountRule> rules) {
        this.rules = rules
    }

    public BigDecimal getDiscount(Basket basket) {

        List<DiscountRule> applicableRules = findApplicableRules(basket)

        List<Discount> applicableDiscounts = findApplicableDiscounts(applicableRules, basket)

        return sumAllDiscounts(applicableDiscounts)
    }

    private BigDecimal sumAllDiscounts(List<Discount> discounts) {
        BigDecimal totalDiscount = BigDecimal.ZERO
        discounts.each({ discount ->
            totalDiscount = totalDiscount.add(discount.getValue())
        })
        return totalDiscount
    }

    private List<Discount> findApplicableDiscounts(List<DiscountRule> applicableRules, Basket basket) {
        List<Discount> discounts = []
        applicableRules.each({ rule ->
            discounts.addAll(rule.calculate(basket))
        })
        return discounts
    }

    private List<DiscountRule> findApplicableRules(Basket basket) {
        return rules.findAll({ rule -> rule.accept(basket) })
    }
}
