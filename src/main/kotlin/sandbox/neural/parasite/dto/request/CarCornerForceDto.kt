package sandbox.neural.parasite.dto.request

import com.fasterxml.jackson.annotation.JsonProperty

data class CarCornerForceDto (
        @get:JsonProperty("car_id") val carId: Long,
        @get:JsonProperty("corner_id") val cornerId: Long,
        @get:JsonProperty("speed_meter_per_second") val speed: String,
        @get:JsonProperty("wheels_rotation_radians") val wheelRotation: String
)
