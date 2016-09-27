package com.travelcorporation.baskettest

import spock.lang.Specification

class BasketSpec extends Specification {

    private DiscountsEngine mockDiscountsEngine
    private BasketRowFactory mockBasketRowFactory
    private BasketRow mockBreadBasketRow
    private BasketRow mockButterBasketRow
    private BasketRow mockMilkBasketRow
    private Product mockBreadProduct
    private Product mockButterProduct
    private Product mockMilkProduct

    private Basket basket

    def setup() {
        mockBreadProduct = Mock(Product)
        mockButterProduct = Mock(Product)
        mockMilkProduct = Mock(Product)

        mockDiscountsEngine = Mock(DiscountsEngine)
        mockBasketRowFactory = Mock(BasketRowFactory)
        mockBreadBasketRow = Mock(BasketRow)
        mockButterBasketRow = Mock(BasketRow)
        mockMilkBasketRow = Mock(BasketRow)

        basket = new Basket(mockBasketRowFactory, mockDiscountsEngine)
    }

    def "addProduct throws exception when product is null"() {

        when:
        basket.addProduct(null, 1)

        then:
        NullPointerException ex = thrown()
        ex.message == 'product cannot be null'
    }

    def "addProduct throws exception when quantity is not a positive number"() {

        when:
        basket.addProduct(mockBreadProduct, -1)

        then:
        IllegalArgumentException ex = thrown()
        ex.message == 'quantity must be positive'
    }

    def "addProduct stores a basket row with product and quantity"() {

        given:
        mockBreadProduct.getProductType() >> ProductType.BREAD
        mockBasketRowFactory.create(mockBreadProduct, 1) >> mockBreadBasketRow

        when:
        basket.addProduct(mockBreadProduct, 1)

        then:
        BasketRow basketRow = basket.getBasketRow(ProductType.BREAD)
        basketRow == mockBreadBasketRow
    }

    def "getBasketRow throws exception when productType is null"() {

        when:
        basket.getBasketRow(null)

        then:
        NullPointerException ex = thrown()
        ex.message == 'productType cannot be null'
    }

    def "getTotalCost returns the total cost of the basket minus discounts"() {

        given:
        mockBreadProduct.getProductType() >> ProductType.BREAD
        mockButterProduct.getProductType() >> ProductType.BUTTER
        mockMilkProduct.getProductType() >> ProductType.MILK

        and:
        mockBasketRowFactory.create(mockBreadProduct, 1) >> mockBreadBasketRow
        mockBasketRowFactory.create(mockButterProduct, 2) >> mockButterBasketRow
        mockBasketRowFactory.create(mockMilkProduct, 3) >> mockMilkBasketRow

        and:
        mockBreadBasketRow.getCost() >> BigDecimal.valueOf(1.00)
        mockButterBasketRow.getCost() >> BigDecimal.valueOf(1.0)
        mockMilkBasketRow.getCost() >> BigDecimal.valueOf(1.00)
        mockDiscountsEngine.getDiscount(basket) >> BigDecimal.valueOf(0.25)

        and:
        basket.addProduct(mockBreadProduct, 1)
        basket.addProduct(mockButterProduct, 2)
        basket.addProduct(mockMilkProduct, 3)

        when:
        BigDecimal totalCost = basket.getTotalCost()

        then:
        totalCost == BigDecimal.valueOf(2.75)
    }

}
