package com.itesm.demo.endpoint;

import com.itesm.demo.domain.EquipoComputo;
import com.itesm.demo.domain.Usuario;
import com.itesm.demo.service.UsuarioService;
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
//import java.sql.Date;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Component
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class UsuarioEndpoint {

    @Autowired
    private UsuarioService usuarioService;

    @GET
    @Path("/usuarios/{uuid}")
    public Response getUsuario(@PathParam("uuid") String uuid){
        Optional<Usuario> usuario = usuarioService.get(uuid);
        Response response;
        if(usuario.isPresent()) {
            response = Response.ok(usuario.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/usuarios")
    public Response getListaUsuarios(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Usuario>> usuarios = usuarioService.list(page, size);
        Response response;
        if(usuarios.isPresent()) {
            response = Response.ok(usuarios.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/usuarios")
    public Response insertUsuario(Usuario usuario){
        Optional<Usuario> usuarioDB = usuarioService.insert(usuario);
        Response response;
        if(usuarioDB.isPresent()) {
            response = Response.ok(usuarioDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/usuarios/{uuid}")
    public Response updateUsuario(@PathParam("uuid") String uuid, Usuario usuario){
        usuario.setUuid(uuid);
        Optional<Usuario> usuarioDB = usuarioService.update(usuario);
        Response response;
        if(usuarioDB.isPresent()) {
            response = Response.ok(usuarioDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/usuarios/{uuid}")
    public Response deleteUsuario(@PathParam("uuid") String uuid){
        Optional<Usuario> usuario = usuarioService.get(uuid);
        usuario.get().setStatus(-1);
        Optional<Usuario> usuarioDB = usuarioService.update(usuario.get());
        Response response;
        if(usuarioDB.isPresent()) {
            response = Response.ok(usuarioDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/usuarios")
    public Response searchByEmail(@QueryParam("email") String email ){
        Optional<Usuario> usuario = usuarioService.getMail(email);
        Response response;
        if(usuario.isPresent()) {
            response = Response.ok(usuario.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/usuarios")
    public Response searchByNombre(@QueryParam("nombre") String nombre ){
        Optional<Usuario> usuario = usuarioService.getNombre(nombre);
        Response response;
        if(usuario.isPresent()) {
            response = Response.ok(usuario.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/usuarios")
    public Response searchByUsuarioNombre(@QueryParam("usuario") String usuario ){
        Optional<Usuario> usuarioAObtener = usuarioService.getUsuarioNombre(usuario);
        Response response;
        if(usuarioAObtener.isPresent()) {
            response = Response.ok(usuarioAObtener.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/usuarios")
    public Response searchByFechaCreacion(@QueryParam("fecha_creacion") Date fecha_creacion ){
        Optional<Usuario> usuario = usuarioService.getFechaCreacion(fecha_creacion);
        Response response;
        if(usuario.isPresent()) {
            response = Response.ok(usuario.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/usuarios")
    public Response searchByTipoUsuario(@QueryParam("tipo_usuario") Integer tipo_usuario ){
        Optional<Usuario> usuario = usuarioService.getTipoUsuario(tipo_usuario);
        Response response;
        if(usuario.isPresent()) {
            response = Response.ok(usuario.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/usuarios/{uuid}")
    public Response getListaEquipos(@QueryParam("page") Integer page, @QueryParam("size") Integer size, @PathParam("uuid") String uuid ){
        Optional<List<EquipoComputo>> equiposUsuario = usuarioService.listEquipos(page, size, uuid);
        Response response;
        if(equiposUsuario.isPresent()) {
            response = Response.ok(equiposUsuario.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
