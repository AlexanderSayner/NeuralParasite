package sandbox.neural.parasite.genetic.principal

import sandbox.neural.parasite.auxiliary.getDecimalNumber
import kotlin.math.abs

@ExperimentalStdlibApi
class PrimitiveGene(value: Int) : AbstractGene(value) {
    override fun getFitness(fitnessCriteria: Float): Float {
        return abs(fitnessCriteria - getDecimalNumber(
                alleles
        ))
    }
}
