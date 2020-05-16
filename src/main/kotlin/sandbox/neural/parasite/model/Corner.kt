package sandbox.neural.parasite.model

import javax.persistence.*

@Entity
@Table(schema = "basic", name = "corner")
data class Corner(
        @Column(name = "outer_torque")
        val outerTorque: Int = 0,

        @Column(name = "inner_torque")
        val innerTorque: Int = 0,

        @Column(name="angle_of_front_heel")
        val angleOfFrontHeel: Float,

        @Column(name="angle_of_rear_heel")
        val angleOfRearHeel: Float,

        @Id
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long = 0L
)
