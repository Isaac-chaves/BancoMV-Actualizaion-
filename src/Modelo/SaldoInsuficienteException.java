/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Home
 */
class SaldoInsuficienteException extends Exception {
    public SaldoInsuficienteException(String mensaje) {
        super(mensaje);
    }
}
  class CuentaInactivaException extends Exception {
    public CuentaInactivaException(String mensaje) {
        super(mensaje);
    }
}

  class MonedaIncompatibleException extends Exception {
    public MonedaIncompatibleException(String mensaje) {
        super(mensaje);
    }
}

