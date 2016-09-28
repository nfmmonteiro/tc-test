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
class BuyTwoButterGetOneBreadHalfPriceSpec extends Specification {

    private Basket mockBasket
    private BasketRow mockButterBasketRow
    private BasketRow mockBreadBasketRow
    private Product mockBreadProduct
    private DiscountFactory mockDiscountFactory

    private BuyTwoButterGetOneBreadHalfPrice rule

    def setup() {
        mockBasket = Mock(Basket)
        mockButterBasketRow = Mock(BasketRow)
        mockBreadBasketRow = Mock(BasketRow)
        mockBreadProduct = Mock(Product)
        mockDiscountFactory = Mock(DiscountFactory)

        rule = new BuyTwoButterGetOneBreadHalfPrice(mockDiscountFactory)
    }

    def 'accept returns false with an empty basket'() {

        when:
        boolean result = rule.accept(mockBasket)

        then:
        1 * mockBasket.getBasketRow(ProductType.BUTTER) >> null
        0 * _

        and:
        !result
    }

    def "accept returns #expected when #it"() {

        given:
        setupMockResponses(qtdButter, qtdBread)

        when:
        boolean result = rule.accept(mockBasket)

        then:
        result == expected

        where:
        qtdButter | qtdBread | expected | it
        1         | 2        | false    | 'quantity of butter is not sufficient to get a discount'
        2         | 0        | false    | 'quantity of bread is not sufficient to get a discount'
        2         | 2        | true     | 'quantity of butter and bread are sufficient to get a discount'
    }

    def 'calculate returns no discounts when #it'() {

        given:
        setupMockResponses(qtdButter, qtdBread)

        when:
        List<Discount> butterDiscounts = rule.calculate(mockBasket)

        then:
        butterDiscounts.isEmpty()

        where:
        qtdButter | qtdBread | it
        1         | 2        | 'quantity of butter is not sufficient to get a discount'
        4         | 0        | 'quantity of bread is not sufficient to get a discount'
    }

    def 'calculate returns #qtdDiscounts discounts when #it'() {

        given:
        setupMockResponses(qtdButter, qtdBread)

        and:
        Discount mockDiscount = Mock(Discount)
        mockDiscountFactory.create(mockBreadProduct, qtdDiscounts, BigDecimal.valueOf(0.5)) >> mockDiscount

        when:
        List<Discount> discounts = rule.calculate(mockBasket)

        then:
        discounts.size() == 1

        and:
        discounts.first() == mockDiscount

        where:
        qtdButter | qtdBread | qtdDiscounts | it
        4         | 1        | 1            | 'quantity of bread is not sufficient to get all possible discounts'
        5         | 3        | 2            | 'quantity of bread is sufficient to get all possible discounts'
    }

    private void setupMockResponses(int qtdButter, int qtdBread) {
        mockBasket.getBasketRow(ProductType.BUTTER) >> mockButterBasketRow
        mockBasket.getBasketRow(ProductType.BREAD) >> mockBreadBasketRow
        mockBreadBasketRow.getProduct() >> mockBreadProduct
        mockButterBasketRow.getQuantity() >> qtdButter
        mockBreadBasketRow.getQuantity() >> qtdBread
    }
}