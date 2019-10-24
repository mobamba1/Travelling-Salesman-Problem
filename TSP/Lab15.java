import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.concurrent.TimeUnit;

public class Lab15 {
	static double [][] distance = TSP.ReadArrayFile("TSP_48.txt", " ");
	
	//Optimal tour
	static ArrayList<Integer> OPT = TSP.ReadIntegerFile("TSP_48_OPT.txt");

	public static void main (String [ ] args){
		TSM OPTSolution = new TSM(OPT,distance.length);
		double OPTfitness = OPTSolution.TSMfitness(distance);
		System.out.println("Optimal Tour: " + OPTSolution.GetTour());//returns the sequence of tour
		System.out.println("Optimal Fitness: " + OPTfitness);//calculates opt fitness
		double MSTfitness = MST.Mfitness(distance);
		System.out.println("Global Optimal Fitness: " + MSTfitness);//calculates best global fitness
		int iter = 10;//iter of the algorithm
		int sequence = 100;//change the amount of times its run
		
		System.out.println("Random Mution Hill Climbing:");
		long HCSTime = System.nanoTime();
		double HCEfficiency = 0;
		for (int i = 1; i < iter+1; i++){
			TSM HC= RandomMutationHillClimbing(sequence,distance,false);
			System.out.println("Iteration " + i +  " : " + HC.Getfit());//returns fitness
		    System.out.println("Tour: " + HC.GetTour());//returns tour
			HCEfficiency = HC.Getfit();
			
		}
		long HCETime = System.nanoTime();
		long HCTime = HCETime - HCSTime;
		System.out.println("Final time was "+ ((double)HCTime / 1000000000.0)+" seconds");
//		System.out.println( HCEfficiency/iter);
		double OHCPercent = (OPTfitness/HCEfficiency) * 100;
		double HCPercent = (MSTfitness/HCEfficiency) * 100;//calculates time
		System.out.println("EfficiencyOPT: " + Math.round(OHCPercent));//calculates efficiency
		System.out.println("EfficiencyMST: " + Math.round(HCPercent));
		System.out.println("___________________________________________________");
		
		System.out.println("Random Restart:");
		long RRHCSTime = System.nanoTime();
		double RRHCEfficiency = 0;
		for (int i = 1; i < iter + 1; i++){
			System.out.println("Iteration: "+ i );
			TSM RRHC = RandomRestart(sequence, 10, distance, false);
          System.out.println("Tour: " + RRHC.GetTour());
          RRHCEfficiency =  RRHC.Getfit();
		}
		long RRHCETime = System.nanoTime();
		long RRHCTime = RRHCETime - RRHCSTime;
		System.out.println("Final time was "+ ((double)RRHCTime / 1000000000.0)+" seconds");
//		System.out.println(RRHCEfficiency/iter);
		double ORRHCPercent = (OPTfitness/RRHCEfficiency) * 100;
		double RRHCPercent = (MSTfitness/RRHCEfficiency) * 100;
		System.out.println("EfficiencyOPT: " + Math.round(ORRHCPercent));
		System.out.println("EfficiencyMST: " + Math.round(RRHCPercent));
		System.out.println("___________________________________________________");
		
		System.out.println("Simulated Annealing:");
		long SASTime = System.nanoTime();
		double SAEfficiency = 0;
		for (int i = 1; i < iter +1; i++){
			
			TSM SA = SimulatedAnnealing(sequence,distance,false,10,0.0003);
			System.out.println("Iteration: " + i);
			System.out.println("Tour: " + SA.GetTour());
			System.out.println( SA.Getfit());
			SAEfficiency =  SA.Getfit();
			
		}
		long SAETime = System.nanoTime();
		long SATime = SAETime - SASTime;
		System.out.println("Final time was "+ ((double)SATime / 1000000000.0)+" seconds");
//		System.out.println(SAEfficiency/iter);
		double OSAPercent = (OPTfitness/SAEfficiency) * 100;
		double RSAPercent = (MSTfitness/SAEfficiency) * 100;
		System.out.println("EfficiencyOPT: " + Math.round(OSAPercent));
		System.out.println("EfficiencyMST: " + Math.round(RSAPercent));
		System.out.println("___________________________________________________");
		
		System.out.println("Stochastic Hill Climbing:");
		long SHSTime = System.nanoTime();
		double SHCEfficiency = 0;
		for (int i = 1; i < iter+1; i++){
			TSM SHC = StachosticHillClimbing(sequence,distance,false,3);
			System.out.println("Iteration: " + i);
			System.out.println("Tour: " + SHC.GetTour());
			System.out.println(SHC.Getfit());
			SHCEfficiency =  SHC.Getfit();
			
			
		}
		long SHETime = System.nanoTime();
		long SHTime = SHETime - SHSTime;
		System.out.println("Final time was "+ ((double)SHTime / 1000000000.0)+" seconds");
//		System.out.println(SHCEfficiency/iter);
		double OSHPercent = (OPTfitness/SHCEfficiency) * 100;
		double RSHPercent = (MSTfitness/SHCEfficiency) * 100;
		System.out.println("EfficiencyOPT: " + Math.round(OSHPercent));
		System.out.println("EfficiencyMST: " + Math.round(RSHPercent));
	}
	
	//Random Mutation Hill Climbing
	private static TSM RandomMutationHillClimbing(int iter, double [][] distance, boolean print){
		int a = distance.length;//total number of cities
		TSM RMHC = new TSM(a);//start at a random place in the cities 
		for(int i = 0; i < iter; i++){//for loop to the number of iterations
			TSM old = new TSM(RMHC.GetTour(),a);//copy the solution into old
			double oldfitness = old.TSMfitness(distance);//evaluates the fitness of old
			RMHC.SmallChange();//small changes which is swapping 2 cities
			TSM New = new TSM(RMHC.GetTour(),a);
			double newfitness = New.TSMfitness(distance);//evaluate the fitness with the new swapped cities
			
			if (newfitness > oldfitness){//compares the fitness 
				RMHC = old;
			}
			else{
				RMHC = New;
			}
			if (print){
				System.out.println("Iteration: " + (i+1) + ": " + "Fitness: " + oldfitness);//prints the changes
			}
		}
		return (RMHC);//returns the best solution
		
	}
	//Random Restart
	private static TSM RandomRestart( int iter, int iterRestart, double [][] distance, boolean print){
		ArrayList<TSM> Restart = new ArrayList<>();
		for (int i = 0; i < iterRestart; i ++){//runs how many restart you want
			Restart.add(RandomMutationHillClimbing(iter,distance,print));//runs the algorithm using the parameters, gets the best one and adds it to the array
		}
		return RestartSolution(Restart);//array is passed 
		
	}
	
	private static TSM RestartSolution(ArrayList<TSM> arrayRestart){
		ArrayList<Double> fitnessRestart = new ArrayList<>();
		for (int i = 0; i< arrayRestart.size(); i++){
			fitnessRestart.add(arrayRestart.get(i).Getfit());//gets the current fitness and stores into array
		}
		int min = minIndex(fitnessRestart);//gets the min value 
		
		return arrayRestart.get(min);//returns the lowest fitness
		
	}
	
	public static int minIndex (ArrayList<Double> fitnessRestart) {//calculates the min
		System.out.println(Collections.min(fitnessRestart));//prints out the min of the array
		  return fitnessRestart.indexOf (Collections.min(fitnessRestart)); }
	
	
	//Simulated Annealing 
	private static TSM SimulatedAnnealing(int iter, double[][] distance, boolean print,double temperature,double cooling){
		int a = distance.length;
		TSM SA = new TSM(a);
		double currentT = temperature;
		for(int i = 0; i < iter; i++){
			TSM old = new TSM(SA.GetTour(),a);//copy the solution into old
			double oldfitness = old.TSMfitness(distance);//evaluates the fitness of old
			SA.SmallChange();//small changes which is swapping 2 cities
			TSM New = new TSM(SA.GetTour(),a);
			double newfitness = New.TSMfitness(distance);//evaluate the fitness with the new swapped cities
			
			if (newfitness > oldfitness){
				double pr = PR(newfitness,oldfitness, currentT);//checks acceptance rate
				if(pr < CS2004.UR(0, 1)){
					SA= old;
				}else{
					SA=New;
				}
			}
			if(print){
				System.out.println("Iteration: " + (i+1) + " Fitness: " + oldfitness);
			}
			//can use any of these but middle is the best
//			currentT *=coolingRate (temperature, i);
			currentT *= coolingRate1(cooling);
//			currentT = cooling * currentT;

		}
		
		return (SA);
		
	}
	
	private static double PR(double Newfitness, double oldfitness, double temperature){
		
		return (Math.exp(-(Math.abs(oldfitness-Newfitness)/temperature)));
		
	}
	
	private static double coolingRate(double temperature, int iter){
		return Math.exp((Math.log(Math.pow(10, -100))-Math.log(temperature))/iter);
	}
	
	private static double coolingRate1(double cooling){
		
		return 1 - cooling;
	}
	
	//Stochastic Hill Climbing
	private static TSM StachosticHillClimbing(int iter, double[][] distance, boolean print,double temp){
		int a = distance.length;
		TSM SHC = new TSM(a);
//		ArrayList<Double> array = new ArrayList<Double>();
		for(int i = 0; i < iter; i++){
			TSM old = new TSM(SHC.GetTour(),a);//copy the solution into old
			double oldfitness = old.TSMfitness(distance);//evaluates the fitness of old
			SHC.SmallChange();//small changes which is swapping 2 cities
			TSM New = new TSM(SHC.GetTour(),a);
			double newfitness = New.TSMfitness(distance);//evaluate the fitness with the new swapped cities
			
			if (newfitness > oldfitness){
				double pr = PR(newfitness,oldfitness, temp);//acceptance rate
				if(pr < CS2004.UR(0, 1)){
					SHC= old;
				}else{
					SHC=New;
				}
			}
			if (print){
				System.out.println("Iteration: " + (i+1) + " " + "Fitness: " + oldfitness);
			}
		}
		
		return (SHC);
		
	}
	
	
}
