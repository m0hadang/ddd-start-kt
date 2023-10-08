package com.example.dddstartkt.domain

interface RuleDiscounter {
    fun applyRules(orderLines: List<OrderLine>): Money
}
class DroolsRuleDiscounter: RuleDiscounter {
    override fun applyRules(orderLines: List<OrderLine>): Money {
        return Money(0)
    }
}
class CalculateDiscountService(val ruleDiscounter: RuleDiscounter) {
    fun calculateDiscount(orderLines: List<OrderLine>, customerId: String): Money{
        return ruleDiscounter.applyRules(orderLines)
    }
}