package com.itesm.demo.endpoint;

import com.itesm.demo.domain.EquipoComputo;
import com.itesm.demo.domain.Reporte;
import com.itesm.demo.service.EquipoComputoService;
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
public class EquipoComputoEndpoint {

    @Autowired
    private EquipoComputoService equipoComputoService;

    @GET
    @Path("/equipos/{uuid}")
    public Response getEquipo(@PathParam("uuid") String uuid){
        Optional<EquipoComputo> equipo_computo = equipoComputoService.get(uuid);
        Response response;
        if(equipo_computo.isPresent()) {
            response = Response.ok(equipo_computo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/equipos")
    public Response getListaEquipos(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<EquipoComputo>> equipos = equipoComputoService.list(page, size);
        Response response;
        if(equipos.isPresent()) {
            response = Response.ok(equipos.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/equipos")
    public Response insertEquipo(EquipoComputo equipo_computo){
        Optional<EquipoComputo> equipoComputoDB = equipoComputoService.insert(equipo_computo);
        Response response;
        if(equipoComputoDB.isPresent()) {
            response = Response.ok(equipoComputoDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/equipos/{uuid}")
    public Response updateEquipo(@PathParam("uuid") String uuid, EquipoComputo equipo_computo){
        equipo_computo.setUuid(uuid);
        Optional<EquipoComputo> equipoComputoDB = equipoComputoService.update(equipo_computo);
        Response response;
        if(equipoComputoDB.isPresent()) {
            response = Response.ok(equipoComputoDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/equipos/{uuid}")
    public Response deleteEquipo(@PathParam("uuid") String uuid){
        Optional<EquipoComputo> equipo_computo = equipoComputoService.get(uuid);
        equipo_computo.get().setStatus(-1);
        Optional<EquipoComputo> equipoComputoDB = equipoComputoService.update(equipo_computo.get());
        Response response;
        if(equipoComputoDB.isPresent()) {
            response = Response.ok(equipoComputoDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/equipos")
    public Response getByFechaCreacion(@QueryParam("fecha_creacion") Date fecha_creacion ){
        Optional<EquipoComputo> equipo_computo = equipoComputoService.getFechaCreacion(fecha_creacion);
        Response response;
        if(equipo_computo.isPresent()) {
            response = Response.ok(equipo_computo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/equipos")
    public Response getByNombre(@QueryParam("nombre") String nombre ){
        Optional<EquipoComputo> equipo_computo = equipoComputoService.getNombre(nombre);
        Response response;
        if(equipo_computo.isPresent()) {
            response = Response.ok(equipo_computo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/equipos")
    public Response getByNumSerie(@QueryParam("num_serie") String num_serie ){
        Optional<EquipoComputo> equipo_computo = equipoComputoService.getNumSerie(num_serie);
        Response response;
        if(equipo_computo.isPresent()) {
            response = Response.ok(equipo_computo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/equipos")
    public Response getByModelo(@QueryParam("modelo") String modelo ){
        Optional<EquipoComputo> equipo_computo = equipoComputoService.getModelo(modelo);
        Response response;
        if(equipo_computo.isPresent()) {
            response = Response.ok(equipo_computo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/equipos")
    public Response getByMarca(@QueryParam("marca") String marca ){
        Optional<EquipoComputo> equipo_computo = equipoComputoService.getMarca(marca);
        Response response;
        if(equipo_computo.isPresent()) {
            response = Response.ok(equipo_computo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/equipos")
    public Response getBySistOperativo(@QueryParam("sistema_operativo") String sistema_operativo ){
        Optional<EquipoComputo> equipo_computo = equipoComputoService.getSistOperativo(sistema_operativo);
        Response response;
        if(equipo_computo.isPresent()) {
            response = Response.ok(equipo_computo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/equipos")
    public Response getListaReportes(@QueryParam("uuid") String uuid, @QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Reporte>> reportesEquipo = equipoComputoService.listReportes(uuid, page, size);
        Response response;
        if(reportesEquipo.isPresent()) {
            response = Response.ok(reportesEquipo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
