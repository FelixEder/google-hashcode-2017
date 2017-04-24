import java.util.ArrayList;
import java.util.Collections;

public class Endpoint {
	private int ID;
	private int latency;
	
	class CacheRelation implements Comparable<CacheRelation>{
		int ID, latency;
		
		CacheRelation(int ID, int latency){
			this.ID = ID;
			this.latency = latency;
		}
		
		public int compareTo(CacheRelation r){
			if(this.latency > r.latency)
				return -1;
			else if(this.latency == r.latency)
				return 0;
			else
				return 1;
		}
	}
	
	public boolean requestEmpty() {
		return requests.isEmpty();
	}
	
	public void removeRequest(int videoID) {
		for(int i = 0; i < requests.size(); i++){
			Request r = requests.get(i);
			if(r.ID == videoID) {
				requests.remove(i);
				return;
			}
		}
	}
	
	private ArrayList<CacheRelation> caches;
	private ArrayList<Request> requests;
	
	private int nCaches, added;
	
	public Endpoint(int ID, int latency, int nCaches){
		this.ID = ID;
		this.latency = latency;
		this.nCaches = nCaches;
		added = 0;
		caches = new ArrayList<CacheRelation>();
		requests = new ArrayList<Request>();
		this.latency = latency;
	}
	
	public int getBestCache(Video video){
		if(caches.isEmpty())
			return -1;
	
		for(int i = 0; i < caches.size(); i++){
			CacheRelation cr = caches.get(i);
			Cache c = Main.caches[cr.ID];
			if(c.canUploadVideo(video)){
				return cr.ID;
			}
		}
		
		return -1;
	}
	
	public int getDataCenterLatency(){
		return latency;
	}
	
	public void addCache(int ID, int latency){
		caches.add(new CacheRelation(ID, latency));
		Collections.sort(caches);
	}
	
	public void addRequest(int videoID, int nRequests){
		requests.add(new Request(videoID, nRequests));
	}
	
	public int getCacheLatency(int ID){
		for(int i = 0; i < caches.size(); i++){
			CacheRelation c = caches.get(i);
			if(c.ID == ID)
				return c.latency;
		}
		return -1;
	}
	
	public int getRequestNumber(int videoID){
		for(int i = 0; i < requests.size(); i++){
			Request r = requests.get(i);
			if(r.ID == ID)
				return r.amount;
		}
		return -1;
	}
	
	public int getnCaches() {
		return nCaches;
	}
	
	public Request getHighestVideoRequests() {
		Request maxRequest = new Request(0, 0);
		for(Request req: requests) {
			if(req.amount > maxRequest.amount)
				maxRequest = req;
		}
		return maxRequest;
	}
	
}
