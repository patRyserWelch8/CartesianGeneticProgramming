/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseLibraries;

import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author patriciaryser-welch
 */
public class BaseEvalManagement 
{
    
    private int EvalBudget;
    private final int evalOriginalBudget;
    private double AlgorithmDivider = 0.5;
    private Map<Integer,Integer[]> Dividers;

    

    public BaseEvalManagement(int evalOriginalBudget) 
    {
        this.EvalBudget = evalOriginalBudget;
        this.evalOriginalBudget = evalOriginalBudget;
        Dividers = new TreeMap<Integer,Integer[]>();
    }

    /**
     * Get the value of evalOriginalBudget
     *
     * @return the value of evalOriginalBudget
     */
    public int getEvalOriginalBudget() 
    {
        return evalOriginalBudget;
    }


    /**
     * Get the value of EvalBudget
     *
     * @return the value of EvalBudget
     */
    public int getEvalBudget() 
    {
        return EvalBudget;
    }

    
    public int getCurrentEval()
    {
        return this.evalOriginalBudget - this.EvalBudget;
    }
    /**
     * reduce the value of EvalBudget
     *
     * @param EvalBudget new value of EvalBudget
     */
    public void reduceEvalBudget(int noEval) 
    {
        this.EvalBudget -= noEval; 
    }

    /***
     *  Check that all the eval budget is used
     * @return true when all evaluations are used.
     */
    public boolean AreAllEvalUsed()
    {
        return(this.EvalBudget <= 0);  
    }
    
    /****
     * Indicate whether or not it is in the range [0,evalOriginalBudget * divider)the subset2 of evaluations
     * @return false if the evalBudget is in the subset2. Otherwise true
     */
    public boolean AreAllEvalUsedInSubset2()
    {
       //  return this.AreAllEvalUsedInSubset1() & this.AreAllEvalUsed();
        return (this.EvalBudget > (this.evalOriginalBudget * this.AlgorithmDivider)) || this.AreAllEvalUsed();
    }
    
    public boolean AreAllEvalUsedInSubsets(int loopID)
    {
        //Integer[] limits = new Integer[2];
        return this.AreAllEvalUsedInSubsets(loopID, (int)(this.getMaxOfEvaluations() * Math.random()));
      
       /* if (!this.Dividers.containsKey(loopID))
        {
            limits[0] = this.EvalBudget;
            limits[1] = (int) (this.EvalBudget - (this.getMaxOfEvaluations() * Math.random()));;
            addDivider(limits[0], limits[1], loopID);
            System.out.println ("limit 1 "  + limits[1]);
        }
        else
        {
            limits = this.Dividers.get(loopID);
        }
        
        return this.EvalBudget < limits[1];*/
    }

   
    public boolean AreAllEvalUsedInSubsets(int loopID, int noOfEvaluations)
    {
        Integer[] limits = new Integer[2];
      
        if (!this.Dividers.containsKey(loopID))
        {
            limits[0] = this.EvalBudget;
            limits[1] = (int) (this.EvalBudget - noOfEvaluations);
            addDivider(limits[0], limits[1], loopID);
            System.out.println ("limit 1 "  + limits[1]);
        }
        else
        {
            limits = this.Dividers.get(loopID);
        }
        System.out.println("***" + limits[0] + "- " + limits[1] + " -" + (this.EvalBudget <= limits[1]));
        return this.EvalBudget <= limits[1];
    }

    
    private void addDivider(Integer Start, Integer End, int loopID)
    {
        Integer[] limits = new Integer[2];
      
        limits[0] = Start;
        limits[1] = End;
        this.Dividers.put(loopID, limits);
    }
    
    public void deleteDivider(int loopID)
    {
        if (this.Dividers.containsKey(loopID))
        {
            if(this.EvalBudget < this.Dividers.get(loopID)[1])
            {
                this.Dividers.remove(loopID);
            }
        }
    }
    
    private Integer getMaxOfEvaluations()
    {
        Integer minToReach = this.EvalBudget;
        if (this.Dividers.size() > 0)
        {
            Object[] keys = this.Dividers.keySet().toArray();
            int i = keys.length-1;
            boolean hasPassed = this.EvalBudget <= minToReach;
            //Find the 
            while (hasPassed & i >= 0)
            {
                minToReach = this.Dividers.get((int)keys[i])[1];
                hasPassed = this.EvalBudget <= minToReach;
                if (hasPassed)
                {
                    this.Dividers.remove(keys[i]);
                    i--; 
                }
            }
        }
        else 
        {
            minToReach = 0;
        }
        
        return this.EvalBudget - minToReach;
    }
    /***
     * Indicate whether or not it is in the range [evalOriginalBudget * divider, evalOriginalBudget] the subset1
     * @return false if the evalBudget is in the subset1. Otherwise true
     */
    public boolean AreAllEvalUsedInSubset1()
    {
         return (this.EvalBudget <= (this.evalOriginalBudget * this.AlgorithmDivider));
    }
    
    public void resetBudget()
    {
        this.EvalBudget = this.evalOriginalBudget;
    }
    
    /**
     * Get the value of AlgorithmDivider
     *
     * @return the value of AlgorithmDivider
     */
    public double getAlgorithmDivider()
    {
        return AlgorithmDivider;
    }

    /**
     * Set the value of AlgorithmDivider. The algorithm divider separates the algorithms in 2 sections. 
     * This value indicates the number of evaluations executed in the first part. 
     *
     * @param AlgorithmDivider new value of AlgorithmDivider [0..1]
     */
    public void setAlgorithmDivider(double AlgorithmDivider)
    {
        this.AlgorithmDivider = AlgorithmDivider;
    }

}
