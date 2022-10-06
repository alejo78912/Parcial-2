
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.ClsArticulo;
import Modelo.ClsConexion;
import Modelo.ClsUsuario;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author alejandro
 */
public class ClsGestionArticulo {
     ClsConexion conexion = new ClsConexion();

    public ClsGestionArticulo() {
    }
      
      
    
    public boolean guardar(String nombre, double precio, int cantidad, String descripcion, String categoria, String codigoProducto) {
        ClsArticulo art = new ClsArticulo(nombre, precio, 
                cantidad, descripcion,categoria, codigoProducto);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("insert into articulo(nombre,precio,cantidad,descripcion, categoria, codigoProducto) "
                + "values('" + art.getNombre() + "','" +
                art.getPrecio() + "','" +
                art.getDescripcion() + "'," +
                art.getCantidad()+","+art.getCodigoProducto()+","+art.getCategoria()+")");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

     
    
    public List<String> buscar(String codigo) {
        
        List<String> temp = new ArrayList<String>();
        
        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select nombre,precio,cantidad,descripcion, categoria, codigoProducto where "
                            + "codigoProducto='" + codigo + "'"));//consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("codigoProducto"));
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("fecha"));
                temp.add(conexion.getResultadoDB().getInt("deudaValor")+"");
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {            
            Logger.getLogger(ClsGestionArticulo.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificar(String codigoDueda, String nombre, String fecha, double deudaValor) {
        ClsDeuda deu = new ClsDeuda(codigoDueda, nombre, 
                fecha, deudaValor);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("update deuda set nombre='" + deu.getNombre() + 
                "',fecha='" + deu.getFecha() + "'," + 
                "deudaValor=" + deu.getDeudaValor()
                + " where codigoDeuda='" + deu.getCodigoDueda()+"'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminar(String codigoDueda) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute
        ("delete from deuda where codigoDeuda='" + codigoDueda+"'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listar() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Codigo", "Nombre", "fecha", "Valor"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigoDeuda,nombre,fecha,deudaValor from deuda order by codigoDeuda"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("codigoDeuda"),
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getString("fecha"),
                    conexion.getResultadoDB().getDouble("deudaValor")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {            
            Logger.getLogger(GestionDeuda.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }
    
    
    public double deuda(){
        double total = 0 ;
    
        conexion.conectar();
        try {conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select codigoDeuda,nombre,fecha,deudaValor from deuda order by codigoDeuda"));//consulta        
            while (conexion.getResultadoDB().next()) {

                    
               total+=conexion.getResultadoDB().getDouble("deudaValor");
                      
            
            conexion.desconectar();//se desconecta de la base de datos 
            return total;
            }
        } catch (SQLException ex) {            
            Logger.getLogger(GestionDeuda.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }        
          return 0;

    
        }
}
