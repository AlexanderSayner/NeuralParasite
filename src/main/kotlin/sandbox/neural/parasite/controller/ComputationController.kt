package sandbox.neural.parasite.controller

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sandbox.neural.parasite.dto.GenericApiResponseDto
import sandbox.neural.parasite.dto.request.CarCornerForceDto
import sandbox.neural.parasite.utility.ComputationService

@RestController
@RequestMapping("computation")
@OpenAPIDefinition(info = Info(
        title = "Чёрный ящик")
)
class ComputationController(
        private val computationService: ComputationService
) {
    @PostMapping
    fun rollTests(@RequestBody carCornerForceDto: CarCornerForceDto) =
            GenericApiResponseDto("Расчёт возможности сноса или заноса",
                    computationService.generalComputation(carCornerForceDto)
            )
}
