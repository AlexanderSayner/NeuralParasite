package sandbox.neural.parasite.controller

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import sandbox.neural.parasite.dto.GenericApiResponseDto
import sandbox.neural.parasite.dto.request.*
import sandbox.neural.parasite.dto.response.*
import sandbox.neural.parasite.utility.FunctionService
import java.math.BigDecimal.valueOf

@RestController
@RequestMapping("function")
@OpenAPIDefinition(info = Info(
        title = "Белый ящик")
)
class FunctionController(private val functionService: FunctionService) {

    @PostMapping("turn/radius")
    fun turningRadius(@RequestBody turningRadiusAngleOfRotationOfTheWheelsDto: TurningRadiusAngleOfRotationOfTheWheelsDto) =
            GenericApiResponseDto("Радиус траектории автомобиля в метрах", functionService
                    .turningRadiusAngleOfRotationOfTheWheels(
                            turningRadiusAngleOfRotationOfTheWheelsDto.wheelBaseLength.toDouble(),
                            turningRadiusAngleOfRotationOfTheWheelsDto.radianAngle.toDouble()
                    )
            )

    @PostMapping("ratio")
    fun ratioCalculation(@RequestBody ratioDto: RatioDto) =
            GenericApiResponseDto("Расчёт расстояние от центра масс до осей",
                    RatioIndicationDto(
                            valueOf(
                                    functionService.frontLengthToMassCenter(
                                            ratioDto.rearWeightRatio.toDouble(),
                                            ratioDto.baseLength.toDouble()
                                    )
                            ),
                            valueOf(
                                    functionService.rearLengthToMassCenter(
                                            ratioDto.frontWeightRatio.toDouble(),
                                            ratioDto.baseLength.toDouble()
                                    )
                            ),
                            valueOf(
                                    functionService.axleMass(
                                            ratioDto.frontWeightRatio.toDouble(),
                                            ratioDto.mass.toDouble()
                                    )
                            ),
                            valueOf(
                                    functionService.axleMass(
                                            ratioDto.rearWeightRatio.toDouble(),
                                            ratioDto.mass.toDouble()
                                    )
                            )
                    )
            )

    @PostMapping("suspension/firmness")
    fun firmnessOfTheSuspension(@RequestBody suspensionFirmnessDto: SuspensionFirmnessDto) =
            GenericApiResponseDto("Расчётная жёсткость подвески",
                    SuspensionFirmnessResponseDto(
                            valueOf(
                                    functionService.firmnessOfTheSuspension(
                                            suspensionFirmnessDto.frontAxelMass.toDouble()
                                    )
                            ),
                            valueOf(
                                    functionService.firmnessOfTheSuspension(
                                            suspensionFirmnessDto.rearAxelMass.toDouble()
                                    )
                            )
                    )
            )

    @PostMapping("reactions/redistributing")
    fun redistributingReactions(@RequestBody redistributionReactions: RedistributionReactions) =
            GenericApiResponseDto("Расчёт перераспределения реакций оси",
                    ReactionDeltaDto(
                            valueOf(
                                    functionService.redistributingReactions(
                                            redistributionReactions.firmnessOfTheFrontSuspension.toDouble(),
                                            redistributionReactions.angleOfFrontHeel.toDouble(),
                                            functionService.tireProfileValue(
                                                    redistributionReactions.tireWidth.toDouble(),
                                                    redistributionReactions.tireProfile.toDouble()))),
                            valueOf(
                                    functionService.redistributingReactions(
                                            redistributionReactions.firmnessOfTheRearSuspension.toDouble(),
                                            redistributionReactions.angleOfRearHeel.toDouble(),
                                            functionService.tireProfileValue(
                                                    redistributionReactions.tireWidth.toDouble(),
                                                    redistributionReactions.tireProfile.toDouble()
                                            )
                                    )
                            )
                    )
            )

    @PostMapping("reactions/wheel")
    fun reactionOnWheel(@RequestBody wheelsReactionDto: WheelsReactionDto) =
            GenericApiResponseDto("Расчёт деакции на каждое колесо",
                    WheelsReactionResponseDto(
                            valueOf(
                                    functionService.wheelReaction(
                                            wheelsReactionDto.totalMass.toDouble(),
                                            wheelsReactionDto.frontWeightRatio.toDouble(),
                                            wheelsReactionDto.frontReactionDelta.toDouble())),
                            valueOf(
                                    functionService.wheelReaction(
                                            wheelsReactionDto.totalMass.toDouble(),
                                            wheelsReactionDto.rearWeightRatio.toDouble(),
                                            wheelsReactionDto.rearReactionDelta.toDouble())),
                            valueOf(
                                    functionService.wheelReaction(
                                            wheelsReactionDto.totalMass.toDouble(),
                                            wheelsReactionDto.frontWeightRatio.toDouble(),
                                            -1 * wheelsReactionDto.frontReactionDelta.toDouble())),
                            valueOf(
                                    functionService.wheelReaction(
                                            wheelsReactionDto.totalMass.toDouble(),
                                            wheelsReactionDto.rearWeightRatio.toDouble(),
                                            -1 * wheelsReactionDto.rearReactionDelta.toDouble()
                                    )
                            )
                    )
            )

    @PostMapping("reaction/coefficient")
    fun reactionCoefficient(@RequestBody speedRadiusDto: SpeedRadiusDto) =
            GenericApiResponseDto("Коэффициент реакции дороги",
                    valueOf(
                            functionService.reactionCoefficient(
                                    speedRadiusDto.speed.toDouble(),
                                    speedRadiusDto.radius.toDouble()
                            )
                    )
            )

    @PostMapping("wheel/radius")
    fun completeWheelRadius(@RequestBody wheelDto: WheelDto) =
            GenericApiResponseDto("Радиус колеса",
                    valueOf(
                            functionService.wheelRadius(
                                    wheelDto.wheelWidth.toDouble(),
                                    wheelDto.tireProfile.toDouble(),
                                    wheelDto.rimSize.toDouble()
                            )
                    )
            )

    @PostMapping("reactions/tangent")
    fun tangentReactions(@RequestBody tangentReactionsDto: TangentReactionsDto) =
            GenericApiResponseDto("Расчёт касательных реакций",
                    TangentWheelReactionsDto(
                            valueOf(
                                    functionService.tangentReactionFront(
                                            tangentReactionsDto.outerFrontWheelReaction.toDouble(),
                                            functionService.reactionCoefficient(
                                                    tangentReactionsDto.speed.toDouble(),
                                                    tangentReactionsDto.radius.toDouble()))),
                            valueOf(
                                    functionService.tangentReactionFront(
                                            tangentReactionsDto.innerFrontWheelReaction.toDouble(),
                                            functionService.reactionCoefficient(
                                                    tangentReactionsDto.speed.toDouble(),
                                                    tangentReactionsDto.radius.toDouble()))),
                            valueOf(
                                    functionService.tangentReactionRear(
                                            tangentReactionsDto.outerRearWheelReaction.toDouble(),
                                            functionService.reactionCoefficient(
                                                    tangentReactionsDto.speed.toDouble(),
                                                    tangentReactionsDto.radius.toDouble()),
                                            tangentReactionsDto.torque.toDouble(),
                                            tangentReactionsDto.wheelRadius.toDouble())),
                            valueOf(
                                    functionService.tangentReactionRear(
                                            tangentReactionsDto.innerRearWheelReaction.toDouble(),
                                            functionService.reactionCoefficient(
                                                    tangentReactionsDto.speed.toDouble(),
                                                    tangentReactionsDto.radius.toDouble()),
                                            tangentReactionsDto.torque.toDouble(),
                                            tangentReactionsDto.wheelRadius.toDouble()
                                    )
                            )
                    )
            )

    @PostMapping("reactions/side/max")
    fun maxSideReactions(@RequestBody maxSideEffectDto: MaxSideEffectDto) =
            GenericApiResponseDto("Максимальные боковые реакции",
                    MaxSideEffectResponseDto(
                            valueOf(
                                    functionService.sideEffectFrontMax(
                                            maxSideEffectDto.outerFrontWheelReaction.toDouble(),
                                            maxSideEffectDto.innerFrontWheelReaction.toDouble(),
                                            maxSideEffectDto.reactionCoefficient.toDouble())),
                            valueOf(
                                    functionService.sideEffectRearMax(
                                            maxSideEffectDto.outerRearWheelReaction.toDouble(),
                                            maxSideEffectDto.innerRearWheelReaction.toDouble(),
                                            maxSideEffectDto.outerRearTangentReaction.toDouble(),
                                            maxSideEffectDto.innerRearTangentReaction.toDouble()
                                    )
                            )
                    )
            )

    @PostMapping("turning/moment")
    fun turningMoment(@RequestBody turningMomentDto: TurningMomentDto) =
            GenericApiResponseDto("Поворачивающий момент",
                    valueOf(
                            functionService.turningMoment(
                                    turningMomentDto.outerFrontTangentReaction.toDouble(),
                                    turningMomentDto.innerFrontTangentReaction.toDouble(),
                                    turningMomentDto.outerRearTangentReaction.toDouble(),
                                    turningMomentDto.innerRearTangentReaction.toDouble(),
                                    turningMomentDto.gauge.toDouble(),
                                    turningMomentDto.wheelbase.toDouble()
                            )
                    )
            )

    @PostMapping("force/centrifugal")
    fun centrifugalForce(@RequestBody centrifugalForceCalculationDto: CentrifugalForceCalculationDto) =
            GenericApiResponseDto("Центробежная сила",
                    CentrifugalForceDto(
                            valueOf(
                                    functionService.centrifugalForce(
                                            centrifugalForceCalculationDto.mass.toDouble(),
                                            centrifugalForceCalculationDto.speed.toDouble(),
                                            centrifugalForceCalculationDto.radius.toDouble(),
                                            centrifugalForceCalculationDto.frontWeightRatio.toDouble()
                                    )
                            ),
                            valueOf(
                                    functionService.centrifugalForce(
                                            centrifugalForceCalculationDto.mass.toDouble(),
                                            centrifugalForceCalculationDto.speed.toDouble(),
                                            centrifugalForceCalculationDto.radius.toDouble(),
                                            centrifugalForceCalculationDto.rearWeightRatio.toDouble()
                                    )
                            )
                    )
            )

    @PostMapping("force/total")
    fun totalForceCalculation(@RequestBody totalForceCalculationDto: TotalForceCalculationDto) =
            GenericApiResponseDto("Суммарная сила на переднюю и заднюю оси автомобиля",
                    TotalForceDto(
                            valueOf(
                                    functionService.totalForce(
                                            totalForceCalculationDto.centrifugalFrontForce.toDouble(),
                                            totalForceCalculationDto.turningMoment.toDouble()
                                    )
                            ),
                            valueOf(functionService.totalForce(
                                    totalForceCalculationDto.centrifugalRearForce.toDouble(),
                                    -1 * totalForceCalculationDto.turningMoment.toDouble())
                            )
                    )
            )
}
