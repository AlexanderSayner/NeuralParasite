package sandbox.neural.parasite.dto.response

import com.fasterxml.jackson.annotation.JsonProperty
import java.math.BigDecimal

data class RollResultDto(
        @get:JsonProperty("max_front_side_force") val maxSideForceFront: BigDecimal,
        @get:JsonProperty("max_rear_side_force") val maxSideForceRear: BigDecimal,
        @get:JsonProperty("front_side_force") val totalSideForceFront: BigDecimal,
        @get:JsonProperty("rear_side_force") val totalSideForceRear: BigDecimal
)
