package com.itesm.demo.endpoint;

import com.itesm.demo.domain.CasoAtendido;
import com.itesm.demo.service.CasoAtendidoService;
import com.sun.org.apache.xerces.internal.impl.xpath.regex.CaseInsensitiveMap;
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
public class CasoAtendidoEndpoint {

    @Autowired
    private CasoAtendidoService casoAtendidoService;

    @GET
    @Path("/casos/{uuid}")
    public Response getCasoAtendido(@PathParam("uuid") String uuid){
        Optional<CasoAtendido> caso_atendido = casoAtendidoService.get(uuid);
        Response response;
        if(caso_atendido.isPresent()) {
            response = Response.ok(caso_atendido.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/casos")
    public Response search(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<CasoAtendido>> casos = casoAtendidoService.list(page, size);
        Response response;
        if(casos.isPresent()) {
            response = Response.ok(casos.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/casos")
    public Response insert(CasoAtendido caso_atendido){
        Optional<CasoAtendido> caso_atendidoDB = casoAtendidoService.insert(caso_atendido);
        Response response;
        if(caso_atendidoDB.isPresent()) {
            response = Response.ok(caso_atendidoDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/casos/{uuid}")
    public Response insert(@PathParam("uuid") String uuid, CasoAtendido caso_atendido){
        caso_atendido.setUuid(uuid);
        Optional<CasoAtendido> caso_atendidoDB = casoAtendidoService.update(caso_atendido);
        Response response;
        if(caso_atendidoDB.isPresent()) {
            response = Response.ok(caso_atendidoDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/casos/{uuid}")
    public Response delete(@PathParam("uuid") String uuid){
        Optional<CasoAtendido> caso_atendido = casoAtendidoService.get(uuid);
        caso_atendido.get().setStatus("-1");
        Optional<CasoAtendido> caso_atendidoDB = casoAtendidoService.update(caso_atendido.get());
        Response response;
        if(caso_atendidoDB.isPresent()) {
            response = Response.ok(caso_atendidoDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
