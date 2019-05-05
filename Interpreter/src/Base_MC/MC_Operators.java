/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base_MC;

import BaseLibraries.BaseEvalManagement;
import BaseLibraries.PopulationManagement;
import Base_Mimicry.MC_Individual;
import Statistics.Statistics;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 *
 * @author patriciaryser-welch
 */
public class MC_Operators extends Base_Mimicry.MC_Operator
{
    private final PopulationManagement Pop;
    private final BaseEvalManagement evalBudget; 
    private MC_Param param;
    private double lastGenMean;
    private int MaxNumGenerations = 100;
    private double lastFitnessOffspring;
    private double lastFitnessParent;
    private double gradients[];
    private int lastGradient =0;
    private double threshold; 
    private Map<String,Integer> noOperands;

    
    public MC_Operators(MC_Param param)
    {
        super(param.getIntensityOfMutation(), param.getSeedValue(), param.getNoOfParents(),  param.getNoOfOffsprings(), param.getNoCandidates(), param.getInstances());
        this.param = param;
        this.Pop = new PopulationManagement(param.getPopSize(), param.getNoOfOffsprings(), param.getNoOfParents(), (int) param.getThreshold());
        this.evalBudget = new BaseEvalManagement(param.getEvalBudget()); 
        this.MaxNumGenerations = param.getMaxNumGenerations();
        this.MutationRateUniform = this.param.getMutationRateUniform();
        this.lastFitnessOffspring = Integer.MIN_VALUE;
        this.lastFitnessParent = Integer.MIN_VALUE;
       // this.maxOperator = this.evalBudget.getEvalOriginalBudget() / this.param.getNoCandidates();
        this.gradients = new double[5];
        this.threshold = this.param.getThreshold();
        this.noOperands = new HashMap();
    }

    public PopulationManagement getPop()
    {
        return Pop;
    }

    public BaseEvalManagement getEvalBudget()
    {
        return evalBudget;
    }
   
    public boolean hasOffSpringImproved()
    {
        boolean improvement = false;
        
        if (this.lastFitnessOffspring == Integer.MIN_VALUE)
        {
            improvement = true;
        }
        else 
        {
            System.out.println("offspring " + this.offsprings[this.getBestOffspringIndex()].getFitness());
            System.out.println("lastoffspring " + this.lastFitnessOffspring);
          
            if (this.offsprings[this.getBestOffspringIndex()].getFitness() < this.lastFitnessOffspring)
            {
                improvement = true;
            }
        }
        
        System.out.println("offspring " + this.offsprings[this.getBestOffspringIndex()].getFitness());
        this.lastFitnessOffspring = this.offsprings[this.getBestOffspringIndex()].getFitness();
        System.out.println("lastoffspring " + this.lastFitnessOffspring);
       
        
        return improvement;
    }
    
    public boolean hasParentImproved()
    {
        boolean improvement = false;
        
        if (this.lastFitnessParent == Integer.MIN_VALUE)
        {
            improvement = true;
        }
        else 
        {
            System.out.println("parent " + this.parents[this.getBestParentIndex()].getFitness());
            System.out.println("lastparent " + this.lastFitnessParent);
            
            if (this.parents[this.getBestParentIndex()].getFitness() < this.lastFitnessParent)
            {
                improvement = true;
            }
        }
        System.out.println("parent * " + this.parents[this.getBestParentIndex()].getFitness());
          
        this.lastFitnessParent = this.parents[this.getBestParentIndex()].getFitness();
        System.out.println("lastparent * " + this.lastFitnessParent);
        return improvement;
    }
    /**
     * This method returns a boolean value related to a termination criteria
     * @param terCode
     * @return true to stop an iteration and false to enter it.
     */
    public boolean applyTerminationCriteria(int terCode, int loopID)
    {
      //  this.addOperandOccurances("ter" + terCode);
        switch(terCode)
        {
            case 0: return this.evalBudget.AreAllEvalUsed();
            case 1: return this.evalBudget.AreAllEvalUsed()|| this.IsOptimaFound();
            case 2: return this.evalBudget.AreAllEvalUsedInSubsets(loopID);
            case 3: return this.AreAllSolutionWorseAfterAWhile(loopID);
            case 4: return this.evalBudget.AreAllEvalUsed() || this.AreAllSolutionsWorseOrTheSame();
            case 5: return this.evalBudget.AreAllEvalUsed() || this.IsOptimaFound() || this.IsRandomWalkBlock(); // not efficient remove
            case 6: return this.evalBudget.AreAllEvalUsed() || this.IsAThresholdReached();
            case 7: return this.evalBudget.AreAllEvalUsedInSubsets(loopID) || this.IsAThresholdReached();
            default: return true;
        }
    }
    
    /**
     * This method returns a boolean value related to a termination criteria
     * @param terCode
     * @return true to stop an iteration and false to enter it.
     */
    public String getTerminationCriteriaToString(int terCode, int loopID)
    {
        switch(terCode)
        {
            case 0: return "AreAllEvalUsed()";
            case 1: return "AreAllEvalUsed() or this.IsOptimaFound()";
            case 2: return "AreAllEvalUsedInSubsets()";
            case 3: return "EvalCount < Limits And Solutions are not improving";
            case 4: return "EvalCount < MaxEval And Solutions are not improving";
            case 5: return "AreAllEvalUsed() or IsOptimaFound() or this.IsRandomWalkBlock()";
            case 6: return "AreAllEvalUsed() or this.IsAThresholdReached()";
            case 7: return "AreAllEvalUsedInSubsets() or this.IsAThresholdReached()";
            default: return "";
        }
    }
    
    public int getTerminationCriteriaNoOperands(int terCode)
    {
        switch(terCode)
        {
            case 0: return 1;
            case 1: return 2;
            case 2: return 1;
            case 3: return 1;
            case 4: return 2;
            case 5: return 3;
            case 6: return 2;
            case 7: return 2;
            default: return 0;
        }
    }
    
    public int getTerminationCriteriaNoOperators(int terCode)
    {
        switch(terCode)
        {
            case 0: return 0;
            case 1: return 1;
            case 2: return 0;
            case 3: return 0;
            case 4: return 1;
            case 5: return 2;
            case 6: return 1;
            case 7: return 1;
            default: return 0;
        }
    }
    
    public void addOperandOccurances(String key)
    {
        int total = 1;
        if (this.noOperands.containsKey(key))
        {
            total = this.noOperands.get(key) + 1;
        }
        
        this.noOperands.put(key, total);
    }
    
    public void applyHeuristics(int opCode) throws CloneNotSupportedException
    {
      
       if (this.evalBudget.getEvalBudget() > 0)
       {
             this.setNoOfEvaluations(0);
            // System.out.print("opCode " + opCode + " ");
           //  this.displayMemory();
             switch(opCode)
             {
           
                case 0:  super.crossOverOnePoint();
                         break;
                case 1:  this.crossOverTwoPoints();
                         break;
                case 2:  super.crossOverUniform();
                         break;
                case 3:  this.mutateBitFlip();
                         break;
                case 4:  this.mutateBitFlipHillClimbing(this.evalBudget.getEvalBudget());
                         break;
                case 5:  this.mutateBitsInASubString(this.evalBudget.getEvalBudget());
                         break;
                case 6:  this.mutateUniformHillClimbing(this.evalBudget.getEvalBudget());
                         break;
                case 7:  this.mutateWithVariableRate();
                         break;
                case 13: this.replaceWeakest();
                         this.getBestParentsForReproduction();
                         break;
             }
             this.evalBudget.reduceEvalBudget(this.getNoOfEvaluations());
       } // end of if statement    
    }// applyheuristics 
    
    public String getHeuristicsToString(int opCode) 
    {
             switch(opCode)
             {
                case 0:  return "crossOverOnePoint()\n";
                case 1:  return "crossOverTwoPoints()\n";
                case 2:  return "crossOverUniform()\n";
                case 3:  return "mutateBitFlip()\n";
                case 4:  return "mutateBitFlipHillClimbing(this.evalBudget.getEvalBudget())\n";
                case 5:  return "mutateBitsInASubString(this.evalBudget.getEvalBudget())\n";
                case 6:  return "mutateUniformHillClimbing(this.evalBudget.getEvalBudget())\n";
                case 7:  return "mutateWithVariableRate()\n";
                case 13: return "replaceWeakest() \n getBestParentsForReproduction()\n";
                default: return "\n";
             }
    
    }// applyheuristics 
    
    public int getHeuristicsNoOfOperators(int opCode) throws CloneNotSupportedException
    {
             switch(opCode)
             {
                case 0:  return 1;
                case 1:  return 1;
                case 2:  return 1;
                case 3:  return 1;
                case 4:  return 1;
                case 5:  return 1;
                case 6:  return 1;
                case 7:  return 1;
                case 13: return 2;
                default: return 0;
             }
    }
     
     /*
     * This count as offspring = crossover(offspring, parent);
     * offspring = mutate(offspring)
     */
    public int getHeuristicsNoOfOperands(int opCode) throws CloneNotSupportedException
    {
             switch(opCode)
             {
                case 0:  return 4;
                case 1:  return 4;
                case 2:  return 4;
                case 3:  return 3;
                case 4:  return 3;
                case 5:  return 3;
                case 6:  return 3;
                case 7:  return 3;
                case 13: return 6;
                default: return 0;
             }
    }
    
     /*
     * This count as offspring = crossover(offspring, parent);
     * offspring = mutate(offspring)
     */
    public int getHeuristicsNoOfEdges(int opCode) throws CloneNotSupportedException
    {
             switch(opCode)
             {
                case 0:  return 1;
                case 1:  return 1;
                case 2:  return 1;
                case 3:  return 1;
                case 4:  return 1;
                case 5:  return 1;
                case 6:  return 1;
                case 7:  return 1;
                case 13: return 2;
                default: return 0;
             }
    }
    
     /*
     * This count as offspring = crossover(offspring, parent);
     * offspring = mutate(offspring)
     */
    public int getHeuristicsNoOfNodes(int opCode) throws CloneNotSupportedException
    {
             switch(opCode)
             {
                case 0:  return 1;
                case 1:  return 1;
                case 2:  return 1;
                case 3:  return 1;
                case 4:  return 1;
                case 5:  return 1;
                case 6:  return 1;
                case 7:  return 1;
                case 13: return 2;
                default: return 0;
             }
    }
    
    
    public Map<String, Integer> getNoOperands()
    {
        return noOperands;
    }
    
    
    public boolean AreAllEvalUsedInSubset2()
    {
        //  System.out.println("cond 3" +this.evalBudget.AreAllEvalUsedInSubset1());
      
        return this.evalBudget.AreAllEvalUsedInSubset2();
        //return this.evalBudget.hasNotReachedSecondPartOfEvals();
    }
    
    public boolean AreAllEvalUsedInSubset1()
    {
        
      //  System.out.println("cond 2" +this.evalBudget.AreAllEvalUsedInSubset1());
        return this.evalBudget.AreAllEvalUsedInSubset1();
    }
    
    public boolean isEvalBudgetAllUsed()
    {
      //  System.out.println ("IsEvalBudgetAllUsed " + this.evalBudget.AreAllEvalUsed() +  " " + this.evalBudget.getEvalBudget());
        return this.evalBudget.AreAllEvalUsed();
    }
    
    public boolean IsOptimaFound()
    {
        int bestParent = this.getBestParentIndex();
        int bestOffspring = this.getBestOffspringIndex();
        
        return (this.offsprings[bestOffspring].getFitness() == 0.0) || (this.parents[bestParent].getFitness()== 0.0);
    }
    
   public boolean IsRandomWalkBlock()
   {
       boolean result;
       if (!this.isEvalBudgetAllUsed())
       {
            double probability = Math.exp((this.getBestParentFitness() - this.getBestOffspringFitness())/ 
                                           (this.evalBudget.getEvalOriginalBudget() - this.evalBudget.getEvalBudget()));
            result = Math.random() > probability;
       }
       else 
       {
           result = true;
       }
       return result;
   }
   
   public boolean IsAThresholdReached()
   {
        int bestParent = this.getBestParentIndex();
        int bestOffspring = this.getBestOffspringIndex();
        
        return (this.offsprings[bestOffspring].getFitness() <= this.threshold) || (this.parents[bestParent].getFitness()<= this.threshold);
   }
   
   public boolean AreAllSolutionWorseAfterAWhile(int loopID)
   {
       Double adaptEval = (this.evalBudget.getEvalBudget() * this.getBestSolutionFitnessValue());
       boolean result = true;
       boolean isWorseOrTheSame = this.Pop.AreAllSolutionWorseOrTheSame(loopID, this.getParentsFitnessValue(), this.getOffspringFitnessValue());
       boolean areAllEvalUsedinSubset = this.evalBudget.AreAllEvalUsedInSubsets(loopID, adaptEval.intValue());
       if (areAllEvalUsedinSubset)
       {
           //end of all evaluation used those are set randomly
           if (!isWorseOrTheSame) // improving 
           {
               this.evalBudget.deleteDivider(loopID); // remove record from db
               this.Pop.deleteStartFitness(loopID); // remove record from db 
               
               if (!this.evalBudget.AreAllEvalUsed()) // not end of the search 
               {
                   result = this.evalBudget.AreAllEvalUsedInSubsets(loopID, adaptEval.intValue()); //may redundant 
                   result = this.Pop.AreAllSolutionWorseOrTheSame(loopID, this.getParentsFitnessValue(), this.getOffspringFitnessValue());
               }
               else 
               {
                   result = true; //end must stop
               }
           }
           else
           {
               result = true;
           }
       }
       else
       {
           result = false;
       }
       return result;
   }
   
   public boolean AreAllSolutionsWorseOrTheSame()
   {
        boolean noImprovement = true;
        for (MC_Individual parent : this.parents)
        {
            if (parent.hasImproved())
            {
                noImprovement = false;
            }
        }
       
       if (noImprovement)
       {
           for (MC_Individual offspring : this.offsprings)
           {
               if (offspring.hasImproved())
               {
                   noImprovement = false;
               }
           }
       }
       
       return noImprovement;
   }
   
   
    public boolean hasAConstantGradientForAWhile(int iteration)
    {
        double mean = this.FindMeanOfPop(); 
        double gradient; 
      //  System.out.println ("cond 4");
        if ((iteration % this.MaxNumGenerations) == 0)
        {
            if(this.lastGenMean == Double.MAX_VALUE)
            {
                gradient = (mean - Double.MAX_VALUE)/1;
            }
            else 
            {
                gradient =  (mean - this.lastGenMean)/this.MaxNumGenerations ;
            }
            
            this.lastGenMean = mean;
           // System.out.println(gradient + " " + (gradient < 0));
       
        }
        else
        {
            gradient = 10;
        }
                
      // System.out.println(gradient);
        
      //  System.out.println(this.lastGenMean);
        return gradient <= 0;
    }
    
    private double FindMeanOfPop()
    {
        // retrieve the fitness values of the population
        List<Double> fitness = new ArrayList<>();
        Statistics stat;
        for (int i = 0; i < super.parents.length; i++)
        {
            fitness.add((double) super.parents[i].getFitness());
        }
        // calculate the mean of the population
        stat = new Statistics(fitness);
        return stat.getMean();
    }
    
    /**
     *
     * @return
     */
   
   public double getBestSolutionFitnessValue()
   {
       return this.parents[this.getBestSolutionIndex()].getFitness();
   }
       
   @Override
    public int getBestSolutionIndex()
    {
        int indexOfFittest = 0;
        for (int i = 1; i < this.parents.length; i++)
        {
            if (this.parents[indexOfFittest].getFitness() >= this.parents[i].getFitness())
            {
                indexOfFittest = i;
            }
        }
      //  System.out.println("***" + indexOfFittest);
        return indexOfFittest;
    }

   public void displayMemory() 
    {
        String s = "eval left ";
        
        s += this.evalBudget.getEvalBudget();
        s += " " + this.toString();
        System.out.println(s);
      
    }

   private void setGradients()
   {
       for(int i = 0; i < this.offsprings.length; i++)
       {
            if (this.lastGradient == this.gradients.length)
            {
                this.lastGradient = 0;
            }
            else 
            {
                this.gradients[this.lastGradient] = this.offsprings[i].getGradient();
                this.lastGradient++;
            }
       }
   }
}
