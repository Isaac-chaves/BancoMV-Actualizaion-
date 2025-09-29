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
    
    public void guardar(String idTitular, double saldoInicial, TipoMoneda tipoMoneda) {
        validarRequeridos(idTitular);
        Objects.requireNonNull(tipoMoneda, "Tipo de moneda requerido");
        validarTitularExiste(idTitular);
        validarSaldo(saldoInicial);
        
        String numeroCuenta = gestorCuentas.generarNumeroCuenta();
        
        if (gestorCuentas.existe(numeroCuenta)) {
            throw new IllegalArgumentException("Ya existe una cuenta con número=" + numeroCuenta);
        }
        
        Cuenta cuenta;
        if (tipoMoneda == TipoMoneda.COLONES) {
            cuenta = new CuentaColones(numeroCuenta, idTitular, saldoInicial);
        } else {
            cuenta = new CuentaDolares(numeroCuenta, idTitular, saldoInicial);
        }
        
        gestorCuentas.guardar(cuenta);
    }
    
    public void guardar(String idTitular, double saldoInicial) {
        // Por defecto, crea cuenta en colones
        guardar(idTitular, saldoInicial, TipoMoneda.COLONES);
    }
    
    public void crearCuentaColones(String idTitular, double saldoInicial) {
        guardar(idTitular, saldoInicial, TipoMoneda.COLONES);
    }
    
    public void crearCuentaDolares(String idTitular, double saldoInicial) {
        guardar(idTitular, saldoInicial, TipoMoneda.DOLARES);
    }
    
    public void eliminar(String numeroCuenta) {
        if (!gestorCuentas.existe(numeroCuenta)) {
            throw new IllegalArgumentException("No existe ninguna cuenta con número=" + numeroCuenta);
        }
        gestorCuentas.eliminar(numeroCuenta);
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
    
    public void actualizar(String numeroCuenta, Estado nuevoEstado) {
        Objects.requireNonNull(ultimoRegistro(), "No se ha cargado ningún registro");
        validarRequeridos(numeroCuenta);
        Objects.requireNonNull(nuevoEstado, "Estado requerido");
        
        if (!hayCambios(numeroCuenta, nuevoEstado)) return;
        if (!gestorCuentas.existe(numeroCuenta)) {
            throw new IllegalArgumentException("No existe cuenta con número=" + numeroCuenta);
        }
        
        Cuenta cuenta = gestorCuentas.buscar(numeroCuenta);
        cuenta.setEstado(nuevoEstado);
        gestorCuentas.actualizar(cuenta);
    }
    
    public void cambiarEstadoCuenta(String numeroCuenta, Estado nuevoEstado) {
        Cuenta cuenta = buscarCuenta(numeroCuenta);
        Estado estadoAnterior = cuenta.getEstado();
        cuenta.setEstado(nuevoEstado);
        gestorCuentas.actualizar(cuenta);
        
        System.out.println("Estado de cuenta " + numeroCuenta + " cambiado de " + estadoAnterior + " a " + nuevoEstado);
    }
    
    public Cuenta buscarCuenta(String numeroCuenta) {
        validarRequeridos(numeroCuenta);
        if (!gestorCuentas.existe(numeroCuenta)) {
            throw new IllegalArgumentException("No existe cuenta con número: " + numeroCuenta);
        }
        return gestorCuentas.buscar(numeroCuenta);
    }
    
    public boolean validarNumeroCuentaDisponible(String numeroCuenta) {
        if (numeroCuenta == null || numeroCuenta.isBlank()) {
            throw new IllegalArgumentException("Número de cuenta requerido");
        }
        return !gestorCuentas.existe(numeroCuenta);
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
    
    private void validarSaldo(double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
    }
    
    private void validarRequeridos(String... datos) {
        for (String dato : datos) {
            if (dato == null || dato.isBlank()) {
                throw new IllegalArgumentException("Faltan datos requeridos");
            }
        }
    }
    
    private boolean hayCambios(String numeroCuenta, Estado nuevoEstado) {
        Cuenta cuenta = gestorCuentas.buscar(numeroCuenta);
        Objects.requireNonNull(cuenta, "No se ha cargado ningún registro");
        return !cuenta.getEstado().equals(nuevoEstado);
    }
}
