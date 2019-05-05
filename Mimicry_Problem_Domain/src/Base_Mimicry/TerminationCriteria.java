/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base_Mimicry;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author patriciaryser-welch
 */
public class TerminationCriteria
{
    private int evalBudget;
    private int evalLeft;
    private double previousFitness;
    private double currentFitness;
    private double previousBestFitness;
    private double currentBestFitness;
    private int level;
    private double threshold;

    /**
     * constructor
     * @param evalBudget
     * @param evalUsed
     * @param previousFitness
     * @param currentFitness
     * @param previousBestFitness
     * @param currentBestFitness
     * @param level
     */
    public TerminationCriteria(int evalLeft, double previousFitness, double currentFitness, 
            double previousBestFitness, double currentBestFitness, int level, double threshold)
    {
        this.evalLeft = evalLeft;
        this.previousFitness = previousFitness;
        this.currentFitness = currentFitness;
        this.previousBestFitness = previousBestFitness;
        this.currentBestFitness = currentBestFitness;
        this.level = level;
        this.threshold = threshold;
    }
    
   /****
    * indicates whether or not all the evaluations have been used. 
    * @param evalBudget
    * @param evalUsed
    * @return true when evalLeft is larger or  equal than the eval budget
    */
   public boolean areAllEvalUsed()
   {
       return this.evalLeft <= 0;
   }
   
   public boolean hasFoundOptima()
   {
       return (this.currentFitness == 0.0)|| (this.currentBestFitness == 0.0);
   }
   
   public boolean hasFoundOptimaOrArrAllEvalUsed()
   {
        return this.hasFoundOptima()|| 
               this.areAllEvalUsed() ;
   }
   
   
   
   public boolean IsSolutionWorse()
   {
       return (this.currentFitness - this.previousFitness) > 0.0;
   }
   
   
   public boolean hasSomeEvaluationBeenUsed()
   {
       return this.areAllEvalUsed();
   }
   
   public boolean hasBestSolutionReachedAthreshold()
   {
       return this.currentBestFitness <= this.threshold;
   }   
}
