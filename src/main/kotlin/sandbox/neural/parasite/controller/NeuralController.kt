package sandbox.neural.parasite.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sandbox.neural.parasite.dto.request.NeuralRequestDto
import sandbox.neural.parasite.service.NeuralService

@RestController
@RequestMapping("neural")
class NeuralController(_neuralService: NeuralService) {
    private val neuralService = _neuralService

    @ExperimentalStdlibApi
    @PostMapping("by/rotation")
    fun index(@RequestBody neuralRequestDto: NeuralRequestDto) =
            neuralService.startGeneticAlgorithm(neuralRequestDto)
}
