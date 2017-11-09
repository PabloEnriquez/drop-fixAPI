package com.itesm.demo.endpoint;

import com.itesm.demo.domain.Compra;
import com.itesm.demo.service.CompraService;
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
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class CompraEndpoint {

    @Autowired
    private CompraService compraService;

    @GET
    @Path("/compras/{uuid}")
    public Response getCompra(@PathParam("uuid") String uuid){
        Optional<Compra> compra = compraService.get(uuid);
        Response response;
        if(compra.isPresent()) {
            response = Response.ok(compra.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/compras")
    public Response getListaCompras(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Compra>> compras = compraService.list(page, size);
        Response response;
        if(compras.isPresent()) {
            response = Response.ok(compras.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/compras")
    public Response insertCompra(Compra compra){
        Optional<Compra> compraDB = compraService.insert(compra);
        Response response;
        if(compraDB.isPresent()) {
            response = Response.ok(compraDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/compras/{uuid}")
    public Response updateCompra(@PathParam("uuid") String uuid, Compra compra){
        compra.setUuid(uuid);
        Optional<Compra> compraDB = compraService.update(compra);
        Response response;
        if(compraDB.isPresent()) {
            response = Response.ok(compraDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/compras/{uuid}")
    public Response deleteCompra(@PathParam("uuid") String uuid){
        Optional<Compra> compra = compraService.get(uuid);
        compra.get().setStatus(-1);
        Optional<Compra> compraDB = compraService.update(compra.get());
        Response response;
        if(compraDB.isPresent()) {
            response = Response.ok(compraDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/compras")
    public Response searchByFechaCreacion(@QueryParam("fecha_cracion") Date fecha_creacion ){
        Optional<Compra> compra = compraService.getFechaCreacion(fecha_creacion);
        Response response;
        if(compra.isPresent()) {
            response = Response.ok(compra.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
