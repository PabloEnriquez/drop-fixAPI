package com.itesm.demo.endpoint;

import com.itesm.demo.domain.User;
import com.itesm.demo.service.UserService;
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
public class UserEndpoint {
    @Autowired
    private UserService userService;

    @GET
    @Path("/user/{uuid}")
    public Response getUser(@PathParam("uuid") String uuid){
        Optional<User> user = userService.get(uuid);
        Response response;
        if(user.isPresent()) {
            response = Response.ok(user.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/user")
    public Response search(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<User>> users = userService.list(page, size);
        Response response;
        if(users.isPresent()) {
            response = Response.ok(users.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/user")
    public Response insert(User user){
        Optional<User> userDB = userService.insert(user);
        Response response;
        if(userDB.isPresent()) {
            response = Response.ok(userDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/user/{uuid}")
    public Response insert(@PathParam("uuid") String uuid, User user){
        user.setUuid(uuid);
        Optional<User> userDB = userService.update(user);
        Response response;
        if(userDB.isPresent()) {
            response = Response.ok(userDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/user/{uuid}")
    public Response delete(@PathParam("uuid") String uuid){
        Optional<User> user = userService.get(uuid);
        user.get().setStatus(-1);
        Optional<User> userDB = userService.update(user.get());
        Response response;
        if(userDB.isPresent()) {
            response = Response.ok(userDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
