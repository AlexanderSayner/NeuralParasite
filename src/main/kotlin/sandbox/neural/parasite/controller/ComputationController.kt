package sandbox.neural.parasite.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sandbox.neural.parasite.dto.GenericApiResponseDto
import sandbox.neural.parasite.dto.request.RatioDto
import sandbox.neural.parasite.dto.request.TurningRadiusAngleOfRotationOfTheWheelsDto
import sandbox.neural.parasite.dto.response.RatioIndicationDto
import sandbox.neural.parasite.utility.ComputationService
import java.math.BigDecimal

@RestController
@RequestMapping("function")
class ComputationController(private val computationService: ComputationService) {

    @PostMapping("turn/radius")
    fun turningRadius(@RequestBody turningRadiusAngleOfRotationOfTheWheelsDto: TurningRadiusAngleOfRotationOfTheWheelsDto) =
            GenericApiResponseDto("Радиус траектории автомобиля в метрах", computationService
                    .turningRadiusAngleOfRotationOfTheWheels(
                            turningRadiusAngleOfRotationOfTheWheelsDto.wheelBaseLength.toDouble(),
                            turningRadiusAngleOfRotationOfTheWheelsDto.radianAngle.toDouble()
                    )
            )

    @PostMapping("ratio")
    fun ratioCalculation(@RequestBody ratioDto: RatioDto) =
            GenericApiResponseDto("Расчёт расстояние от центра масс до осей", RatioIndicationDto(
                    BigDecimal.valueOf(computationService.frontLengthToMassCenter(ratioDto.rearWeightRatio.toDouble(), ratioDto.baseLength.toDouble())),
                    BigDecimal.valueOf(computationService.rearLengthToMassCenter(ratioDto.frontWeightRatio.toDouble(), ratioDto.baseLength.toDouble())),
                    BigDecimal.valueOf(computationService.frontAxleMass(ratioDto.frontWeightRatio.toDouble(), ratioDto.mass.toDouble())),
                    BigDecimal.valueOf(computationService.rearAxleMass(ratioDto.rearWeightRatio.toDouble(), ratioDto.mass.toDouble())))
            )
}
