package sandbox.neural.parasite.service

import org.springframework.stereotype.Service
import sandbox.neural.parasite.model.Corner
import sandbox.neural.parasite.repository.CornerRepository
import java.util.*

@Service
class CornerService(private val cornerRepository: CornerRepository) {
    fun all(): Iterable<Corner> = cornerRepository.findAll()
    fun get(id: Long): Optional<Corner> = cornerRepository.findById(id)
    fun add(corner: Corner): Corner = cornerRepository.save(corner)
    fun edit(id: Long, corner: Corner): Corner = cornerRepository.save(corner.copy(id = id))
    fun remove(id: Long) = cornerRepository.deleteById(id)
}
