package com.travelcorporation.baskettest

import com.travelcorporation.baskettest.Discount.DiscountFactory
import com.travelcorporation.baskettest.BasketRow.BasketRowFactory
import com.travelcorporation.baskettest.rules.BuyThreeMilkGetOneFree
import com.travelcorporation.baskettest.rules.BuyTwoButterGetOneBreadHalfPrice
import spock.lang.Specification
import spock.lang.Unroll

/**
 * This class holds the integration tests that verify that all the components are working well together
 */
@Unroll
class BasketIntegrationSpec extends Specification {

    private static final Product BUTTER = new Product(ProductType.BUTTER, BigDecimal.valueOf(0.8))
    private static final Product MILK = new Product(ProductType.MILK, BigDecimal.valueOf(1.15))
    private static final Product BREAD = new Product(ProductType.BREAD, BigDecimal.valueOf(1.0))

    private Basket basket

    def setup() {

        DiscountFactory discountFactory = new DiscountFactory()
        BasketRowFactory basketRowFactory = new BasketRowFactory()

        DiscountsEngine discountsEngine = new DiscountsEngine([
                new BuyThreeMilkGetOneFree(discountFactory),
                new BuyTwoButterGetOneBreadHalfPrice(discountFactory)
        ])

        basket = new Basket(basketRowFactory, discountsEngine)
    }

    def 'Given the basket has #qtdBread bread, #qtdButter butter and #qtdMilk milk when I total the basket then the total should be £ #total'() {

        given:
        basket.addProduct(BREAD, qtdBread)
        basket.addProduct(BUTTER, qtdButter)
        basket.addProduct(MILK, qtdMilk)

        when:
        BigDecimal totalCost = basket.getTotalCost()

        then:
        totalCost == BigDecimal.valueOf(total)

        where:
        qtdBread | qtdButter | qtdMilk | total
        1        | 1         | 1       | 2.95
        2        | 2         | 0       | 3.10
        0        | 0         | 4       | 3.45
        1        | 2         | 8       | 9.00
        1        | 4         | 4       | 7.15
        2        | 4         | 4       | 7.65
    }

}