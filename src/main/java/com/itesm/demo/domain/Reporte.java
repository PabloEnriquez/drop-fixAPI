package com.itesm.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Reporte {

    private Long id;

    private String uuid;

    private Integer status;

    private Integer status_atendido;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha_creacion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date fecha_modificacion;

    private String descripcion;

    private Long id_equipo_computo;

    private Long id_usuario;

    private Long id_chat;

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

    public String getDescripcion() {
        return descripcion;
    }

    public Long getId_equipo_computo() {
        return id_equipo_computo;
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

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public void setId_equipo_computo(Long id_equipo_computo) {
        this.id_equipo_computo = id_equipo_computo;
    }

    public void setId_usuario(Long id_usuario) {
        this.id_usuario = id_usuario;
    }

    public Integer getStatus_atendido() {
        return status_atendido;
    }

    public void setStatus_atendido(Integer status_atendido) {
        this.status_atendido = status_atendido;
    }

    public Long getId_chat() {
        return id_chat;
    }

    public void setId_chat(Long id_chat) {
        this.id_chat = id_chat;
    }
}
