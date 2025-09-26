/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import Modelo.Cliente;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author jprod
 */
public class GestorClientesMem implements IGestorClientes {
    private final HashMap<String, Cliente> map;
    private Cliente cliente;

    @Override
    public Cliente ultimoRegistro() {
        return cliente;
    }
    
    public GestorClientesMem() {
        map = new HashMap<>();
        cliente=null;
    }
    
    @Override
    public void guardar(Cliente cliente) {
        Objects.requireNonNull(cliente, "Cliente requerido");
        if (map.putIfAbsent(cliente.getId(), cliente) != null){ 
            throw new IllegalStateException("Ya existe un cliente con id=" + cliente.getId());
        }
        this.cliente=cliente;
    }
    
    @Override
    public void actualizar(Cliente cliente) {
        Objects.requireNonNull(cliente, "Cliente requerido");
        String id = cliente.getId();
        if (!map.containsKey(id)) {
            throw new IllegalArgumentException("No existe cliente con id=" + id);
        }
        map.put(id, cliente);
        this.cliente=cliente;
    }
    
    @Override
    public void eliminar(String id) {
        Objects.requireNonNull(id, "Id requerido");
        if (map.remove(id) == null) {
            throw new IllegalArgumentException("No existe cliente con id=" + id);
        }
        this.cliente=null;
    }
    
    @Override
    public Cliente buscar(String id) { 
        Objects.requireNonNull(id, "Id requerido");
        cliente=map.get(id);
        return cliente; 
    }
    
    @Override
    public boolean existe(String id) {
        Objects.requireNonNull(id, "Id requerido");
        return map.containsKey(id);
    }

    @Override
    public List<Cliente> listar() {
        return new ArrayList<>(map.values());
    }
}
