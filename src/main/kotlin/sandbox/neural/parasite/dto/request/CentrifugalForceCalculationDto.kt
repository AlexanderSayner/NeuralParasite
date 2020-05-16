package sandbox.neural.parasite.dto.request

data class CentrifugalForceCalculationDto(
        val mass: String,
        val speed: String,
        val radius: String,
        val frontWeightRatio: String,
        val rearWeightRatio: String
)
