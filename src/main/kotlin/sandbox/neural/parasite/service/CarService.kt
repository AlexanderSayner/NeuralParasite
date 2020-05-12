package sandbox.neural.parasite.service

import org.springframework.stereotype.Service
import sandbox.neural.parasite.model.Car
import sandbox.neural.parasite.repository.CarRepository
import java.util.*

@Service // Позволяем IoC контейнеру внедрять класс
class CarService(private val carRepository: CarRepository) { // Внедряем репозиторий в качестве зависимости
    fun all(): Iterable<Car> = carRepository.findAll() // Возвращаем коллекцию сущностей, функциональная запись с указанием типа

    fun get(id: Long): Optional<Car> = carRepository.findById(id)

    fun add(car: Car): Car = carRepository.save(car)

    fun edit(id: Long, car: Car): Car = carRepository.save(car.copy(id = id)) // Сохраняем копию объекта с указанным id в БД. Идиоматика Kotlin говорит что НЕ изменяемый - всегда лучше чем изменяемый (никто не поправит значение в другом потоке) и предлагает метод copy для копирования объектов (специальных классов для хранения данных) с возможностью замены значений

    fun remove(id: Long) = carRepository.deleteById(id)
}
