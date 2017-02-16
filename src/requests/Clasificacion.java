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
import models.Page;
import utils.JsonFileWriter;

public class Clasificacion {
	private ArrayList<String> uList;
	private ArrayList<String> categorias = new ArrayList<>();
	private Page page;
	private Db db;

	public Clasificacion(ArrayList<String> uList, Db db) {
		this.uList = uList;
		this.db=db;
	}

	public Page getCategorias(String id,String url) {
		Document dom = null;
		try {
			dom =null;
			dom = Jsoup.connect(url).userAgent(this.uList.get(new Random().nextInt(this.uList.size() - 1)))
					.referrer("http://www.google.co.ve").get();

			Element firstList = dom.select(".list").first();
			Elements elements = firstList.select(".list > ul > li > a");
			//Elements elements = dom.select(".list:first-child > ul > li > a");
			
			Elements title = dom.select("title");
			String aux = title.text();
			String[] ex = aux.split("|");
			String titleStr = ex[ex.length-1].trim();
			
			Radios radio = new Radios(uList,db,id);
			categorias = null;
			categorias =new ArrayList<>();
			for (Element element : elements) {
				String href = element.attr("href");
				String val = element.text().trim();
				//System.out.println(href);
				categorias.add(href);
			}
			ArrayList<Emisora> emisoras = new ArrayList<>();

			for (String string : categorias) {
				System.out.println("<>"+string);
				//emisoras.addAll(radio.getEmisoras(string, url));
				radio.getEmisoras(string, url);
			}

			page = new Page(titleStr, url, emisoras);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			System.out.println("Error al obtener las categorias reintentando...");
			getCategorias(id,url);
		}

		return page;
	}

}
