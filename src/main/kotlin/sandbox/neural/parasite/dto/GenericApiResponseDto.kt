package sandbox.neural.parasite.dto

import com.fasterxml.jackson.annotation.JsonProperty

data class GenericApiResponseDto<T>(
        @JsonProperty("message") val message: String,
        @JsonProperty("result") val result: T
)
