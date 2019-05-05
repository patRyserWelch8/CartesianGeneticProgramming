/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base_MC;

/**
 *
 * @author patriciaryser-welch
 */
public class MC_Param extends BaseLibraries.GeneralParam
{
    
    private double MutationRateUniform;
    private int noCandidates;
    private double threshold;

    /**
     * Get the value of threshold
     *
     * @return the value of threshold
     */
    public double getThreshold()
    {
        return threshold;
    }

    /**
     * Set the value of threshold
     *
     * @param threshold new value of threshold
     */
    public void setThreshold(double threshold)
    {
        this.threshold = threshold;
    }


    /**
     * Get the value of noCandidates
     *
     * @return the value of noCandidates
     */
    public int getNoCandidates()
    {
        return noCandidates;
    }

    /**
     * Set the value of noCandidates
     *
     * @param noCandidates new value of noCandidates
     */
    public void setNoCandidates(int noCandidates)
    {
        this.noCandidates = noCandidates;
    }

 

    /**
     * Get the value of depthOfSearch
     *
     * @return the value of depthOfSearch
     */
   

    

    /**
     * Get the value of MutationRateUniform
     *
     * @return the value of MutationRateUniform
     */
    public double getMutationRateUniform()
    {
        return MutationRateUniform;
    }

    /**
     * Set the value of MutationRateUniform
     *
     * @param MutationRateNonUniform new value of MutationRateUniform
     */
    public void setMutationRateUniform(double MutationRateNonUniform)
    {
        this.MutationRateUniform = MutationRateNonUniform;
    }


    @Override
    public int getInstanceOptima()
    {
        return 0;
    }

    @Override public short[] getEEAFunctionSet()
    {
        short[] functionSet = new short[1];
        
        functionSet[0] = 0; //crossOverOnePoint
       
       
        return functionSet;
    } 
    public short[] getEMHFunctionSet()
    {
        short[] functionSet = new short[9];
        
        functionSet[0] = 0; 
        functionSet[1] = 1; 
        functionSet[2] = 2; 
        functionSet[3] = 3; 
        functionSet[4] = 4; 
        functionSet[5] = 5; 
        functionSet[6] = 6; 
        functionSet[7] = 7; 
        functionSet[8] = 13;
        return functionSet;
    }
    public short[] getConditionSet()
    {
        short[] conditionSet = new short[7];
        
        conditionSet[0] = 0; // isEvalBudgetAllUsed()
        conditionSet[1] = 1; // isEvalBudgetAllsUsedinSubset1()
        conditionSet[2] = 2;  // isEvalBudgetAllsUsedinSubset2()
        conditionSet[3] = 3;  // isLessThanHalfOfBudgetUsed() || hasAConstantGradientForAWhile
        conditionSet[4] = 4;  // // isEvalBudgetAllUsed() || hasFoundOptima();
        conditionSet[5] = 6;
        conditionSet[6] = 7;
     
        return conditionSet;
    }  
    
  
     
}
