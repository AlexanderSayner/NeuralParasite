package sandbox.neural.parasite.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class SuspensionFirmnessResponseDto(
        @get:JsonProperty("C1") val frontAxelSuspension: BigDecimal,
        @get:JsonProperty("C2") val rearAxelSuspension: BigDecimal
)
