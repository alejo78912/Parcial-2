/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

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
public class ClsGestionUsuario {
     ClsConexion conexion = new ClsConexion();

    public ClsGestionUsuario() {
    }

    

    public boolean guardar(String Nombre, String apellido, String cedula, String correoElectronico, String contraseña) {
        ClsUsuario usuario = new ClsUsuario(Nombre, apellido, cedula, correoElectronico,  contraseña);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("insert into usuario(Nombre,apellido,cedula,correoElectronico,contraseña)" 
                + "values ('" + usuario.getNombre() + " ',' " +
                usuario.getApellido() + " ',' " +
                usuario.getCedula() + " ',' " +
                usuario.getCorreoElectronico() + " ',"+
                usuario.getContraseña()+")");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    
    public ClsUsuario login(String correo, String contraseña ){
         
         List<ClsUsuario> temp = new ArrayList<ClsUsuario>();
         
         for(int i = 0; i<temp.size();i++){
             if( temp.get(i).getCorreoElectronico().equals(correo)
                    && temp.get(i).getContraseña().equals(contraseña))  {
                 try {
                    conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select cedula,Nombre,apellido,"
                            + "correoElectronico ,contraseña from usuario where correoElectronico and contraseña='" + correo + "'"));//consulta        

                            if (conexion.getResultadoDB().next()) {
                                temp.add((ClsUsuario) conexion.getResultadoDB().getObject("cedula"));
                                temp.add((ClsUsuario) conexion.getResultadoDB().getObject("nombre"));
                                temp.add((ClsUsuario) conexion.getResultadoDB().getObject("apellido"));
                                temp.add((ClsUsuario) conexion.getResultadoDB().getObject("correoElectronico"));
                                temp.add((ClsUsuario) conexion.getResultadoDB().getObject("contraseña"));
                            }
                            conexion.desconectar();//se desconecta de la base de datos                
                    } catch (SQLException ex) {            
                        Logger.getLogger(ClsGestionUsuario.class.getName())
                                .log(Level.SEVERE, null, ex);
                        conexion.desconectar();//se desconecta de la base de datos
                    }
                        return temp.get(i);
              }
         } 
         return null;
         }
    
    
    
    
    
    public List<String> buscar(String cedula) {
        
        List<String> temp = new ArrayList<String>();
        
        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select cedula,nombre,apellido,"
                            + "correoElectronico, contraseña from usuario where "
                            + "cedula='" + cedula + "'"));//consulta        

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("cedula"));
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("apellido"));
                temp.add(conexion.getResultadoDB().getString("correoElectronico"));
                temp.add(conexion.getResultadoDB().getString("contraseña")+"");
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {            
            Logger.getLogger(ClsGestionUsuario.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }
        return temp;
    }

    public boolean modificar(String Nombre, String apellido, String cedula, String correoElectronico, String contraseña) {
        ClsUsuario usuario = new ClsUsuario (Nombre, apellido, cedula, correoElectronico,  contraseña);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute
        ("update usuario set nombre='" + usuario.getNombre() + 
                "',apellido='" + usuario.getApellido() + "'," + 
                "correoElectronico=" + usuario.getCorreoElectronico()+ "'," + 
                 "contraseña=" + usuario.getContraseña()
                + " where cedula='" + usuario.getCedula()+"'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public boolean eliminar(String cedula) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute
        ("delete from usuario where cedula='" + cedula+"'");//consulta
            conexion.desconectar();//se desconecta de la base de datos          
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();//se desconecta de la base de datos          
            return false;
        }
    }

    public DefaultTableModel listar() {
        DefaultTableModel temporal;
        String nombreColumnas[] = {"Cedula", "Nombre", "Apellido", "Edad"};
        temporal = new DefaultTableModel(
                new Object[][]{}, nombreColumnas);
        conexion.conectar();
        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select cedula,nombre,apellido,"
                            + "edad from usuario order by cedula"));//consulta        
            while (conexion.getResultadoDB().next()) {
                temporal.addRow(new Object[]{
                    conexion.getResultadoDB().getString("cedula"),
                    conexion.getResultadoDB().getString("nombre"),
                    conexion.getResultadoDB().getString("apellido"),
                    conexion.getResultadoDB().getInt("edad")});
            }
            conexion.desconectar();//se desconecta de la base de datos                
        } catch (SQLException ex) {            
            Logger.getLogger(ClsGestionUsuario.class.getName()).
                    log(Level.SEVERE, null, ex);
            conexion.desconectar();//se desconecta de la base de datos
        }

        return temporal;

    }
}
