package com.itesm.demo.endpoint;

import com.itesm.demo.domain.Faq;
import com.itesm.demo.service.FaqService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
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

//@CrossOrigin(origins = {"http://localhost:3000"})
@Component
@Path("/v1")
@Produces(MediaType.APPLICATION_JSON)
public class FaqEndpoint {
    @Autowired
    private FaqService faqService;

    @GET
    @Path("/faq/{uuid}")
    public Response getFaq(@PathParam("uuid") String uuid){
        Optional<Faq> faq = faqService.get(uuid);
        Response response;
        if(faq.isPresent()) {
            response = Response.ok(faq.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/faq")
    public Response getListaFaqs(@QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Faq>> faqs = faqService.list(page, size);
        Response response;
        if(faqs.isPresent()) {
            response = Response.ok(faqs.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @POST
    @Path("/faq")
    public Response insertFaq(Faq faq){
        Optional<Faq> faqDB = faqService.insert(faq);
        Response response;
        if(faqDB.isPresent()) {
            response = Response.ok(faqDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @PUT
    @Path("/faq/{uuid}")
    public Response updateFaq(@PathParam("uuid") String uuid, Faq faq){
        faq.setUuid(uuid);
        Optional<Faq> faqDB = faqService.update(faq);
        Response response;
        if(faqDB.isPresent()) {
            response = Response.ok(faqDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @DELETE
    @Path("/faq/{uuid}")
    public Response deleteFaq(@PathParam("uuid") String uuid){
        Optional<Faq> faq = faqService.get(uuid);
        faq.get().setStatus(-1);
        Optional<Faq> faqDB = faqService.update(faq.get());
        Response response;
        if(faqDB.isPresent()) {
            response = Response.ok(faqDB.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

    @GET
    @Path("/faq/x-titulo")
    public Response searchByTitulo(@QueryParam("titulo") String titulo, @QueryParam("page") Integer page, @QueryParam("size") Integer size ){
        Optional<List<Faq>> faqsTitulo = faqService.getTitulo(titulo, page, size);
        Response response;
        if(faqsTitulo.isPresent()) {
            response = Response.ok(faqsTitulo.get()).build();
        }else{
            response = Response.noContent().build();
        }
        return response;
    }

}
