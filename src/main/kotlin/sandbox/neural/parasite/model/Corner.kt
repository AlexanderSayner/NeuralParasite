package sandbox.neural.parasite.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity
@Table(schema = "basic", name = "corner")
data class Corner(
        @JsonProperty("radius")
        @Column(name = "wheel_rotation_angle")
        val wheelRotationAngle: Float = 0.0f,

        @JsonProperty("speed")
        @Column(name = "speed")
        val speed: Int = 0,

        @Id
        @JsonProperty("id")
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L
)
