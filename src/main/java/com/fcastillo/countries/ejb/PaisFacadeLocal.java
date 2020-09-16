/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fcastillo.countries.ejb;

import com.fcastillo.countries.entidades.Pais;
import javax.ejb.Local;
import java.util.List;
import java.util.Map;

/**
 *
 * @author fcastillo
 */
@Local
public interface PaisFacadeLocal {

  void create(Pais pais);

  void edit(Pais pais);

  void remove(Pais pais);

  Pais find(Object id);

  List<Pais> findAll();

  List<Pais> findByParams(int start, int size, String sortField, String sortOrder, Map<String, Object> filters);

  List<Pais> findRange(int[] range);

  int count();

}
