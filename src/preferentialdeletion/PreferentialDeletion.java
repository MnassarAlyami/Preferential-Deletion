/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package preferentialdeletion;
import java.util.ArrayList;

import org.jfree.ui.RefineryUtilities;

import java.util.*;

/***************************************************************
@author Mnassar Alyami
Implementation as described in the paper:
Link: https://www.sciencedirect.com/science/article/pii/S0020019006003632

Basic mathematical formulas that are not presented in the paper and the logical construction of random graph are inferred from previous implementations:
Helena Julie Arpudaraj (2018): https://github.com/HelenaJulie/-Preferential-deletion-in-dynamic-models-of-web-like-networks-implementation/blob/master/AlgoAssignment2.py
[Commit 6b447b3e5d5172b11e4028718a03f4da211f3b71]
Prashanth Kandhuri (2018): https://github.com/prashanth41/Preferential-Deletion/blob/master/preferential%20deletion.py 
[Commit 56f03c18bdfb247229a7e75dd3c668c6c70012af]

For graph plotting, it is required to add two required jar files into the project:
Jcommon
Jfreechart
***************************************************************/

public class PreferentialDeletion {

    /**
     * @param args the command line arguments
     */
    
    public static int i;
	
        //Total Degree
	public static int Deg;
	static ArrayList<Node> nodeList = new ArrayList<>();
	//Birth Probability
	static ArrayList<Integer> BProb = new ArrayList<>();
        //Death Probability
	static ArrayList<Integer> DProb = new ArrayList<>();
        // Cumulative Birth Prob 
	static ArrayList<Integer> CBProb = new ArrayList<>();
	
	static Random rand = new Random();
        
       // Return Length Method
       public static int len(ArrayList<?> list){
		return list.size();
	}

       //Cumulative Probability
       public static Node CumProb(){

		int Cum_BProb=0;
		
		for(int k = 0 ; k < len(nodeList); k++){
			Cum_BProb=Cum_BProb+BProb.get(k);
			CBProb.add(Cum_BProb);
		}
		double y=rand.nextInt(10);
		y=y/10;
		
                for(int k = 0; k < len(CBProb) ; k++){
			if (CBProb.get(k)>=y){
				Node node=nodeList.get(k);
				return node;
			}
			if (k==(len(CBProb)-1))
				return nodeList.get(k);
		}
		return null;
	}

	public static void Start(){
		i=1;
		nodeList.add(new Node(i));
		i=i+1;
		nodeList.get(0).getAdjacentnode().add(nodeList.get(0));
		nodeList.get(0).setNumOfEdges(1);
		Deg=1;
		BProb.add(1);
		DProb.add(1);
	}
        
        //Birth Method
	@SuppressWarnings("static-access")
	public static void Birthnode(Node nodeSelected){
		Node node1= new Node(i);
		nodeList.add(node1);
		Node.adjNode.add(nodeSelected);
		Node.numOfEdges=1; 
		Node.adjNode.add(node1);
		Node.numOfEdges=(Node.numOfEdges)+1; 
		i=i+1;
		Deg=Deg+2;   
                //Number of Nodes
		int n_Nodes=nodeList.size();
                
		//Calculating the Probability
		for(int k = 0 ; k < nodeList.size(); k++){
			BProb.add((nodeList.get(k).numOfEdges)/(Deg));
			DProb.add((n_Nodes-(nodeList.get(k).numOfEdges))/((n_Nodes*n_Nodes)-(Deg)));
		}
	}
        
        //Death Method
        @SuppressWarnings("static-access")
	public static void Deathnode(Node NodeSelected){
		int pos=nodeList.indexOf(NodeSelected);
		nodeList.remove(NodeSelected);
		Deg=Deg-(Node.adjNode).size();
		int length=nodeList.size();
		if(pos > 0){
			for(int j = 0; j < length ; j++){
				nodeList.get(j);
				if(Node.adjNode.contains(NodeSelected)){
					nodeList.get(j);
					Node.adjNode.remove(NodeSelected);
					nodeList.get(j);
					Node.numOfEdges=(nodeList.get(j).numOfEdges)-1;
					Deg=Deg-1;
				}
			}
			int numOfNodes=len(nodeList);
			if (numOfNodes==1){
				DProb.add(1);
				BProb.add(1);
			}else{
				//Calculating the Probability of all nodes again
				for(int k = 0 ; k < len(nodeList);k++){
					BProb.add((nodeList.get(k).numOfEdges)/(Deg));
					DProb.add((numOfNodes-(nodeList.get(k).numOfEdges))/((numOfNodes*numOfNodes)-(Deg)));
				}
			}

		}
	}
    
               
    public static void main(String[] args) {
        // TODO code application logic here
        
        int maxSteps = 20000;
        int startSteps=10000;
		
	nodeList = new ArrayList<>();	
        BProb = new ArrayList<>();
	DProb = new ArrayList<>();
		
		
	Start();
            //First Run
            ArrayList<Integer> numOfSteps= new ArrayList<>();
            ArrayList<Integer> numOfNodes= new ArrayList<>();
            ArrayList<Integer> numOfEdges= new ArrayList<>();
		
		for(int j = startSteps ; j < maxSteps ; j++){
			double x=rand.nextInt(10);
			x=x/10;
                        //p=0.6
			if(x <= 0.6){
				CBProb.removeAll(CBProb);
				Node NodeSelected=CumProb();
				Birthnode(NodeSelected);
			}else{
				int maxVal = Collections.max(DProb);
				int maxpos = DProb.indexOf(maxVal);
				Node NodeSelected=nodeList.get(maxpos);
				Deathnode(NodeSelected);
				int length=len(nodeList);
				if(length == 0){
					Start();
				}
			}
                    numOfSteps.add(j);
		    numOfNodes.add(len(nodeList));
		    numOfEdges.add(Deg);
		}
		
		nodeList = new ArrayList<>();
                BProb = new ArrayList<>();
		DProb = new ArrayList<>();
		
		
	//Second Run
	Start();
        
            ArrayList<Integer> numOfSteps2= new ArrayList<>();
            ArrayList<Integer> numOfNodes2= new ArrayList<>();
            ArrayList<Integer> numOfEdges2= new ArrayList<>();		
		
		for(int j = startSteps ; j < maxSteps ; j++){
			double x=rand.nextInt(10);
			x=x/10;
                        //p=0.75
			if(x <= 0.75){
				CBProb.removeAll(CBProb);
				Node NodeSelected=CumProb();
				Birthnode(NodeSelected);
			}else{
				int maxVal = Collections.max(DProb);
				int maxpos = DProb.indexOf(maxVal);
				Node NodeSelected=nodeList.get(maxpos);
				Deathnode(NodeSelected);
				int length=len(nodeList);
				if(length == 0){
					Start();
				}
			}
                    numOfSteps2.add(j);
		    numOfNodes2.add(len(nodeList));
		    numOfEdges2.add(Deg);
		}
		
                nodeList = new ArrayList<>();
		BProb = new ArrayList<>();
		DProb = new ArrayList<>();
		
		
	//Third Run
	Start();
        
            ArrayList<Integer> numOfSteps3= new ArrayList<>();
            ArrayList<Integer> numOfNodes3= new ArrayList<>();
            ArrayList<Integer> numOfEdges3= new ArrayList<>();
		
		for(int j = startSteps ; j < maxSteps ; j++){
			double x=rand.nextInt(10);
			x=x/10;
                        //p=0.9
			if(x <= 0.9){
				CBProb.removeAll(CBProb);
				Node NodeSelected=CumProb();
				Birthnode(NodeSelected);
			}else{
				int maxVal = Collections.max(DProb);
				int maxpos = DProb.indexOf(maxVal);
				Node NodeSelected=nodeList.get(maxpos);
				Deathnode(NodeSelected);
				int length=len(nodeList);
				if(length == 0){
					Start();
				}
			}
                    numOfSteps3.add(j);
		    numOfNodes3.add(len(nodeList));
		    numOfEdges3.add(Deg);
		}
		
    //Plots
        // #Nodes p=0.6
	LineChart chart1 = new LineChart("Graph 1","p=0.6",numOfSteps,numOfNodes,"Steps and Number of Nodes");
	chart1.pack();
	RefineryUtilities.centerFrameOnScreen( chart1 );          
	chart1.setVisible( true ); 
	
        // #Nodes p=0.75
	LineChart chart2 = new LineChart("Graph 2","p=0.75",numOfSteps2,numOfNodes2,"Steps and Number of Nodes");
	chart2.pack();
	RefineryUtilities.centerFrameOnScreen( chart2 );          
	chart2.setVisible( true );
	    
        // #Nodes p=0.9
	LineChart chart3 = new LineChart("Graph 3","p=0.9",numOfSteps3,numOfNodes3,"Steps and Number of Nodes");
	chart3.pack();
	RefineryUtilities.centerFrameOnScreen( chart3 );          
	chart3.setVisible( true );
	    
	    
	// #Edges p=0.6
	LineChart chart4 = new LineChart("Graph 4","p=0.6",numOfSteps,numOfEdges,"Steps and Number of Edges");
	chart4.pack();
	RefineryUtilities.centerFrameOnScreen( chart4 );          
	chart4.setVisible( true ); 
	
        // #Edges p=0.75
	LineChart chart5 = new LineChart("Graph 5","p=0.75",numOfSteps2,numOfEdges2,"Steps and Number of Edges");
	chart5.pack();
	RefineryUtilities.centerFrameOnScreen( chart5 );          
	chart5.setVisible( true );
	    
        // #Edges p=0.9
	LineChart chart6 = new LineChart("Graph 6","p=0.9",numOfSteps3,numOfEdges3,"Steps and Number of Edges");
	chart6.pack();
	RefineryUtilities.centerFrameOnScreen( chart6 );          
	chart6.setVisible( true );
	 
    }
    
}
