/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseTSP;
import java.util.*;
import Statistics.*;
import BaseLibraries.*;



/**
 *
 * @author patriciaryser-welch
 */
public class TSP_Operators extends TSP.TSP implements BaseLibraries.OperatorsInterface
{
    private final PopulationManagement Pop;
    private final BaseEvalManagement evalBudget; // evaluation budget (may not be suitable here)
    private double previousMean, lastGenMean;
    private TSP_Param param;
    private int lastUpdate;
    private boolean[] improvement;
    private int MaxNumGenerations = 100;
    private double threshold = 0.05; 
   
    /*****
     * Constructor
     * @param seed
     * @param popSize
     * @param instance
     * @param noParents
     * @param noOffsprings
     * @param evalBudget
     * @param Threshold 
     * @param TSP_param;
     */
    public TSP_Operators(TSP_Param param) 
    {
        super(System.currentTimeMillis());
        this.Pop = new PopulationManagement(param.getPopSize(), param.getNoOfOffsprings(), param.getNoOfParents(), (int) param.getThreshold());
        this.evalBudget = new BaseEvalManagement(param.getEvalBudget());
        this.previousMean = Double.MAX_VALUE;
        this.param = param;
        this.lastGenMean = Double.MAX_VALUE;
        this.loadInstance(param.getInstances());
        this.lastUpdate = 0;
        this.MaxNumGenerations = param.getMaxNumGenerations();
           
    }

    @Override public BaseEvalManagement getEvalBudget()
    {
        return this.evalBudget;
    }

      /****
     * initial population. To make sure the other operators works. the Siblings are too
     * initialised.
     */
    @Override public void initPop() 
    {
        this.setMemorySize(0);
        this.setMemorySize(Pop.getPopSize() + Pop.getNoOffsprings());
     //   System.out.println("Start init pop");
        for (int i = 0; i < Pop.getPopSize(); i++) 
        {
            this.initialiseSolution(i);
            this.evalBudget.reduceEvalBudget(1);
        }
        
        for (int i= Pop.getPopSize(); i < (Pop.getPopSize() + Pop.getNoOffsprings());i++ )
        {
            this.initialiseSolution(i);
        }
        
        this.improvement = new boolean[Pop.getPopSize() + Pop.getNoOffsprings()];
        for(boolean improve:improvement)
        {
            improve = true;
        }
        //this.lastGenMean = this.FindMeanOfPop();
        this.setLastUpdate();
    }
    
        /****
     * initial population. To make sure the other operators works. the Siblings are too
     * initialised.
     */
    public void initHalfPop() 
    {
        this.setMemorySize(0);
        this.setMemorySize(Pop.getPopSize() + Pop.getNoOffsprings());
     //   System.out.println("Start init pop");
        for (int i = 0; i < (Pop.getPopSize()/2); i++) 
        {
            this.initialiseSolution(i);
            this.evalBudget.reduceEvalBudget(1);
        }
        
        for (int i= Pop.getPopSize(); i < (Pop.getPopSize() + Pop.getNoOffsprings());i++ )
        {
            this.initialiseSolution(i);
        }
    }
    
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
            case 5: return this.evalBudget.AreAllEvalUsed() || this.IsOptimaFound() || this.IsRandomWalkBlock();
            case 6: return this.evalBudget.AreAllEvalUsed() || this.IsAThresholdReached();
            case 7: return this.evalBudget.AreAllEvalUsedInSubsets(loopID) || this.IsAThresholdReached();
            default: return true;
        }
    }
     
     public void displayMemory()
     {
        System.out.print(this.evalBudget.getEvalBudget() + " - ");
        for (int i = 0; i < this.Pop.getPopSize() + this.Pop.getNoOffsprings(); i++) 
        {
           System.out.print(this.getSolutionFitnessValue(i) + ",");
        }
        System.out.println(); 
     }
     
     public double getSolutionFitnessValue(int index)
    {
       return (this.getFunctionValue(index) - this.param.getInstanceOptimaCost())/this.param.getInstanceOptimaCost();
    }
     
  public boolean IsAThresholdReached()
   {
        
        return (this.getBestSolution() <= this.threshold);
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
   
    public double getBestParentFitness()
    {
        int bestParent = this.chooseFirstParent();
        return this.getFunctionValue(bestParent);
    }
     
     public double getBestOffspringFitness()
    {
        int bestOffspring = this.chooseFirstOffspring();
        return this.getFunctionValue(bestOffspring);
    }
  
     public boolean AreAllSolutionsWorseOrTheSame()
   {
        boolean noImprovement = true;
        for (int i = 0; i < (this.Pop.getNoOffsprings() + this.Pop.getPopSize()); i++)
        {
           
           noImprovement = !this.improvement[i];
        }
       
       return noImprovement;
   }
    
     
      public boolean AreAllSolutionWorseAfterAWhile(int loopID)
   {
       Double adaptEval; 
       boolean result = true;
       boolean isWorseOrTheSame;
       Double[] parentFitness = new Double[this.param.getNoOfParents()];
       Double[] offspringFitness = new Double[this.param.getNoOfOffsprings()];;
       
       for(int i = 0; i < this.Pop.getPopSize();i++)
       {
           parentFitness[i] = new Double(this.getFunctionValue(i));
       }
       
       for(int i = this.Pop.getPopSize(); i < (this.Pop.getPopSize() + this.Pop.getNoOffsprings());i++)
       {
           offspringFitness[i-this.Pop.getPopSize()] = new Double(this.getFunctionValue(i));
       }
       
       if(this.getBestSolutionFitnessValue() > 1.0)
       {
           adaptEval = (double) this.evalBudget.getEvalBudget();
       }
       else
       {
           adaptEval = (this.evalBudget.getEvalBudget() * this.getBestSolutionFitnessValue());
       }
       
       isWorseOrTheSame = this.Pop.AreAllSolutionWorseOrTheSame(loopID, parentFitness, offspringFitness);
       boolean areAllEvalUsedinSubset = this.evalBudget.AreAllEvalUsedInSubsets(loopID, adaptEval.intValue());
       if (areAllEvalUsedinSubset)
       {
           if (!isWorseOrTheSame)
           {
               this.evalBudget.deleteDivider(loopID);
               this.Pop.deleteStartFitness(loopID);
               
               if (!this.evalBudget.AreAllEvalUsed())
               {
                   result = this.evalBudget.AreAllEvalUsedInSubsets(loopID, adaptEval.intValue());
               }
               else 
               {
                   result = true;
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
     
   
    public boolean IsOptimaFound()
    {
        System.out.println (this.getBestSolution());
        return (this.getBestFitness() == this.param.getInstanceOptimaCost());
    }

     public void applyHeuristic(int opCode)
    {
        if (!this.isEvalBudgetAllUsed())
        {
            if (opCode < 13)
            {
                 if (this.param.getNoOfOffsprings() == 1 & this.param.getNoOfParents() == 1)
                 {
                    double saveOut = super.getFunctionValue(Pop.getPopSize());
                    this.applyHeuristic(opCode, 0, 1, 1 );
                    this.evalBudget.reduceEvalBudget(1);
                    this.improvement[1] = (saveOut > super.getFunctionValue(1));
                 }
                 else
                 {
               
                    for (int offspring = 0; offspring < this.getPopulationParam().getNoOffsprings(); offspring++)
                    {
                        double saveOut = super.getFunctionValue(Pop.getPopSize() + offspring);
                        this.applyHeuristic(opCode, Pop.getPopSize(),Pop.getPopSize() + 1,  Pop.getPopSize() + offspring);
                        this.evalBudget.reduceEvalBudget(1);
                        this.improvement[Pop.getPopSize() + offspring] = (saveOut > super.getFunctionValue(Pop.getPopSize() + offspring));
                    } 
                 }
            }
            else if (opCode == 13)
            {
                this.replaceWeakest();
                this.evalBudget.reduceEvalBudget(1);
            //    this.selectParents();
                this.selectParentsForReproduction();
            
            }
            else if (opCode == 14)
            {
                this.replaceRandom();
                this.evalBudget.reduceEvalBudget(1);
                this.selectParents();
            }
           else if (opCode == 15)
            {
                this.restartPop();
                this.selectParents();
                this.evalBudget.reduceEvalBudget(this.Pop.getNoOffsprings() + this.Pop.getPopSize());
            }
        }// end if 
         System.out.print(opCode + " -  "  );
         this.displayMemory();
    } // end applyHeuristic 
   
     
      public void selectParentsForReproduction() 
    {
        int indexBest = this.chooseFirstParent();
        int secondBest; 
        if (this.Pop.getPopSize() > 1)
        {
            secondBest = this.chooseSecondParent(indexBest);
         
            for (int i = Pop.getPopSize(); i < (Pop.getPopSize() + Pop.getNoOffsprings());i++ ) 
            {
                 if ((i % 2) == 0)
                 {
                    this.copySolution(indexBest,i);
                 }
                 else 
                 {
                     this.copySolution(secondBest,i);
                 }
            }
        }
        else 
        {
            for (int i = Pop.getPopSize(); i < (Pop.getPopSize() + Pop.getNoOffsprings());i++ ) 
            {
                indexBest = this.chooseFirstParent(); 
                this.copySolution(indexBest,i);
            }
        }
        
      
    }

    /*public void applyHeuristic(int opCode, int offspring, int iteration) 
    {
        if (!this.isEvalBudgetAllUsed())
        {
            System.out.println("Apply Heursitic - Before " + offspring +  " " + opCode + " " + this.getFunctionValue( Pop.getPopSize() + offspring));
            this.applyHeuristic(opCode, Pop.getPopSize(),Pop.getPopSize() + 1,  Pop.getPopSize() + offspring);
            this.evalBudget.reduceEvalBudget(1);
            System.out.println("Apply Heuristic - after " + offspring +    " " + opCode + " " + this.getFunctionValue( Pop.getPopSize() + offspring));
        }
   } */

      
    @Override public void selectParents() 
    {
        Pop.getParents()[0] = chooseFirstParent();
        Pop.getParents()[1] = this.chooseSecondParent(Pop.getParents()[0]);
        this.copySolution(Pop.getParents()[0], Pop.getPopSize());
        this.copySolution(Pop.getParents()[1], Pop.getPopSize() + 1);
      //  System.out.println("Select parents " + this.getFunctionValue(Pop.getPopSize()) + " " + this.getFunctionValue(Pop.getPopSize() +1 ));
    }

    public void replaceWeakest() 
    {
        int WorseSol;
    //    this.evalBudget.reduceEvalBudget(1);
    // 
  //      displayMemory();
  //      System.out.println("in replaceWeakest");
        for (int i = 0; i < Pop.getNoOffsprings(); i++) 
        {
            int actualPos = i + Pop.getPopSize();
            WorseSol = FindWeakest(); // find worseSol
            if (this.getFunctionValue(actualPos) <= this.getFunctionValue(WorseSol)) 
            {
                //Replace
                this.copySolution(actualPos, WorseSol); // replace Offspring with it
                //   this.fitnessArray[WorseSol] = this.fitnessArray[offsprings[i]];
                this.setLastUpdate();
             }
        }
      //  displayMemory();
    }

    public void replaceRandom() 
    {
        int randomSol;
       // System.out.println("in replace Random");
    //    this.evalBudget.reduceEvalBudget(1);
       
        for (int i = 0; i < Pop.getNoOffsprings(); i++) 
        {
            int actualPos = i + Pop.getPopSize();
            randomSol = this.rng.nextInt(this.Pop.getPopSize()); // find worseSol index
            if (this.getFunctionValue(actualPos) <= this.getFunctionValue(randomSol))
            {
                this.setLastUpdate();
            }
            
            this.copySolution(actualPos,randomSol); // randomly the new offspring  
        }
    }

    protected int FindWeakest() 
    {
        double maxFit = Double.NEGATIVE_INFINITY;
        int maxIdx = -1;
        for (int i = 0; i < this.Pop.getPopSize(); i++) 
        {
            if (this.getFunctionValue(i) > maxFit) 
            {
                maxFit = this.getFunctionValue(i);
                maxIdx = i;
            }
        }
     //   System.out.println("Find Weakest " + this.Pop.getPopSize() + "," + maxIdx);
        return maxIdx;
    }

     protected int chooseFirstOffspring() 
    {
        double minFit = Double.POSITIVE_INFINITY;
        int minidx = -1;
        for (int i = this.Pop.getPopSize(); i < (this.Pop.getPopSize() + this.Pop.getNoOffsprings()); i++) 
        {
            if (this.getFunctionValue(i) < minFit) 
            {
                minFit = this.getFunctionValue(i);
                minidx = i;
            }
        }
        return minidx;
        // Double pos = r.nextGaussian() * this.myPopSize;
        // return pos.intValue();
    }
    protected int FindBest() 
    {
        double minFit = Double.POSITIVE_INFINITY;
        int minIdx = -1;
        for (int i = 0; i < this.Pop.getPopSize(); i++) 
        {
            if (this.getFunctionValue(i) < minFit)
            {
                minFit = this.getFunctionValue(i);
                minIdx = i;
            }
        }
        return minIdx;
    }
    
    //Asta Code
    protected int chooseFirstParent() 
    {
        double minFit = Double.POSITIVE_INFINITY;
        int minidx = -1;
        for (int i = 0; i < Pop.getPopSize(); i++) 
        {
            if (this.getFunctionValue(i) < minFit) 
            {
                minFit = this.getFunctionValue(i);
                minidx = i;
            }
        }
        return minidx;
        // Double pos = r.nextGaussian() * this.myPopSize;
        // return pos.intValue();
    }

    //Asta code
    protected int chooseSecondParent(int fpidx) 
    {
        double minFit = Double.POSITIVE_INFINITY;
        int minidx = -1;
        for (int i = 0; i < Pop.getPopSize(); i++) {
            if (this.getFunctionValue(i) < minFit && i != fpidx) 
            {
                minFit = this.getFunctionValue(i);
                minidx = i;
            }
        }
        return minidx;
        //  Double pos = r.nextGaussian() * this.myPopSize;
        //intValue();
    }

   

    /****
     * restart the population every ten iteration if the threshold of 20 has not been met.
     * @param iteration
     *
     */
  /*  @Override public void restartPop() 
    {
        if ((iteration % 200) == 0) 
        {
            double mean = FindMeanOfPop();
            if (iteration == 0 & this.previousMean == Double.MAX_VALUE) 
            {
                //initialise the previousMean
                this.previousMean = mean;
            } 
            else 
            {
               // System.out.println("RESTART");
              //  System.out.println("BEFORE" + this.previousMean + " " + mean);
             ///   this.displayMemory();
                // restart only if the difference is less than 10 of the previous mean.
                // Each instance has a different optimum tour 2%
                if ((this.previousMean - mean) <= (this.previousMean / 50)) 
                {
                    this.initPop();   
                }
                this.setLastUpdate();
              //  System.out.println("AFTER");
               // this.displayMemory();
                this.previousMean = mean;
            }
        }
    }*/

    private double FindMeanOfPop()
    {
        // retrieve the fitness values of the population
        List<Double> fitness = new ArrayList<>();
        Statistics stat;
        for (int i = 0; i < Pop.getPopSize(); i++)
        {
            fitness.add(this.getFunctionValue(i));
        }
        // calculate the mean of the population
        stat = new Statistics(fitness);
        return stat.getMean();
    }

    /****
     * Identifies the lowest tour and returns its value.  
     * @return the best current solution in the population.
     */
    @Override public double getBestSolution() 
    {
        int i = this.chooseFirstParent();
        return this.getFunctionValue(i);
    }
    
    @Override public PopulationManagement getPopulationParam()
    {
         return Pop; 
    }
    
    public void restartPop()
    {
        if (this.AreAllSolutionWorseAfterAWhile(1000))
        {
            this.initPop();
        }
    }
    /***
     *  get return the best individual in the population. Does not count the sibling
     * 
     *  @return BestSolutionValue
     */
     
    
    public double getBestFitness()
    {
        
        return this.getSolutionFitnessValue(this.FindBest());
    }

    public boolean isEvalBudgetAllUsed()
    {
        return this.evalBudget.AreAllEvalUsed();
    }
    
    public boolean hasFoundOptima()
    {
        return this.getFunctionValue(this.FindBest()) <= this.param.getInstanceOptima();
    }
    
    public boolean hasAConstantGradientDescentOrWorse()
    {
        double mean = this.FindMeanOfPop(); 
        double gradient; 
        
        if(this.lastGenMean == Double.MAX_VALUE)
        {
            gradient = mean - Double.MAX_VALUE;
        }
        else 
        {
            
            gradient =  mean - this.lastGenMean;
        }
                
       // System.out.println(gradient);
        this.lastGenMean = mean;
      //  System.out.println(this.lastGenMean);
        return gradient <= 0;
    }
    
    public boolean hasAConstantGradientForAWhile(int iteration)
    {
        double mean = this.FindMeanOfPop(); 
        double gradient; 
        
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
         //   System.out.println(gradient + " " + (gradient < 0));
       
        }
        else
        {
            gradient = 10;
        }
                
     //  System.out.println(gradient);
        
      //  System.out.println(this.lastGenMean);
        return gradient <= 0;
    }
    /***
     * 
     * @return 
     */
    public boolean AreAllEvalUsedInSubset2()
    {
        return this.evalBudget.AreAllEvalUsedInSubset2();
        //return this.evalBudget.hasNotReachedSecondPartOfEvals();
    }
    
    public boolean AreAllEvalUsedInSubset1()
    {
        return this.evalBudget.AreAllEvalUsedInSubset1();
    }
    
    public int getLastUpdated()
    {
        return this.lastUpdate;
    }
    
    public void setLastUpdate()
    {
        this.lastUpdate = this.evalBudget.getEvalBudget();
    }
    
    
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

    @Override
    public double getBestTSPFitness()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void applyHeuristic(int opCode, int offspring) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void replaceRandom(int iteration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void replaceWeakest(int iteration) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void restartPop(int iteration) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public double getBestSolutionFitnessValue()
   {
       return this.getSolutionFitnessValue(this.getBestSolutionIndex());
   }
       
 
    public int getBestSolutionIndex()
    {
        int indexOfFittest = 0;
        for (int i = 1; i < (this.Pop.getPopSize() +  this.Pop.getNoOffsprings()); i++)
        {
            if (this.getSolutionFitnessValue(indexOfFittest) >= this.getSolutionFitnessValue(indexOfFittest))
            {
                indexOfFittest = i;
            }
        }
      //  System.out.println("***" + indexOfFittest);
        return indexOfFittest;
    }
}

