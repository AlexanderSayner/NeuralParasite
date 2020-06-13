package sandbox.neural.parasite.controller

import org.springframework.web.bind.annotation.*
import sandbox.neural.parasite.auxiliary.getDecimalNumber
import sandbox.neural.parasite.genetic.PrimitiveGeneticAlgorithm
import sandbox.neural.parasite.genetic.principal.PrimitiveGene

@RestController
@RequestMapping("genetic")
class GeneticController {

    @ExperimentalStdlibApi
    @GetMapping
    fun index(@RequestParam iterations: Int, population: Int): String {
        val geneticAlgorithm = PrimitiveGeneticAlgorithm(15.0f, iterations, population)
        return "${geneticAlgorithm.solve()}"
    }

    @ExperimentalStdlibApi
    @PostMapping("/test/mutation")
    fun mutant(@RequestParam number: Int): String {
        val geneticAlgorithm = PrimitiveGeneticAlgorithm(15.0f, 50, 30)
        return getDecimalNumber(geneticAlgorithm.mutation(PrimitiveGene(number).alleles)).toString()
    }

    @ExperimentalStdlibApi
    @PostMapping("test/gene/binary")
    fun gene(@RequestParam number: Int) = PrimitiveGene(number).alleles

    @ExperimentalStdlibApi
    @GetMapping("test/breed/const")
    fun breedConst(): String {
        val geneticAlgorithm = PrimitiveGeneticAlgorithm(15.0f, 50, 30)
        val parentOne = PrimitiveGene(10)
        val parentTwo = PrimitiveGene(63)
        return "child = ${geneticAlgorithm.breed(parentOne, parentTwo).alleles}"
    }

    @ExperimentalStdlibApi
    @PostMapping("test/breed")
    fun breed(@RequestParam one: Int, @RequestParam two: Int): String {
        val geneticAlgorithm = PrimitiveGeneticAlgorithm(15.0f, 50, 30)
        val parentOne = PrimitiveGene(one)
        val parentTwo = PrimitiveGene(two)
        return "child = ${geneticAlgorithm.breed(parentOne, parentTwo).alleles}"
    }

    @GetMapping("test")
    fun random(): Int {
        return (1 until 6).random()
    }
}