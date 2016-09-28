package com.travelcorporation.baskettest

import spock.lang.Specification
import com.travelcorporation.baskettest.BasketRow.BasketRowFactory
import spock.lang.Unroll

@Unroll
class BasketRowSpec extends Specification {

    BasketRowFactory basketRowFactory

    def setup() {
        basketRowFactory = new BasketRowFactory()
    }

    def 'getCost returns #expectedCost as total cost of the row with unit cost #unitCost and quantity #quantity' () {

        given:
            Product mockProduct = Mock(Product)
            mockProduct.getUnitCost() >> BigDecimal.valueOf(unitCost)

        and:
            BasketRow basketRow = basketRowFactory.create(mockProduct, quantity)

        when:
            BigDecimal rowCost = basketRow.getCost()

        then:
            rowCost == BigDecimal.valueOf(expectedCost)

        where:
            unitCost | quantity | expectedCost
            1.2d     | 2        | 2.4d
            3d       | 0        | 0.0d
            3.2d     | 3        | 9.6d
    }

}