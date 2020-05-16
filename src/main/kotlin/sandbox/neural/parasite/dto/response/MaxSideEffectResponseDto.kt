package sandbox.neural.parasite.dto.response

import java.math.BigDecimal

data class MaxSideEffectResponseDto(
        val frontMaxSideEffect: BigDecimal,
        val rearMaxSideEffect: BigDecimal
)
