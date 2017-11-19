package com.itesm.demo.service;

import com.itesm.demo.dao.FaqDAO;
import com.itesm.demo.domain.Faq;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class FaqService {

    @Autowired
    private FaqDAO faqDAO;

    public Optional<Faq> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Pattern p = Pattern.compile("(^[a-zA-Z0-9][ A-Za-z0-9_-]*$)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(uuid);
        boolean b = m.find();
        if ( (!uuid.isEmpty()) /*&& (!b)*/ ){
            Optional<Faq> faq = faqDAO.getByUuid(uuid);
            return faq;
        }else {
            return Optional.empty();
        }
    }

    public Optional<Faq> insert(Faq faq){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        if ( (faq.getTitulo() != null) && (faq.getDescripcion() != null) ){
            faq.setStatus(1);
            return faqDAO.insert(faq);
        }else{
            return Optional.empty();
        }
    }

    public Optional<Faq> update(Faq faq){
        // validar los datos y cualquier lógica de negocio
        Optional<Faq> faqDB = faqDAO.getByUuid(faq.getUuid());
        if(faqDB.isPresent()) {
            if(faq.getStatus() == null){
                faq.setStatus(faqDB.get().getStatus());
            }
            if(faq.getFecha_modificacion() == null){
                faq.setFecha_modificacion(faqDB.get().getFecha_modificacion());
            }
            if(faq.getTitulo() == null){
                faq.setTitulo(faqDB.get().getTitulo());
            }
            if(faq.getDescripcion() == null){
                faq.setDescripcion(faqDB.get().getDescripcion());
            }
            return faqDAO.update(faq);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<Faq>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( (page != null && page >= 0) && (size != null && size > 0) ){
            return faqDAO.list(page, size);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<Faq>> getTitulo(String titulo, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( (!titulo.isEmpty()) && (page != null && page >= 0) && (size != null && size > 0) ){
            return faqDAO.getByTitulo(titulo, page, size);
        }else{
            return Optional.empty();
        }
    }

}
