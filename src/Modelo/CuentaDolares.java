/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;

/**
 *
 * @author Home
 */
public class CuentaDolares extends Cuenta{
   public CuentaDolares(String numero, String titular, double saldoInicial) {
        super(numero, titular, saldoInicial);
    }
    
    public CuentaDolares(String numero, String titular) {
        super(numero, titular, 0.0);
    }
    
    @Override
    public TipoMoneda getTipoMoneda() {
        return TipoMoneda.DOLARES;
    }
    
    @Override
    public String getSimboloMoneda() {
        return "$";
    }
    
}
