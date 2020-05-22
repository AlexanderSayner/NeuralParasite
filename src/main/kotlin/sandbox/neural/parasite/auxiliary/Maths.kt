package sandbox.neural.parasite.auxiliary

import kotlin.math.pow

class Maths {
    companion object{
        /**
         * to get Decimal number from input binary number
         * @param binaryString строка с двоичным числом
         * @return соответствующее десятичное число
         */
        @JvmStatic
        fun getDecimalNumber(binaryString: String): Int {
            var binaryNumber = binaryString.toInt()
            var decimalNo = 0
            var power = 0

            while (binaryNumber > 0) {
                val r = binaryNumber % 10
                decimalNo = (decimalNo + r * 2.0.pow(power.toDouble())).toInt()
                binaryNumber /= 10
                power++
            }
            return decimalNo
        }
    }
}