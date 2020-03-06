package dev.hekate.data.resource

import org.jboss.resteasy.annotations.cache.Cache
import javax.ws.rs.GET
import javax.ws.rs.Path
import javax.ws.rs.Produces
import javax.ws.rs.core.Link
import javax.ws.rs.core.MediaType

@Path("/links")
class RootResource {

    private val env = System.getProperties()
    var GATEWAY_URL: String = env.getProperty("GATEWAY_URL") ?: "localhost:8080"
    var URL_PROTOCOL: String = env.getProperty("URL_PROTOCOL") ?: "http"


    @GET
    @Cache
    @Produces(MediaType.APPLICATION_JSON)
    fun rootObject() = listOf(
            Link.fromPath(GATEWAY_URL)
                    .title("This service")
                    .type(MediaType.APPLICATION_JSON)
                    .build(),
            Link.fromPath("$GATEWAY_URL/tiles")
                    .title("Tile service")
                    .type(MediaType.APPLICATION_JSON)
                    .build(),
            Link.fromPath("$GATEWAY_URL/routing")
                    .title("Routing service")
                    .type(MediaType.APPLICATION_JSON)
                    .build(),
            Link.fromPath("$GATEWAY_URL/vrp")
                    .title("Vehicle routing problem service")
                    .type(MediaType.APPLICATION_JSON)
                    .build()
    )
}