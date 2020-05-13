package sandbox.neural.parasite.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity // Указывает на то что этот класс описывает модель данных
@Table(schema = "basic", name = "car") // Говорим как назвать таблицу в БД
data class Car( // Дата класс нам сгенерирует методы equals и hashCode и даст метод copy
        @get:JsonProperty("model") // Говорим как будет называться свойство в JSON объекте
        @Column(name = "name", length = 200) // Говорим как будет называться поле в БД и задаем его длину
        val name: String = "", // Объявляем неизменяемое свойство (геттер, а также поле для него будут сгенерированы автоматически) name, с пустой строкой в качестве значения по умолчанию

        @Column(name = "description", length = 1000)
        val description: String = "",

        @Column(name = "weight")
        val weight: Int = 0,

        @Column(name = "wheelbase")
        val wheelbase: Int = 0,

        @Column(name = "front_share_of_weight")
        val frontShareOfWeight: Float = 0.5f,

        @Column(name = "rear_share_of_weight")
        val rearShareOfWeight: Float = 0.5f,

        @Column(name = "wheel_width")
        val wheelWidth: Int = 0,

        @Column(name = "tire_profile")
        val tireProfile: Float = 0.0f,

        @Column(name = "rim_size")
        val rimSize: Int = 0,

        @Id // Сообщяем ORM что это поле - Primary Key
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - к id прибавляется 1 при создании новой записи
        val id: Long = 0L
)
