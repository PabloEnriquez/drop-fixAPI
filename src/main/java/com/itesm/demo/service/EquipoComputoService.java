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

@Service
public class EquipoComputoService {

    @Autowired
    private EquipoComputoDAO equipoComputoDAO;

    @Autowired
    private ReporteDAO reporteDAO;

    public Optional<EquipoComputo> get(String uuid){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<EquipoComputo> equipo_computo = equipoComputoDAO.getByUuid(uuid);
//        equipo_computo.set;
        return equipo_computo;
    }

    public Optional<EquipoComputo> insert(EquipoComputo equipo_computo){
        // validar que el correo no existe por ejemplo
        // validar que vengan todos los campos necesarios
        equipo_computo.setStatus(1);
//        user.setPassword(DigestUtils.sha1Hex(user.getPassword()));
        return equipoComputoDAO.insert(equipo_computo);
    }

    public Optional<EquipoComputo> update(EquipoComputo equipo_computo){
        // validar los datos y cualquier lógica de negocio
        Optional<EquipoComputo> equipo_computoDB = equipoComputoDAO.getByUuid(equipo_computo.getUuid());
        if(equipo_computoDB.isPresent()) {
            if(equipo_computo.getStatus() == null){
                equipo_computo.setStatus(equipo_computoDB.get().getStatus());
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
        return equipoComputoDAO.list(page, size);
    }

    public Optional<EquipoComputo> getFechaCreacion(Date fecha_creacion){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<EquipoComputo> equipo_computo = equipoComputoDAO.getByFechaCreacion(fecha_creacion);
//        equipo_computo.set;
        return equipo_computo;
    }

    public Optional<EquipoComputo> getNombre(String nombre){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<EquipoComputo> equipo_computo = equipoComputoDAO.getByNombre(nombre);
//        equipo_computo.set;
        return equipo_computo;
    }

    public Optional<EquipoComputo> getNumSerie(String num_serie){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<EquipoComputo> equipo_computo = equipoComputoDAO.getByNumSerie(num_serie);
//        equipo_computo.set;
        return equipo_computo;
    }

    public Optional<EquipoComputo> getModelo(String modelo){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<EquipoComputo> equipo_computo = equipoComputoDAO.getByModelo(modelo);
//        equipo_computo.set;
        return equipo_computo;
    }

    public Optional<EquipoComputo> getMarca(String marca){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<EquipoComputo> equipo_computo = equipoComputoDAO.getByMarca(marca);
//        equipo_computo.set;
        return equipo_computo;
    }

    public Optional<EquipoComputo> getSistOperativo(String sistema_operativo){
        // validar los datos y cualquier lógica de negocio
        // modificar el objeto o agregar datos
        Optional<EquipoComputo> equipo_computo = equipoComputoDAO.getBySistemaOp(sistema_operativo);
//        equipo_computo.set;
        return equipo_computo;
    }

    public Optional<List<Reporte>> listReportes(Long id_equipo_computo, Integer page, Integer size){
        // validar los datos y cualquier lógica de negocio
        return reporteDAO.listReportesEquipo(id_equipo_computo, page, size);
    }

}
