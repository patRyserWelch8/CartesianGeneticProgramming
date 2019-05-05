/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Hyper;

/**
 *
 * @author patriciaryser-welch
 */
public abstract class AbstractParameters
{
    
    private short noProgramInput;
    private short noNodesInputFeedforward;
    private short noNodesInputFeedback;
    private short noOutput = 1;
  
    private short  noNodes;  
    private short noHyperOffSpring;
    private short noHyperParents;
    private short[] functionSet;
    private short hyperMaxIterations; 
    private double mutationRate;
    private short maxCGPIteration;
    private double targetFitnessValue;
    private short noNodeBack;
    private short noNodesForward;
    private short[] conditionSet;
    private String FileName;
    
   
   

    /**
     * Get the value of FileName
     *
     * @return the value of FileName
     */
    public String getFileName()
    {
        return FileName;
    }

    /**
     * Set the value of FileName
     *
     * @param FileName new value of FileName
     */
    public void setFileName(String FileName)
    {
        this.FileName = FileName;
    }

    
    
    public AbstractParameters() 
    {
        this.noProgramInput =0;
        this.noNodesInputFeedforward =0;
        this.noOutput = 0;
        this.noNodes = 0;  
        this.noHyperOffSpring = 0;
        this.noHyperParents =  0;
        this.functionSet = new short[150];
        this.hyperMaxIterations = (short) 0;
        this.targetFitnessValue = 0;
    }

    /**
     * Get the value of conditionSet
     *
     * @return the value of conditionSet
     */
    public short[] getConditionSet()
    {
        return conditionSet;
    }

    /**
     * Set the value of conditionSet
     *
     * @param conditionSet new value of conditionSet
     */
    public void setConditionSet(short[] conditionSet)
    {
        this.conditionSet = conditionSet;
    }

    /**
     * Get the value of conditionSet at specified index
     *
     * @param index the index of conditionSet
     * @return the value of conditionSet at specified index
     */
    public short getConditionSet(int index)
    {
        return this.conditionSet[index];
    }

    /**
     * Set the value of conditionSet at specified index.
     *
     * @param index the index of conditionSet
     * @param conditionSet new value of conditionSet at specified index
     */
    public void setConditionSet(int index, short conditionSet)
    {
        this.conditionSet[index] = conditionSet;
    }


    /**
     * Get the value of noNodesForward
     *
     * @return the value of noNodesForward
     */
    public short getNoNodesForward()
    {
        return noNodesForward;
    }

    /**
     * Set the value of noNodesForward
     *
     * @param noNodesForward new value of noNodesForward
     */
    public void setNoNodesForward(short noNodesForward)
    {
        this.noNodesForward = noNodesForward;
    }

    /**
     * Get the value of noNodeBack
     *
     * @return the value of noNodeBack
     */
    public short getNoNodeBack()
    {
        return noNodeBack;
    }

    /**
     * Set the value of noNodeBack
     *
     * @param noNodeBack new value of noNodeBack
     */
    public void setNoNodeBack(short noNodeBack)
    {
        this.noNodeBack = noNodeBack;
    }
    /**
     * Get the value of hyperMaxIterations
     *
     * @return the value of hyperMaxIterations
     */
    public short getHyperMaxIterations() 
    {
        return hyperMaxIterations;
    }

    /**
     * Set the value of hyperMaxIterations
     *
     * @param hyperMaxIterations new value of hyperMaxIterations
     */
    public void setHyperMaxIterations(short hyperMaxIterations) 
    {
        this.hyperMaxIterations = hyperMaxIterations;
    }

  
    /**
     * Get the value of functionSet
     *
     * @return the value of functionSet
     */
    public short[] getFunctionSet()
    {
        return this.functionSet;
    }

    /**
     * Set the value of functionSet
     *
     * @param functionSet new value of functionSet
     */
    public void setFunctionSet(short[] functionSet) 
    {
        this.functionSet = functionSet;
    }

    /**
     * Get the value of functionSet at specified index
     *
     * @param index the index of functionSet
     * @return the value of functionSet at specified index
     */
    public void setFunctionSet(int index, short functionSet) 
    {
        this.functionSet[index] = functionSet;
    }
    
    public void setFunctionSetAll(short[] functionSet)
    {
        this.functionSet = new short[functionSet.length];
        System.arraycopy(functionSet, 0, this.functionSet,0 , functionSet.length);
        
    }


    /**
     * Get the value of noHyperParents
     *
     * @return the value of noHyperParents
     */
    public short getNoHyperParents() 
    {
        return noHyperParents;
    }

    /**
     * Set the value of noHyperParents
     *
     * @param noHyperParents new value of noHyperParents
     */
    public void setNoHyperParents(short noHyperParents) 
    {
        this.noHyperParents = noHyperParents;
    }
    public double getTargetFitnessValue() 
    {
        return targetFitnessValue;
    }

    public void setTargetFitnessValue(double targetFitnessValue) 
    {
        this.targetFitnessValue = targetFitnessValue;
    }


    /**
     * Get the value of noHyperOffSpring
     *
     * @return the value of noHyperOffSpring
     */
    public short getNoHyperOffSpring() 
    {
        return noHyperOffSpring;
    }

    /**
     * Set the value of noHyperOffSpring
     *
     * @param noHyperOffSpring new value of noHyperOffSpring
     */
    public void setNoHyperOffSpring(short noHyperOffSpring) 
    {
        this.noHyperOffSpring = noHyperOffSpring;
    }


    /**
     * Get the value of noNodes
     *
     * @return the value of noNodes
     */
    public short getNoNodes() 
    {
        return noNodes;
    }

    /**
     * Set the value of noNodes
     *
     * @param noNodes new value of noNodes
     */
    public void setNoNodes(short noNodes) 
    {
        this.noNodes = noNodes;
    }

    

    /**
     * Get the value of noOutput
     *
     * @return the value of noOutput
     */
    public short getNoOutput() 
    {
        return noOutput;
    }

    /**
     * Set the value of noOutput
     *
     * @param NoOutput new value of noOutput
     */
    public void setNoOutput(short NoOutput) 
    {
        this.noOutput = NoOutput;
    }


    /**
     * Get the value of noNodesInputFeedforward
     *
     * @return the value of noNodesInputFeedforward
     */
    public short getNoNodesInputFeedForward() 
    {
        return noNodesInputFeedforward;
    }

    /**
     * Set the value of noNodesInputFeedforward
     *
     * @param noNodesInput new value of noNodesInputFeedforward
     */
    public void setNoNodesInputFeedForward(short noNodesInput) 
    {
        this.noNodesInputFeedforward = noNodesInput;
    }


    /**
     * Get the value of noProgramInput
     *
     * @return the value of noProgramInput
     */
    public short getNoGraphInput() 
    {
        return noProgramInput;
    }

    /**
     * Set the value of noProgramInput
     *
     * @param noGraphInput new value of noProgramInput
     */
    public void setNoGraphInput(byte noProgramInput) 
    {
        this.noProgramInput = noProgramInput;
    }
    
     /**
     * Get the value of mutationRate
     *
     * @return the value of mutationRate
     */
    public double getMutationRate() {
        return mutationRate;
    }

    /**
     * Set the value of mutationRate
     *
     * @param mutationRate new value of mutationRate
     */
    public void setMutationRate(double mutationRate) {
        this.mutationRate = mutationRate;
    }

    /**
     * Get the value of maxCGPIteration
     *
     * @return the value of maxCGPIteration
     */
    public short getMaxCGPIteration() 
    {
        return maxCGPIteration;
    }

    /**
     * Set the value of maxCGPIteration
     *
     * @param maxCGPIteration new value of maxCGPIteration
     */
    public void setMaxCGPIteration(short maxCGPIteration) 
    {
        this.maxCGPIteration = maxCGPIteration;
    }

  
    /**
     * Get the value of noNodesInputFeedback
     *
     * @return the value of noNodesInputFeedback
     */
    public short getNoNodesInputFeedback()
    {
        return noNodesInputFeedback;
    }

    /**
     * Set the value of noNodesInputFeedback
     *
     * @param noNodesInputFeedback new value of noNodesInputFeedback
     */
    public void setNoNodesInputFeedback(short noNodesInputFeedback)
    {
        this.noNodesInputFeedback = noNodesInputFeedback;
    }
    
   
}
