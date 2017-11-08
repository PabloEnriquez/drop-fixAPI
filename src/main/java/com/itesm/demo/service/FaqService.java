package com.itesm.demo.service;

import com.itesm.demo.dao.FaqDAO;
import com.itesm.demo.domain.Faq;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class FaqService {

    @Autowired
    private FaqDAO faqDAO;

    public Optional<Faq> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Faq> faq = faqDAO.getByUuid(uuid);
        return faq;
    }

    public Optional<Faq> insert(Faq faq){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        faq.setStatus(1);
        return faqDAO.insert(faq);
    }

    public Optional<Faq> update(Faq faq){
        // validar los datos y cualquier lógica de negocio
        Optional<Faq> faqDB = faqDAO.getByUuid(faq.getUuid());
        if(faqDB.isPresent()) {
            if(faq.getStatus() == null){
                faq.setStatus(faqDB.get().getStatus());
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
        return faqDAO.list(page, size);
    }

}
