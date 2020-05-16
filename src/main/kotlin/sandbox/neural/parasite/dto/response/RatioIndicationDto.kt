package sandbox.neural.parasite.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class RatioIndicationDto(
        @get:JsonProperty("L1") val frontLengthToMassCenter: BigDecimal,
        @get:JsonProperty("L2") val rearLengthToMassCenter: BigDecimal,
        @get:JsonProperty("m1") val frontAxleMass: BigDecimal,
        @get:JsonProperty("m2") val rearAxleMass: BigDecimal
)
