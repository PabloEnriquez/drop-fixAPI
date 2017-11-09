package com.itesm.demo.endpoint;

import com.itesm.demo.domain.Chat;
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
    public Response search(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
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
    public Response insert(Chat chat){
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
    public Response insert(@PathParam("uuid") String uuid, Chat chat){
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
    public Response delete(@PathParam("uuid") String uuid){
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

}
