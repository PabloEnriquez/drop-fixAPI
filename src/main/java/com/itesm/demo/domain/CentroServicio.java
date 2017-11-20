package com.itesm.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CentroServicio {

    private Long id;

    private String uuid;

    private Integer status;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fecha_creacion;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date fecha_modificacion;

    private Float longitud;

    private Float latitud;

    private String descripcion;

    private String direccion;

    private String titulo;

    private String url;

	/**
	* Returns value of id
	* @return
	*/
	public Long getId() {
		return id;
	}

	/**
	* Sets new value of id
	* @param
	*/
	public void setId(Long id) {
		this.id = id;
	}

	/**
	* Returns value of uuid
	* @return
	*/
	public String getUuid() {
		return uuid;
	}

	/**
	* Sets new value of uuid
	* @param
	*/
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	* Returns value of status
	* @return
	*/
	public Integer getStatus() {
		return status;
	}

	/**
	* Sets new value of status
	* @param
	*/
	public void setStatus(Integer status) {
		this.status = status;
	}

	/**
	* Returns value of longitud
	* @return
	*/
	public Float getLongitud() {
		return longitud;
	}

	/**
	* Sets new value of longitud
	* @param
	*/
	public void setLongitud(Float longitud) {
		this.longitud = longitud;
	}

	/**
	* Returns value of latitud
	* @return
	*/
	public Float getLatitud() {
		return latitud;
	}

	/**
	* Sets new value of latitud
	* @param
	*/
	public void setLatitud(Float latitud) {
		this.latitud = latitud;
	}

	/**
	* Returns value of descripcion
	* @return
	*/
	public String getDescripcion() {
		return descripcion;
	}

	/**
	* Sets new value of descripcion
	* @param
	*/
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	/**
	* Returns value of direccion
	* @return
	*/
	public String getDireccion() {
		return direccion;
	}

	/**
	* Sets new value of direccion
	* @param
	*/
	public void setDireccion(String direccion) {
		this.direccion = direccion;
	}

	/**
	* Returns value of titulo
	* @return
	*/
	public String getTitulo() {
		return titulo;
	}

	/**
	* Sets new value of titulo
	* @param
	*/
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}

	/**
	* Returns value of url
	* @return
	*/
	public String getUrl() {
		return url;
	}

	/**
	* Sets new value of url
	* @param
	*/
	public void setUrl(String url) {
		this.url = url;
	}


    public Date getFecha_creacion() {
        return fecha_creacion;
    }

    public void setFecha_creacion(Date fecha_creacion) {
        this.fecha_creacion = fecha_creacion;
    }

    public Date getFecha_modificacion() {
        return fecha_modificacion;
    }

    public void setFecha_modificacion(Date fecha_modificacion) {
        this.fecha_modificacion = fecha_modificacion;
    }
}
