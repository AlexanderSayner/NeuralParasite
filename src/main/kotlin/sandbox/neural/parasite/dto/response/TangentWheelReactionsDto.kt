package sandbox.neural.parasite.dto.response

import java.math.BigDecimal

data class TangentWheelReactionsDto(
        val outerFrontTangentReaction: BigDecimal,
        val innerFrontTangentReaction: BigDecimal,
        val outerRearTangentReaction: BigDecimal,
        val innerRearTangentReaction: BigDecimal
)
