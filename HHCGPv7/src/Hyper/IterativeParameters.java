/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import Interpreter.IterativeInterpreter;

/**
 * Acyclic parameters. 
 * @author patriciaryser-welch
 */
public class IterativeParameters extends AbstractParameters
{
    private Interpreter.IterativeInterpreter Interpreter;
    
    private short numGenerationForMutation = 6;

    
    public void setNumGenerationForMutation(short numGenerationForMutation)
    {
        this.numGenerationForMutation = numGenerationForMutation;
    }

    public short getNumGenerationForMutation()
    {
        return numGenerationForMutation;
    }

    public void setInterpreter(IterativeInterpreter Interpreter)
    {
        this.Interpreter = Interpreter;
    }

    public IterativeInterpreter getInterpreter()
    {
        return Interpreter;
    }

    @Override public String toString()
    {
        return super.toString();
    }

    @Override protected Object clone() throws CloneNotSupportedException
    {
          IterativeParameters clone = new IterativeParameters();
          clone.setConditionSet(this.getConditionSet());
          clone.setFileName(this.getFileName());
          clone.setFunctionSet(this.getFunctionSet());
          clone.setHyperMaxIterations(this.getHyperMaxIterations());
          clone.setMaxCGPIteration(this.getMaxCGPIteration());
          clone.setMutationRate(this.getMutationRate());
          clone.setNoHyperOffSpring(this.getNoHyperOffSpring());
          clone.setNoHyperParents(this.getNoHyperParents());
          clone.setNoNodeBack(this.getNoNodeBack());
          clone.setNoNodes(this.getNoNodes());
          clone.setNoNodesForward(this.getNoNodesForward());
          clone.setNoNodesInputFeedForward(this.getNoNodesInputFeedForward());
          clone.setNoNodesInputFeedback(this.getNoNodesInputFeedback());
          clone.setNoOutput(this.getNoOutput());
          clone.setNumGenerationForMutation(this.getNumGenerationForMutation());
          clone.setTargetFitnessValue(this.getTargetFitnessValue());
          //missing interpreter
          return clone;
    }
    
    
    
}
