package sandbox.neural.parasite.controller

import org.springframework.web.bind.annotation.*
import sandbox.neural.parasite.genetic.GeneticAlgorithm
import sandbox.neural.parasite.genetic.principal.Gene

@RestController
@RequestMapping("genetic")
class GeneticController {

    @ExperimentalStdlibApi
    @GetMapping
    fun index(@RequestParam iterations: Int, population: Int): String {
        val geneticAlgorithm = GeneticAlgorithm(15.0f, iterations, population)
        return "${geneticAlgorithm.solve()}"
    }

    @ExperimentalStdlibApi
    @PostMapping("test/gene")
    fun gene(@RequestParam number: Int) = Gene(number).alleles

    @ExperimentalStdlibApi
    @GetMapping("test/breed/const")
    fun breedConst(): String {
        val geneticAlgorithm = GeneticAlgorithm(15.0f, 50, 30)
        val parentOne = Gene(10)
        val parentTwo = Gene(63)
        return "child = ${geneticAlgorithm.breed(parentOne, parentTwo).alleles}"
    }

    @ExperimentalStdlibApi
    @PostMapping("test/breed")
    fun breed(@RequestParam one: Int, @RequestParam two: Int): String {
        val geneticAlgorithm = GeneticAlgorithm(15.0f, 50, 30)
        val parentOne = Gene(one)
        val parentTwo = Gene(two)
        return "child = ${geneticAlgorithm.breed(parentOne, parentTwo).alleles}"
    }

    @GetMapping("test")
    fun random(): Int {
        return (1 until 6).random()
    }
}