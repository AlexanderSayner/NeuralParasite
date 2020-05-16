package sandbox.neural.parasite.dto.request

data class MaxSideEffectDto(
        val reactionCoefficient: String,
        val outerFrontWheelReaction: String,
        val innerFrontWheelReaction: String,
        val outerRearWheelReaction: String,
        val innerRearWheelReaction: String,
        val outerRearTangentReaction: String,
        val innerRearTangentReaction: String
)
