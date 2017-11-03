package com.itesm.demo.endpoint;

import com.itesm.demo.domain.Mensaje;
import com.itesm.demo.service.MensajeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.Optional;

@Component
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class MensajeEndpoint {

    @Autowired
    private MensajeService mensajeService;

    @GET
    @Path("/mensajes/{uuid}")
    public Response getMensaje(@PathParam("uuid") String uuid){
        Optional<Mensaje> mensaje = mensajeService.get(uuid);
        Response response;
        if(mensaje.isPresent()) {
            response = Response.ok(mensaje.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/mensajes")
    public Response search(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Mensaje>> mensajes =  mensajeService.list(page, size);
        Response response;
        if(mensajes.isPresent()) {
            response = Response.ok(mensajes.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/mensajes")
    public Response insert(Mensaje mensaje){
        Optional<Mensaje> mensajeDB = mensajeService.insert(mensaje);
        Response response;
        if(mensajeDB.isPresent()) {
            response = Response.ok(mensajeDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/mensajes/{uuid}")
    public Response insert(@PathParam("uuid") String uuid, Mensaje mensaje){
        mensaje.setUuid(uuid);
        Optional<Mensaje> mensajeDB = mensajeService.update(mensaje);
        Response response;
        if(mensajeDB.isPresent()) {
            response = Response.ok(mensajeDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/mensajes/{uuid}")
    public Response delete(@PathParam("uuid") String uuid){
        Optional<Mensaje> mensaje = mensajeService.get(uuid);
//        mensaje.get().setStatus("-1");
        Optional<Mensaje> mensajeDB = mensajeService.update(mensaje.get());
        Response response;
        if(mensajeDB.isPresent()) {
            response = Response.ok(mensajeDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
