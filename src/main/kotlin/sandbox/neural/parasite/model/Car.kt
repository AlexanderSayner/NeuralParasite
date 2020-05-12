package sandbox.neural.parasite.model

import com.fasterxml.jackson.annotation.JsonProperty
import javax.persistence.*

@Entity // Указывает на то что этот класс описывает модель данных
@Table(schema = "basic", name = "car") // Говорим как назвать таблицу в БД
data class Car( // Дата класс нам сгенерирует методы equals и hashCode и даст метод copy
        @JsonProperty("name") // Говорим как будет называться свойство в JSON объекте
        @Column(name = "name", length = 200) // Говорим как будет называться поле в БД и задаем его длину
        val name: String = "", // Объявляем неизменяемое свойство (геттер, а также поле для него будут сгенерированы автоматически) name, с пустой строкой в качестве значения по умолчанию

        @JsonProperty("description")
        @Column(name = "description", length = 1000)
        val description: String = "",

        @JsonProperty("weight")
        @Column(name = "weight")
        val weight: Int = 0,

        @JsonProperty("wheelbase")
        @Column(name = "wheelbase")
        val wheelbase: Int = 0,

        @JsonProperty("wheel_width")
        @Column(name = "wheel_width")
        val wheel_width: Int = 0,

        @JsonProperty("tire_profile")
        @Column(name = "tire_profile")
        val tire_profile: Int = 0,

        @JsonProperty("rim_size")
        @Column(name = "rim_size")
        val rim_size: Int = 0,

        @Id // Сообщяем ORM что это поле - Primary Key
        @JsonProperty("id")
        @Column(name = "id")
        @GeneratedValue(strategy = GenerationType.IDENTITY) // IDENTITY - к id прибавляется 1 при создании новой записи
        val id: Long = 0L
)
