/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Objects;

/**
 *
 * @author jprod
 */
public class Cliente {
    private final String id;
    private final String nombre;
    private String correo;
    private String telefono;
    
    public String getId() { return id; }
    public String getNombre() { return nombre; }
    public String getCorreo() { return correo; }
    public String getTelefono() { return telefono; }
    
    public void setCorreo(String correo) { this.correo = Objects.requireNonNull(correo,"El correo no puede ser un valor nulo"); }
    public void setTelefono(String telefono) { this.telefono = Objects.requireNonNull(telefono,"El telefono no puede ser un valor nulo"); }

    public Cliente(String id, String nombre, String correo, String telefono) {
        this.id = Objects.requireNonNull(id,"El id no puede ser un valor nulo");
        this.nombre = Objects.requireNonNull(nombre,"El nombre no puede ser un valor nulo");
        this.correo = Objects.requireNonNull(correo,"El correo no puede ser un valor nulo");
        this.telefono = Objects.requireNonNull(telefono,"El telefono no puede ser un valor nulo");
    }
}
