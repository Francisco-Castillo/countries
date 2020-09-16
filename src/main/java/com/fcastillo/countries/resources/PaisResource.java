/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcastillo.countries.resources;

import com.fcastillo.countries.ejb.PaisFacadeLocal;
import com.fcastillo.countries.entidades.Pais;
import com.fcastillo.countries.interfaces.Operacion;
import com.fcastillo.countries.modelos.PaisForm;
import com.fcastillo.utilidades.excepciones.BadRequestException;
import com.fcastillo.utilidades.excepciones.ErrorMessage;
import com.fcastillo.utilidades.excepciones.NotFoundException;
import com.fcastillo.utilidades.rest.Respuesta;
import io.swagger.annotations.Api;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ws.rs.Path;
import java.util.List;
import java.util.Map;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;

/**
 *
 * @author fcastillo
 */
@Path("Pais")
@Api("Pais")
public class PaisResource implements Operacion {

  @EJB
  PaisFacadeLocal paisEJB;

  @Context
  UriInfo uriInfo;

  @Override
  public Response crear(PaisForm pais) {
    Respuesta respuesta = new Respuesta();
    Pais nuevoPais = new Pais();
    try {

      // Podriamos validar que el pais no exista en la bd...
      nuevoPais.setAbreviatura(pais.getAbreviatura());
      nuevoPais.setNombrecorto(pais.getNombreCorto());
      nuevoPais.setNombrelargo(pais.getNombreLargo());
      nuevoPais.setCapital(pais.getCapital());

      paisEJB.create(nuevoPais);

      return respuesta.jsonSimpleResponse(201, nuevoPais.getNombrecorto() + " fue insertado exitosamente");

    } catch (Exception e) {
      ErrorMessage em = new ErrorMessage(Response.Status.BAD_REQUEST,
              Response.Status.BAD_REQUEST.getStatusCode(),
              e.getLocalizedMessage());
      throw new BadRequestException(em);
    }

  }

  @Override
  public Response editar(PaisForm pais) {
    Respuesta respuesta = new Respuesta();
    Pais find = paisEJB.find(pais.getId());

    if (find == null) {
      ErrorMessage em = new ErrorMessage(Response.Status.NOT_FOUND,
              Response.Status.NOT_FOUND.getStatusCode(),
              "No se encontró el pais con id: " + pais.getId());
      throw new NotFoundException(em);
    }

    find.setId(pais.getId());
    find.setNombrecorto(pais.getNombreCorto());
    find.setNombrelargo(pais.getNombreLargo());
    find.setCapital(pais.getCapital());
    find.setAbreviatura(pais.getAbreviatura());

    paisEJB.edit(find);
    return respuesta.jsonSimpleResponse(201, "Se actualizó exitosamente la información de " + find.getNombrecorto());
  }

  @Override
  public Response remover(int idpais) {
    Respuesta respuesta = new Respuesta();

    // Consultamos la BD
    Pais find = paisEJB.find(idpais);

    // Retornamos una excepcion si el codigo de pais no fue encontrado
    if (find == null) {
      ErrorMessage em = new ErrorMessage(Response.Status.NOT_FOUND, Response.Status.NOT_FOUND.getStatusCode(), "No se encontró el pais con el id: " + idpais);
      throw new NotFoundException(em);
    }

    // Accedemos a la BD y eliminamos el registro
    paisEJB.remove(find);

    return respuesta.jsonSimpleResponse(200, find.getNombrecorto() + " fue eliminado exitosamente");
  }

  @Override
  public Response buscar() {
    // Objetos
    Respuesta respuesta = new Respuesta();
    JsonArrayBuilder arreglo = Json.createArrayBuilder();
    Map<String, Object> filtros = new HashMap<>();

    //  Extraemos parámetros
    MultivaluedMap<String, String> parametros = uriInfo.getQueryParameters();

    String start = parametros.getFirst("start");
    String size = parametros.getFirst("size");
    String sortField = parametros.getFirst("sortField");
    String sortOrder = parametros.getFirst("sortOrder");
    String nombreCorto = parametros.getFirst("nombreCorto");
    String nombreLargo = parametros.getFirst("nombreLargo");
    String capital = parametros.getFirst("capital");
    String abreviatura = parametros.getFirst("abreviatura");
    String id = parametros.getFirst("id");

    // Validar y convertir
    int iStart = start == null ? 0 : Integer.parseInt(start);
    int iSize = size == null ? 10 : Integer.parseInt(size);

    if (id != null) {
      int iId = Integer.parseInt(id);
      filtros.put("id", iId);
    }

    filtros.put("nombreCorto", nombreCorto);
    filtros.put("nombreLargo", nombreLargo);
    filtros.put("capital", capital);
    filtros.put("abreviatura", abreviatura);

    // Consultamos la BD
    List<Pais> lstPaises = paisEJB.findByParams(iStart, iSize, sortField, sortOrder, filtros);

    // Si la lista esta vacia, retornamos una excepcion
    if (lstPaises.isEmpty()) {
      ErrorMessage em = new ErrorMessage(Response.Status.NOT_FOUND, Response.Status.NOT_FOUND.getStatusCode(), "No se encontraron registros");
      throw new NotFoundException(em);
    }

    // Generamos arreglo Json
    lstPaises.stream().forEach(x -> {
      arreglo.add(Json.createObjectBuilder()
              .add("id", x.getId())
              .add("nombrelargo", x.getNombrelargo())
              .add("nombrecorto", x.getNombrecorto())
              .add("abreviatura", x.getAbreviatura())
              .add("capital", x.getCapital()));
    });

    // Enviamos respuesta a cliente
    return respuesta.jsonResponse(Response.Status.OK.getStatusCode(), arreglo, lstPaises.size());

  }

}
