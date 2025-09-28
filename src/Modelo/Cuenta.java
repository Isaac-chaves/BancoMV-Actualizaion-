/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

import java.util.Objects;

/**
 *
 * @author Home
 */
public abstract class Cuenta {
    
   
    private final String numero;
    private final String titular; 
    private double saldo;
    private Estado estado;
    
    protected Cuenta(String numero, String titular, double saldoInicial) {
        this.numero = Objects.requireNonNull(numero, "El número de cuenta no puede ser nulo");
        this.titular = Objects.requireNonNull(titular, "El titular no puede ser nulo");
        validarNumeroCuenta(numero);
        validarSaldoInicial(saldoInicial);
        this.saldo = saldoInicial;
        this.estado = Estado.ACTIVA;
    }

    public String getNumero() {
        return numero;
    }

    public String getTitular() {
        return titular;
    }

    public double getSaldo() {
        return saldo;
    }

    public Estado getEstado() {
        return estado;
    }

    public void setEstado(Estado estado) {
        this.estado = estado;
    }
    
    // Métodos abstractos
    public abstract TipoMoneda getTipoMoneda();
    public abstract String getSimboloMoneda();
    
    // Operaciones bancarias
    public void depositar(double monto) throws CuentaInactivaException {
        validarCuentaActiva();
        validarMontoPositivo(monto);
        saldo = saldo + monto;
    }
    
    public void retirar(double monto) throws SaldoInsuficienteException, CuentaInactivaException {
        validarCuentaActiva();
        validarMontoPositivo(monto);
        
        if (saldo < monto) {
            throw new SaldoInsuficienteException(
                "Saldo insuficiente. Saldo actual: " + getSimboloMoneda() + " " + saldo + 
                ", Monto solicitado: " + getSimboloMoneda() + " " + monto
            );
        }
        
        saldo = saldo - monto;
    }
    
    public void transferir(Cuenta cuentaDestino, double monto) 
            throws SaldoInsuficienteException, CuentaInactivaException, MonedaIncompatibleException {
   Objects.requireNonNull(cuentaDestino, "La cuenta destino no puede ser nula");
        if (!this.getTipoMoneda().equals(cuentaDestino.getTipoMoneda())) {
            throw new MonedaIncompatibleException("No se pueden transferir fondos entre cuentas de diferentes monedas"
            );
        }
        this.retirar(monto);
         try {
            cuentaDestino.depositar(monto);
        } catch (Exception e) {
            // Si falla el depósito, revertir el retiro
        try {
              this.depositar(monto);
            } catch (CuentaInactivaException ex) {
                // En caso extremo, al menos registrar el error
                System.out.println("Error crítico: No se pudo revertir transacción");
            }
            throw e;
        }
    }
    
    // Métodos de validación
    private void validarNumeroCuenta(String numero) {
        if (numero == null || !numero.matches("\\d{17}")) {
            throw new IllegalArgumentException("El número de cuenta debe tener exactamente 17 dígitos");
        }
    }
    
    private void validarSaldoInicial(double saldo) {
        if (saldo < 0) {
            throw new IllegalArgumentException("El saldo inicial no puede ser negativo");
        }
    }
    
    private void validarMontoPositivo(double monto) {
        if (monto <= 0) {
            throw new IllegalArgumentException("El monto debe ser positivo");
        }
    }
    
    private void validarCuentaActiva() throws CuentaInactivaException {
        if (estado != Estado.ACTIVA) {
            throw new CuentaInactivaException("La cuenta debe estar activa para realizar operaciones. Estado actual: " + estado);
        }
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Cuenta cuenta = (Cuenta) obj;
        return Objects.equals(numero, cuenta.numero);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(numero);
    }
    
    @Override
    public String toString() {
        return "Cuenta{numero='" + numero + "', titular='" + titular + 
               "', saldo=" + getSimboloMoneda() + " " + saldo + ", estado=" + estado + "}";
    }
}

