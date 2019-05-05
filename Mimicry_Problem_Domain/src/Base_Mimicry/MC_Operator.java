/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base_Mimicry;

import java.util.Arrays;

/**
 *
 * @author patriciaryser-welch
 */
public class MC_Operator extends BaseLibraries.BinaryOperator
{
    private static final double ONE_FIFTH_RULE_THRESHOLD = 0.2;
    protected int noCandidates;
    protected MC_Individual[] offsprings; 
    protected MC_Individual[] parents;
    private int[] SolutionsQuality;
    private int lastIndex = 0;
   
            
    public MC_Operator(double MutationRate, long timeStamp, int noParents, int noOffsprings, int noCandidates, int length)
    {
        super(MutationRate, timeStamp, noParents, noOffsprings, length);
        this.offsprings = new MC_Individual[this.noOffsprings];
        this.parents = new MC_Individual[this.noParents];
       // this.offsprings = new MC_Individual[this.noCandidates]; 
        this.SolutionsQuality = new int[5];
    }
   
    /***
     * This method initialises randomly the population of offsprings and parents solutions. Both population are evaluated. The number 
     * of evaluations used are the number of offsprings and the number of parents added together.
     */
    @Override public void initPop() throws CloneNotSupportedException
    {
        MC_Individual Pattern =  new MC_Individual(this.length, this.MutationRateUniform);
        for (int i = 0; i < this.parents.length; i++)
        {
            parents[i] = new MC_Individual(this.length, this.MutationRateUniform, Pattern.getPattern());
        }
        
        for (int i = 0; i < this.offsprings.length; i++)
        {
            this.offsprings[i] = new MC_Individual(this.length, this.MutationRateUniform, Pattern.getPattern());
        }
        
        this.evaluateParents();
        this.evaluateOffsprings();
        this.noOfFlips = 0;
    }
  
    /****
     * obsolete
     * @throws CloneNotSupportedException 
     */
    @Override
    public void replaceRamdon() throws CloneNotSupportedException
    {
        for (MC_Individual offspring : (MC_Individual[]) this.getOffsprings())
        {
            Double pos = Math.random() *  (this.getParents().length - 1);
            this.getParents()[pos.intValue()] = (MC_Individual) offspring.clone();     
        }
    }

    /***
     * obsolete
     * @throws CloneNotSupportedException 
     */
    @Override
    public void replaceWeakest() throws CloneNotSupportedException
    {
        int pos = 0;
        for (MC_Individual offspring : (MC_Individual[]) this.offsprings)
        {
            if (this.parents[pos] == null)
            {
                this.parents[pos] = this.getParents()[pos] = (MC_Individual) offspring.clone();
                this.parents[pos].Evaluate();
            }
            else 
            {
                pos = this.getWorseSolutionIndex();
                if (this.getParents()[pos].getFitness() >= offspring.getFitness())
                {
                    this.getParents()[pos] = (MC_Individual) offspring.clone();
                    this.getParents()[pos].Evaluate();
                }
            }
        }
        this.setNoOfEvaluations(this.noParents);
    }

    /***
     * obsolete
     * @throws CloneNotSupportedException 
     */
     @Override public void getBestParentsForReproduction() throws CloneNotSupportedException
    {
        if (this.parents.length > 1)
        {
            int indexBest = getBestParentIndex();
            int SecondBest = this.noParents-1;
          
            for (int i = 0; i < this.parents.length; i++)
            {
                if (i != indexBest)
                {
                    if (this.parents[i].getFitness() > this.parents[SecondBest].getFitness())
                    {
                        SecondBest = i;   
                    }
                } 
            }
             
           for (int i = 0; i <  this.offsprings.length; i++)
            {
                 if ((i % 2) == 0)
                 {
                    this.offsprings[i] = (MC_Individual) this.getParents()[indexBest].clone();
                 }
                 else 
                 {
                    this.offsprings[i] = (MC_Individual) this.getParents()[SecondBest].clone();
                 }
            }
        }
        else 
        {
            for (int i = 0; i <  this.offsprings.length; i++)
            {
                this.offsprings[i] = (MC_Individual) this.getParents()[0].clone();
            }
        }
    }

    protected int getBestOffspringIndex()
    {
        int indexBest = 0;
        for (int i = 0; i < this.offsprings.length; i++)
        {
            if (this.offsprings[indexBest].getFitness() > this.offsprings[i].getFitness())
            {
                indexBest = i;
            }
        }
        return indexBest;
    }
    
     
    protected int getBestParentIndex()
    {
        int indexBest = 0;
        for (int i = 0; i < this.parents.length; i++)
        {
            if (this.parents[i].getFitness() > this.parents[indexBest].getFitness())
            {
                indexBest = i;
            }
        }
        return indexBest;
    }
    
    protected int getBestCandidateIndex()
    {
        int indexBest = 0;
       
        for (int i = 0; i < this.offsprings.length; i++)
        {
            if (this.offsprings[i] != null & this.offsprings[indexBest] != null)
            {
                if (this.offsprings[i].getFitness() < this.offsprings[indexBest].getFitness())
                {
                    indexBest = i;
                }
            }
        }
        return indexBest;
    }
    
    public double getBestParentFitness()
    {
        return this.getBestSolution().getFitness();
    }
    
    public double getBestOffspringFitness()
    {
        return this.offsprings[this.getBestOffspringIndex()].getFitness();
    }
  
    public MC_Individual getBestSolution()
    {
        return parents[this.getBestParentIndex()];
    }
 
    public MC_Individual[] getOffsprings()
    {
        return (MC_Individual[]) this.offsprings;
    }
    
    public Double[] getOffspringFitnessValue()
    {
        Double[] result = new Double[this.offsprings.length];
        for (int i = 0; i < this.offsprings.length; i++)
        {
            result[i] = this.offsprings[i].getFitness();
        }
        return result; 
    }
    
    public Double[] getParentsFitnessValue()
    {
        Double[] result = new Double[this.parents.length];
        for (int i = 0; i < this.parents.length; i++)
        {
            result[i] = this.parents[i].getFitness();
        }
        return result; 
    }
    
    public MC_Individual[] getParents()
    {
        return (MC_Individual[]) this.parents;
    }
    
    public void mutateBitsInASubString(int evalLeft) throws CloneNotSupportedException
    {   
       int noEval = 0;
       for (int i = 0; i < this.offsprings.length; i++)
       {
           this.offsprings[i] = (MC_Individual) this.offsprings[this.getBestOffspringIndex()].clone();
           this.offsprings[i].mutateASubstringClimbing(evalLeft);
           noEval += this.offsprings[i].getNoEval();
        }
        this.setNoOfEvaluations(noEval);
    }
    
    public void mutateBitFlipHillClimbing(int evalLeft) throws CloneNotSupportedException
    {
       int noEval = 0;
       for (int i = 0; i < this.offsprings.length; i++)
       {
           this.offsprings[i].mutateBitFlipHillClimbing(evalLeft);
           noEval += this.offsprings[i].getNoEval();
       }
      
       this.setNoOfEvaluations(noEval);
    }
    
    public void mutateUniformHillClimbing(int evalLeft) throws CloneNotSupportedException
    {
        int noEval = 0;
        double previousResult;
        for (int i = 0; i < this.offsprings.length; i++)
        {
           previousResult = this.offsprings[i].getFitness();
           
           this.offsprings[i].mutateUniformHillClimbing(evalLeft);
           
           noEval += this.offsprings[i].getNoEval();
        }
        this.evaluateOffsprings();
        this.setNoOfEvaluations(noEval);
        //this.replaceOffsprings();
    }
    
    public void mutateBitFlip() throws CloneNotSupportedException
    {
      
       for (int i = 0; i < this.offsprings.length; i++)
       {
          this.offsprings[i].mutateBitFlip();
          this.noOfFlips++;
       }
       this.evaluateOffsprings();
       this.setNoOfEvaluations(this.offsprings.length);
    }
    
    public void mutateWithVariableRate() throws CloneNotSupportedException
    {
        Double max = 0.0;
        double newMutationRate = 0.0; 
        
        for (MC_Individual offspring : this.offsprings) // each parent is being mutated!
        {
            /*Application of the one-fifth rule essential metaheuristics p. 36*/
            
            if (this.getRatioOfFitterOffsprings() > MC_Operator.ONE_FIFTH_RULE_THRESHOLD)
            {
                newMutationRate = offspring.getVariableMutationRate() + (1.0/this.length);
            }
            else if (this.getRatioOfFitterOffsprings() < MC_Operator.ONE_FIFTH_RULE_THRESHOLD)
            {
                newMutationRate = offspring.getVariableMutationRate() - (1.0/this.length);
            }
            else 
            {
                newMutationRate = offspring.getVariableMutationRate(); 
            }
            
            offspring.setVariableMutationRate(newMutationRate);
            max = newMutationRate * this.length;
            
            for (int i = 0; i < max.intValue(); i++)
            {
                offspring.mutateBitFlip();
                offspring.setStepSize(max.intValue());
                this.noOfFlips++;
            }
        }
       this.evaluateOffsprings();
    }
        
    /**
     *
     * @throws CloneNotSupportedException
     */
    public void crossOverOnePoint() throws CloneNotSupportedException
     {
         
       if (this.offsprings!= null & this.parents != null)
       {
           boolean [] firstBinary;
           boolean [] secondBinary; 
           int bestParent = this.getBestParentIndex();
           
           firstBinary = Arrays.copyOf(this.parents[bestParent].getBinary(), this.length);
           
           for (int i = 0; i < this.offsprings.length; i++)
           {
               secondBinary = Arrays.copyOf(this.offsprings[i].getBinary(), length);
               
               this.offsprings[i].setBinary(this.offsprings[i].getBitStringOp().applyCrossOverOnePoint(firstBinary, secondBinary));
           }
           this.evaluateOffsprings();
           this.setNoOfEvaluations(this.offsprings.length);
           //this.replaceOffsprings();
         } 
      
               
    }// end function
    
     public void crossOverTwoPoints()
     {
         
       if (this.offsprings!= null & this.parents != null)
       {
           boolean [] firstBinary;
           boolean [] secondBinary; 
           int bestParent = this.getBestParentIndex();
           
           firstBinary = Arrays.copyOf(this.parents[bestParent].getBinary(), this.length);
           
           for (int i = 0; i < this.offsprings.length; i++)
           {
               secondBinary = Arrays.copyOf(this.offsprings[i].getBinary(), length);
               this.offsprings[i].setBinary((this.offsprings[i].getBitStringOp().applyCrossOverTwoPoints(firstBinary, secondBinary)));
           }
           this.evaluateOffsprings();
           this.setNoOfEvaluations(this.offsprings.length);
           //this.replaceOffsprings();
         }         
    }// end function
    
     public void crossOverTwoPointsBestSolution() throws CloneNotSupportedException
     {
         
       
    }// end function
    
    public void crossOverUniform()
    {
           boolean [] firstBinary;
           boolean [] secondBinary; 
           int bestParent = this.getBestParentIndex();
           
           firstBinary = Arrays.copyOf(this.parents[bestParent].getBinary(), this.length);
           
           for (int i = 0; i < this.offsprings.length; i++)
           {
               secondBinary = Arrays.copyOf(this.offsprings[i].getBinary(), length);
               
               this.offsprings[i].setBinary(this.offsprings[i].getBitStringOp().applyCrossOverUniform(firstBinary, secondBinary));
           }
           this.evaluateOffsprings();
           this.setNoOfEvaluations(this.offsprings.length);
     }
    
    
   
    @Override
     protected void evaluateOffsprings()
    {
        for (int i=0; i < this.offsprings.length;i++)
        {
            this.offsprings[i].Evaluate();
            this.recordCurrentSolution(i);
        }
        this.setNoOfEvaluations(this.offsprings.length);
    }
     
     
     
    @Override
     protected void evaluateParents()
    {
        for (int i=0; i < this.parents.length;i++)
        {
            this.parents[i].Evaluate();
        }
    }
   
     protected int getWorseSolutionIndex()
    {
        int indexOfLeastFit =0;
        
        for (int i = 0; i < this.parents.length; i++)
        {
            if (this.parents[indexOfLeastFit].getFitness() < this.parents[i].getFitness())
            {
                indexOfLeastFit = i;
            }
        }
        return indexOfLeastFit;
    }
    /***
     * retrieve the best solution index. The best solution has a fitness that is the highest.
     * @return index of parents solution with the highest fitness 
     */
    public int getBestSolutionIndex()
    {
        int indexOfFittest = 0;
        for (int i = 1; i < this.parents.length; i++)
        {
            if (this.parents[indexOfFittest].getFitness() <= this.parents[i].getFitness())
            {
                indexOfFittest = i;
            }
        }
      //  System.out.println("***" + indexOfFittest);
        return indexOfFittest;
    }

     public int getNoOfFlips()
    {
        return this.parents[this.getBestSolutionIndex()].getNoOfFlips();
    }
    
     
  

    @Override public String toString()
    {
        String s = "parent(s) "; 
        
        for (int i = 0; i < this.parents.length; i++)
        {
            if (this.parents[i] != null)
            {
                s+= "," + this.parents[i].getFitness() + "(" + this.parents[i].getVariableMutationRate() + ")";
            }
        }
        
        s += " offspring(s) "; 
        for (int i = 0; i < this.offsprings.length; i++)
        {
             if (this.offsprings[i] != null)
             {
                s+=  "," + + this.offsprings[i].getFitness() + "(" + this.offsprings[i].getVariableMutationRate() + ")";
             }
        }
        s+= "\n";
        return s;
    }  

    private void recordCurrentSolution(int offspring)
    {
        //System.out.println (Arrays.toString(this.SolutionsQuality));
        if (offspring >= 0 & offspring <= this.offsprings.length)
        {
            if (this.lastIndex >=0 & this.lastIndex < this.SolutionsQuality.length)
            {
                if (this.offsprings[offspring].getGradient() == 0.0)
                {
                    this.SolutionsQuality[lastIndex] = 0; //ok
                }
                else if (this.offsprings[offspring].getGradient() > 0.0)
                {
                    this.SolutionsQuality[lastIndex] = -1; // worse 
                }
                else if (this.offsprings[offspring].getGradient() < 0.0)
                {
                    this.SolutionsQuality[lastIndex] = 1; // good 
                }
            
                if (this.lastIndex == (this.SolutionsQuality.length-1))
                {
                     this.lastIndex = 0;
                }
                else 
                {
                    this.lastIndex++;
                }
            }
        }
       // System.out.println (Arrays.toString(this.SolutionsQuality));
        
    }
    
    
    private double getRatioOfFitterOffsprings()
    {
        double total =  0;
        for(int i = 0; i < this.SolutionsQuality.length; i++)
        {
            if (this.SolutionsQuality[i] == 1)
            {
                total++;
            }
        }
        return total/this.SolutionsQuality.length;
    }
    
    
}
