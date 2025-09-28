/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author jprod
 */

public class ServicioClientes {
    private final IGestorClientes gestor;
    private final List<Cliente> historicoClientes = new ArrayList<>();
    private static final Pattern EMAIL = Pattern.compile("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$");

    public ServicioClientes(IGestorClientes gestor) {
        this.gestor = gestor;
    }

    public void guardar(String id, String nombre, String correo, String telefono, boolean preferente) {
        validarRequeridos(id, nombre, correo, telefono);
        if (gestor.existe(id)) throw new IllegalArgumentException("Ya existe un registro con id=" + id);
        if (!EMAIL.matcher(correo).matches()) throw new IllegalArgumentException("Formato de correo inválido");

        // Usamos el nuevo constructor que incluye 'preferente'
        Cliente cliente = new Cliente(id, nombre, correo, telefono, preferente);
        gestor.guardar(cliente);
    }

    public void guardar(String id, String nombre, String correo, String telefono) {
        // Por defecto, no es preferente
        guardar(id, nombre, correo, telefono, false);
    }

    public void eliminar(String id) {
    if (!gestor.existe(id)) throw new IllegalArgumentException("No existe ningun registro con id=" + id);
     gestor.eliminar(id); 
}

    public Cliente buscar(String id) {
        validarRequeridos(id);
        if (!gestor.existe(id)) throw new IllegalArgumentException("No existe ningun registro con id=" + id);
        return gestor.buscar(id);
    }

    public Cliente ultimoRegistro() {
        Cliente cliente = gestor.ultimoRegistro();
        Objects.requireNonNull(cliente, "No se ha cargado ningun registro");
        return cliente;
    }

    public boolean validarIdDisponible(String id) {
        if (id == null || id.isBlank()) throw new IllegalArgumentException("Faltan datos requeridos");
        return !gestor.existe(id);
    }

    public List<Cliente> listar() {
        return gestor.listar();
    }

   public List<Cliente> getHistoricoClientes() {
    return gestor.getHistoricoClientes();
}

    private void validarRequeridos(String... datos) {
        for (String dato : datos) {
            if (dato == null || dato.isBlank())
                throw new IllegalArgumentException("Faltan datos requeridos");
        }
    }
    
   public void actualizar(String id, String correo, String telefono, boolean preferente) {
    Objects.requireNonNull(ultimoRegistro(), "No se ha cargado ningun registro");
    validarRequeridos(id, correo, telefono);
    if (!hayCambios(id, correo, telefono, preferente)) return;
    if (!gestor.existe(id)) throw new IllegalArgumentException("No existe un registro con id=" + id);
    if (!EMAIL.matcher(correo).matches()) throw new IllegalArgumentException("Formato de correo inválido");

    Cliente cliente = gestor.buscar(id);
    cliente.setCorreo(correo);
    cliente.setTelefono(telefono);
    cliente.setPreferente(preferente);
    gestor.actualizar(cliente);
}

private boolean hayCambios(String id, String correo, String telefono, boolean preferente) {
    Cliente cliente = gestor.buscar(id);
    Objects.requireNonNull(cliente, "No se ha cargado ningun registro");
    validarRequeridos(correo, telefono);
    return !(cliente.getCorreo().equals(correo) && cliente.getTelefono().equals(telefono) && cliente.isPreferente() == preferente);
}
}

