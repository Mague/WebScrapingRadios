package models;

import java.util.ArrayList;

public class Page {
	private String title;
	private String url;
	private ArrayList<Emisora> categorys;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public ArrayList<Emisora> getCategorys() {
		return categorys;
	}
	public void setCategorys(ArrayList<Emisora> categorys) {
		this.categorys = categorys;
	}
	public Page(String title, String url, ArrayList<Emisora> categorys) {
		super();
		this.title = title;
		this.url = url;
		this.categorys = categorys;
	}
	
	
}
