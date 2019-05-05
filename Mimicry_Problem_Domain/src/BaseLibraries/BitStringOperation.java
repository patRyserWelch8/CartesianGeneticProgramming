/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseLibraries;

import Base_Mimicry.MC_Individual;
import java.util.Random;

/**
 *
 * @author patriciaryser-welch
 */
public class BitStringOperation
{
    private final Random r;
    
    public BitStringOperation()
    {
         this.r = new Random(System.currentTimeMillis());
    }
    
    public boolean[] applyCrossOverTwoPoints(boolean[] firstBinary, boolean[] secondBinary)
    {
         boolean[] offspring0;
         offspring0 = new boolean[firstBinary.length];
         int point1, point2 = 0;
            
         point1 = this.getRandomPos(firstBinary);
         point2 = this.getRandomPosInRange(point1+1, firstBinary.length-1);
         
         System.arraycopy(firstBinary, 0, offspring0,0, point1);
         System.arraycopy(secondBinary, point1, offspring0, point1, point2 - point1);
         System.arraycopy(firstBinary, point2, offspring0, point2, firstBinary.length- point2);
         return offspring0;
     }
    
    public boolean[] applyCrossOverUniform(boolean[] firstBinary, boolean[] secondBinary)
    {
         boolean[] offspring0;
         offspring0 = new boolean[firstBinary.length];
         for(int i = 0; i < firstBinary.length; i++)
         {
            if (r.nextBoolean())
             {
                 offspring0[i] = firstBinary[i];
             }
             else
             {
                 offspring0[i] = secondBinary[i];
             }
         }
         return offspring0;
     }
   
    public boolean[] applyCrossOverOnePoint(boolean[] firstBinary, boolean[] secondBinary)
    {
         boolean[] offspring0;
         offspring0 = new boolean[firstBinary.length];
         int point1, point2 = 0;
            
         point1 = this.getRandomPos(firstBinary);
         
         System.arraycopy(firstBinary, 0, offspring0,0, point1);
         System.arraycopy(secondBinary, point1, offspring0, point1, firstBinary.length - point1);
      //   System.arraycopy(firstBinary, point2, offspring0, point2, this.length- point2);
         return offspring0;
    }
   
    public int getRandomPos(boolean[] Binary)
    {
      return getRandomPosInRange (0, Binary.length-1);
        
    }
    /***
     * A Gaussian random number is used to guarantees some random numbers different all the time. Also, 
     * it helps to find random values between 0 and length -1 more effectively. Otherwise the spread of number is too narrow.
     * @param start
     * @param end
     * @return 
     */
    public int getRandomPosInRange(int start, int end)
    {
     
        double l = (double) (end - start);
        double d = Math.round(Math.random() * (end - start)) + start;
        if (d < 0)
        {
             d = d * -1;
        }
        return (int) d;
    }
    
    public boolean[] generateDNA(int length)
    {
        r.setSeed((long) (System.currentTimeMillis() * Math.random()));
        
        boolean[] result = new boolean[length];
        
        for (int i = 0; i < result.length; i++)
        { 
            result[i] = this.r.nextBoolean();
        } 
      
       return result;
    } // end generateDNA  
    
    public boolean[] bitFlip(boolean[] Binary)
    {
       int pos = this.getRandomPos(Binary);
       return this.bitFlip(Binary, pos);
    }
   
    public boolean[] bitFlip(boolean[] Binary, int pos)
    {
       if (pos >=0 & pos < Binary.length)
       {
            Binary[pos] = !Binary[pos];
       }
       return Binary;
    }
    
    public boolean[] FlipSubStringsHillClimbing(boolean[] subString, boolean[] subPattern)
    {
        for (int i = 0; i < subString.length; i++)
        {
           //System.out.println(p);
           if (subString[i] != subPattern[i])
           {
               subString[i] = !subString[i];
           }
        } 
        return subString;
    }
   
    
}
