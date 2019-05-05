/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interpreter;

/**
 *
 * @author patriciaryser-welch
 */
public class Result
{
 
    private final String runID;    // unique identifier
    private final double fitness;  // performance of best candidates solutions
    private final int generation;  // iterations 
    private final int eval;    // evalutions of solutions
    private final String type;     // type of algorithm
    private final long lengthTime; // miliseconds 
    private final int evalCounter; // number of evaluation
    private int cyclomaticMetrics; 

    private double processVolume;
    private double processLength;
    private double processDifficulty;
    /**
     * Get the value of evalLeft
     *
     * @return the value of evalLeft
     */
    public int getEvaluations() 
    {
        return eval;
    }

   /**
   * init array 
   * @param runID
   * @param fitness
   * @param generation
   * @param evalLeft 
   */
    public Result(String runID, String type, double fitness, int generation, int eval, int evalCounter, long timing) 
    {
        this.runID = runID;
        this.fitness = fitness;
        this.generation = generation;
        this.eval = eval;
        this.type = type;
        this.lengthTime = timing;
        this.evalCounter = evalCounter;
        this.cyclomaticMetrics = 0;
        this.processDifficulty =0;
        this.processVolume = 0;
        this.processLength = 0;          
    }

    public Result(String runID, double fitness, int generation, int eval, String type, long lengthTime, int evalCounter, int cyclomaticMetrics, double processVolume, double processLength, double processDifficulty)
    {
        this.runID = runID;
        this.fitness = fitness;
        this.generation = generation;
        this.eval = eval;
        this.type = type;
        this.lengthTime = lengthTime;
        this.evalCounter = evalCounter;
        this.cyclomaticMetrics = cyclomaticMetrics;
        this.processVolume = processVolume;
        this.processLength = processLength;
        this.processDifficulty = processDifficulty;
    }
    
    public int getCyclomaticMetrics()
    {
        return cyclomaticMetrics;
    }

    public double getProcessVolume()
    {
        return processVolume;
    }

    public double getProcessLength()
    {
        return processLength;
    }

    public double getProcessDifficulty()
    {
        return processDifficulty;
    }
    
   
    public long getLengthTime() 
    {
        return lengthTime;
    }

    public int getEvalCounter() 
    {
        return evalCounter;
    }
  
    
    /**
     * Get the value of generation
     *
     * @return the value of generation
     */
    public int getGeneration() 
    {
        return generation;
    }

    /**
     * Get the value of fitness
     *
     * @return the value of fitness
     */
    public double getFitness() 
    {
        return fitness;
    }


    /**
     * Get the value of runID
     *
     * @return the value of runID
     */
    public String getRunID() 
    {
        return runID;
    }
    public String getResultCSV()
    {
        String s;
        s= this.runID + ",";
        s+= this.type + ",";
        s+= this.generation + ",";
        s+= this.eval + ",";
        s+= this.evalCounter + ",";
        s+= this.lengthTime + ",";
        s+= this.fitness + "\n";
        return s;   
    }

    @Override
    protected Object clone() throws CloneNotSupportedException
    {
        return new Result (this.runID, this.type, this.fitness, this.generation, this.eval, this.evalCounter, this.lengthTime);
    }
    
    @Override public String toString()
    {
        String s = "<ITEM>";
        s+="<ID>" + this.runID + "</ID>";
        s+="<TYPE>" + this.type + "</TYPE>";
        s+="<GENERATION>" + this.generation + "</GENERATION>";
        s+="<EVAL>" + this.eval + "</EVAL>";
        s+="<EVAL_COUNTER>" + this.evalCounter + "</EVAL_COUNTER>";
        s+="<TIME>" + this.lengthTime + "</TIME>";
        s+="<FITNESS>" + this.fitness + "</FITNESS>";
        s += "</ITEM>\n";
        return s; 
    }

    public void setCyclomaticMetrics(int cyclomaticMetrics)
    {
        this.cyclomaticMetrics = cyclomaticMetrics;
    }

    public void setProcessVolume(double processVolume)
    {
        this.processVolume = processVolume;
    }

    public void setProcessLength(double processLength)
    {
        this.processLength = processLength;
    }

    public void setProcessDifficulty(double processDifficulty)
    {
        this.processDifficulty = processDifficulty;
    }
    
    
}
