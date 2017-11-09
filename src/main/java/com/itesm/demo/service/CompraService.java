package com.itesm.demo.service;

import com.itesm.demo.dao.CompraDAO;
import com.itesm.demo.domain.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CompraService {

    @Autowired
    private CompraDAO compraDAO;

    public Optional<Compra> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Compra> compra = compraDAO.getByUuid(uuid);
//        equipo_computo.set;
        return compra;
    }

    public Optional<Compra> insert(Compra compra){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        compra.setStatus(1);
//        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        return compraDAO.insert(compra);
    }

    public Optional<Compra> update(Compra compra){
        // validar los datos y cualquier lógica de negocio
        Optional<Compra> compraDB = compraDAO.getByUuid(compra.getUuid());
        if(compraDB.isPresent()) {
            if(compra.getStatus() == null){
                compra.setStatus(compraDB.get().getStatus());
            }
            if(compra.getMonto_total() == null){
                compra.setMonto_total(compraDB.get().getMonto_total());
            }
            return compraDAO.update(compra);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<Compra>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return compraDAO.list(page, size);
    }

}
