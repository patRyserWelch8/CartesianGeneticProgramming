/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BaseLibraries;

import java.util.Arrays;
import java.util.Random;

/**
 *
 * @author patriciaryser-welch
 */
public abstract class BinaryOperator  
{
  
   
    protected double MutationRateUniform;
    protected double DepthOfSearch;
  
    protected final Random r;  
    protected int noParents;
    protected int noOffsprings;
    protected BinaryIndividual[] parents;
    protected BinaryIndividual[] offsprings;
    protected int length;
    protected int generation;
    protected int noOfFlips;
    protected int noOfEvaluations;

    
    public BinaryOperator(double MutationRate, long timeStamp,int noParents, int noOffsprings, int length) 
    {   
        this.r = new Random(timeStamp);
        this.MutationRateUniform = MutationRate;
        this.noParents = noParents;
        this.noOffsprings = noOffsprings;
        this.length = length;
        this.noOfFlips = 0;
    }
    
    protected void evaluate() throws CloneNotSupportedException
    {
        
        this.evaluateOffsprings();
    }
    
    protected void evaluateOffsprings()
    {
        for (int i=0; i < this.offsprings.length;i++)
        {
            this.offsprings[i].Evaluate();
        }
    }
    
    protected void evaluateParents()
    {
        for (int i=0; i < this.parents.length;i++)
        {
            this.parents[i].Evaluate();
        }
    }
    
    
    
    
    public int getNoOfEvaluations()
    {
        return noOfEvaluations;
    }
    
    protected void setNoOfEvaluations(int noEval)
    {
        this.noOfEvaluations = noEval;
    }
    
/*  
    @Override
    public String toString()
    {
        String s = "OM_Operator{MutationRate=" + MutationRateUniform + ", noParents=" + noParents + ", noOffsprings=" + noOffsprings +  "length=" + length + "}\n";
        for (int i = 0; i < this.parents.length; i++)
        {
           s+= "\n parent  " + i + " \n" + this.parents[i].toString();
        }
        
        for (int i = 0; i < this.offsprings.length; i++)
        {
           s+= "\n offspring " + i + " \n" + this.offsprings[i].toString();
        }
        
        return s;
    }  

    public BinaryIndividual[] getParents()
    {
        return parents;
    }

    public BinaryIndividual[] getOffsprings()
    {
        return offsprings;
    }
    
    public void setNoOfEvaluations(int noOfEvaluations)
    {
        this.noOfEvaluations = noOfEvaluations;
    }
     /**
     * Get the value of noOfFlips
     *
     * @return the value of noOfFlips
     */
   
    
    
    public abstract void replaceRamdon() throws CloneNotSupportedException;
    public abstract void replaceWeakest() throws CloneNotSupportedException;
    public abstract void getBestParentsForReproduction() throws CloneNotSupportedException;
    public abstract void initPop() throws CloneNotSupportedException;;
 
}

