/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Vista;

/**
 *
 * @author jprod
 */
public interface IVista<Entidad> {
    public static int YES_OPTION=0;
    public static int NO_OPTION=1;
    public void limpiar();
    public void cambiarEstadoCampos(boolean estado);
    default void habilitarCampos(){
        cambiarEstadoCampos(true);
    }
    default void deshabilitarCampos(){
        cambiarEstadoCampos(false);
    }
    public void mostrarDatos(Entidad entidad);
    public boolean confirmar(String msg, String titulo);
    public void mostrarMensaje(String msg, String titulo);
    public void mostrarError(String msg);
    public String solicitar(String msg, String titulo);
}
