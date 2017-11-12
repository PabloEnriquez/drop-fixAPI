package com.itesm.demo.service;

import com.itesm.demo.dao.CompraDAO;
import com.itesm.demo.domain.Compra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CompraService {

    @Autowired
    private CompraDAO compraDAO;

    public Optional<Compra> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Pattern p = Pattern.compile("[^A-Za-z0-9]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(uuid);
        boolean b = m.find();
        if ( (!uuid.isEmpty()) && (!b) ){
            Optional<Compra> compra = compraDAO.getByUuid(uuid);
            return compra;
        }else {
            return Optional.empty();
        }
    }

    public Optional<Compra> insert(Compra compra){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        if ( (compra.getMonto_total() != null && compra.getMonto_total() >= 0) && (compra.getId_reporte() != null && compra.getId_reporte() > 0)
                && (compra.getId_servicio() != null && compra.getId_servicio() > 0) && (compra.getId_usuario() != null && compra.getId_usuario() > 0) ) {
            compra.setStatus(1);
            return compraDAO.insert(compra);
        }else{
            return Optional.empty();
        }
    }

    public Optional<Compra> update(Compra compra){
        // validar los datos y cualquier lógica de negocio
        Optional<Compra> compraDB = compraDAO.getByUuid(compra.getUuid());
        if(compraDB.isPresent()) {
            if(compra.getStatus() == null){
                compra.setStatus(compraDB.get().getStatus());
            }
            if(compra.getFecha_modificacion() == null){
                compra.setFecha_modificacion(compraDB.get().getFecha_modificacion());
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
        if ( (page != null && page > 0) && (size != null && size > 0) ){
            return compraDAO.list(page, size);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<Compra>> getFechaCreacion(Date fecha_creacion, Integer page, Integer size ){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( (fecha_creacion != null) && (page != null && page > 0) && (size != null && size > 0) ){
            return compraDAO.getByFechaCreacion(fecha_creacion, page, size);
        }else{
            return Optional.empty();
        }
    }

}
