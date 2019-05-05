/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interpreter;

import Hyper.IterativeCoordinates;
import java.util.List;

/**
 *
 * @author patriciaryser-welch
 */
public abstract class IterativeInterpreter extends AbstractInterpreter
{

    protected IterativeCoordinates CGPGraph;
    protected IterativeCoordinates PreviousCGPGraph;

    public void setPreviousCGPGraph(IterativeCoordinates PreviousCGPGraph)
    {
        this.PreviousCGPGraph = PreviousCGPGraph;
    }

    public void setBaseFitnessValues(List<Double> baseFitnessValues)
    {
        this.baseFitnessValues = baseFitnessValues;
    }

    public void setHyperFitnessValue(Double hyperFitnessValue)
    {
        this.hyperFitnessValue = hyperFitnessValue;
    }

    public void setResults(Results results)
    {
        this.results = results;
    }

    public void setNoOfTestRun(int noOfTestRun)
    {
        this.noOfTestRun = noOfTestRun;
    }

    public IterativeCoordinates getPreviousCGPGraph()
    {
        return PreviousCGPGraph;
    }

    public List<Double> getBaseFitnessValues()
    {
        return baseFitnessValues;
    }

    public Double getHyperFitnessValue()
    {
        return hyperFitnessValue;
    }

    public int getBaseMaxIterations()
    {
        return baseMaxIterations;
    }

    public Results getResults()
    {
        return results;
    }

    public int getNoOfTestRun()
    {
        return noOfTestRun;
    }

    public IterativeCoordinates getCGPGraph()
    {
        return CGPGraph;
    }

    /**
     * Set the value of CGPGraph
     *
     * @param CGPGraph new value of CGPGraph
     */
    public void setCGPGraph(IterativeCoordinates CGPGraph, boolean flagCopy)
    {
        if (flagCopy)
        {
            this.PreviousCGPGraph = this.CGPGraph;
        }
        this.CGPGraph = CGPGraph;
    }

    
    public IterativeInterpreter(int baseMaxIterations, int noOfTestRun)
    {
        super(baseMaxIterations, noOfTestRun);
    }

    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return this;
    }
    
    
    
}
