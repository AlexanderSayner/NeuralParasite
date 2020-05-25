package sandbox.neural.parasite.genetic

import org.slf4j.LoggerFactory
import sandbox.neural.parasite.auxiliary.getDecimalNumber
import sandbox.neural.parasite.genetic.principal.CarGene
import sandbox.neural.parasite.genetic.principal.Gene
import sandbox.neural.parasite.model.Car
import sandbox.neural.parasite.model.Corner
import sandbox.neural.parasite.utility.ComputationService
import kotlin.math.abs

@ExperimentalStdlibApi
class CarGeneticAlgorithm(_answer: Float,
                          _iterationsNumber: Int,
                          _populationNumber: Int,
                          private val computationService: ComputationService,
                          private val car: Car,
                          private val corner: Corner,
                          private val personRotation: String) {
    private val logger = LoggerFactory.getLogger("CarGeneticAlgorithmLog")
    private val populationNumber = _populationNumber
    private val iterationsNumber = _iterationsNumber
    private val limit = 63
    private val answer = _answer
    private var population = mutableListOf<CarGene>()

    init {
        logger.debug("CarGeneticAlgorithm init has called")
        if (populationNumber.compareTo(1) < 1) throw IllegalStateException("Нужно хотя бы немного больше, чем $populationNumber  особей в одной популяции")
        logger.info("Стартовая популяция из $populationNumber особей:")
        (1..populationNumber).forEach {
            val rands = (0..limit).random()
            val initGen = CarGene(rands, computationService, car, corner, personRotation)
            logger.info("$it. ${initGen.value} to binary ${initGen.alleles}")
            population.add(initGen)
        }
    }

    fun solve(): Int {
        val bestOfTheBest = mutableListOf<CarGene>()
        likelihoodInitialisation(population, answer)
        bestOfTheBest.add(population.minBy { gene -> gene.getFitness(answer) }!!)
        (0..iterationsNumber).forEach { i ->
            val newPopulation = getNewGeneration(population)
            likelihoodInitialisation(newPopulation, answer)
            if (comparePopulations(population, newPopulation) < 1) {
                population = newPopulation.toMutableList()
                logger.info("Ход $i Ракировочка")
            } else {
                logger.info("Ход $i - Ничего не дало")
            }
            bestOfTheBest.add(newPopulation.minBy { gene -> gene.getFitness(answer) }!!)
            (0 until populationNumber).forEach {
                val f = population[it].getFitness(answer)
                logger.info("Особь $i.$it, значение = ${population[it].value} ,приспособленность = $f")
                if (f == 0.0f) {
                    logger.info("Результат получен на $i-м/$iterationsNumber шаге")
                    return getDecimalNumber(population[it].alleles)
                }
            }
        }
        val result = getDecimalNumber(bestOfTheBest.minBy { gene -> gene.getFitness(answer) }!!.alleles)
        logger.info("За $iterationsNumber поколений так и не удалось найти достойный ответ. " +
                "Ожидался $answer, алгоритм дал ${result.toFloat()}. " +
                "Ошибка составила ${((result - answer) / answer) * 100}%")
        return result
    }

    private fun likelihoodInitialisation(geneList: List<Gene>, answer: Float) {
        val sum = theSumOfTheInverseRatios(geneList, answer)
        theCalculationOfProbabilities(geneList, answer, sum)
    }

    /**
     * Расчёт сумма обратных коэффициентов, учавствует в вычислении вероятности скрещивания хромосом
     */
    private fun theSumOfTheInverseRatios(geneList: List<Gene>, answer: Float): Float {
        var sum = 0.0f
        geneList.forEach {
            sum += 1.0f / (it.getFitness(answer))
        }
        return sum
    }

    /**
     * Вычисления вероятности взятия более желанных результатов в %
     */
    private fun theCalculationOfProbabilities(geneList: List<Gene>, answer: Float, theSumOfTheInverseRatios: Float) {
        geneList.forEach {
            it.likelihood = (1 / (it.getFitness(answer))) / theSumOfTheInverseRatios
        }
    }

    /**
     * Хромосома делится на две части в случайном месте
     * Затем случайным образом выбирается какая из частей уйдёт потомкам
     */
    fun breed(parentOne: CarGene, parentTwo: CarGene): CarGene {
        val size = parentOne.alleles.length
        val crossover = (1 until size).random()
        val headsOrTails = (0..1).random()
        val startIndex = crossover * headsOrTails
        val endIndex = size * headsOrTails + abs(crossover * (headsOrTails - 1))
        val piece = parentOne.alleles.subSequence(startIndex, endIndex)
        val child = parentTwo.alleles.replaceRange(startIndex, endIndex, piece)
        logger.info("\n Parent One: ${parentOne.alleles.substring(IntRange(0, crossover - 1))}|${parentOne.alleles.substring(IntRange(crossover, size - 1))}" +
                "\n Parent Two: ${parentTwo.alleles.substring(IntRange(0, crossover - 1))}|${parentTwo.alleles.substring(IntRange(crossover, size - 1))}" +
                "\n Left (0) Or Right (1): $headsOrTails" +
                "\n Child: $child")
        return CarGene(getDecimalNumber(child), computationService, car, corner, personRotation)
    }

    private fun breed(pair: Pair<CarGene, CarGene>) = breed(pair.first, pair.second)

    /**
     * Задаёт случайное значение и по параметру likelihood выбирает родителей для нового поколения
     */
    private fun getNewGeneration(geneList: List<CarGene>): List<CarGene> {
        if (geneList.size < 2) throw IllegalStateException("Надо бы побольше особей, чем ${geneList.size}")
        val parents = mutableListOf<Pair<CarGene, CarGene>>()
//        var last = 0.0f // Чтобы хоть немного повысить случайность пар
        geneList.forEach { _ ->// Здесь я хочу, чтобы в будущем поколении было столько же особей, сколько дано

            val index = (1 until geneList.size).random()
            var parentOne = geneList[(index until geneList.size).random()]
            var parentTwo = geneList[(0 until index).random()]
            for (it in geneList) {
                // Здесь начинается креатив
                if (it.likelihood > (0..99).random().toFloat()) {
                    parentOne = it
                    parentTwo = geneList
                            .filter { g -> g.likelihood != parentOne.likelihood }
                            .maxBy { g -> g.likelihood }
                            .let { parentTwo }
                    break
                }
            }
            parents.add(Pair(parentOne, parentTwo))
        }

        return parents.map { partners: Pair<CarGene, CarGene> ->
            breed(partners)
        }
    }

//    private fun mutation(was: Gene): Gene {
//        var mutant = ""
//        (1..6).forEach { _ ->
//            mutant = mutant.plus((0..1).random())
//        }
//        return Gene(Maths.getDecimalNumber(mutant))
//    }

    private fun comparePopulations(pop1: List<CarGene>, other: List<CarGene>): Int {
        var sum = 0.0f
        pop1.forEach {
            sum += it.likelihood
        }
        val likelihood1 = sum / pop1.size
        sum = 0.0f
        other.forEach {
            sum += it.likelihood
        }
        val likelihood2 = sum / other.size
        return when {
            likelihood1 > likelihood2 -> 1
            likelihood1 == likelihood2 -> 0
            else -> -1
        }
    }
}
