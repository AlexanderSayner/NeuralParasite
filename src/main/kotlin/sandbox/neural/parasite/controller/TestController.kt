package sandbox.neural.parasite.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import kotlin.math.roundToInt

@RestController
@RequestMapping("test")
class TestController {
    @PostMapping("round/double/to/int")
    fun index(@RequestParam double: Double) = double.roundToInt()
}
