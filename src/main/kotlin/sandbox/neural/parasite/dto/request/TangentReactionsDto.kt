package sandbox.neural.parasite.dto.request

data class TangentReactionsDto(
        val speed: String,
        val radius: String,
        val outerFrontWheelReaction: String,
        val innerFrontWheelReaction: String,
        val outerRearWheelReaction: String,
        val innerRearWheelReaction: String,
        val torque: String,
        val wheelRadius: String
)
