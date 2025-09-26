/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;

import java.util.List;

/**
 *
 * @author jprod
 */
public interface IGestorClientes {
    void guardar(Cliente cliente);
    void actualizar(Cliente cliente);
    void eliminar(String id);
    Cliente buscar(String id);
    boolean existe(String id);
    List<Cliente> listar();
    public Cliente ultimoRegistro();
}
