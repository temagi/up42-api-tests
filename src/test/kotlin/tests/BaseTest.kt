package tests

import helpers.WorkflowUtils.createWorkflow
import helpers.WorkflowUtils.deleteWorkflow
import helpers.WorkflowUtils.getWorkflow
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.restassured.RestAssured
import models.AuthResponse
import models.Workflow

class BaseTest : DescribeSpec({
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

    describe("MODIS workflow") {
        var token = ""
        it("get valid token") {
            val auth = RestAssured.given()
                .contentType("application/x-www-form-urlencoded")
                .formParam("grant_type", "client_credentials")
                .`when`()
                .post("https://286b4323-7b05-4e43-9c08-689bac15801b:e3gPtegq.rRmLWWeUwbuxjwyc82QB1HwQhMgdY27AgIl@api.up42.com/oauth/token")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .jsonPath()
                .getObject(".", AuthResponse::class.java)
            auth.data!!.accessToken shouldNotBe ""
            println(auth.access_token)
            println(auth.token_type)
            token = "Bearer ${auth.access_token}"
        }

        it("Crate an a workflow") {
            val workflow = Workflow("QA coding challenge workflow", "Workflow description")
            val createdWorkflow = createWorkflow(token, workflow)
            createdWorkflow.id shouldNotBe ""

            it("Get created workflow") {
                getWorkflow(token, createdWorkflow.id).run {
                    id shouldBe createdWorkflow.id
                    name shouldBe createdWorkflow.name
                    description shouldBe createdWorkflow.description
                    createdAt shouldBe createdWorkflow.createdAt
                    updatedAt shouldBe createdWorkflow.updatedAt
                    totalProcessingTime shouldBe createdWorkflow.totalProcessingTime
                }
            }

            it("Drop created workflow") {
                deleteWorkflow(token, createdWorkflow.id)
            }
        }
    }
})