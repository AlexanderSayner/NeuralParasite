package sandbox.neural.parasite.genetic.principal

import sandbox.neural.parasite.model.Car
import sandbox.neural.parasite.model.Corner
import sandbox.neural.parasite.utility.ComputationService
import kotlin.math.abs

@ExperimentalStdlibApi
class CarGene(speed: Int,
              private val computationService: ComputationService,
              private val car: Car,
              private val corner: Corner,
              private val personRotation: String)
    : AbstractGene(speed) {
    override fun getFitness(fitnessCriteria: Float): Float {
        val generalComputation = computationService.generalComputation(car, corner, personRotation, value.toDouble())

        val maxSideForceFront = generalComputation.maxSideForceFront
        val maxSideForceRear = generalComputation.maxSideForceRear
        val totalSideForceFront = generalComputation.totalSideForceFront
        val totalSideForceRear = generalComputation.totalSideForceRear

        val differenceFront = (maxSideForceFront - totalSideForceFront).abs()
        val differenceRear = (maxSideForceRear - totalSideForceRear).abs()
        return abs(fitnessCriteria - (differenceFront + differenceRear).toFloat().div(2))
    }
}
