package com.itesm.demo.endpoint;

import com.itesm.demo.domain.Reporte;
import com.itesm.demo.service.ReporteService;
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
public class ReporteEndpoint {

    @Autowired
    private ReporteService reporteService;

    @GET
    @Path("/reportes/{uuid}")
    public Response getReporte(@PathParam("uuid") String uuid){
        Optional<Reporte> reporte = reporteService.get(uuid);
        Response response;
        if(reporte.isPresent()) {
            response = Response.ok(reporte.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/reportes")
    public Response search(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Reporte>> reportes = reporteService.list(page, size);
        Response response;
        if(reportes.isPresent()) {
            response = Response.ok(reportes.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/reportes")
    public Response insert(Reporte reporte){
        Optional<Reporte> reporteDB = reporteService.insert(reporte);
        Response response;
        if(reporteDB.isPresent()) {
            response = Response.ok(reporteDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/reportes/{uuid}")
    public Response insert(@PathParam("uuid") String uuid, Reporte reporte){
        reporte.setUuid(uuid);
        Optional<Reporte> reporteDB = reporteService.update(reporte);
        Response response;
        if(reporteDB.isPresent()) {
            response = Response.ok(reporteDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/reportes/{uuid}")
    public Response delete(@PathParam("uuid") String uuid){
        Optional<Reporte> reporte = reporteService.get(uuid);
        reporte.get().setStatus("-1");
        Optional<Reporte> reporteDB = reporteService.update(reporte.get());
        Response response;
        if(reporteDB.isPresent()) {
            response = Response.ok(reporteDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
