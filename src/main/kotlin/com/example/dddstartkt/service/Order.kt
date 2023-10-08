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
    private var amount: Int;
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
}