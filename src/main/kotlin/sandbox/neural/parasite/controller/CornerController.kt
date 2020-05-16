package sandbox.neural.parasite.controller

import io.swagger.v3.oas.annotations.OpenAPIDefinition
import io.swagger.v3.oas.annotations.info.Info
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sandbox.neural.parasite.model.Corner
import sandbox.neural.parasite.service.CornerService

@RestController
@RequestMapping("corner")
@OpenAPIDefinition(info = Info(
        title = "CRUD операции для данных о поворотах")
)
class CornerController(private val cornerService: CornerService) {
    @GetMapping
    fun index() = cornerService.all()

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun create(@RequestBody corner: Corner) = cornerService.add(corner)

    @GetMapping("{id}")
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = cornerService.get(id)

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody corner: Corner) = cornerService.edit(id, corner)

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = cornerService.remove(id)
}
