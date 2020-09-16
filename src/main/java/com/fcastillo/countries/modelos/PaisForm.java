/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcastillo.countries.modelos;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author fcastillo
 */
@ApiModel(description = "Modelo de formulario para la entidad Pais")
public class PaisForm {

  @ApiModelProperty(position = 1, dataType = "Integer", value = "Id pais", example = "1")
  private int id;

  @ApiModelProperty(position = 2, required = true, dataType = "String", value = "Nombre corto del pais", example = "Argentina", allowEmptyValue = false)
  @NotNull
  @Size(min = 1, max = 50)
  private String nombreCorto;

  @ApiModelProperty(position = 3, required = true, dataType = "String", value = "Nombre oficial del pais", example = "República Argentina")
  @Size(min = 1, max = 80)
  private String nombreLargo;

  @ApiModelProperty(position = 4, required = true, dataType = "String", value = "Capital", example = "Ciudad Autónoma de Buenos Aires")
  @Size(min = 1, max = 50)
  private String capital;

  @ApiModelProperty(position = 5, required = true, dataType = "String", value = "Abreviatura", example = "Arg")
  @Size(min = 1, max = 5)
  private String abreviatura;

  public PaisForm() {
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNombreCorto() {
    return nombreCorto;
  }

  public void setNombreCorto(String nombreCorto) {
    this.nombreCorto = nombreCorto;
  }

  public String getNombreLargo() {
    return nombreLargo;
  }

  public void setNombreLargo(String nombreLargo) {
    this.nombreLargo = nombreLargo;
  }

  public String getCapital() {
    return capital;
  }

  public void setCapital(String capital) {
    this.capital = capital;
  }

  public String getAbreviatura() {
    return abreviatura;
  }

  public void setAbreviatura(String abreviatura) {
    this.abreviatura = abreviatura;
  }

}
