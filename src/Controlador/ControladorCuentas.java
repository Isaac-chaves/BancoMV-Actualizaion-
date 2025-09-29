/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controlador;

import Modelo.Cuenta;
import Modelo.Estado;
import Modelo.ServicioCuentas;
import Modelo.TipoMoneda;
import Vista.IVista;
import java.util.List;

/**
 *
 * @author jprod
 */
public class ControladorCuentas {
    private final ServicioCuentas servicio;
    private final IVista vista;
    
    public ControladorCuentas(ServicioCuentas servicio, IVista vista) {
        this.servicio = servicio;
        this.vista = vista;
    }
    
    public void guardar(String idTitular, double saldoInicial, TipoMoneda tipoMoneda) {
        try {
            servicio.guardar(idTitular, saldoInicial, tipoMoneda);
            vista.deshabilitarCampos();
            vista.mostrarMensaje("El registro se agregó correctamente", "Registro exitoso");
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    
    public void actualizar(String numeroCuenta, Estado estado) {
        try {
            servicio.actualizar(numeroCuenta, estado);
            vista.mostrarDatos(servicio.buscarCuenta(numeroCuenta));
            vista.mostrarMensaje("El registro actualizado correctamente", "Actualización exitosa");
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    
    public void eliminar(String numeroCuenta) {
        try {
            servicio.ultimoRegistro();
            if (!vista.confirmar("¿Está seguro que desea eliminar el registro?", "Eliminar Registro")) return;
            servicio.eliminar(numeroCuenta);
            vista.habilitarCampos();
            vista.limpiar();
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    
    public void cancelar() {
        try {
            vista.mostrarDatos(servicio.ultimoRegistro());
            vista.deshabilitarCampos();
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    
    public void buscar(String numeroCuenta) {
        try {
            vista.deshabilitarCampos();
            vista.mostrarDatos(servicio.buscarCuenta(numeroCuenta));
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    
    public void validarNumeroCuentaDisponible(String numeroCuenta) {
        try {
            if (servicio.validarNumeroCuentaDisponible(numeroCuenta))
                return;
            if (!vista.confirmar("El número de cuenta ya se encuentra registrado, ¿desea cargar el registro?", "Número Duplicado")) return;
            vista.mostrarDatos(servicio.buscarCuenta(numeroCuenta));
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    
    public void mostrarHistorico() {
        List<Cuenta> historico = servicio.listarCuentas();
        for (Cuenta c : historico) {
            System.out.println("Número: " + c.getNumero() + ", Titular: " + c.getTitular());
        }
    }
}