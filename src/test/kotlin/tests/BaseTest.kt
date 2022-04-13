package tests

import helpers.WorkflowUtils.addWorkflowTasks
import helpers.WorkflowUtils.createAndRunJob
import helpers.WorkflowUtils.createWorkflow
import helpers.WorkflowUtils.deleteWorkflow
import helpers.WorkflowUtils.getJob
import helpers.WorkflowUtils.getWorkflow
import io.kotest.common.ExperimentalKotest
import io.kotest.core.spec.style.DescribeSpec
import io.kotest.framework.concurrency.eventually
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.restassured.RestAssured
import models.AuthResponse
import models.Workflow
import models.Job
import models.Task
import models.Pleiades
import models.Sharpening

@ExperimentalKotest
class BaseTest : DescribeSpec({
    RestAssured.enableLoggingOfRequestAndResponseIfValidationFails()

    describe("check MODIS workflow") {
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

            it("Create workflow tasks") {
                val tasks = listOf(
                    // TODO: Move to generators
                    Task("nasa-modis:1", blockId = "ef6faaf5-8182-4986-bce4-4f811d2745e5"),
                    Task("sharpening:1", "nasa-modis:1", "e374ea64-dc3b-4500-bb4b-974260fb203e")
                )

                val createdTasks = addWorkflowTasks(token, createdWorkflow.id, tasks)
            }

            it("Create and run job") {
                val job = Job(
                    nasaModis = Pleiades(
                        time = "2018-12-01T00:00:00+00:00/2020-12-31T23:59:59+00:00",
                        limit = 1,
                        zoom_level = 9,
                        imagery_layers = listOf("MODIS_Terra_CorrectedReflectance_TrueColor"),
                        bbox = listOf(
                            13.365373,
                            52.49582,
                            13.385796,
                            52.510455
                        )
                    ),
                    sharpening = Sharpening(
                        strength = "medium"
                    )
                )

                val createdJob = createAndRunJob(token, createdWorkflow.id, job)

                it("Retrieving job and check status is SUCCEEDED") {
                    eventually(20000) {
                        val getJob = getJob(token, createdWorkflow.id, createdJob.id)
                        getJob.status shouldBe "SUCCEEDED"
                    }
                }
            }

            it("Drop created workflow") {
                deleteWorkflow(token, createdWorkflow.id)
            }
        }
    }
})