package dev.hekate.data.config

import com.fasterxml.jackson.databind.ObjectMapper
import io.quarkus.jackson.ObjectMapperCustomizer
import javax.inject.Singleton

@Singleton
class RegisterCustomModuleCustomizer : ObjectMapperCustomizer {
    override fun customize(mapper: ObjectMapper) {
//        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
    }
}