package sandbox.neural.parasite.genetic.principal

import sandbox.neural.parasite.auxiliary.Maths
import kotlin.math.abs

class Gene(value: Int) {
    private val length = 6

    // Работаю с двоичными числами - так интереснее
    private val charAlleles: CharArray = value.toString(2).toCharArray()
    @ExperimentalStdlibApi
    val alleles: String
        get() {
            var strAlleles = charAlleles.concatToString()
            while (strAlleles.length < length) {
                strAlleles = "0".plus(strAlleles)
            }
            return strAlleles
        }

    // % приспособленности гена
    var likelihood = 0.0f

    // Коэффициент пригодности
    @ExperimentalStdlibApi
    fun getFitness(welcomeAnswer: Float): Float {
        return abs(welcomeAnswer - Maths.getDecimalNumber(
                alleles
        ))
    }

    @ExperimentalStdlibApi
    val value:Int get() {
        return Maths.getDecimalNumber(alleles)
    }

    /**
     * Indicates whether some other object is "equal to" this one. Implementations must fulfil the following
     * requirements:
     *
     * * Reflexive: for any non-null value `x`, `x.equals(x)` should return true.
     * * Symmetric: for any non-null values `x` and `y`, `x.equals(y)` should return true if and only if `y.equals(x)` returns true.
     * * Transitive:  for any non-null values `x`, `y`, and `z`, if `x.equals(y)` returns true and `y.equals(z)` returns true, then `x.equals(z)` should return true.
     * * Consistent:  for any non-null values `x` and `y`, multiple invocations of `x.equals(y)` consistently return true or consistently return false, provided no information used in `equals` comparisons on the objects is modified.
     * * Never equal to null: for any non-null value `x`, `x.equals(null)` should return false.
     *
     * Read more about [equality](https://kotlinlang.org/docs/reference/equality.html) in Kotlin.
     */
    @ExperimentalStdlibApi
    override fun equals(other: Any?): Boolean {
        if (other == null || other !is Gene) return false
        var counter = 0
        alleles.forEach { c ->
            if (other.alleles[counter++] != c)
                return false
        }
        return true
    }

    /**
     * Returns a hash code value for the object.  The general contract of `hashCode` is:
     *
     * * Whenever it is invoked on the same object more than once, the `hashCode` method must consistently return the same integer, provided no information used in `equals` comparisons on the object is modified.
     * * If two objects are equal according to the `equals()` method, then calling the `hashCode` method on each of the two objects must produce the same integer result.
     */
    override fun hashCode(): Int {
        return charAlleles.contentHashCode()
    }
}
