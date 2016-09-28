package com.travelcorporation.baskettest.rules

import com.travelcorporation.baskettest.Basket
import com.travelcorporation.baskettest.BasketRow
import com.travelcorporation.baskettest.Discount
import com.travelcorporation.baskettest.DiscountFactory
import com.travelcorporation.baskettest.Product
import com.travelcorporation.baskettest.ProductType
import spock.lang.Specification
import spock.lang.Unroll

@Unroll
class BuyThreeMilkGetOneFreeSpec extends Specification {

    private Basket mockBasket
    private BasketRow mockBasketRow
    private Product mockProduct
    private DiscountFactory mockDiscountFactory

    private BuyThreeMilkGetOneFree rule

    def setup() {
        mockBasket = Mock(Basket)
        mockBasketRow = Mock(BasketRow)
        mockProduct = Mock(Product)
        mockDiscountFactory = Mock(DiscountFactory)
        rule = new BuyThreeMilkGetOneFree(mockDiscountFactory)
    }

    def 'accept returns false with an empty basket'() {

        when:
        boolean result = rule.accept(mockBasket)

        then:
        1 * mockBasket.getBasketRow(ProductType.MILK) >> null
        0 * _

        and:
        !result
    }

    def "accept returns #expected when #it"() {

        when:
        boolean result = rule.accept(mockBasket)

        then:
        1 * mockBasket.getBasketRow(ProductType.MILK) >> mockBasketRow
        1 * mockBasketRow.getQuantity() >> qtdMilk
        0 * _

        and:
        result == expected

        where:
        qtdMilk | expected | it
        1       | false    | 'quantity of milk is not sufficient to get a discount'
        4       | true     | 'quantity of milk is sufficient to get a discount'
    }

    def 'calculate returns no discounts when quantity of milk is not sufficient to get a discount'() {

        when:
        List<Discount> discounts = rule.calculate(mockBasket)

        then:
        1 * mockBasket.getBasketRow(ProductType.MILK) >> mockBasketRow
        1 * mockBasketRow.getQuantity() >> 3
        0 * _

        and:
        discounts.isEmpty()
    }

    def 'calculate returns #qtdDiscounts when quantity of milk is #qtdMilk'() {

        given:
        Discount mockDiscount = Mock(Discount)

        when:
        List<Discount> discounts = rule.calculate(mockBasket)

        then:
        1 * mockBasket.getBasketRow(ProductType.MILK) >> mockBasketRow
        1 * mockBasketRow.getQuantity() >> qtdMilk
        1 * mockDiscountFactory.create(mockProduct, qtdDiscounts, BigDecimal.valueOf(1)) >> mockDiscount
        1 * mockBasketRow.getProduct() >> mockProduct
        0 * _

        and:
        discounts.size() == 1

        and:
        discounts.first() == mockDiscount

        where:
        qtdMilk | qtdDiscounts
        4       | 1
        5       | 1
        9       | 2
    }
}