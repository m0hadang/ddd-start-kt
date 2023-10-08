package com.example.dddstartkt.service

enum class OrderState {
    PAYMENT_WAITING {
        override fun isShippingProgress(): Boolean {
            return true
        }
    },
    PREPARING {
        override fun isShippingProgress(): Boolean {
            return true
        }
    },
    SHIPPED,
    DELIVERING,
    DELIVERY_COMPLETED;
    open fun isShippingProgress(): Boolean {
        return false
    }
}

class ShippingInfo(
)

class Order(
    private var orderState: OrderState,
    private var shippingInfo: ShippingInfo,
) {
    fun changeShippingInfo(changeShippingInfo: ShippingInfo) {
        if (!orderState.isShippingProgress()) {
            throw IllegalStateException("배송중이 아닌 주문은 배송지를 변경할 수 없습니다.")
        }
        this.shippingInfo = changeShippingInfo
    }
    fun changeShipped() {
        this.orderState = OrderState.SHIPPED
    }
}