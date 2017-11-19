package com.itesm.demo.service;

import com.itesm.demo.dao.EquipoComputoDAO;
import com.itesm.demo.dao.ReporteDAO;
import com.itesm.demo.domain.EquipoComputo;
import com.itesm.demo.domain.Reporte;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class EquipoComputoService {

    @Autowired
    private EquipoComputoDAO equipoComputoDAO;

    @Autowired
    private ReporteDAO reporteDAO;

    public Optional<EquipoComputo> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Pattern p = Pattern.compile("(^[a-zA-Z0-9][ A-Za-z0-9_-]*$)", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(uuid);
        boolean b = m.find();
        if ( (!uuid.isEmpty()) /*&& (!b)*/){
            Optional<EquipoComputo> equipo_computo = equipoComputoDAO.getByUuid(uuid);
            return equipo_computo;
        }else {
            return Optional.empty();
        }
    }

    public Optional<EquipoComputo> insert(EquipoComputo equipo_computo){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        if ( (equipo_computo.getNombre() != null) && (equipo_computo.getNum_serie() != null) && (equipo_computo.getModelo() != null)
                && (equipo_computo.getMarca() != null) && (equipo_computo.getSistema_operativo() != null) &&
                (equipo_computo.getId_usuario() != null && equipo_computo.getId_usuario() > 0) ){
            equipo_computo.setStatus(1);
            return equipoComputoDAO.insert(equipo_computo);
        }else{
            return Optional.empty();
        }
    }

    public Optional<EquipoComputo> update(EquipoComputo equipo_computo){
        // validar los datos y cualquier lógica de negocio
        Optional<EquipoComputo> equipo_computoDB = equipoComputoDAO.getByUuid(equipo_computo.getUuid());
        if(equipo_computoDB.isPresent()) {
            if(equipo_computo.getStatus() == null){
                equipo_computo.setStatus(equipo_computoDB.get().getStatus());
            }
            if(equipo_computo.getFecha_modificacion() == null){
                equipo_computo.setFecha_modificacion(equipo_computoDB.get().getFecha_modificacion());
            }
            if(equipo_computo.getNombre() == null){
                equipo_computo.setNombre(equipo_computoDB.get().getNombre());
            }
            if(equipo_computo.getNum_serie() == null){
                equipo_computo.setNum_serie(equipo_computoDB.get().getNum_serie());
            }
            if(equipo_computo.getModelo() == null){
                equipo_computo.setModelo(equipo_computoDB.get().getModelo());
            }
            if(equipo_computo.getMarca() == null){
                equipo_computo.setMarca(equipo_computoDB.get().getMarca());
            }
            if(equipo_computo.getSistema_operativo() == null){
                equipo_computo.setSistema_operativo(equipo_computoDB.get().getSistema_operativo());
            }
            return equipoComputoDAO.update(equipo_computo);
        } else {
            return Optional.empty();
        }
    }

    public Optional<List<EquipoComputo>> list(Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( (page != null && page >= 0) && (size != null && size > 0) ){
            return equipoComputoDAO.list(page, size);
        }else {
            return Optional.empty();
        }
    }

    public Optional<List<EquipoComputo>> getFechaCreacion(Date fecha_creacion, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( (fecha_creacion != null) && (page != null && page >= 0) && (size != null && size > 0) ){
            return equipoComputoDAO.getByFechaCreacion(fecha_creacion, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<EquipoComputo>> getNombre(String nombre, Integer page, Integer size ){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( (!nombre.isEmpty()) && (page != null && page >= 0) && (size != null && size > 0) ){
            return equipoComputoDAO.getByNombre(nombre, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<EquipoComputo>> getNumSerie(String num_serie, Integer page, Integer size ){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( (!num_serie.isEmpty()) && (page != null && page >= 0) && (size != null && size > 0) ){
            return equipoComputoDAO.getByNumSerie(num_serie, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<EquipoComputo>> getModelo(String modelo, Integer page, Integer size ){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( (!modelo.isEmpty()) && (page != null && page >= 0) && (size != null && size > 0) ){
            return equipoComputoDAO.getByModelo(modelo, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<EquipoComputo>> getMarca(String marca, Integer page, Integer size ){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( (!marca.isEmpty()) && (page != null && page >= 0) && (size != null && size > 0) ){
            return equipoComputoDAO.getByMarca(marca, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<EquipoComputo>> getSistOperativo(String sistema_operativo, Integer page, Integer size ){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        if ( (!sistema_operativo.isEmpty()) && (page != null && page >= 0) && (size != null && size > 0) ){
            return equipoComputoDAO.getBySistemaOp(sistema_operativo, page, size);
        }else{
            return Optional.empty();
        }
    }

    public Optional<List<Reporte>> listReportes(Long id_equipo_computo, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        if ( ((id_equipo_computo != null) && (id_equipo_computo > 0)) && (page != null && page >= 0) && (size != null && size > 0) ){
            return reporteDAO.listReportesEquipo(id_equipo_computo, page, size);
        }else{
            return Optional.empty();
        }
    }

}
