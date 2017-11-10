package com.itesm.demo.endpoint;

import com.itesm.demo.domain.CentroServicio ;
import com.itesm.demo.service.CentroServicioService;
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
public class CentroServicioEndpoint {
    @Autowired
    private CentroServicioService centroServicioService;

    @GET
    @Path("/centrosServicio/{uuid}")
    public Response getFaq(@PathParam("uuid") String uuid){
        Optional<CentroServicio > centroServicio = centroServicioService.get(uuid);
        Response response;
        if(centroServicio.isPresent()) {
            response = Response.ok(centroServicio.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/centrosServicio")
    public Response getListaCentros(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<CentroServicio >> faqs = centroServicioService.list(page, size);
        Response response;
        if(faqs.isPresent()) {
            response = Response.ok(faqs.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/centrosServicio")
    public Response insertCentro(CentroServicio  centroServicio){
        Optional<CentroServicio > centroServicioDB = centroServicioService.insert(centroServicio);
        Response response;
        if(centroServicioDB.isPresent()) {
            response = Response.ok(centroServicioDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/centrosServicio/{uuid}")
    public Response updateCentro(@PathParam("uuid") String uuid, CentroServicio  centroServicio){
        centroServicio.setUuid(uuid);
        Optional<CentroServicio > centroServicioDB = centroServicioService.update(centroServicio);
        Response response;
        if(centroServicioDB.isPresent()) {
            response = Response.ok(centroServicioDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/centrosServicio/{uuid}")
    public Response deleteCentro(@PathParam("uuid") String uuid){
        Optional<CentroServicio > centroServicio = centroServicioService.get(uuid);
        centroServicio.get().setStatus(-1);
        Optional<CentroServicio > centroServicioDB = centroServicioService.update(centroServicio.get());
        Response response;
        if(centroServicioDB.isPresent()) {
            response = Response.ok(centroServicioDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/centrosServicio")
    public Response getByTitulo(@QueryParam("titulo") String titulo, @QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<CentroServicio >> faqsTitulo = centroServicioService.getTitulo(titulo, page, size);
        Response response;
        if(faqsTitulo.isPresent()) {
            response = Response.ok(faqsTitulo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/centrosServicio")
    public Response getByDireccion(@QueryParam("direccion") String direccion, @QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<CentroServicio >> faqsDireccion = centroServicioService.getDireccion(direccion, page, size);
        Response response;
        if(faqsDireccion.isPresent()) {
            response = Response.ok(faqsDireccion.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
