/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 *
 * @author patriciaryser-welch
 */
public class AbstractInterpreter 
{
    protected List<Double> baseFitnessValues;
    protected  Double hyperFitnessValue; // to review
    protected final int baseMaxIterations; // in param
    protected Results results;
    protected int noOfTestRun;
    protected Map<String,Double> algorithms;
    
    
    public AbstractInterpreter(int baseMaxIterations, int noOfTestRun)
    {
        this.hyperFitnessValue = Double.POSITIVE_INFINITY;
        this.baseMaxIterations = baseMaxIterations;
        this.baseFitnessValues = new ArrayList<>();
        this.results = new Results();
        this.noOfTestRun = noOfTestRun;
    }
   
    /*
    /****
     * Execute the algorithm. It is the link between a CGP graph of anytypes to the base problem.
     * @param output 
     */
     public void ExecuteOnceAlgorithm(int output)
     {
         
     }
    /****
     * Calculate the fitness value of an algorithm. 
     * @throws CloneNotSupportedException 
     */
    public  void calculateHyperFitnessValue() throws CloneNotSupportedException
    {
        
    }
    
    protected void addAlgorithm(String algorithm, Double Fitness)
    {
        if (!this.algorithms.containsKey(algorithm))
        {
            this.algorithms.put(algorithm, Fitness);
        }
    }
    
    protected double getAlgorithmValue(String algorithm)
    {
        return this.algorithms.get(algorithm);
    }
  

    
    /****
     * Convert the CGP graph in a human readable format.
     * @return the algorithm in a readable format. 
     */
    public String getAlgorithm(int output) 
    {
        return "";
    }
    
    /**
     * Get the value of hyperFitnessValue
     *
     * @return the value of hyperFitnessValue
     */
    public Double getHyperFitnessValue(int output) 
    {
      //  System.out.println ("get " + this.hyperFitnessValue);
        return hyperFitnessValue;
    }

    /**
     * Get the value of baseFitnessValues
     *
     * @return the value of baseFitnessValues
     */
    public List<Double> getAllBaseFitnessValues() 
    {
        return baseFitnessValues;
    }
    
    public Results getResults()
    {
        return this.results;
    }
    
    /**
     * Get the value of noOfTestRun
     *
     * @return the value of noOfTestRun
     */
    public int getNoOfTestRun()
    {
        return noOfTestRun;
    }

    /**
     * Set the value of noOfTestRun
     *
     * @param noOfTestRun new value of noOfTestRun
     */
    public void setNoOfTestRun(int noOfTestRun)
    {
        this.noOfTestRun = noOfTestRun;
    }

    public static class AcyclicInterpreter
    {

        public AcyclicInterpreter()
        {
        }
    }
    
}
