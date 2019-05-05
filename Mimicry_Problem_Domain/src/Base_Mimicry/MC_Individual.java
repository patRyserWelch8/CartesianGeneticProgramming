/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Base_Mimicry;

/**
 *
 * @author patriciaryser-welch
 */
public class MC_Individual extends BaseLibraries.BinaryIndividual
{
    private boolean[] pattern;
    private final int numberOfSubstring = 4;
    private double previousFitness;
    private int noEval;
    private int stepSize;
    private double mutationRate;
    private double variableMutationRate;
    
    public MC_Individual(int length, double aMutationRate)
    {
        super(length);
        this.pattern = super.bitStringOp.generateDNA(super.Length);
        this.Binary = super.bitStringOp.generateDNA(super.Length);
        this.previousFitness = Double.POSITIVE_INFINITY;
        this.mutationRate = aMutationRate;
        this.setVariableMutationRate(mutationRate);
    }
    
    public MC_Individual(int length, double aMutationRate, boolean[] aPattern)
    {
        this(length, aMutationRate);
        this.pattern = aPattern; 
    }
    
    public MC_Individual(boolean[] Binary, boolean[] aPattern, int length, double aMutationRate, 
                         double aVariableMutationRate, double aFitness, 
                         int noOfFlips, double aPreviousFitness)
    {
        this(Binary.clone(), aPattern.clone(), length, aMutationRate);
        this.Fitness = aFitness;
        this.noOfFlips = noOfFlips;
        this.previousFitness = aPreviousFitness;  
        this.variableMutationRate = aVariableMutationRate;
    }
 
    public MC_Individual(boolean[] Binary, boolean[] aPattern, int length, double aMutationRate)
    {
        super(Binary.clone(), length);
        this.pattern = aPattern.clone();
        this.mutationRate = aMutationRate;
    }
  
     
    @Override public Object clone() throws CloneNotSupportedException 
    {
        MC_Individual copy = new MC_Individual (this.getBinary(), this.getPattern(),this.getBinary().length, this.mutationRate, this.variableMutationRate, this.getFitness(), noOfFlips, this.previousFitness);
        copy.setLength(this.Length);
        copy.setBinary(this.getBinary());
        copy.setFitness(this.getFitness());
        copy.setNoOfFlips(this.noOfFlips);
        return copy;
    }
 
    public boolean[] getPattern()
    {
        return pattern;
    }
    
    public void setPreviousFitness(double previousFitness)
    {
        this.previousFitness = previousFitness;
    }
   
    public double getGradient()
    {
        return (this.Fitness - this.previousFitness)/this.Length;
    }
  
   /***
    * Calculate the hamming distance. The difference between each string
    */
    @Override public void Evaluate()
    {
        double total = 0;
        this.previousFitness = this.Fitness;
        if(this.pattern.length == this.Binary.length)
        {
            for (int i = 0; i < this.Binary.length;i++)
            {
                if (this.Binary[i] != this.pattern[i])
                {
                    total++;
                }
            }
        }
        this.setFitness((double) total/this.Length);
    }
    
    public boolean hasImproved()
    {
        return (this.previousFitness - this.Fitness) > (double) 0.0 ;
    }
    
    /**
     *
     */
     public void mutateUniform() throws CloneNotSupportedException
    {
        this.setSubBitStringRandomly();
                
        for (int pos = start; pos <= end; pos++)
        {
            this.mutateBitFlip(pos);
        }  
        this.Evaluate();
        this.setNoEval(1);         
    }
    
    public void mutateUniformHillClimbing(int evalLeft) throws CloneNotSupportedException
    {
        MC_Individual copyIndividual; 
        int noEval = 0;
        this.setSubBitStringRandomly();
        copyIndividual = (MC_Individual) this.clone();
        int pos = start;
                
        while ((pos <= end) || ((evalLeft-noEval) >0))
        {
                 copyIndividual.mutateBitFlip(pos);
                 copyIndividual.Evaluate();
                 noEval++;
                 if (copyIndividual.hasImproved())
                 {
                     System.arraycopy(copyIndividual.getBinary(), 0, this.Binary, 0, Length);
                     this.setFitness(copyIndividual.getFitness());
                 }
                 else 
                 {
                     copyIndividual = (MC_Individual) this.clone();
                 }
                 pos++;
        }    
        this.setNoEval(noEval);         
    }
    
    public void mutateASubstringClimbing(int evalLeft) throws CloneNotSupportedException
    {
        MC_Individual copyIndividual; 
        int noEval = 0;
        this.setSubBitStringRandomly();
        copyIndividual = (MC_Individual) this.clone();
        int pos = start;
                
        while ((pos <= end) || ((evalLeft-noEval) > 0))
        {
                 copyIndividual.mutateBitFlip(pos);
                 copyIndividual.Evaluate();
                 noEval++;
                 if (copyIndividual.hasImproved())
                 {
                     System.arraycopy(copyIndividual.getBinary(), 0, this.Binary, 0, Length);
                     this.setFitness(copyIndividual.getFitness());
                 }
                 else 
                 {
                     copyIndividual = (MC_Individual) this.clone();
                 }
                 pos++;
        }    
        this.setNoEval(noEval);         
    }
    
    @Override public String toString()
    {
        String s = "<PATTERN_INDIVIDUAL       >";
        for (int i = 0; i < this.pattern.length;i++)
        {
            if (this.pattern[i])
                s += "1";
            else 
                s += "0";
        }
        s += "</PATTERN_INDIVIDUAL>\n";

         return s + super.toString();
    }
    
    

    public void setNoOfFlips(int noOfFlips)
    {
        this.noOfFlips = noOfFlips;
    }
    
    /**
     *
     * @param iterations
     * @throws CloneNotSupportedException
     */
     public void mutateBitFlipHillClimbing(int evalLeft) throws CloneNotSupportedException 
     {
         MC_Individual copyIndividual = (MC_Individual) this.clone();
         int noEval = 0;
         int maxEval = 4;
         this.noOfFlips = this.numberOfSubstring;
         boolean stop = false;
         int i = 0; 
          
         while (!stop)
         {
             copyIndividual = (MC_Individual) this.clone();
             copyIndividual.mutateBitFlip();
             copyIndividual.Evaluate();
             noEval++;
             if (copyIndividual.hasImproved())
             {
                 this.setBinary(copyIndividual.getBinary().clone());
                 this.setFitness(copyIndividual.getFitness());
                 stop = true;
             }
             else if (noEval >= maxEval)
             {
                 stop = true;
             }
             else if ((evalLeft - noEval) <= 0)
             {
                 stop = true;
             }
             else 
             {
                 i++;
             }
         } 
        
         this.noEval = noEval;
 
     }
  
    public void setNoEval(int noEval)
    {
        this.noEval = noEval;
    }

    public int getNoEval()
    {
        return noEval;
    }
   
    public int getAbsoluteHammingWeightDifference()
    {
        int HammingWeightPattern = 0;
        int HammingWeightImitator = 0;
        int i;
        for (i = 0; i < this.pattern.length; i++)
        {
            if (this.pattern[i])
            {
                HammingWeightPattern++;
            }
        }
        
         for (i = 0; i < this.Binary.length; i++)
         {
             if (this.Binary[i])
             {
                 HammingWeightImitator++;
             }
         }
         
         return Math.abs(HammingWeightPattern - HammingWeightImitator);
    } 
    
    protected void setSubBitStringRandomly()
    {
        int noFlip = (int) Math.round(this.mutationRate * this.Length-1); 
        if (noFlip < 1)
        {
            noFlip = 1;
        }
      
        start = super.bitStringOp.getRandomPosInRange(0, this.Length -1);
        if (start > (this.Length - noFlip - 1))
        {
            end = this.Length-1;
        }
        else 
        {
            end = start + noFlip;
        }
    } 
    
    /**
     * Get the value of stepSize
     *
     * @return the value of stepSize
     */
    public int getStepSize()
    {
        return stepSize;
    }

    /**
     * Set the value of stepSize
     *
     * @param stepSize new value of stepSize
     */
    public void setStepSize(int stepSize)
    {
        this.stepSize = stepSize;
    }  
    
     /**
     * Get the value of variableMutationRate
     *
     * @return the value of variableMutationRate
     */
    public double getVariableMutationRate()
    {
        return variableMutationRate;
    }

    /**
     * Set the value of variableMutationRate
     *
     * @param variableMutationRate new value of variableMutationRate
     */
    public void setVariableMutationRate(double variableMutationRate)
    {
        if (variableMutationRate < (1/this.Length))
        {
            this.variableMutationRate = 1/this.Length;
        }
        else 
        {
            this.variableMutationRate = variableMutationRate;
        }
    }
}


