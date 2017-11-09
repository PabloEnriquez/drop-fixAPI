package com.itesm.demo.endpoint;

import com.itesm.demo.domain.Compra;
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
import java.util.Date;
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
    public Response getListaReportes(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
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
    public Response insertReporte(Reporte reporte){
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
    public Response updateReporte(@PathParam("uuid") String uuid, Reporte reporte){
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
    public Response deleteReporte(@PathParam("uuid") String uuid){
        Optional<Reporte> reporte = reporteService.get(uuid);
        reporte.get().setStatus(-1);
        Optional<Reporte> reporteDB = reporteService.update(reporte.get());
        Response response;
        if(reporteDB.isPresent()) {
            response = Response.ok(reporteDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/reportes")
    public Response searchByStatusAtendido(@QueryParam("status_atendido") Long status_atendido ){
        Optional<Reporte> reporte = reporteService.getStatusAtendido(status_atendido);
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
    public Response searchByFechaCreacion(@QueryParam("fecha_creacion") Date fecha_creacion ){
        Optional<Reporte> reporte = reporteService.getFechaCreacion(fecha_creacion);
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
    public Response getListaComprasReporte(@QueryParam("id_reporte") Long id_reporte, @QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Compra>> comprasReporte = reporteService.listComprasReporte(id_reporte, page, size);
        Response response;
        if(comprasReporte.isPresent()) {
            response = Response.ok(comprasReporte.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
