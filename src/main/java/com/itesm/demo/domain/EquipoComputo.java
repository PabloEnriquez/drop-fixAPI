package com.itesm.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class EquipoComputo {

    private Long id;

    private String uuid;

    private Integer status;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha_creacion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha_modificacion;

    private String nombre;

    private String num_serie;

    private String modelo;

    private String marca;

    private String sistema_operativo;

    private Long id_usuario;

    public Long getId() {
        return id;
    }

    public String getUuid() {
        return uuid;
    }

    public Integer getStatus() {
        return status;
    }

    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }

    public String getNombre() {
        return nombre;
    }

    public String getNum_serie() {
        return num_serie;
    }

    public String getModelo() {
        return modelo;
    }

    public String getMarca() {
        return marca;
    }

    public String getSistema_operativo() {
        return sistema_operativo;
    }

    public Long getId_usuario() {
        return id_usuario;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public void setNum_serie(String num_serie) {
        this.num_serie = num_serie;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public void setSistema_operativo(String sistema_operativo) {
        this.sistema_operativo = sistema_operativo;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }
}
