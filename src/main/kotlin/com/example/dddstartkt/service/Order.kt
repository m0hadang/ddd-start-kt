package com.example.dddstartkt.service

enum class OrderState {
    PAYMENT_WAITING,
    PREPARING,
    SHIPPED,
    DELIVERING,
    DELIVERY_COMPLETED,
}

class ShippingInfo(
)

class Product()
class OrderLine(
    private var product: Product,
    private var price: Int,
    private var quantity: Int,
) {
    var amount: Int;
    init {
        this.amount = calculateAmounts()
    }
    fun isDifferentProduct(product: Product): Boolean {
        return this.product != product
    }
    private fun calculateAmounts(): Int {
        return this.price * this.quantity
    }
}

class Order(
    private var orderState: OrderState,
    private var shippingInfo: ShippingInfo,
    private var orderLines: List<OrderLine>, // 주문 집합
    private var totalAmounts: Int,
) {
    // 출고 상태로 변경
    fun changeShipped() {
        this.orderState = OrderState.SHIPPED
    }
    // 배송지 정보 변경
    fun changeShippingInfo(changeShippingInfo: ShippingInfo) {
        if (!this.isShippingChangeable()) {
            throw IllegalStateException("배송중이 아닌 주문은 배송지를 변경할 수 없습니다.")
        }
        this.shippingInfo = changeShippingInfo
    }
    // 주문 취소
    fun cancel() {
    }
    // 결제 완료로 변경
    private fun isShippingChangeable(): Boolean {
        return this.orderState == OrderState.PAYMENT_WAITING ||
                this.orderState == OrderState.PAYMENT_WAITING
    }
    private fun setOrderLines(orderLines: List<OrderLine>) {
        verifyAtLeastOneOrMoreOrderLines(orderLines)
        this.orderLines = orderLines
        calculateTotalAmounts()
    }
    // Rule : 최소 한 종류 이상의 상품을 주문해야 한다.
    private fun verifyAtLeastOneOrMoreOrderLines(orderLines: List<OrderLine>) {
        if (this.orderLines.isEmpty()) {
            throw IllegalArgumentException("최소 한 종류 이상의 상품이 있어야 합니다.")
        }
    }
    // Rule : 총 주문 금액은 각 상품의 구매 가격 합을 모두 더한 금액이다.
    private fun calculateTotalAmounts() {
        this.totalAmounts =
            this.orderLines.sumOf { it.amount }
    }
}