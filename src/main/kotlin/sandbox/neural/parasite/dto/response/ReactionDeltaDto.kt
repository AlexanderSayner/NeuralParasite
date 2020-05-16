package sandbox.neural.parasite.dto.response

import java.math.BigDecimal

data class ReactionDeltaDto(
        val frontReactionDelta: BigDecimal,
        val rearReactionDelta: BigDecimal
)
