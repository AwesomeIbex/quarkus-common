package dev.hekate.data.config

import io.vertx.core.Vertx
import io.vertx.core.net.SocketAddress
import io.vertx.redis.client.Redis
import io.vertx.redis.client.RedisAPI
import io.vertx.redis.client.RedisOptions
import javax.enterprise.context.ApplicationScoped
import javax.enterprise.inject.Produces

@ApplicationScoped
class RedisConfig {

    @Produces
    fun redis(vertx: Vertx): Redis {
        val redisOptions = RedisOptions()
        redisOptions.endpoint = SocketAddress.inetSocketAddress(6379, "redis-cache")
        val client = Redis.createClient(vertx, redisOptions)
                .connect { onConnect ->
                    if (onConnect.succeeded()) {
                        println("Redis connection succeeded")
                    } else {
                        throw onConnect.cause()
                    }
                }
        return client!!
    }

    @Produces
    fun redisApi(redisClient: Redis): RedisAPI {
        return RedisAPI.api(redisClient)
    }
}