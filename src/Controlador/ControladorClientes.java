/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Cliente;
import Modelo.ServicioClientes;
import Vista.IVista;
import java.util.List;

/**
 *
 * @author jprod
 */

public class ControladorClientes {
    private final ServicioClientes servicio;
    private final IVista vista;

    public ControladorClientes(ServicioClientes servicio, IVista vista){
        this.servicio = servicio;
        this.vista = vista;
    }

public void guardar(String id, String nombre, String correo, String telefono, boolean preferente){
    try {
        servicio.guardar(id, nombre, correo, telefono, preferente);
        vista.deshabilitarCampos();
        vista.mostrarMensaje("El registro se agregó correctamente", "Registro exitoso");
    } catch(Exception ex){
        vista.mostrarError(ex.getMessage());
    }
}

public void actualizar(String id, String correo, String telefono, boolean preferente){
    try {
        servicio.actualizar(id, correo, telefono, preferente);
        vista.mostrarDatos(servicio.buscar(id));
        vista.mostrarMensaje("El registro actualizado correctamente", "Actualización exitosa");
    } catch(Exception ex){
        vista.mostrarError(ex.getMessage());
    }
}


    public void eliminar(String id){
        try {
            servicio.ultimoRegistro();
            if(!vista.confirmar("¿Está seguro que desea eliminar el registro?", "Eliminar Registro")) return;
            servicio.eliminar(id); // Lógica de eliminación (activo = false)
            vista.habilitarCampos();
            vista.limpiar();
        } catch(Exception ex){
            vista.mostrarError(ex.getMessage());
        }
    }

    public void cancelar(){
        try {
            vista.mostrarDatos(servicio.ultimoRegistro());
            vista.deshabilitarCampos();
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void buscar(String id){
        try {
            vista.deshabilitarCampos();
            vista.mostrarDatos(servicio.buscar(id));
        } catch (Exception ex) {
            vista.mostrarError(ex.getMessage());
        }
    }

    public void validarIdDisponible(String id){
        try {            
            if (servicio.validarIdDisponible(id)) return;
            if (!vista.confirmar("El ID ingresado ya se encuentra registrado, ¿desea cargar el registro?", "ID Duplicado")) return;
            vista.mostrarDatos(servicio.buscar(id));
        } catch (Exception ex){
            vista.mostrarError(ex.getMessage());
        }
    }
    
    public void mostrarHistorico() {
    List<Cliente> historico = servicio.getHistoricoClientes();
    for (Cliente c : historico) {
        System.out.println("ID: " + c.getId() + ", Nombre: " + c.getNombre());
    }
}
}
