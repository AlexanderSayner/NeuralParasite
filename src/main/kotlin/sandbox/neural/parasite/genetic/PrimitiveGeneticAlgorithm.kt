package sandbox.neural.parasite.genetic

import org.slf4j.LoggerFactory
import sandbox.neural.parasite.genetic.principal.PrimitiveGene

@ExperimentalStdlibApi
class PrimitiveGeneticAlgorithm(
        answer: Float,
        iterationsNumber: Int,
        populationNumber: Int
) : AbstractGeneticAlgorithm(answer, iterationsNumber, mutableListOf()) {
    private val logger = LoggerFactory.getLogger("PrimitiveGeneticAlgorithm")
    private val limit = 63

    init {
        logger.warn("PrimitiveGeneticAlgorithm init has called")
        if (populationNumber.compareTo(1) < 1) throw IllegalStateException("Нужно хотя бы немного больше, чем $populationNumber  особей в одной популяции")
        logger.info("Стартовая популяция из $populationNumber особей:")
        (1..populationNumber).forEach {
            val rands = (1..limit).random()
            val initGen = if (rands.toFloat() == answer)
                PrimitiveGene(rands + (0 until answer.toInt()).random())
            else
                PrimitiveGene(rands)
            logger.info("$it. ${initGen.value} to binary ${initGen.alleles}")
            population.add(initGen)
        }
    }

    override fun initializeNewGene(value: Int) =
            PrimitiveGene(value)
}
