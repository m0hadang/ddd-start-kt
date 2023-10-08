package com.example.dddstartkt.domain

/*
고수준 모듈 : 여러 하위 기능을 조합해서 어떤 기능을 수행하는 모듈
저수준 모듈 : 하위 기능을 구현한 모듈

- 고수준 모듈은 저수준 모듈에 의존해서는 안된다.
- 인터페이스로 추상화 하였어도 하위 레이어에 존재하는 모듈에 의존 하였다면 여전히 저수준 모듈에 의존하고 있느 것이다.
* */
interface RuleDiscounter { // 고수준 모듈
    fun applyRules(orderLines: List<OrderLine>): Money
}
class DroolsRuleDiscounter: RuleDiscounter { // 저수준 모듈
    override fun applyRules(orderLines: List<OrderLine>): Money {
        return Money(0)
    }
}

class CalculateDiscountService(val ruleDiscounter: RuleDiscounter) { // 고수준 모듈
    fun calculateDiscount(orderLines: List<OrderLine>, customerId: String): Money{
        return ruleDiscounter.applyRules(orderLines)
    }
}