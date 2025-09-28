/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.List;
import java.util.Objects;

/**
 *
 * @author Home
 */
public class ServicioCuentas {
   private final IGestorCuentas gestorCuentas;
    private final IGestorClientes gestorClientes;
    
    public ServicioCuentas(IGestorCuentas gestorCuentas, IGestorClientes gestorClientes) {
        this.gestorCuentas = Objects.requireNonNull(gestorCuentas, "Gestor de cuentas requerido");
        this.gestorClientes = Objects.requireNonNull(gestorClientes, "Gestor de clientes requerido");
    }
    
    public void crearCuentaColones(String idTitular, double saldoInicial) {
        validarTitularExiste(idTitular);
        String numeroCuenta = gestorCuentas.generarNumeroCuenta();
        CuentaColones cuenta = new CuentaColones(numeroCuenta, idTitular, saldoInicial);
        gestorCuentas.guardar(cuenta);
    }
    
    public void crearCuentaDolares(String idTitular, double saldoInicial) {
        validarTitularExiste(idTitular);
        String numeroCuenta = gestorCuentas.generarNumeroCuenta();
        CuentaDolares cuenta = new CuentaDolares(numeroCuenta, idTitular, saldoInicial);
        gestorCuentas.guardar(cuenta);
    }
    
    public void depositar(String numeroCuenta, double monto) 
            throws CuentaInactivaException {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        cuenta.depositar(monto);
        gestorCuentas.actualizar(cuenta);
    }
    
    public void retirar(String numeroCuenta, double monto) 
            throws SaldoInsuficienteException, CuentaInactivaException {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        cuenta.retirar(monto);
        gestorCuentas.actualizar(cuenta);
    }
    
    public void transferir(String numeroCuentaOrigen, String numeroCuentaDestino, double monto)
            throws SaldoInsuficienteException, CuentaInactivaException, MonedaIncompatibleException {
        Cuenta cuentaOrigen = buscarCuenta(numeroCuentaOrigen);
        Cuenta cuentaDestino = buscarCuenta(numeroCuentaDestino);
        
        cuentaOrigen.transferir(cuentaDestino, monto);
        
        gestorCuentas.actualizar(cuentaOrigen);
        gestorCuentas.actualizar(cuentaDestino);
    }
    
 
    public void cambiarEstadoCuenta(String numeroCuenta, Estado nuevoEstado) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        Estado estadoAnterior = cuenta.getEstado();
        cuenta.setEstado(nuevoEstado);
        gestorCuentas.actualizar(cuenta);
        
        System.out.println("Estado de cuenta " + numeroCuenta + " cambiado de " + estadoAnterior + " a " + nuevoEstado);
    }
    
    
    public Cuenta buscarCuenta(String numeroCuenta) {
        if (!gestorCuentas.existe(numeroCuenta)) {
            throw new IllegalArgumentException("No existe cuenta con número: " + numeroCuenta);
        }
        return gestorCuentas.buscar(numeroCuenta);
    }
    
    public List<Cuenta> listarCuentas() {
        return gestorCuentas.listar();
    }
    
    public List<Cuenta> listarCuentasPorCliente(String idCliente) {
        validarTitularExiste(idCliente);
        return gestorCuentas.listarPorTitular(idCliente);
    }
    
    public Cuenta ultimoRegistro() {
        Cuenta cuenta = gestorCuentas.ultimoRegistro();
        Objects.requireNonNull(cuenta, "No se ha cargado ningún registro");
        return cuenta;
    }
    
    private void validarTitularExiste(String idTitular) {
        if (!gestorClientes.existe(idTitular)) {
            throw new IllegalArgumentException("No existe cliente con ID: " + idTitular);
        }
    }  
}
