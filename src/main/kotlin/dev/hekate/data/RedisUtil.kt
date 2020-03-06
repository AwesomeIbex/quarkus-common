package dev.hekate.data

import io.vertx.core.AsyncResult
import io.vertx.ext.web.RoutingContext
import io.vertx.redis.client.RedisAPI
import io.vertx.redis.client.Response
import io.vertx.redis.client.ResponseType
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.Response.Status

@ApplicationScoped
class RedisUtil @Inject constructor(
        val redisAPI: RedisAPI
) {
    val tableName = "Collections"

    fun getValue(key: String, rc: RoutingContext) {
        redisAPI.hget(tableName, key) { result ->
            publishResult(result, rc)
        }
    }

    fun putValue(key: String, rc: RoutingContext) {
        redisAPI.hset(mutableListOf(tableName, key, rc.bodyAsJson.encode())) { result ->
            if (result.succeeded()) {
                rc.response()
//                        .putHeader("Location", "http://localhost:8080/collections") TODO this
                        .setStatusCode(Status.CREATED.statusCode)
                        .end()
            } else {
                rc.response().end(result.cause().localizedMessage)
            }
        }
    }

    fun deleteValue(key: String, rc: RoutingContext) {
        redisAPI.hdel(mutableListOf(tableName, key)) { result ->
            if (result.succeeded()) {
                rc.response().end("OK")
            } else {
                rc.response().end(result.cause().localizedMessage)
            }
        }
    }

    fun getAllValues(rc: RoutingContext) {
        redisAPI.hvals(tableName) { result ->
            publishResult(result, rc)
        }
    }

    private fun publishResult(asyncResult: AsyncResult<Response>, rc: RoutingContext) {
        rc.response().isChunked = true
        if (asyncResult.succeeded()) {
            if (asyncResult.result().type() == ResponseType.MULTI) {
                asyncResult.result().forEach { response ->
                    rc.response().write(response.toString())
                }
                rc.response().end()
            } else {
                rc.response().end(asyncResult.result().toBuffer())
            }
        } else {
            rc.response().end(asyncResult.cause().localizedMessage)
        }
    }


}