package config

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.json.JsonMapper
import io.kotest.core.listeners.BeforeSpecListener
import io.kotest.core.spec.Spec
import io.restassured.RestAssured
import io.restassured.builder.RequestSpecBuilder
import io.restassured.config.ObjectMapperConfig.objectMapperConfig
import io.restassured.config.RestAssuredConfig

object RestAssuredListener : BeforeSpecListener {
    override suspend fun beforeSpec(spec: Spec) {
        // TODO: As a temporary solution, could be removed for a real project
        RestAssured.config = RestAssuredConfig.config().objectMapperConfig(
            objectMapperConfig().jackson2ObjectMapperFactory { cls, charset ->
                val mapper: JsonMapper = JsonMapper.builder()
                    .findAndAddModules()
                    .build()
                mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
                mapper
            }
        )

        RestAssured.requestSpecification = RequestSpecBuilder()
            .build()
            .header("Content-Type", "application/json")
            .header("Accept", "*/*")
            .baseUri("https://api.up42.com")
    }
}
