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
public class MC_Herdy extends MC_Operator       
{
    private int noMaxEval;
    private int generation;

    
    public MC_Herdy(double MutationRate, long timeStamp, int noParents, int noOffsprings, int length, int MaxEval)       
    {
        super(MutationRate, timeStamp, noParents, noOffsprings,0, length);
        this.noMaxEval = MaxEval;
        this.generation = 0;
    }

    public int getNoMaxEval()
    {
        return noMaxEval;
    }
    
    public int getGeneration()
    {
        return generation;
    }
   
 /*   @Override public void crossOverUniform()
    {
        if (this.offsprings != null & this.parents != null)
        {    
                boolean[] offspring0;
                boolean value;
                offspring0 = new boolean[this.length];
                
                for(int i = 0; i < this.offsprings[0].getBinary().length-1; i++)
                {
         
                   if (r.nextBoolean())
                   {
                       value = this.parents[0].getBinary(i,i+1)[0];
                     //  System.arraycopy(this.parents[0].getBinary(i,i+1), i, offspring0, i, 1);
                        
                   }
                   else 
                   {
                        value = this.offsprings[0].getBinary(i,i+1)[0];
                    
                        //System.arraycopy(this.offsprings[0].getBinary(i,i+1), i, offspring0, i, 1);
                   } 
                   offspring0[i] = value;
                }// end for i
                this.offsprings[0].setBinary(offspring0);
                this.evaluateOffsprings();
                this.setNoOfEvaluations(this.offsprings.length);
        } 
       
     } */
    
    
  
     public void executeAlgorithm() throws CloneNotSupportedException
     {
         super.initPop();
         System.out.println(this.toString());
         this.generation = 0;
         System.out.print(this.generation +  " " + this.toString());
        
         this.noMaxEval = this.noMaxEval - 2;
         
         while ((this.noMaxEval > 0) & (this.parents[0].getFitness() > 0))
         {
             super.crossOverUniform();
             super.mutateBitFlip();
             this.noMaxEval -= 2;
             this.replaceWeakest();
             super.getBestParentsForReproduction();
           
           
             this.generation++;
             System.out.print(this.generation +  " " + this.toString());
            
         }
         
        
     }
   
    
}
