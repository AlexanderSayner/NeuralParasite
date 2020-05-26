package sandbox.neural.parasite.service

import org.springframework.stereotype.Service
import sandbox.neural.parasite.dto.request.NeuralRequestDto
import sandbox.neural.parasite.genetic.CarGeneticAlgorithm
import sandbox.neural.parasite.repository.CarRepository
import sandbox.neural.parasite.repository.CornerRepository
import sandbox.neural.parasite.utility.ComputationService

@Service
class NeuralService(
        private val carRepository: CarRepository,
        private val cornerRepository: CornerRepository,
        private val computationService: ComputationService
) {
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
        return carGeneticAlgorithm.solve()
    }
}
