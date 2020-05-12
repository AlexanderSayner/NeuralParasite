package sandbox.neural.parasite.repository

import org.springframework.data.jpa.repository.JpaRepository
import sandbox.neural.parasite.model.Corner

interface CornerRepository : JpaRepository<Corner, Long>
