package config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.ObjectMapperConfig.objectMapperConfig
import io.restassured.config.RestAssuredConfig
import io.restassured.path.json.mapper.factory.Jackson2ObjectMapperFactory
import java.lang.reflect.Type


object RestAssuredListener : BeforeSpecListener {
    override suspend fun beforeSpec(spec: Spec) {
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(objectMapperConfig().jackson2ObjectMapperFactory(
            object : Jackson2ObjectMapperFactory {
                override fun create(cls: Type?, charset: String?): com.fasterxml.jackson.databind.ObjectMapper {
                    val mapper: JsonMapper = JsonMapper.builder()
                        .findAndAddModules()
                        .build()
                    mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                    return mapper
                }
            }
        ))

        RestAssured.requestSpecification = RequestSpecBuilder()
            .build()
            .header("Content-Type", "application/json")
            .header("Accept", "*/*")
    }
}