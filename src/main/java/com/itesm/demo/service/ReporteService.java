package com.itesm.demo.service;

import com.itesm.demo.dao.CompraDAO;
import com.itesm.demo.dao.ReporteDAO;
import com.itesm.demo.domain.Compra;
import com.itesm.demo.domain.Reporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class ReporteService {

    @Autowired
    private ReporteDAO reporteDAO;

    @Autowired
    private CompraDAO compraDAO;

    public Optional<Reporte> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Pattern p = Pattern.compile("(^[a-zA-Z0-9][ A-Za-z0-9_-]*$)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(uuid);
        boolean b = m.find();
        if ( (!uuid.isEmpty()) /*&& (!b)*/ ){
            Optional<Reporte> reporte = reporteDAO.getByUuid(uuid);
            return reporte;
        }else {
            return Optional.empty();
        }
    }

    public Optional<Reporte> insert(Reporte reporte){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        if ( (reporte.getStatus_atendido() != null) && (reporte.getDescripcion() != null) && (reporte.getId_equipo_computo() != null && reporte.getId_equipo_computo() > 0)
                && (reporte.getId_usuario() != null && reporte.getId_usuario() > 0) && (reporte.getId_chat() != null && reporte.getId_chat() > 0) ){
            reporte.setStatus(1);
            return reporteDAO.insert(reporte);
        }else{
            return Optional.empty();
        }
    }

    public Optional<Reporte> update(Reporte reporte){
        // validar los datos y cualquier lógica de negocio
        Optional<Reporte> reporteDB = reporteDAO.getByUuid(reporte.getUuid());
        if(reporteDB.isPresent()) {
            if(reporte.getStatus() == null){
                reporte.setStatus(reporteDB.get().getStatus());
            }
            if(reporte.getStatus_atendido() == null){
                reporte.setStatus_atendido(reporteDB.get().getStatus_atendido());
            }
            if(reporte.getFecha_modificacion() == null){
                reporte.setFecha_modificacion(reporteDB.get().getFecha_modificacion());
            }
            if(reporte.getDescripcion() == null){
                reporte.setDescripcion(reporteDB.get().getDescripcion());
            }
            return reporteDAO.update(reporte);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<Reporte>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( (page != null && page >= 0) && (size != null && size > 0) ){
            return reporteDAO.list(page, size);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<Reporte>> getStatusAtendido(Integer status_atendido, Integer page, Integer size ){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( ((status_atendido != null) && (status_atendido >= 0)) && (page != null && page >= 0) && (size != null && size > 0) ){
            return reporteDAO.getByStatusAtendido(status_atendido, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<Reporte>> getFechaCreacion(Date fecha_creacion, Integer page, Integer size ){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( (fecha_creacion != null) && (page != null && page >= 0) && (size != null && size > 0) ){
            return reporteDAO.getByFechaCreacion(fecha_creacion, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<Compra>> listComprasReporte(Long id_reporte, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( ((id_reporte != null) && (id_reporte > 0)) && (page != null && page >= 0) && (size != null && size > 0) ){
            return compraDAO.listComprasReporte(id_reporte, page, size);
        }else{
            return Optional.empty();
        }
    }

}
