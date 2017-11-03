package com.itesm.demo.service;

import com.itesm.demo.dao.ReporteDAO;
import com.itesm.demo.domain.Reporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReporteService {

    @Autowired
    private ReporteDAO reporteDAO;

    public Optional<Reporte> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<Reporte> reporte = reporteDAO.getByUuid(uuid);
//        equipo_computo.set;
        return reporte;
    }

    public Optional<Reporte> insert(Reporte reporte){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        reporte.setStatus("1");
//        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        return reporteDAO.insert(reporte);
    }

    public Optional<Reporte> update(Reporte reporte){
        // validar los datos y cualquier lógica de negocio
        Optional<Reporte> reporteDB = reporteDAO.getByUuid(reporte.getUuid());
        if(reporteDB.isPresent()) {
            if(reporte.getStatus() == null){
                reporte.setStatus(reporteDB.get().getStatus());
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
        return reporteDAO.list(page, size);
    }

}
