package requests;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Pages {
	private ArrayList<String> uList;
	private ArrayList<String> pages = new ArrayList<>();

	public Pages(ArrayList<String> uList) {
		this.uList = uList;
	}

	public ArrayList<String> getPages(String url) {
		Document dom = null;		
		try {
			dom = Jsoup.connect(url).userAgent(this.uList.get(new Random().nextInt(this.uList.size() - 1)))
					.referrer("http://www.google.com").get();
			Elements elements = dom.select(".countryflag-list > li > a");
			
			for (Element element : elements) {
				String a = element.attr("href");
				System.out.println(a);
				pages.add(a);
			}
		} catch (IOException e) {
			//e.printStackTrace();
			System.out.println("Error al obtener las paginas reintentando...");
			getPages(url);
		}

		return pages;
	}
}
