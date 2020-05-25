package sandbox.neural.parasite.service

import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import sandbox.neural.parasite.dto.request.NeuralRequestDto
import sandbox.neural.parasite.genetic.CarGeneticAlgorithm
import sandbox.neural.parasite.repository.CarRepository
import sandbox.neural.parasite.repository.CornerRepository
import sandbox.neural.parasite.utility.ComputationService
import java.text.DecimalFormat

@Service
class NeuralService(
        private val carRepository: CarRepository,
        private val cornerRepository: CornerRepository,
        private val computationService: ComputationService
) {
    private val logger = LoggerFactory.getLogger("NeuralServiceLog")
    private val df = DecimalFormat("# ###.00")

    @ExperimentalStdlibApi
    fun startGeneticAlgorithm(neuralRequestDto: NeuralRequestDto): Int {

        val car = carRepository.findById(neuralRequestDto.carId).orElseThrow {
            IllegalStateException("Нет данных об автомобиле с id=${neuralRequestDto.carId}")
        }
        val corner = cornerRepository.findById(neuralRequestDto.cornerId).orElseThrow {
            IllegalStateException("Нет данных о повороте с id=${neuralRequestDto.cornerId}")
        }

        val personRotation = neuralRequestDto.wheelAngle
        val carGeneticAlgorithm = CarGeneticAlgorithm(
                0.0f,
                50,
                30,
                computationService,
                car,
                corner,
                personRotation

        )
        /*
        val geneticSpeed = 12.0

        val generalComputation = computationService.generalComputation(car, corner, personRotation, geneticSpeed)

        var maxSideForceFront = generalComputation.maxSideForceFront
        var maxSideForceRear = generalComputation.maxSideForceRear
        var totalSideForceFront = generalComputation.totalSideForceFront
        var totalSideForceRear = generalComputation.totalSideForceRear

        var differenceFront = maxSideForceFront.minus(totalSideForceFront)
        var differenceRear = maxSideForceRear.minus(totalSideForceRear)
        var differenceInTotal = differenceFront.abs().plus(differenceRear.abs()).divide(valueOf(2))

        logger.info("Результаты расчётов генетического алгоритма:\n" +
                "Скорость - $geneticSpeed м/с,\n" +
                "Угол поворота колёс $personRotation радиан.\n" +
                "Отклонение от предельной силы по передней оси ${df.format(differenceFront)};\n" +
                "По задней ${df.format(differenceRear)}\n" +
                "Усреднённое значение отклонения ${df.format(differenceInTotal)}")


         */
        return carGeneticAlgorithm.solve()
    }
}
