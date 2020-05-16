package sandbox.neural.parasite.dto.response

import java.math.BigDecimal

data class WheelsReactionResponseDto(
        val frontOuter: BigDecimal,
        val rearOuter: BigDecimal,
        val frontInner: BigDecimal,
        val rearInner: BigDecimal
)
