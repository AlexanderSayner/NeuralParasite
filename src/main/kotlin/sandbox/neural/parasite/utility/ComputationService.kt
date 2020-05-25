package sandbox.neural.parasite.utility

import org.springframework.stereotype.Service
import sandbox.neural.parasite.dto.request.CarCornerForceDto
import sandbox.neural.parasite.dto.response.RollResultDto
import sandbox.neural.parasite.model.Car
import sandbox.neural.parasite.model.Corner
import sandbox.neural.parasite.repository.CarRepository
import sandbox.neural.parasite.repository.CornerRepository
import java.math.BigDecimal.valueOf

@Service
class ComputationService(
        private val carRepository: CarRepository,
        private val cornerRepository: CornerRepository,
        private val functionService: FunctionService
) {
    /**
     * Для вызова контроллером
     */
    fun generalComputation(carCornerForceDto: CarCornerForceDto) =
            generalComputation(
                    carCornerForceDto.carId,
                    carCornerForceDto.cornerId,
                    carCornerForceDto.wheelRotation,
                    carCornerForceDto.speed.toDouble()
            )

    /**
     * Встроенный поиск объектов
     */
    fun generalComputation(carId: Long, cornerId: Long, wheelAngle: String, speed: Double): RollResultDto {
        val car = carRepository.findById(carId).orElseThrow {
            IllegalStateException("Нет данных об автомобиле с id=${carId}")
        }
        val corner = cornerRepository.findById(cornerId).orElseThrow {
            IllegalStateException("Нет данных о повороте с id=${cornerId}")
        }
        return generalComputation(car, corner, wheelAngle, speed)
    }

    /**
     * Самый примитивный вызов для использования в расчётах генетическим алгоритмом
     */
    fun generalComputation(car: Car, corner: Corner, wheelAngle: String, speed: Double): RollResultDto {
        // Радиус траектории движения
        val radius = functionService.turningRadiusAngleOfRotationOfTheWheels(
                car.wheelbase.toDouble(),
                wheelAngle.toDouble()
        )
        return mainCalculations(car, corner, radius, speed)
    }

    /**
     * Операции вычисления физических величин
     */
    private fun mainCalculations(car: Car, corner: Corner, radius: Double, speed: Double): RollResultDto {
        // Масса на передней оси
        val m1 = functionService.axleMass(car.frontShareOfWeight.toDouble(), car.weight.toDouble())
        // Масса за задней оси
        val m2 = functionService.axleMass(car.rearShareOfWeight.toDouble(), car.weight.toDouble())
        // Жёсткость передней подвески
        val c1 = functionService.firmnessOfTheSuspension(m1)
        // Жёсткость задней подвески
        val c2 = functionService.firmnessOfTheSuspension(m2)
        // Профиль шины в метрах
        val b = functionService.tireProfileValue(car.wheelWidth.toDouble(), car.tireProfile.toDouble())
        // Реакция перераспределения на передней оси
        val dR1 = functionService.redistributingReactions(c1, corner.angleOfFrontHeel.toDouble(), b)
        // Реакция перераспределения на задней оси
        val dR2 = functionService.redistributingReactions(c2, corner.angleOfRearHeel.toDouble(), b)
        // Реакция дороги на переднее внешнее колесо
        val rz1outer = functionService.wheelReaction(car.weight.toDouble(), car.frontShareOfWeight.toDouble(), dR1)
        // Реакция дороги на переднее внутреннее колесо
        val rz1inner = functionService.wheelReaction(car.weight.toDouble(), car.frontShareOfWeight.toDouble(), dR1 * -1)
        // Реакция дороги на заднее внешнее колесо
        val rz2outer = functionService.wheelReaction(car.weight.toDouble(), car.rearShareOfWeight.toDouble(), dR2)
        // Реакция дороги на заднее внутреннее колесо
        val rz2inner = functionService.wheelReaction(car.weight.toDouble(), car.rearShareOfWeight.toDouble(), dR2 * -1)
        // Коэффициент реакции дороги
        val fr = functionService.reactionCoefficient(speed, radius)
        // Касательная реакция дороги для переднего внешнего колеса
        val rx1outer = functionService.tangentReactionFront(rz1outer, fr)
        // Касательная реакция дороги для переднего внутреннего колеса
        val rx1inner = functionService.tangentReactionFront(rz1inner, fr)
        // Wheel radius
        val rw = functionService.wheelRadius(car.wheelWidth.toDouble(), car.tireProfile.toDouble(), car.rimSize.toDouble())
        // Касательная реакция дороги для заднего внешнего колеса
        val rx2outer = functionService.tangentReactionRear(rz2outer, fr, corner.outerTorque.toDouble(), rw)
        // Касательная реакция дороги для заднего внутреннего колеса
        val rx2inner = functionService.tangentReactionRear(rz2inner, fr, corner.innerTorque.toDouble(), rw)
        // Максимальная боковая сила передней оси для автомобиля
        val ry1max = functionService.sideEffectFrontMax(rz1outer, rz1inner, fr)
        // Максимальная боковая сила задней передней оси для автомобиля
        val ry2max = functionService.sideEffectRearMax(rz2outer, rz2inner, rx2outer, rx2inner)
        // Центробежная сила на передней оси
        val fc1 = functionService
                .centrifugalForce(
                        car.weight.toDouble(),
                        speed,
                        radius,
                        car.frontShareOfWeight.toDouble()
                )
        // Центробежная сила на задней оси
        val fc2 = functionService
                .centrifugalForce(
                        car.weight.toDouble(),
                        speed,
                        radius,
                        car.rearShareOfWeight.toDouble()
                )
        // Поворачивающий момент
        val t = functionService.turningMoment(rx1outer, rx1inner, rx2outer, rx2inner, car.gauge.toDouble(), b)
        // Суммарная сила, действующая на переднюю ось
        val f1sum = functionService.totalForce(fc1, t)
        // Суммарная сила, действующая на заднюю ось
        val f2sum = functionService.totalForce(fc2, -t)
        // Результаты
        return RollResultDto(
                valueOf(ry1max),
                valueOf(ry2max),
                valueOf(f1sum),
                valueOf(f2sum)
        )
    }
}
