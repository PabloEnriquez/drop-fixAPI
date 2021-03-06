package com.itesm.demo.endpoint;

import com.itesm.demo.domain.Chat;
import com.itesm.demo.domain.Mensaje;
import com.itesm.demo.domain.Reporte;
import com.itesm.demo.service.ChatService;
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
public class ChatEndpoint {

    @Autowired
    private ChatService chatService;

    @GET
    @Path("/chats/{uuid}")
    public Response getChat(@PathParam("uuid") String uuid){
        Optional<Chat> chat = chatService.get(uuid);
        Response response;
        if(chat.isPresent()) {
            response = Response.ok(chat.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/chats")
    public Response getListaChats(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Chat>> chats = chatService.list(page, size);
        Response response;
        if(chats.isPresent()) {
            response = Response.ok(chats.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/chats")
    public Response insertChat(Chat chat){
        Optional<Chat> chatDB = chatService.insert(chat);
        Response response;
        if(chatDB.isPresent()) {
            response = Response.ok(chatDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/chats/{uuid}")
    public Response updateChat(@PathParam("uuid") String uuid, Chat chat){
        chat.setUuid(uuid);
        Optional<Chat> chatDB = chatService.update(chat);
        Response response;
        if(chatDB.isPresent()) {
            response = Response.ok(chatDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/chats/{uuid}")
    public Response deleteChat(@PathParam("uuid") String uuid){
        Optional<Chat> chat = chatService.get(uuid);
        chat.get().setStatus(-1);
        Optional<Chat> chatDB = chatService.update(chat.get());
        Response response;
        if(chatDB.isPresent()) {
            response = Response.ok(chatDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/chats/reportes")
    public Response getListaReportes(@QueryParam("id_chat") Long id_chat, @QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Reporte>> reportesChat = chatService.listReportes(id_chat, page, size);
        Response response;
        if(reportesChat.isPresent()) {
            response = Response.ok(reportesChat.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/chats/mensajes")
    public Response getListaMensajes(@QueryParam("id_chat") Long id_chat, @QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Mensaje>> mensajesChat = chatService.listMensajes(id_chat, page, size);
        Response response;
        if(mensajesChat.isPresent()) {
            response = Response.ok(mensajesChat.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/chats/x-fecha-creacion")
    public Response searchByFechaCreacion(@QueryParam("fecha_creacion") Date fecha_creacion, @QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Chat>> chatsFechaCreacion = chatService.getFechaCreacion(fecha_creacion, page, size);
        Response response;
        if(chatsFechaCreacion.isPresent()) {
            response = Response.ok(chatsFechaCreacion.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
