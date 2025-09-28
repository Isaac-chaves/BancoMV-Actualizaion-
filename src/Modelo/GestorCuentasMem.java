/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

/**
 * Implementación en memoria del gestor de cuentas
 * @author jprod
 */
public class GestorCuentasMem implements IGestorCuentas {
    private final HashMap<String, Cuenta> map;
    private Cuenta cuenta;
    private long contadorCuentas;
    private static final String PREFIJO_CUENTA = "506";
    
    @Override
    public Cuenta ultimoRegistro() {
        return cuenta;
    }
    
    public GestorCuentasMem() {
        map = new HashMap<>();
        cuenta = null;
        contadorCuentas = 1;
    }
    
    @Override
    public void guardar(Cuenta cuenta) {
        Objects.requireNonNull(cuenta, "Cuenta requerida");
        if (map.putIfAbsent(cuenta.getNumero(), cuenta) != null) {
            throw new IllegalStateException("Ya existe una cuenta con número=" + cuenta.getNumero());
        }
        this.cuenta = cuenta;
    }
    
    @Override
    public void actualizar(Cuenta cuenta) {
        Objects.requireNonNull(cuenta, "Cuenta requerida");
        String numero = cuenta.getNumero();
        if (!map.containsKey(numero)) {
            throw new IllegalArgumentException("No existe cuenta con número=" + numero);
        }
        map.put(numero, cuenta);
        this.cuenta = cuenta;
    }
    
    @Override
    public void eliminar(String numeroCuenta) {
        Objects.requireNonNull(numeroCuenta, "Número de cuenta requerido");
        if (map.remove(numeroCuenta) == null) {
            throw new IllegalArgumentException("No existe cuenta con número=" + numeroCuenta);
        }
        this.cuenta = null;
    }
    
    @Override
    public Cuenta buscar(String numeroCuenta) {
        Objects.requireNonNull(numeroCuenta, "Número de cuenta requerido");
        cuenta = map.get(numeroCuenta);
        return cuenta;
    }
    
    @Override
    public boolean existe(String numeroCuenta) {
        Objects.requireNonNull(numeroCuenta, "Número de cuenta requerido");
        return map.containsKey(numeroCuenta);
    }
    
    @Override
    public List<Cuenta> listar() {
        return new ArrayList<>(map.values());
    }
    
    @Override
    public List<Cuenta> listarPorTitular(String idTitular) {
        Objects.requireNonNull(idTitular, "ID de titular requerido");
        List<Cuenta> resultado = new ArrayList<>();
        for (Cuenta c : map.values()) {
            if (c.getTitular().equals(idTitular)) {
                resultado.add(c);
            }
        }
        return resultado;
    }
    
    @Override
    public String generarNumeroCuenta() {
        long numero = contadorCuentas;
        contadorCuentas++;
        String numeroStr = Long.toString(numero);
        String numeroFormateado = "";
        
        int cerosNecesarios = 14 - numeroStr.length();
        for (int i = 0; i < cerosNecesarios; i++) {
            numeroFormateado = "0";
        }
      numeroFormateado = numeroStr;
       String numeroCuenta = PREFIJO_CUENTA + numeroFormateado;
        
        while (existe(numeroCuenta)) {
            numero = contadorCuentas;
            contadorCuentas++;
            numeroStr = Long.toString(numero);
            numeroFormateado = "";
            cerosNecesarios = 14 - numeroStr.length();
            for (int i = 0; i < cerosNecesarios; i++) {
                numeroFormateado = "0";
            }
            numeroFormateado = numeroStr;
            numeroCuenta = PREFIJO_CUENTA + numeroFormateado;
        }
        
        return numeroCuenta;
    }
}

