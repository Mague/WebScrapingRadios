package models;

public class Emisora {
	private String nombre;
	private String frecuencia;
	private String genero;
	private String estado;
	private String url;
	private String urlImagen;
	private String urlStream = "No Definido";
	private String pais;
	private String web;
	private String telefono;
	private String descripcion;
	public Emisora() {
		
	}
	public Emisora(String nombre, String frecuencia, String genero, String estado, String url, String urlImagen,
			String urlStream,String pais, String web, String telefono, String descripcion) {
		super();
		this.nombre = nombre;
		this.frecuencia = frecuencia;
		this.genero = genero;
		this.estado = estado;
		this.url = url;
		this.urlImagen = urlImagen;
		this.urlStream = urlStream;
		this.pais = pais;
		this.web = web;
		this.telefono = telefono;
		this.descripcion = descripcion;
	}

	public String getUrlStream() {
		return urlStream;
	}

	public void setUrlStream(String urlStream) {
		this.urlStream = urlStream;
	}

	public String getUrlImagen() {
		return urlImagen;
	}

	public void setUrlImagen(String urlImagen) {
		this.urlImagen = urlImagen;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(String frecuencia) {
		this.frecuencia = frecuencia;
	}

	public String getGenero() {
		return genero;
	}

	public void setGenero(String genero) {
		this.genero = genero;
	}

	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}
	public String getPais() {
		return pais;
	}
	public void setPais(String pais) {
		this.pais = pais;
	}
	public String getWeb() {
		return web;
	}
	public void setWeb(String web) {
		this.web = web;
	}
	public String getTelefono() {
		return telefono;
	}
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
}
