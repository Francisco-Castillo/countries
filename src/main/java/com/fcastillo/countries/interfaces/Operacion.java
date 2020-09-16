/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcastillo.countries.interfaces;

import com.fcastillo.countries.modelos.PaisForm;
import com.fcastillo.utilidades.excepciones.ErrorMessage;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author fcastillo
 */
public interface Operacion {

  @POST
  @Path("/Crear")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Permite insertar un nuevo pais")
  @ApiResponses({
    @ApiResponse(code = 201, message = "Crated"),
    @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class),
    @ApiResponse(code = 404, message = "Not Found", response = ErrorMessage.class),
    @ApiResponse(code = 409, message = "Conflict", response = ErrorMessage.class),
    @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
  })
  Response crear(@ApiParam(name = "FormPais", value = "Formulario que representa la entidad pais", required = true) PaisForm pais);

  @PUT
  @Path("/Cambiar")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Permite actualizar la información de un pais")
  @ApiResponses({
    @ApiResponse(code = 200, message = "OK"),
    @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class),
    @ApiResponse(code = 404, message = "Not Found", response = ErrorMessage.class),
    @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
  })
  Response editar(@ApiParam(name = "FormPais", value = "Formulario que representa la entidad pais", required = true) PaisForm pais);

  @DELETE
  @Path("/Eliminar/{id}")
  @Consumes(MediaType.APPLICATION_JSON)
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Permite eliminar un pais")
  @ApiResponses({
    @ApiResponse(code = 200, message = "OK"),
    @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class),
    @ApiResponse(code = 404, message = "Not Found", response = ErrorMessage.class),
    @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
  })
  Response remover(@ApiParam(value = "id de pais", required = true, type = "integer") @PathParam("id") int idpais);

  @GET
  @Path("/GetLista")
  @Produces(MediaType.APPLICATION_JSON)
  @ApiOperation(value = "Permite obtener un listado de paises")
  @ApiResponses({
    @ApiResponse(code = 200, message = "OK"),
    @ApiResponse(code = 400, message = "Bad request", response = ErrorMessage.class),
    @ApiResponse(code = 404, message = "Not Found", response = ErrorMessage.class),
    @ApiResponse(code = 500, message = "Internal Server Error", response = ErrorMessage.class)
  })
  @ApiImplicitParams({
    @ApiImplicitParam(name = "start", value = "Número de página", dataType = "integer", paramType = "query", defaultValue = "0"),
    @ApiImplicitParam(name = "size", value = "Cantidad de registros a mostrar", dataType = "integer", paramType = "query", defaultValue = "10"),
    @ApiImplicitParam(name = "id", value = "Id de país", dataType = "integer", paramType = "query"),
    @ApiImplicitParam(name = "nombreLargo", value = "Nombre completo del país", dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "nombreCorto", value = "Nombre corto del país", dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "capital", value = "Nombre de la capital del pais", dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "abreviatura", value = "Abreviatura", dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "sortField", value = "Campo o atributo por el que se desea ordenar el listado", dataType = "string", paramType = "query"),
    @ApiImplicitParam(name = "sortOrder", value = "Ordenamiento asc o desc", dataType = "string", paramType = "query")
  })
  Response buscar();

}
