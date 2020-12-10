package bgu.spl.mics.application;


import bgu.spl.mics.application.passiveObjects.*;
import bgu.spl.mics.application.services.*;
import com.google.gson.Gson;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.concurrent.CountDownLatch;

/** This is the Main class of the application. You should parse the input file,
 * create the different components of the application, and run the system.
 * In the end, you should output a JSON.
 */
public class Main {
	private  static long StartTime;
	private static Ewoks ewoks;
	private static Thread leia,hanSolo,c3po,lando,r2d2;
	public static CountDownLatch CDL;


	public static void main(String[] args) throws IOException {
		CDL=new CountDownLatch(4);
		Init("/home/spl211/IdeaProjects/StarWars/src/main/input.json");
		class toRun implements Runnable{
			public toRun(){}
			public void run(){
				Simulate();
				}
			}
		Thread Run=new Thread(new toRun());
		Run.start();
		try {
			Run.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		outGson("/home/spl211/IdeaProjects/StarWars/src/main/output.json");


	}


	public static void Init(String path) throws IOException {
		InputApp input= JsonInputReader.getInputFromJson(path);
		LeiaMicroservice l=new LeiaMicroservice(input.getAttacks());
		leia=new Thread(l);
		hanSolo=new Thread(new HanSoloMicroservice());
		c3po=new Thread(new C3POMicroservice());
		lando=new Thread(new LandoMicroservice(input.getLando()));
		r2d2=new Thread(new R2D2Microservice(input.getR2D2()));
		ewoks=Ewoks.getInstance(input.getEwoks());
		StartTime=System.currentTimeMillis();
	}

	public static void Simulate() {
		hanSolo.start();
		c3po.start();
		lando.start();
		r2d2.start();
		leia.start();
	}

	public static void outGson(String Path) {
		try {
			Gson outGson=new Gson();
			Diary diary=Diary.getInstance();
			Writer writer=new FileWriter(Path);
			outGson.toJson(diary,writer);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public static long getStartTime(){
		return StartTime;
	}
}