import java.util.ArrayList;
import java.util.Collections;

public class TSM {
	
	private ArrayList<Integer> Tour;
	private double fitness;
	
	public TSM(ArrayList<Integer> array, int cities){//array of the distances, number of cities
		if(array.size() == 0){
			Tour = RandomCities(cities);//creates cities
			
		}else{
			Tour = (ArrayList<Integer>) array.clone();//stores into array copy of the 
		}
	}
	
	private ArrayList<Integer> RandomCities(int n) {
		Tour = new ArrayList<>();// new tour because its empty
		
		for (int i = 0; i < n-1; i++){//for each distance a new city is made, till no more distance, distance will be the same amount of cities
			Tour.add(i);
		}
		Collections.shuffle(Tour);//shuffles the cities
		return Tour;
		
	}
	
	public TSM(int n){
		Tour = RandomCities(n);//saves all the cities to tour
	}
	//D = distance
	//T = tour.size
	//N = tour that is visited
	public double TSMfitness(double[][] distance){
		double fit = 0;
		int a = Tour.size();
		for (int i = 0; i < a-1; i++ ){
			int city1 = Tour.get(i);//current position	
			int city2 = Tour.get(i + 1);//neighbour position 
			fit = fit + distance[city1][city2];//calculate distance between cities and stacks it
		}
		int start = Tour.get(0);//starting city	
		int end = Tour.get(a-1);//end 
		fit = fit + distance[start][end];//calculates start and end city distance
		fitness = fit;
		return fit;
		
	}
	
	public double Getfit(){
		return fitness;//returns fitness
	}
	
	public ArrayList<Integer> GetTour(){
		return Tour;//returns current tour
					
	}
	
	public void SmallChange(){
		ArrayList<Integer> Swap = new ArrayList<Integer>();
		Swap = (ArrayList<Integer>)Tour.clone();//copy the array of the tour
		int a = Tour.size();
		int point1 = CS2004.UI(0, a-1);//random position 
		int point2 = CS2004.UI(0, a-1);
		Collections.swap(Tour, point1, point2);//swap position in tour
	}
	
	
	

}
