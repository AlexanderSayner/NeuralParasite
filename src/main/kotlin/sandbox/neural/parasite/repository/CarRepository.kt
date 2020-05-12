package sandbox.neural.parasite.repository

import org.springframework.data.jpa.repository.JpaRepository
import sandbox.neural.parasite.model.Car

interface CarRepository : JpaRepository<Car, Long> // Дает нашему слою работы с данными весь набор CRUD операций
