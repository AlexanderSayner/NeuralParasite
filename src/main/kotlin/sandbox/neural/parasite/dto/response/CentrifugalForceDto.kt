package sandbox.neural.parasite.dto.response

import java.math.BigDecimal

data class CentrifugalForceDto(
        val frontAxelForce: BigDecimal,
        val rearAxelForce: BigDecimal
)
