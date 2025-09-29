/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Controlador;
import Modelo.Cuenta;
import Modelo.ServicioCuentas;
import Vista.IVista;
import java.util.List;
public class ControladorCuentas {
    private final ServicioCuentas servicio;
    private final IVista vista;
    public ControladorCuentas(ServicioCuentas servicio, IVista vista) {
        this.servicio = servicio;
        this.vista = vista;
    }
    // Método para crear una cuenta nueva
    public void guardar(String idTitular, String numeroCuenta, double saldoInicial, String tipoCuenta) {
        try {
            servicio.guardar(idTitular, numeroCuenta, saldoInicial, tipoCuenta);
            vista.deshabilitarCampos();
            vista.mostrarMensaje("La cuenta se creó correctamente", "Registro exitoso");
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    // Método para actualizar datos de la cuenta (ejemplo: estado)
    public void actualizar(String numeroCuenta, String estado) {
        try {
            servicio.actualizar(numeroCuenta, estado);
            vista.mostrarDatos(servicio.buscar(numeroCuenta));
            vista.mostrarMensaje("La cuenta se actualizó correctamente", "Actualización exitosa");
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
        // Cancelar operación y mostrar última cuenta
    public void cancelar() {
        try {
            vista.mostrarDatos(servicio.ultimoRegistro());
            vista.deshabilitarCampos();
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    // Buscar cuenta por número
    public void buscar(String numeroCuenta) {
        try {
            vista.deshabilitarCampos();
            vista.mostrarDatos(servicio.buscar(numeroCuenta));
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    // Validar que el número de cuenta esté disponible
    public void validarNumeroCuentaDisponible(String numeroCuenta) {
        try {
            if (servicio.validarNumeroCuentaDisponible(numeroCuenta)) return;
            if (!vista.confirmar("El número de cuenta ya existe, ¿desea cargar la cuenta?", "Número duplicado")) return;
            vista.mostrarDatos(servicio.buscar(numeroCuenta));
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }
    // Mostrar histórico de cuentas eliminadas (si aplica)
    public void mostrarHistorico() {
        List<Cuenta> historico = servicio.getHistoricoCuentas();
        for (Cuenta c : historico) {
            System.out.println("Número: " + c.getNumeroCuenta() + ", Titular: " + c.getIdTitular());
        }
    }
}
