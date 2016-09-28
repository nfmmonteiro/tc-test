package com.travelcorporation.basket

import com.travelcorporation.baskettest.Discount
import com.travelcorporation.baskettest.Discount.DiscountFactory
import com.travelcorporation.baskettest.Product
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class DiscountSpec extends Specification {

    private Product mockProduct
    private DiscountFactory discountFactory

    def setup() {
        mockProduct = Mock(Product)
        discountFactory = new DiscountFactory()
    }

    def "constructor throws exception when product is null"() {

        when:
        discountFactory.create(null, 1, BigDecimal.ONE)

        then:
        NullPointerException ex = thrown()
        ex.message == 'product cannot be null'
    }

    def "constructor throws exception when number of discounts is not greater than zero"() {

        when:
        discountFactory.create(mockProduct, 0, BigDecimal.ONE)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == 'number of discounts must be greater than zero'
    }

    def "constructor throws exception when percentage of discount is null"() {

        when:
        discountFactory.create(mockProduct, 1, null)

        then:
        NullPointerException ex = thrown()
        ex.message == 'percentage of discount cannot be null'
    }


    def "constructor throws exception when percentage of discount is #discountPercentage"() {

        when:
        discountFactory.create(mockProduct, 1, BigDecimal.valueOf(discountPercentage))

        then:
        IllegalArgumentException ex = thrown()
        ex.message == 'percentage of discount must be between zero and one'

        where:
        discountPercentage << [-0.1, 1.001]
    }

    def "getValue calculates the discount value for #qtdDiscount discounts of #discountPerc% of #unitCost"() {

        given:
        Discount discount = discountFactory.create(mockProduct, qtdDiscount, BigDecimal.valueOf(discountPerc / 100.0))

        when:
        BigDecimal value = discount.getValue()

        then:
        1 * mockProduct.getUnitCost() >> unitCost
        0 * _

        and:
        value == expectedValue

        where:
        unitCost | qtdDiscount | discountPerc | expectedValue
        1.0      | 1           | 100          | 1.0
        1.15     | 2           | 50           | 1.15
        0.8      | 2           | 25           | 0.4
    }
}
