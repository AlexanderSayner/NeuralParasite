package sandbox.neural.parasite.dto.request

/**
 * Dto для расчётов радиуса траектории по углу повотора колёс и базе автомобиля
 */
data class TurningRadiusAngleOfRotationOfTheWheelsDto(
        val wheelBaseLength: String,
        val radianAngle: String
)
