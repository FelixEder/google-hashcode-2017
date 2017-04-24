import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	static int V;
	static int E;
	static int R;
	static int C;
	static int X;
	public static Cache[] caches;
	static ArrayList<Endpoint> endpoints;
	
	static Video videos[];
	
	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		input();
		createObjects();
		sortEndpoints();
		cacheOptimization();
		output();
	}
	
	public static void input() throws FileNotFoundException{
		//Scanner sc = new Scanner(System.in);
		Scanner sc = new Scanner(new File("/home/felix/Dokument/Projekt/google-hashcode-2017/kittens.in"));
		
		String line[] = sc.nextLine().split("\\s");
		V = Integer.parseInt(line[0]);
		E = Integer.parseInt(line[1]);
		R = Integer.parseInt(line[2]);
		C = Integer.parseInt(line[3]);
		X = Integer.parseInt(line[4]);
		
		line = sc.nextLine().split("\\s");
		videos = new Video[V];
		for(int i = 0; i < V; i++)
			videos[i] = new Video(i, Integer.parseInt(line[i]));
		
		endpoints = new ArrayList<Endpoint>();
		for(int i = 0; i < E; i++){
			line = sc.nextLine().split("\\s");
			int latency = Integer.parseInt(line[0]);
			int n = Integer.parseInt(line[1]);
			Endpoint e = new Endpoint(i, latency, n);
			for(int j = 0; j < n; j++){
				line = sc.nextLine().split("\\s");
				int ID = Integer.parseInt(line[0]);
				latency = Integer.parseInt(line[1]);
				e.addCache(ID, latency);
			}
			endpoints.add(e);
		}
		
		
		for(int i = 0; i < R; i++){
			line = sc.nextLine().split("\\s");
			int videoID = Integer.parseInt(line[0]);
			int endpointID = Integer.parseInt(line[1]);
			int nRequests = Integer.parseInt(line[2]);
			
			endpoints.get(endpointID).addRequest(videoID, nRequests);
		}
	}
	
	public static void createObjects() {
		caches = new Cache[C];
		for(int id = 0; id < C; id++){
			caches[id] = new Cache(id, X);
		}
	}

	public static void sortEndpoints() {
		for(int i = 0; i < endpoints.size(); i++) {
			Endpoint ep = endpoints.get(i);
			if(ep.getnCaches() == 0){
				endpoints.remove(ep);
				i--;
			}
		}
	}
	
	public static void cacheOptimization() {
		while(!endpoints.isEmpty()) {
			findHighestLatency();
		}
	}
	
	public static void findHighestLatency() {
		int maxLatency = 0;
		Endpoint endpoint = null;
		Request request = null;
		for(Endpoint ep: endpoints) {
			int latency = ep.getDataCenterLatency();
			Request requests = ep.getHighestVideoRequests();
			if((latency * requests.amount) > maxLatency) {
				maxLatency = (latency * requests.amount);
				endpoint = ep;
				request = requests;
			}
		}
		int bestCache = endpoint.getBestCache(videos[request.ID]);
		if(bestCache != -1) {
			caches[bestCache].uploadVideo(videos[request.ID]);
		}
		endpoint.removeRequest(request.ID);
		if(endpoint.requestEmpty()) {
			endpoints.remove(endpoint);
		}
	}
	
	public static void output(){
		int used = 0;
		for(int i = 0; i < caches.length; i++){
			if(!caches[i].empty())
				used++;
		}
		
		System.out.println(used);
		
		for(int i = 0; i < caches.length; i++){
			Cache c = caches[i];
			
			ArrayList<Video> videos = c.getVideoList();
			String s = "" + caches[i].getId() + " ";
			for(int j = 0; j < videos.size(); j++){
				s += videos.get(j).getIndex() + " ";
			}
			System.out.println(s);
		}
	}
}
