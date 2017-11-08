package com.itesm.demo.endpoint;

import com.itesm.demo.domain.Noticia;
import com.itesm.demo.service.NoticiaService;
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
public class NoticiaEndpoint {
    @Autowired
    private NoticiaService noticiaService;

    @GET
    @Path("/noticia/{uuid}")
    public Response getNoticia(@PathParam("uuid") String uuid){
        Optional<Noticia> noticia = noticiaService.get(uuid);
        Response response;
        if(noticia.isPresent()) {
            response = Response.ok(noticia.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/noticia")
    public Response search(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Noticia>> noticias = noticiaService.list(page, size);
        Response response;
        if(noticias.isPresent()) {
            response = Response.ok(noticias.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/noticia")
    public Response insert(Noticia noticia){
        Optional<Noticia> noticiaDB = noticiaService.insert(noticia);
        Response response;
        if(noticiaDB.isPresent()) {
            response = Response.ok(noticiaDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/noticia/{uuid}")
    public Response insert(@PathParam("uuid") String uuid, Noticia noticia){
        noticia.setUuid(uuid);
        Optional<Noticia> noticiaDB = noticiaService.update(noticia);
        Response response;
        if(noticiaDB.isPresent()) {
            response = Response.ok(noticiaDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/noticia/{uuid}")
    public Response delete(@PathParam("uuid") String uuid){
        Optional<Noticia> noticia = noticiaService.get(uuid);
        noticia.get().setStatus("-1");//marca error de incopatibilidad con int to java.lang.string
        Optional<Noticia> noticiaDB = noticiaService.update(noticia.get());
        Response response;
        if(noticiaDB.isPresent()) {
            response = Response.ok(noticiaDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
