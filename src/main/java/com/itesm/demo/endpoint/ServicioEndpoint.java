package com.itesm.demo.endpoint;

import com.itesm.demo.domain.Compra;
import com.itesm.demo.domain.Servicio;
import com.itesm.demo.service.ServicioService;
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
public class ServicioEndpoint {

    @Autowired
    private ServicioService servicioService;

    @GET
    @Path("/servicios/{uuid}")
    public Response getServicio(@PathParam("uuid") String uuid){
        Optional<Servicio> servicio = servicioService.get(uuid);
        Response response;
        if(servicio.isPresent()) {
            response = Response.ok(servicio.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/servicios")
    public Response getListaCompras(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Servicio>> servicios = servicioService.list(page, size);
        Response response;
        if(servicios.isPresent()) {
            response = Response.ok(servicios.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/servicios")
    public Response insertCompra(Servicio servicio){
        Optional<Servicio> servicioDB = servicioService.insert(servicio);
        Response response;
        if(servicioDB.isPresent()) {
            response = Response.ok(servicioDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/servicios/{uuid}")
    public Response updateCompra(@PathParam("uuid") String uuid, Servicio servicio){
        servicio.setUuid(uuid);
        Optional<Servicio> servicioDB = servicioService.update(servicio);
        Response response;
        if(servicioDB.isPresent()) {
            response = Response.ok(servicioDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/servicios/{uuid}")
    public Response deleteCompra(@PathParam("uuid") String uuid){
        Optional<Servicio> servicio = servicioService.get(uuid);
        servicio.get().setStatus(-1);
        Optional<Servicio> srevicioDB = servicioService.update(servicio.get());
        Response response;
        if(srevicioDB.isPresent()) {
            response = Response.ok(srevicioDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/servicios")
    public Response getListaComprasServicio(@QueryParam("id_servicio") Long id_servicio, @QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Compra>> comprasServicio = servicioService.listComprasServicio(id_servicio, page, size);
        Response response;
        if(comprasServicio.isPresent()) {
            response = Response.ok(comprasServicio.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
