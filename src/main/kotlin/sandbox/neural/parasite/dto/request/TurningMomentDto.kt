package sandbox.neural.parasite.dto.request

data class TurningMomentDto(
        val outerFrontTangentReaction: String,
        val innerFrontTangentReaction: String,
        val outerRearTangentReaction: String,
        val innerRearTangentReaction: String,
        val gauge: String,
        val wheelbase: String
)
