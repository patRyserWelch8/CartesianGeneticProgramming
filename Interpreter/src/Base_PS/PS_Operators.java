/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base_PS;

import BaseLibraries.BaseEvalManagement;
import BaseLibraries.PopulationManagement;
import PersonnelScheduling.PersonnelScheduling;
import java.util.*;
import Statistics.*;


/**
 *
 * @author patriciaryser-welch
 */
public class PS_Operators extends PersonnelScheduling implements BaseLibraries.OperatorsInterface
{
    private final PopulationManagement Pop;
    private final BaseEvalManagement evalBudget; // evaluation budget (may not be suitable here)
    private double previousMean, lastGenMean;
    private PS_Param param;
    private int lastUpdate;
    private int MaxNumGenerations = 100;
    private int generation;
    private double threshold = 0.05; 
    private boolean[] improvement;
   

    /*****
     * Constructor
     * @param seed
     * @param popSize
     * @param instance
     * @param noParents
     * @param noOffsprings
     * @param evalBudget
     * @param Threshold 
     * @param PS_param;
     */
    public PS_Operators(PS_Param param) 
    {
        super(param.getSeedValue());
        this.Pop = new PopulationManagement(param.getPopSize(), param.getNoOfOffsprings(), param.getNoOfParents(), (int) param.getThreshold());
        this.evalBudget = new BaseEvalManagement(param.getEvalBudget());
        this.previousMean = Double.MAX_VALUE;
        this.param = param;
        this.lastGenMean = Double.MAX_VALUE;
        this.loadInstance(param.getInstances());
        this.lastUpdate = 0;
        this.MaxNumGenerations = param.getMaxNumGenerations();
        this.generation = 0;
        
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
       // this.setMemorySize(0);
        this.setMemorySize(Pop.getPopSize() + Pop.getNoOffsprings());
       
        initParents();
        initOffsprings();
        
        //this.lastGenMean = this.FindMeanOfPop();
        this.setLastUpdate();
        this.improvement = new boolean[Pop.getPopSize() + Pop.getNoOffsprings()];
        for(boolean improve:improvement)
        {
            improve = true;
        }
    }
    
    public void restartPop()
    {
        System.out.println(this.AreAllSolutionWorseAfterAWhile(1000));
        if (this.AreAllSolutionWorseAfterAWhile(1000))
        {
            System.out.println("***** restart");
            this.initPop();
        }
    }

    private void initParents()
    {
        //   System.out.println("Start init pop");
       // System.out.println(super.getMemorySize());
        for (int i = 0; i < Pop.getPopSize(); i++)
        {
            super.initialiseSolution(i);
           // this.initialiseSolution(i);
            this.evalBudget.reduceEvalBudget(1);
        }
    }

    private void initOffsprings()
    {
        for (int i = Pop.getPopSize(); i < (Pop.getPopSize() + Pop.getNoOffsprings());i++ )
        {
            this.initialiseSolution(i);
        }
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
        
        initOffsprings();
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
               // this.selectParentsForReproduction();
                
            }
            else if (opCode == 14)
            {
                this.replaceRandom(this.generation);
                this.evalBudget.reduceEvalBudget(1);
                this.selectParents();
            }
            else if (opCode == 15)
            {
                this.restartPop();
               // this.selectParents();
                this.evalBudget.reduceEvalBudget(this.Pop.getNoOffsprings() + this.Pop.getPopSize());
            }
        }// end if 
    } // end applyHeuristic 
   

    @Override public void selectParents() 
    {
        Pop.getParents()[0] = chooseFirstParent();
        this.copySolution(Pop.getParents()[0], Pop.getPopSize());
       
        if (this.Pop.getParents().length > 1)
        {
            Pop.getParents()[1] = this.chooseSecondParent(Pop.getParents()[0]);
            this.copySolution(Pop.getParents()[1], Pop.getPopSize() + 1);

        }
      //  System.out.println("Select parents " + this.getFunctionValue(Pop.getPopSize()) + " " + this.getFunctionValue(Pop.getPopSize() +1 ));
    }
    
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

    public void replaceWeakest() 
    {
        int WorseSol;
  
        for (int i = 0; i < Pop.getNoOffsprings(); i++) 
        {
            int actualPos = i + Pop.getPopSize();
            WorseSol = FindWeakest(); // find worseSol
            if (this.getFunctionValue(actualPos) <= this.getFunctionValue(WorseSol)) 
            {
                //Replace
                this.copySolution(actualPos, WorseSol); // replace Offspring with it
                this.setLastUpdate();
             }
        }
      //  displayMemory();
    }

    @Override public void replaceRandom(int iteration) 
    {
        int randomSol;
       
        for (int i = 0; i < Pop.getNoOffsprings(); i++) 
        {
            int actualPos = i + Pop.getPopSize();
            if (this.Pop.getPopSize() == 1)
            {
                randomSol = 0;
            }
            else 
            {
                randomSol = this.rng.nextInt(this.Pop.getPopSize()); // find worseSol index
            }
            
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

    public void displayMemory() 
    {
        System.out.print(this.evalBudget.getEvalBudget() + " - ");
        for (int i = 0; i < (this.Pop.getPopSize() + this.Pop.getNoOffsprings()); i++) 
        {
           System.out.print(this.getSolutionFitnessValue(i)+ "(i),");
        }
        System.out.println();
    }

    /*
    * This is not a very efficient operator.
    */
    public void restartOffSpringOnly(int iteration) 
    {
        this.restartAPopulation(true);
    }
    
    private void restartAPopulation(boolean isItOffspringOnly) 
    {
        if (this.evalBudget.AreAllEvalUsedInSubsets(300, this.evalBudget.getEvalBudget())) 
        {
            double mean = FindMeanOfPop();
            if (this.generation == 0 & this.previousMean == Double.MAX_VALUE) 
            {
                //initialise the previousMean
                this.previousMean = mean;
            } 
            else 
            {
                if (this.previousMean == mean)
                {
                    
                    if (isItOffspringOnly)
                    {
                       this.initOffsprings();
                    }
                    else 
                    {
                        this.initPop(); 
                    }
                }
                this.setLastUpdate();
                this.previousMean = mean;
            }
        }
    }

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
        return (this.getFunctionValue(i) - this.param.getInstanceOptimaCost())/this.param.getInstanceOptimaCost();
    }
    
    @Override public PopulationManagement getPopulationParam()
    {
         return Pop; 
    }
    
    /***
     *  get return the best individual in the population. Does not count the sibling
     * 
     *  @return BestSolutionValue
     */
     
    public double getBestFitness()
    {
        return this.getBestSolutionValue();  
    }

    public boolean hasFoundOptima()
    {
        this.applyHeuristic(13);
        return this.getFunctionValue(this.FindBest()) <= this.param.getInstanceOptima();
    }
    
    public boolean hasAConstantGradientDescentOrWorse()
    {
        double mean = this.FindMeanOfPop(); 
        double gradient; 
        
        this.applyHeuristic(13);
        if(this.lastGenMean == Double.MAX_VALUE)
        {
            gradient = mean - Double.MAX_VALUE;
        }
        else 
        {
            
            gradient =  mean - this.lastGenMean;
        }
                
        this.lastGenMean = mean;
        return gradient >= 0;
    }
    
    public boolean hasAConstantGradientForAWhile()
    {
        double mean = this.FindMeanOfPop(); 
        double gradient; 
     
        this.applyHeuristic(13);
        if ((this.generation  % this.MaxNumGenerations) == 0 || (this.generation  % this.MaxNumGenerations) == (this.MaxNumGenerations-1) )
        {
            System.out.println("in");
            if(this.lastGenMean == Double.MAX_VALUE)
            {
                gradient = (mean - Double.MAX_VALUE)/1;
            }
            else 
            {
                gradient =  (mean - this.lastGenMean)/this.MaxNumGenerations ;
            }
            
            this.lastGenMean = mean;
        
            System.out.println(this.lastGenMean + " " + gradient);
            return gradient <= 0;
       
        }
        else
        {
            return false;
        }
       
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
        this.lastUpdate = this.generation;
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

    
    public double getBestTSPFitness()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   

    public int getGeneration()
    {
        return generation;
    }

    public void setGeneration(int generation)
    {
        this.generation = generation;
    }

   
    public void applyHeuristic(int opCode, int offspring)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

   
    
   
    public void restartPop(int iteration)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public boolean isEvalBudgetAllUsed()
    {
        return this.evalBudget.AreAllEvalUsed();
    }
    
    public boolean IsOptimaFound()
    {
        //System.out.println(this.getSolutionFitnessValue(this.chooseFirstParent()) + " *** " + (this.getSolutionFitnessValue(this.chooseFirstOffspring()))); 
        return (this.getSolutionFitnessValue(this.chooseFirstParent()) == 0.0) || (this.getSolutionFitnessValue(this.chooseFirstOffspring())== 0.0);
    }
    
     public double getBestParentFitness()
    {
           return this.getFunctionValue(this.chooseFirstParent());
    }
    
    public double getBestOffspringFitness()
    {
        return this.getFunctionValue(this.chooseFirstOffspring());
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
        
        return (this.getBestSolution() <= this.threshold);
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
       
       adaptEval = (double) this.evalBudget.getEvalBudget();
       
       isWorseOrTheSame = this.Pop.AreAllSolutionWorseOrTheSame(loopID, parentFitness, offspringFitness);
       System.out.println("areallsolutionsWorthAfterAwhile() - worse " + isWorseOrTheSame);
       boolean areAllEvalUsedinSubset = this.evalBudget.AreAllEvalUsedInSubsets(loopID);
       System.out.println("areallsolutionsWorthAfterAwhile() - evals " + areAllEvalUsedinSubset);
       if (areAllEvalUsedinSubset)
       {
           this.evalBudget.deleteDivider(loopID);
               
           if (!isWorseOrTheSame)
           {
               this.Pop.deleteStartFitness(loopID);
               
               if (!this.evalBudget.AreAllEvalUsed())
               {
                   System.out.println("areallsolutionsWorthAfterAwhile() - 1");
                   result = this.evalBudget.AreAllEvalUsedInSubsets(loopID);
               }
               else 
               {
                   System.out.println("areallsolutionsWorthAfterAwhile() - 2");
                   result = true;
               }
           }
           else
           {
               System.out.println("areallsolutionsWorthAfterAwhile() - 3");
        
               result = true;
           }
       }
       else
       {
           System.out.println("areallsolutionsWorthAfterAwhile() - 4");
           result = false;
       }
       return result;
   }
    
    
    
    public double getSolutionFitnessValue(int index)
    {
      /*  System.out.print(" 1 " + this.getFunctionValue(index)); 
        System.out.print(" 2 " + this.param.getInstanceOptimaCost());
        
        System.out.print(" 3 " + (this.getFunctionValue(index) - this.param.getInstanceOptimaCost()));
        System.out.print(" 4 " + this.param.getInstanceNumFeatures());
        System.out.println(" 5 " + (this.getFunctionValue(index) - this.param.getInstanceOptimaCost())/this.param.getInstanceNumFeatures());
       */ 
        return (this.getFunctionValue(index) - (double)this.param.getInstanceOptimaCost())/this.param.getInstanceNumFeatures();
    }
    
   
    public double getBestSolutionFitnessValue()
    {
        this.replaceWeakest();
        return this.getSolutionFitnessValue(this.chooseFirstParent());
        
       // return (this.getBestSolution() - this.param.getInstanceOptimaCost())/this.param.getInstanceNumFeatures();
      //  return 2*((this.param.getInstanceOptima() - this.getBestSolution())/(this.param.getInstanceOptima() - this.getBestSolution()));
       
    }

    @Override
    public void replaceWeakest(int iteration)
    {
    }
  
}

