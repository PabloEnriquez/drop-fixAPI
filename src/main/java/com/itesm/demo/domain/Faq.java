package com.itesm.demo.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Date;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class Faq {

    private Long id;

    private String uuid;

    private Integer status;

    private String titulo;

    private String descripcion;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateCreated;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date dateModified;



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
	* Returns value of dateCreated
	* @return
	*/
	public Date getDateCreated() {
		return dateCreated;
	}

	/**
	* Sets new value of dateCreated
	* @param
	*/
	public void setDateCreated(Date dateCreated) {
		this.dateCreated = dateCreated;
	}

	/**
	* Returns value of dateModified
	* @return
	*/
	public Date getDateModified() {
		return dateModified;
	}

	/**
	* Sets new value of dateModified
	* @param
	*/
	public void setDateModified(Date dateModified) {
		this.dateModified = dateModified;
	}
}
