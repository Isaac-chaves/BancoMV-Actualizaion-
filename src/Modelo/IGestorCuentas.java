/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Modelo;

import java.util.List;

/**
 *
 * @author Home
 */
public interface IGestorCuentas {
    void guardar(Cuenta cuenta);
    void actualizar(Cuenta cuenta);
    void eliminar(String numeroCuenta);
    Cuenta buscar(String numeroCuenta);
    boolean existe(String numeroCuenta);
    List<Cuenta> listar();
    List<Cuenta> listarPorTitular(String idTitular);
    Cuenta ultimoRegistro();
    String generarNumeroCuenta();
}
