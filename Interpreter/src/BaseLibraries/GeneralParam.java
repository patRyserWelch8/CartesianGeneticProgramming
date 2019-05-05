/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseLibraries;

/**
 *
 * @author patriciaryser-welch
 */
public abstract class GeneralParam 
{
    protected int PopSize = 10;
    protected int NoOfParents = 2;
    protected int NoOfOffsprings = 4;
    protected int Instances = 0;
    protected long seedValue;
    protected long CPUtime = 100000;
    protected int maxBaseIterations;
    protected double Threshold = 20;
    protected Double DepthOfSearch = 0.0; 
    private Double intensityOfMutation =0.0;
    private int evalBudget;
    private int MaxNumGenerations;

    /**
     * Get the value of MaxNumGenerations
     *
     * @return the value of MaxNumGenerations
     */
    public int getMaxNumGenerations()
    {
        return MaxNumGenerations;
    }

    /**
     * Set the value of MaxNumGenerations
     *
     * @param MaxNumGenerations new value of MaxNumGenerations
     */
    public void setMaxNumGenerations(int MaxNumGenerations)
    {
        this.MaxNumGenerations = MaxNumGenerations;
    }

   
   
    /**
     * Get the value of Threshold
     *
     * @return the value of Threshold
     */

    public GeneralParam() 
    {
    
    }

    public GeneralParam(long seed)
    {
        this.seedValue = seed;
    }
    /**
     * Get the value of CPUtime
     *
     * @return the value of CPUtime
     */
    public long getCPUtime() {
        return CPUtime;
    }

    /**
     * Set the value of CPUtime
     *
     * @param CPUtime new value of CPUtime
     */
    public void setCPUtime(long CPUtime) {
        this.CPUtime = CPUtime;
    }

    /**
     * Get the value of seedValue
     *
     * @return the value of seedValue
     */
    public long getSeedValue() {
        return seedValue;
    }

    /**
     * Set the value of seedValue
     *
     * @param seedValue new value of seedValue
     */
    public void setSeedValue(long seedValue) {
        this.seedValue = seedValue;
    }

    /**
     * Get the value of Instances
     *
     * @return the value of Instances
     */
    public int getInstances() 
    {
        return Instances;
    }

    /**
     * Set the value of Instances
     *
     * @param TSPInstances new value of Instances
     */
    public void setInstances(int TSPInstances) 
    {
        this.Instances = TSPInstances;
    }

    /**
     * Get the value of NoOfOffsprings
     *
     * @return the value of NoOfOffsprings
     */
    public int getNoOfOffsprings() 
    {
        return NoOfOffsprings;
    }

    /**
     * Set the value of NoOfOffsprings
     *
     * @param NoOfOffsprings new value of NoOfOffsprings
     */
    public void setNoOfOffsprings(int NoOfOffsprings) 
    {
        this.NoOfOffsprings = NoOfOffsprings;
    }

    /**
     * Get the value of NoOfParents
     *
     * @return the value of NoOfParents
     */
    public int getNoOfParents() 
    {
        return NoOfParents;
    }

    /**
     * Set the value of NoOfParents
     *
     * @param NoOfParents new value of NoOfParents
     */
    public void setNoOfParents(int NoOfParents) 
    {
        this.NoOfParents = NoOfParents;
    }

    /**
     * Get the value of PopSize
     *
     * @return the value of PopSize
     */
    public int getPopSize() 
    {
        return PopSize;
    }

    /**
     * Set the value of PopSize
     *
     * @param PopSize new value of PopSize
     */
    public void setPopSize(int PopSize) 
    {
        this.PopSize = PopSize;
    }

    
    public void setEvalBudget(int evalBudget)
    {
        this.evalBudget = evalBudget;   
    }
    /**
     * Get the value of maxBaseIterations
     *
     * @return the value of maxBaseIterations
     */
    public int getMaxBaseIterations() 
    {
        return maxBaseIterations;
    }

    /**
     * Set the value of maxBaseIterations
     *
     * @param maxBaseIterations new value of maxBaseIterations
     */
    public void setMaxBaseIterations(int maxBaseIterations) 
    {
        this.maxBaseIterations = maxBaseIterations;
        this.setEvalBudget((this.maxBaseIterations * this.NoOfOffsprings) + this.PopSize);
    }
    
    public void setMaxBaseIteration(int MaxBaseIteration, int noOfOperations)
    {        
        this.maxBaseIterations = MaxBaseIteration * noOfOperations;
    }

    public int getEvalBudget() 
    {
        return this.evalBudget;
    }

    public double getThreshold() 
    {
        return Threshold;
    }

    /**
     * Set the value of Threshold
     *
     * @param Threshold new value of Threshold
     */
    public void setThreshold(double Threshold) 
    {
        this.Threshold = Threshold;
    }
    
    /**
     * Get the value of DepthOfSearch
     *
     * @return the value of DepthOfSearch
     */
    public Double getDepthOfSearch()
    {
        return DepthOfSearch;
    }

    /**
     * Set the value of DepthOfSearch
     *
     * @param DepthOfSearch new value of DepthOfSearch
     */
    public void setDepthOfSearch(Double DepthOfSearch) 
    {
        this.DepthOfSearch = DepthOfSearch;
    }
    
     /**
     * Get the value of intensityOfMutation
     *
     * @return the value of intensityOfMutation
     */
    public Double getIntensityOfMutation()
    {
        return intensityOfMutation;
    }

    /**
     * Set the value of intensityOfMutation
     *
     * @param intensityOfMutation new value of intensityOfMutation
     */
    public void setIntensityOfMutation(Double intensityOfMutation) 
    {
        this.intensityOfMutation = intensityOfMutation;
    }

    

    
    abstract public int getInstanceOptima();
    /**
     * 
     * These abstracts functions return the functions set for CGP. 
     * It is defined for each problem specifically as they differ for each problem
     * in Hyflex. See chesc.jar for more information about the operators.
     * @return 
     */
    abstract public short[] getEEAFunctionSet();
  
    @Override
    public String toString() 
    {
        return "GeneralParam{" + "PopSize=" + PopSize + ", NoOfParents=" + NoOfParents + ", NoOfOffsprings=" + NoOfOffsprings + ", Instances=" + Instances + ", seedValue=" + seedValue + ", CPUtime=" + CPUtime + ", maxBaseIterations=" + maxBaseIterations + ", Threshold=" + Threshold + ", DepthOfSearch=" + DepthOfSearch + ", intensityOfMutation=" + intensityOfMutation + '}';
    }

    
}
