/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseLibraries;

import java.util.Map;
import java.util.TreeMap;

/**
 * this  class extend the probelm domain class from Hyflex. 
 * It is required so that all the problem domain classes can be adapted 
 * to Plug-and-play Hyper-heuristics.
 * @author patriciaryser-welch
 */
public class PopulationManagement 
{
    protected final int myPopSize; //Population size without offspring
    protected int[] parents; // parents address in the memory
    protected int NoOffsprings; // No of offsprings Need a minimum of 2 for crossovers.
    protected Map<Integer,Double[]> startFitness;
    protected double Threshold = 20.0; // Threshold used for restart of pop.
    protected double previousMean;  
    
    public PopulationManagement( int myPopSize, int NoOffsprings, int noParents, int Threshold ) 
    {
        this.myPopSize = myPopSize;
        this.NoOffsprings = NoOffsprings;
        this.parents = new int[noParents];
        this.Threshold = Threshold;
        this.startFitness = new TreeMap<>();
    }
    
    public boolean AreAllSolutionWorseOrTheSame(Integer loopID,Double[] parentFitness, Double[] offspringFitness)
    {
        boolean isWorse = true;
        Double[] Fitness = new Double[offspringFitness.length + parentFitness.length];
        if (!this.startFitness.containsKey(loopID)) // first 
        {
            System.arraycopy(parentFitness, 0, Fitness, 0, parentFitness.length);
            System.arraycopy(offspringFitness, 0, Fitness, parentFitness.length, offspringFitness.length);
            this.startFitness.put(loopID, Fitness);
            isWorse = false;
        }
        else 
        { // retrieve fitness from the db
           System.arraycopy(this.startFitness.get(loopID), 0, Fitness, 0, this.startFitness.get(loopID).length);
        }
        
        if (isWorse)
        {
            for (int i = 0; i < parentFitness.length; i++)
            {
                if (parentFitness[i] < Fitness[i])
                {
                    isWorse = false;
                }
            }
        
             if (isWorse)
             {
                for (int i = 0; i < offspringFitness.length; i++)
                {
                    if (offspringFitness[i] < Fitness[i + parentFitness.length])
                    {
                        isWorse = false;
                    }
                } 
             }
        }
            
        return isWorse;
    }

    public void deleteStartFitness(int loopID)
    {
        if (this.startFitness.containsKey(loopID))
        {
            this.startFitness.remove(loopID);
        }
    }
  
    /*****
     * get for PopSize
     * @return
     */
    public int getPopSize() 
    {
        return myPopSize;
    }

    /*****
     * get for No of offsprings
     * @return no of offsprings
     */
    public int getNoOffsprings() 
    {
        return this.NoOffsprings;
    }

    public int[] getParents()
    {
        return this.parents;
    }
}
