

import java.util.ArrayList;

import constants.UserAgents;
import database.Db;
import models.Emisora;
import models.Page;
import requests.Clasificacion;
import requests.Pages;
import requests.Radios;
public class Main {

	private static ArrayList<String> uList;
	private static ArrayList<String> listPages;
	private static ArrayList<Page> data;
	private static ArrayList<ArrayList> paises;
	public static void main(String[] args) {
		Db db = new Db();
        try {
        	String user = "";
        	String pass = "";
        	String nameDatabase ="";
        	String pageMaster = "";
			db.connect(user, pass, nameDatabase);
			UserAgents u=new UserAgents();
			uList=u.getArrayListUserAgent();
			Pages pages=new Pages(uList);
			Clasificacion cla=new Clasificacion(uList,db);
			listPages=pages.getPages(pageMaster);
			for (String urlSite : listPages) {
				db.addPaises(urlSite);
			}
			paises=db.getPaises();
			System.out.println(paises);
			for (int i = 0; i < paises.size(); i++) {
				@SuppressWarnings("unchecked")
				ArrayList<String> p = paises.get(i);
				String id = p.get(0).toString();
				String pais = p.get(1).toString();
				cla.getCategorias(id,pais);
			}
			System.out.println("Termino");
			db.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(data);
	}

}
