package requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import database.Db;
import models.Emisora;
import utils.JsonFileWriter;

public class Radios {
	private ArrayList<String> uList;
	private Db db;
	private String id;

	public static String html2text(String html) {
		return Jsoup.parse(html).text();
	}

	private void getStreamEmisora(String url) {
		Document x = null;		
		String c = "";
		try {
			x = Jsoup.connect(url).userAgent(this.uList.get(new Random().nextInt(this.uList.size() - 1)))
					.referrer("http://www.google.com").get();
			Elements elements = x
					.select(".lazsto-content-left > ul ");
			Elements elements2 = x.select(".lazsto > .lazsto-content-right > ul");
			String url_stream = "";
			//left panel
			String frecuencia = "";
			String genero = "";
			String estado = "";
			String telefono = "";
			String web = "";
			String name = x.select(".product-detail > .welcome > .heading > h2").text().trim();
			String img = x.select(".welcome > .rt_content >img").attr("src");
			String descripcion = x.select(".radio-desc").text().trim();
			System.out.println("Nombre: "+name);
			for(int i = 0; i < elements.size(); i++){
				Elements data_left = elements.get(i).select("ul > li");
				for(int j = 0; j < data_left.size(); j++){
					String label = data_left.get(j).select("span").text().trim();
					String value = data_left.get(j).select("li").text();
					value = value.substring(label.length()).trim();
					if(label.equalsIgnoreCase("Frecuencia:")){
						frecuencia = value;
					}else if(label.equalsIgnoreCase("Teléfono:")){
						telefono = value;
					}else if(label.equalsIgnoreCase("Provincia:")){
						estado = value;
					}
					System.out.println(label+"--"+value);
				}
			}
			//right panel
			for(int i = 0; i < elements2.size(); i++){
				Elements data_right = elements2.get(i).select("ul > li");
				for(int j = 0; j < data_right.size(); j++){
					String label = data_right.get(j).select("span").text().trim();
					String value = data_right.get(j).select("li").text();
					value = value.substring(label.length()).trim();
					if(label.equalsIgnoreCase("Género:")){
						genero = value;
					}else if(label.equalsIgnoreCase("Web:")){
						web = value;
					}
					System.out.println(label+"--"+value);
				}
			}
			String read = new String(x.outerHtml());
			String array[] = read.split("\n");
			for (int index = 0; index < array.length; index++) {
				String m = array[index];
				if (m.indexOf("mp3:") != -1) {
					try {
						c = m.substring(m.indexOf("\"") + 1, m.lastIndexOf("\""));
						url_stream = c;
					} catch (Exception e) {
					}
					if (c.length() == 0) {
						break;
					}
				}
			}
			if(url_stream.length()>0){
				Emisora emisora = new Emisora(name, frecuencia, genero, estado, url, img, url_stream,this.id,web,telefono,descripcion);
				System.out.println("Añadiendo: "+name);
				db.addEmisora(emisora);
			}else{
				System.out.println("No se pudieron obtener datos");
			}
		} catch (IOException e1) {
			System.out.println(e1.toString());
			if(e1.toString().indexOf("Status=404")!= -1 || e1.toString().indexOf("Status=400")!= -1){
				System.out.println("Not Found");
			}else{
				getStreamEmisora(url);
			}
		}		
	}

	public void getEmisoras(String url, String sitename) {
		Document x = null;
		try {
			x = Jsoup.connect(url).userAgent(this.uList.get(new Random().nextInt(this.uList.size() - 1)))
					.referrer("http://www.google.com").get();
			Elements elements = x
					.select(".rt_content > .product-list > .list-total > .list-left > .product-list > .list-total");
			Elements relements = elements.select(".list-total > .list-right");
			Elements infoEmisoras1 = relements.select(".list-right > a");
			Elements infoEmisoras2 = relements.select(".list-right > ul");
			for (int k = 0; k < infoEmisoras2.size(); k++) {
				System.out.println("Emisora: " + (k + 1));
				Element el1 = infoEmisoras1.get(k);
				String url_site = el1.select("a").attr("href").trim();
				System.out.println("Url_site: "+url_site);
				//url_site = url_site.replace(" ","%20");
				
				boolean exist = db.existEmisora(url_site,this.id);
				if(!exist){
					this.getStreamEmisora(url_site);
				}else{
					System.out.println("La emisora ya existe next");
				}		
			}
		} catch (Exception e1) {
			//e1.printStackTrace();
			System.out.println("Error al obtener la lista de emisoras");
			System.out.println("Reintentando");
			getEmisoras(url, sitename);
		}
	}

	public Radios(ArrayList<String> uList, Db db,String id) {
		this.uList = uList;
		this.db = db;
		this.id = id;
		
	}
}
