/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Interpreter;

import Hyper.AcyclicCoordinates;
import java.util.ArrayList;

/**
 * This abstract class is for interpreting abstract CGP graph
 * @author patriciaryser-welch
 */
public class AcyclicInterpreter extends AbstractInterpreter
{
    protected ArrayList<Integer> previousAlgorithm;
    protected ArrayList<Integer> currentAlgorithm;
    

    protected AcyclicCoordinates CGPGraph;

    /**
     * Get the value of CGPGraph
     *
     * @return the value of CGPGraph
     */
    public AcyclicCoordinates getCGPGraph()
    {
        return CGPGraph;
    }

    /**
     * Set the value of CGPGraph
     *
     * @param CGPGraph new value of CGPGraph
     */
    public void setCGPGraph(AcyclicCoordinates CGPGraph)
    {
        this.CGPGraph = CGPGraph;
    }

    
    public AcyclicInterpreter(int baseMaxIterations, int noOfTestRun)
    {
        super(baseMaxIterations, noOfTestRun);
    }
    
    protected void ExtractCurrentAlgorithm()
    {
        this.currentAlgorithm.clear();
        this.CGPGraph.moveToFirstNode(0);
        while (this.CGPGraph.isActiveNode())
        {
            this.currentAlgorithm.add(new Integer(this.CGPGraph.getCurrentNode().getFunction())); 
        }
    }
    
    protected void copyCurrentAlgorithmToPrevious()
    {
        this.previousAlgorithm.clear();
        this.previousAlgorithm.addAll(this.currentAlgorithm);
    }
    
    protected boolean isCurrentAlgorithmTheSameAsThePreviousOnes()
    {
        return this.previousAlgorithm.equals(this.currentAlgorithm);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException
    {
        return this;
    }
    
    
}
