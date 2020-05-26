package sandbox.neural.parasite.genetic

import org.slf4j.LoggerFactory
import sandbox.neural.parasite.genetic.principal.CarGene
import sandbox.neural.parasite.model.Car
import sandbox.neural.parasite.model.Corner
import sandbox.neural.parasite.utility.ComputationService

@ExperimentalStdlibApi
class CarGeneticAlgorithm(
        _answer: Float,
        _iterationsNumber: Int,
        populationNumber: Int,
        private val computationService: ComputationService,
        private val car: Car,
        private val corner: Corner,
        private val personRotation: String
) : AbstractGeneticAlgorithm(_answer, _iterationsNumber, mutableListOf()) {
    private val logger = LoggerFactory.getLogger("CarGeneticAlgorithmLog")
    private val limit = 63

    init {
        logger.debug("CarGeneticAlgorithm init has called")
        if (populationNumber.compareTo(1) < 1) throw IllegalStateException("Нужно хотя бы немного больше, чем $populationNumber особей в одной популяции")
        logger.info("Стартовая популяция из $populationNumber особей:")
        (1..populationNumber).forEach {
            val rands = (0..limit).random()
            val initGen = CarGene(rands, computationService, car, corner, personRotation)
            logger.info("$it. ${initGen.value} to binary ${initGen.alleles}")
            population.add(initGen)
        }
    }

    override fun initializeNewGene(value: Int) =
            CarGene(value, computationService, car, corner, personRotation)
}
