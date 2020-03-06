package dev.hekate.data.resource

import dev.hekate.data.RedisUtil
import io.quarkus.vertx.web.Route
import io.vertx.core.http.HttpMethod
import io.vertx.ext.web.RoutingContext
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject
import javax.ws.rs.core.MediaType

@ApplicationScoped
class DataResource @Inject constructor(
    val redisUtil: RedisUtil
) {
    @Route(path = "/data/collections/:collectionId/:token",
            methods = [HttpMethod.PUT],
            produces = [MediaType.APPLICATION_JSON],
            consumes = [MediaType.APPLICATION_JSON]
    )
    fun putCollection(rc: RoutingContext) {
        val key = rc.pathParam("collectionId") + rc.pathParam("token")
        putJsonResponseHeader(rc)
        redisUtil.putValue(key, rc)
    }

    private fun putJsonResponseHeader(rc: RoutingContext) {
        rc.response().putHeader("Content-Type", MediaType.APPLICATION_JSON)
    }

    @Route(path = "/data/collections/:collectionId/:token",
            methods = [HttpMethod.GET],
            consumes = [MediaType.APPLICATION_JSON]
    )
    fun getCollection(rc: RoutingContext) {
        val key = rc.pathParam("collectionId") + rc.pathParam("token")
        putJsonResponseHeader(rc)
        redisUtil.getValue(key, rc)
    }

    @Route(path = "/data/collections")
    fun getAllCollections(rc: RoutingContext) {
        putJsonResponseHeader(rc)
        redisUtil.getAllValues(rc)
    }
}