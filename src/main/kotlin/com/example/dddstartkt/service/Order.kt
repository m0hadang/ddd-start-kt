package com.example.dddstartkt.service

import org.springframework.core.StandardReflectionParameterNameDiscoverer

enum class OrderState {
    PAYMENT_WAITING,
    PREPARING,
    SHIPPED,
    DELIVERING,
    DELIVERY_COMPLETED,
    CANCELED,
}

data class Receiver(
    private var name: String,
    private var phoneNumber: String
)

data class Address(
    private var address1: String,
    private var address2: String,
    private var zipcode: String,
)

class Product()
class Money(
    var value: Int,
) {
    fun add(amount: Money): Money {
        return Money(this.value + amount.value)
    }
    fun multiply(multiplier: Int): Money {
        return Money(this.value * multiplier)
    }
}
class OrderLine(
    private var product: Product,
    private var price: Money,
    private var quantity: Int,
) {
    var amount: Money;

    init {
        this.amount = calculateAmounts()
    }

    fun isDifferentProduct(product: Product): Boolean {
        return this.product != product
    }

    private fun calculateAmounts(): Money {
        return this.price.multiply(this.quantity)
    }
}
class Order(
    private var orderNumber: String,
    private var orderState: OrderState,
    private var receiver: Receiver, // Rule : 받는 사람 정보는 반드시 지정해야 한다.
    private var address: Address, // Rule : 배송지 주소 정보는 반드시 지정해야 한다.
    private var orderLines: List<OrderLine>, // 주문 집합.
    private var totalAmounts: Int,
) {
    init {
        setOrderLines(orderLines)
    }

    private fun setOrderLines(orderLines: List<OrderLine>) {
        verifyAtLeastOneOrMoreOrderLines(orderLines)
        this.orderLines = orderLines
        calculateTotalAmounts()
    }

    // 출고 상태로 변경
    fun changeShipped() {
        this.orderState = OrderState.SHIPPED
    }

    // 배송지 정보 변경
    fun changeShippingInfo(newReceiver: Receiver, newAddress: Address) {
        // Rule : 배송지 변경은 출고 전에만 가능.
        verifyNotYetShipped()
        this.receiver = newReceiver
        this.address = newAddress
    }

    // 주문 취소
    fun cancel() {
        // Rule : 주문 취소는 출고 전에만 가능.
        verifyNotYetShipped()
        this.orderState = OrderState.CANCELED
    }

    // 결제 완료로 변경
    private fun verifyNotYetShipped() {
        if (this.orderState != OrderState.PAYMENT_WAITING &&
            this.orderState != OrderState.PREPARING
        )
            throw IllegalStateException("already shipped")
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
            this.orderLines.sumOf { it.amount.value }
    }

    override fun equals(other: Any?): Boolean {
        return (other is Order) && this.orderNumber == other.orderNumber
    }

    override fun hashCode(): Int {
        var result = orderNumber.hashCode()
        result = 31 * result + orderState.hashCode()
        result = 31 * result + receiver.hashCode()
        result = 31 * result + address.hashCode()
        result = 31 * result + orderLines.hashCode()
        result = 31 * result + totalAmounts
        return result
    }
}