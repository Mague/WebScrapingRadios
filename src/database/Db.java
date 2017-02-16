package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import models.Emisora;

public class Db {
	private static Connection Conexion;

	public void connect(String user, String pass, String db_name) throws Exception {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Conexion = DriverManager.getConnection("jdbc:mysql://localhost:3306/" + db_name, user, pass);
			System.out.println("Se ha iniciado la conexión con el servidor de forma exitosa");
		} catch (ClassNotFoundException ex) {
			Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
		} catch (SQLException ex) {
			Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void close() {
		try {
			Conexion.close();
			System.out.println("Se ha finalizado la conexión con el servidor");
		} catch (SQLException ex) {
			Logger.getLogger(Db.class.getName()).log(Level.SEVERE, null, ex);
		}
	}

	public void addPaises(String urlSite) {
		try {
			if(!existPais(urlSite)){
				String Query = "INSERT INTO paises(nombre) VALUES(\"" + urlSite + "\")";
				Statement st = Conexion.createStatement();
				st.executeUpdate(Query);
			}
		} catch (SQLException ex) {
			// ex.printStackTrace();
		}
	}
	public boolean existPais(String urlSite){
		boolean exist = false;
		try {
			String Query = "SELECT id FROM paises WHERE nombre =?";
			PreparedStatement ps=Conexion.prepareStatement(Query);
			ps.setString(1, urlSite);
			ResultSet rs=ps.executeQuery();
			if(rs.next()){
				exist=true;
			}
		} catch (SQLException ex) {
			// ex.printStackTrace();
		}
		return exist;
	}
	public ArrayList<ArrayList> getPaises() {
		ArrayList<ArrayList> paises = new ArrayList<>();
		try {
			String Query = "SELECT * FROM paises";
			Statement st = Conexion.createStatement();
			java.sql.ResultSet resultSet;
			resultSet = st.executeQuery(Query);
			
			while (resultSet.next()) {
				ArrayList<String> pais = new ArrayList<>();
				pais.add(resultSet.getString("id"));
				pais.add(resultSet.getString("nombre"));
				paises.add(pais);
			}

		} catch (SQLException ex) {
			System.out.println("Error en la adquisición de datos");
		}
		return paises;
	}

	public void addEmisora(Emisora emisora) {
		try {
			if(!existEmisora(emisora)){
				String Query = "INSERT INTO emisoras(nombre,frecuencia,genero,estado,url,urlImagen,urlStream,pais,webSite,telefono,descripcion) "+
						"VALUES(?,?,?,?,?,?,?,?,?,?,?)";
				PreparedStatement ps = Conexion.prepareStatement(Query);
				ps.setString(1, emisora.getNombre());
				ps.setString(2, emisora.getFrecuencia());
				ps.setString(3, emisora.getGenero());
				ps.setString(4, emisora.getEstado());
				ps.setString(5, emisora.getUrl());
				ps.setString(6, emisora.getUrlImagen());
				ps.setString(7, emisora.getUrlStream());
				ps.setInt(8, Integer.parseInt(emisora.getPais()));
				ps.setString(9, emisora.getWeb());
				ps.setString(10, emisora.getTelefono());
				ps.setString(11, emisora.getDescripcion());
				ps.executeUpdate();
			}else{
				System.out.println("La emisora ya existe");
			}
			// System.out.println("Datos almacenados de forma exitosa");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}
	
	public boolean existEmisora(Emisora emisora){
		boolean exist = false;
		try {
			String Query = "SELECT id FROM emisoras WHERE nombre = ? "
					+ "AND frecuencia = ? AND estado = ? AND url = ? "
					+ "AND urlStream = ? AND pais= ?";
			PreparedStatement ps = Conexion.prepareStatement(Query);
			ps.setString(1, emisora.getNombre());
			ps.setString(2, emisora.getFrecuencia());
			ps.setString(3, emisora.getEstado());
			ps.setString(4, emisora.getUrl());
			ps.setString(5, emisora.getUrlStream());
			ps.setInt(6, Integer.parseInt(emisora.getPais()));
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				exist=true;
			}
			// System.out.println("Datos almacenados de forma exitosa");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return exist;
	}
	
	public boolean existEmisora(String url,String pais){
		boolean exist = false;
		try {
			String Query = "SELECT id FROM emisoras WHERE url = ? "
					+ " AND pais= ?";
			PreparedStatement ps = Conexion.prepareStatement(Query);
			ps.setString(1, url);
			ps.setInt(2, Integer.parseInt(pais));
			ResultSet rs= ps.executeQuery();
			if(rs.next()){
				exist=true;
			}
			// System.out.println("Datos almacenados de forma exitosa");
		} catch (SQLException ex) {
			ex.printStackTrace();
		}
		return exist;
	}
}
