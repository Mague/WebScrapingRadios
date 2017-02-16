package utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import com.google.gson.Gson;

import models.Emisora;

public class JsonFileWriter {
	private ArrayList<Emisora> lista;
	public JsonFileWriter(Emisora lista,String url_path,String file_name) {
		Gson gson = new Gson();
		lista=lista;
		String json = gson.toJson(lista);
		//save("/home/enmanuel/Documentos/", "radios.json", json);
		save(url_path,file_name+".json",json);
		System.out.println(json);
		
	}
	
	private boolean save(String url_dir,String file_name,String data){
		File file = new File(url_dir+file_name);
		BufferedWriter bw;
		if(file.exists()){
			try {
				bw = new BufferedWriter(new FileWriter(file));
				bw.write(data);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}else{
			try {
				bw = new BufferedWriter(new FileWriter(file));
				bw.write(data);
				bw.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}
	public static void main(String[] args){
		//new JsonFileWriter();
	}
}
