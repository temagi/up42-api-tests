package helpers

import io.restassured.RestAssured
import models.*

object WorkflowUtils {
    fun createWorkflow(token: String, workflow: Workflow): GetWorkflowResponse {
        return RestAssured
            .given()
            .header("Content-type", "application/json")
            .header("Authorization", token)
            .body(workflow)
        .`when`()
            .post("https://api.up42.com/projects/286b4323-7b05-4e43-9c08-689bac15801b/workflows/")
        .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getObject("data", GetWorkflowResponse::class.java)
    }

    fun deleteWorkflow(token: String, workflowId: String, statusCode: Int = 204) {
        RestAssured
            .given()
            .header("Content-type", "application/json")
            .header("Authorization", token)
        .`when`()
            .delete("https://api.up42.com/projects/286b4323-7b05-4e43-9c08-689bac15801b/workflows/$workflowId")
        .then()
            .statusCode(statusCode)
    }

    fun getWorkflow(token: String, workflowId: String): GetWorkflowResponse {
        return RestAssured
            .given()
            .header("Content-type", "application/json")
            .header("Authorization", token)
        .`when`()
            .get("https://api.up42.com/projects/286b4323-7b05-4e43-9c08-689bac15801b/workflows/$workflowId")
        .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getObject("data", GetWorkflowResponse::class.java)
    }

    fun addWorkflowTasks(token: String, workflowId: String, tasks: List<Task>): List<TaskResponse> {
        val result = RestAssured.given()
            .header("Content-type", "application/json")
            .header("Authorization", token)
            .body(tasks)
        .`when`()
            .post("https://api.up42.com/projects/286b4323-7b05-4e43-9c08-689bac15801b/workflows/${workflowId}/tasks/")
        .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getObject("data", Array<TaskResponse>::class.java)

        return result.toList()
    }

    fun createAndRunJob(token: String, workflowId: String, job: Job): JobResponse {
        return RestAssured.given()
            .header("Content-type", "application/json")
            .header("Authorization", token)
        .body(job)
            .`when`()
        .post("https://api.up42.com/projects/286b4323-7b05-4e43-9c08-689bac15801b/workflows/${workflowId}/jobs/")
            .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getObject("data", JobResponse::class.java)
    }

    fun getJob(token: String, workflowId: String, jobId: String): JobResponse {
        return RestAssured.given()
            .header("Content-type", "application/json")
            .header("Authorization", token)
        .`when`()
            .get("https://api.up42.com/projects/286b4323-7b05-4e43-9c08-689bac15801b/workflows/${workflowId}/tasks/")
        .then()
            .statusCode(200)
            .extract()
            .body()
            .jsonPath()
            .getObject("data", JobResponse::class.java)
    }
}