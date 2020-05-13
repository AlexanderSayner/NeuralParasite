package sandbox.neural.parasite.utility

import org.springframework.stereotype.Service
import java.math.BigDecimal
import kotlin.math.tan

@Service
class ComputationService {

    /**
     * Формула 2.2.1
     *         L
     *  R = -------
     *       tg(a)
     */
    fun turningRadiusAngleOfRotationOfTheWheels(wheelBaseLength: Double, radianAngle: Double) =
            BigDecimal.valueOf(wheelBaseLength).div(BigDecimal.valueOf(tan(radianAngle)))

    /**
     * Формула 2.2.2
     *            L
     * L1 = m2 * ---
     *            m
     */
    fun frontLengthToMassCenter(rearWeightRatio: Double, wheelBaseLength: Double) = rearWeightRatio * wheelBaseLength

    /**
     * Формула 2.2.3
     *            L
     * L2 = m1 * ---
     *            m
     */
    fun rearLengthToMassCenter(frontWeightRatio: Double, wheelBaseLength: Double) = frontWeightRatio * wheelBaseLength

    /**
     * Масса, приходящаяся на переднюю ось
     */
    fun frontAxleMass(frontWeightRatio: Double, totalMass: Double) = frontWeightRatio * totalMass

    /**
     * Масса, приходящаяся на заднюю ось
     */
    fun rearAxleMass(rearWeightRatio: Double, totalMass: Double) = rearWeightRatio * totalMass
}
