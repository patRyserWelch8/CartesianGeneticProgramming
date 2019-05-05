/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package BaseLibraries;

import java.util.Random;

/**
 *
 * @author patriciaryser-welch
 */
public abstract class BinaryIndividual 
{
    protected BitStringOperation bitStringOp; 
    protected boolean[] Binary;
    protected double Fitness;
    protected  int Length;
    protected int start;
    protected int end;
    protected int noOfFlips;

    
   
    public BinaryIndividual(int length) 
    {
        this.bitStringOp = new BitStringOperation();
        this.Binary = new boolean[length];
        this.Length = length;   
        System.arraycopy(this.bitStringOp.generateDNA(this.Length),0, this.Binary,0, this.Length);
        this.Fitness = Integer.MAX_VALUE;   
    }
    
    public BinaryIndividual(boolean[] Binary, int length) 
    {
        this(length);
        this.setBinary(Binary);
    }
   
   
    /**
     * Get the value of Fitness
     *
     * @return the value of Fitness
     */
    public double getFitness() 
    {
        return Fitness;
    }

    /**
     * Set the value of Fitness
     *
     * @param Fitness new value of Fitness
     */
    public void setFitness(double Fitness) 
    {
        this.Fitness = Fitness;
    }


    /**
     * Get the value of Binary
     *
     * @return the value of Binary
     */
    public boolean[] getBinary()
    {
        return Binary;
    }
    
    public boolean[] getBinary(int Start, int End)
    {
   //    System.out.println(Start + ";" + End);
       boolean[] temp = new boolean[End - Start]; 
       System.arraycopy(this.Binary, Start, temp, 0,End - Start);
       return temp;
    }
    
    /**
     * Set the value of Binary
     *
     * @param Binary new value of Binary
     */
    public void setBinary(boolean[] Binary) 
    {
         if (Binary != null)
         {
            System.arraycopy(Binary,0, this.Binary, 0, Binary.length);
         }
    }  
    
    public void setBinary(boolean[] Binary, int StartGene)
    {
        if (Binary != null & (StartGene + Binary.length) < this.Binary.length)
        {
            System.arraycopy(Binary,0, this.Binary, StartGene, StartGene + Binary.length);
        }
    }

    @Override public String toString() 
    {
        String s = "<TT_INDIVIDUAL Fitness=" + this.Fitness + ">";
        for (int i = 0; i < this.Binary.length;i++)
        {
            if (this.Binary[i])
                s += "1";
            else 
                s += "0";
        }
        s += "</TT_INDIVIDUAL>\n";

        return s;    
    }// end toString.
    
    public abstract void Evaluate();
  
    public void mutateBitFlip(int pos)
    {
       this.Binary = this.bitStringOp.bitFlip(Binary,pos);
    }
   
    public void mutateBitFlip()
    {
       this.Binary = this.bitStringOp.bitFlip(Binary);
    }
   

    public void setLength(int Length) 
    {
        this.Length = Length;
    }
    
    public int getNoOfFlips()
    {
        return this.noOfFlips;
    }

    public BitStringOperation getBitStringOp()
    {
        return bitStringOp;
    }

    
   

  }
