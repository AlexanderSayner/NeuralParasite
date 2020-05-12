package sandbox.neural.parasite.controller

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import sandbox.neural.parasite.model.Car
import sandbox.neural.parasite.service.CarService

@RestController // Сообщаем как обрабатывать http запросы и в каком виде отправлять ответы (сериализация в JSON и обратно)
@RequestMapping("car") // Указываем префикс маршрута для всех экшенов
class CarController(private val carService: CarService) { // Внедряем наш сервис в качестве зависимости
    @GetMapping // Говорим что экшен принимает GET запрос без параметров в url
    fun index() = carService.all() // И возвращает результат метода all нашего сервиса. Функциональная запись с выводом типа

    @PostMapping // Экшен принимает POST запрос без параметров в url
    @ResponseStatus(HttpStatus.CREATED) // Указываем специфический HttpStatus при успешном ответе
    fun create(@RequestBody car: Car) = carService.add(car) // Принимаем объект Product из тела запроса и передаем его в метод add нашего сервиса

    @GetMapping("{id}") // Тут мы говорим что это GET запрос с параметром в url (http://localhost/car/{id})
    @ResponseStatus(HttpStatus.FOUND)
    fun read(@PathVariable id: Long) = carService.get(id) // Сообщаем что наш id типа Long и передаем его в метод get сервиса

    @PutMapping("{id}")
    fun update(@PathVariable id: Long, @RequestBody car: Car) = carService.edit(id, car) // Здесь мы принимаем один параметр из url, второй из тела PUT запроса и отдаем их методу edit

    @DeleteMapping("{id}")
    fun delete(@PathVariable id: Long) = carService.remove(id)
}
