package sandbox.neural.parasite.dto.response

import java.math.BigDecimal

data class TotalForceDto(
        val frontAxelForceInTotal: BigDecimal,
        val rearAxelForceInTotal: BigDecimal
)
