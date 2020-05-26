package sandbox.neural.parasite.utility

import org.springframework.stereotype.Service
import sandbox.neural.parasite.auxiliary.Constant
import kotlin.math.*

@Service
class FunctionService {

    /**
     * Приведение дробного значения радиан в десятичное
     */
    fun angleInRadian(numeratorK: Double, denominatorK: Double) =
            (numeratorK * PI) / denominatorK

    /**
     * Формула 2.2.1
     *         L
     *  R = -------
     *       tg(a)
     */
    fun turningRadiusAngleOfRotationOfTheWheels(wheelBaseLength: Double, radianAngle: Double) =
            wheelBaseLength.div(tan(radianAngle))

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
     * Масса, приходящаяся на ось
     */
    fun axleMass(axelWeightRatio: Double, totalMass: Double) = axelWeightRatio * totalMass

    /**
     * Расчётная жёсткость подвески (Н*м)/рад
     * Формула 2.1.17 и 2.1.18
     * C = m * 4pi^2 * f
     */
    fun firmnessOfTheSuspension(axelMass: Double, frequencyOfSuspensionVibrations: Double = 1.0) =
            axelMass * 4 * PI.pow(2) * frequencyOfSuspensionVibrations

    /**
     * Ширина профиля шины в метрах
     * Формула 2.2.4
     * B = ширина * соотношение (указывается на шинах)
     */
    fun tireProfileValue(tireWidth: Double, tireProfile: Double) =
            tireWidth * tireProfile

    /**
     * Расчёт силы перераспределения реакций
     * Формулы 2.1.14 и 2.1.15
     * dR = C * y * 1/B
     */
    fun redistributingReactions(suspensionFirmness: Double, angleOfHeel: Double, tireProfileValue: Double) =
            suspensionFirmness * angleOfHeel * (1 / tireProfileValue)

    /**
     * Реакция на колесо, см.
     * формулы 2.1.10, 2.1.11, 2.1.12, 2.1.13
     */
    fun wheelReaction(totalMass: Double, inverseLengthRatio: Double, redistributingReactions: Double) =
            (totalMass * Constant.G.value * 0.5 * inverseLengthRatio) + redistributingReactions

    /**
     * Коэффициент реакции
     * Формула 2.1.22
     * fr = f0 * (1 + Af * V^2) * (1 + V^2/(ф*g*R))
     */
    fun reactionCoefficient(speed: Double, radius: Double) =
            Constant.F0.value * (1 + Constant.Af.value * speed.pow(2)) * (1 + speed.pow(2) / (Constant.Fi.value * Constant.G.value * radius))

    /**
     * Касательные реакции для передних колёс
     * Формулы 2.1.19 и 2.1.20
     * Rx = Rz * fr
     */
    fun tangentReactionFront(wheelReaction: Double, reactionCoefficient: Double) =
            wheelReaction * reactionCoefficient

    /**
     * Полный радиус колеса
     * Формула 2.2.5
     */
    fun wheelRadius(wheelWidth: Double, tireProfile: Double, rimSize: Double) =
            (rimSize / 2) * 0.025 + wheelWidth * tireProfile

    /**
     * Касательные реакции для ведущих колёс
     * Формулы 2.1.23 и 2.1.24
     *      T - R * f * r
     * R = --------------
     *           r
     */
    fun tangentReactionRear(wheelReaction: Double, reactionCoefficient: Double, torque: Double, wheelRadius: Double) =
            (torque - wheelReaction * reactionCoefficient * wheelRadius) / wheelRadius

    /**
     * Максимальное боковое воздействие, которое способен выдержать автомобиль перед сносом
     * Формула 2.1.25
     * Rm = (R1outZ + R1innerZ) * sqrt(ф^2 - fr^2)
     * Учитываются реакции только на передние колёса
     */
    fun sideEffectFrontMax(outerWheelReaction: Double, innerWheelReaction: Double, reactionCoefficient: Double) =
            (outerWheelReaction + innerWheelReaction) * sqrt(abs(Constant.Fi.value.pow(2) - reactionCoefficient.pow(2)))

    /**
     * Максимальное боковое воздействие, которое способен выдержать автомобиль перед заносом
     * Формула 2.1.26
     * Rm = sqrt(R2outerZ^2 * ф^2 - R2outerX^2) + sqrt(R2innerZ^2 * ф^2 - R2innerX^2)
     * Учитываются реакции только на задние колёса
     */
    fun sideEffectRearMax(outerWheelReaction: Double, innerWheelReaction: Double, outerTangentReaction: Double, innerTangentReaction: Double) =
            sqrt(abs(outerWheelReaction.pow(2) * Constant.Fi.value.pow(2) - outerTangentReaction.pow(2))) +
                    sqrt(abs(innerWheelReaction.pow(2) * Constant.Fi.value.pow(2) - innerTangentReaction.pow(2)))

    /**
     * Поворачивающий момент
     * Формула 2.1.29
     * T = ((R1outerX-R1innerX)+(R2innerX-R2outerX))*(B/2)
     */
    fun turningMoment(
            outerFrontTangentReaction: Double,
            innerFrontTangentReaction: Double,
            outerRearTangentReaction: Double,
            innerRearTangentReaction: Double,
            gauge: Double,
            wheelbase: Double) =
            ((outerFrontTangentReaction - innerFrontTangentReaction) + (innerRearTangentReaction - outerRearTangentReaction)) * (0.5 * gauge / wheelbase)

    /**
     * Центробежная сила
     * Формулы 2.1.27 и 2.1.28
     */
    fun centrifugalForce(mass: Double, speed: Double, radius: Double, weightRatio: Double) =
            mass * speed.pow(2).div(radius) * weightRatio

    /**
     * Сумма всех сил
     */
    fun totalForce(centrifugalForce: Double, turningMoment: Double) =
            centrifugalForce + turningMoment
}
