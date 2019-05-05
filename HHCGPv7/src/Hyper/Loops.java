/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.ArrayList;
import java.util.Arrays;

/**
 *
 * @author patriciaryser-welch
 */
public class Loops
    {
        /***
         * index in loop array. The cell at address 0 holds the START index of the loop
         */
        private final static short START = 0;
        /***
         * index in loop array. The cell at address 1 holds the END index of the loop
         */
        private final static short END = 1;
       
        /***
         * index in a loop array. The cell is for the counter. 
         */
        private final static short COUNTER = 2;
        
         /***
         * List of all loops in the coordinates
         */
        private ArrayList<int[]> loops;

    public void setLoops(ArrayList<int[]> loops)
    {
        this.loops = loops;
    }
         
        public Loops()
        {
            this.loops = new ArrayList();
        }
        
        public void AddLoop(int startIndex, int endIndex)
        {
            this.loops.add(this.getALoop(startIndex, endIndex));
        }
        
        public void DeleteLoop(int startIndex, int endIndex)
        {
            int index = this.FindIndexOfLoop(startIndex);
            if (index >= 0 & index < this.loops.size())
            {
                this.loops.remove(index);
            }
        }
          
       
        /***
         * check whether or not a loop is illegal. An illegal loop, is one that cut at least an existing one. 
 ie it starts before an existing loop and END in the middle of one.
         * @param startIndex
         * @param endIndex
         * @return true if the loop is illegal, otherwise false
         */
        public boolean isLoopIllegal(int startIndex, int endIndex)
        {
            if (this.loops.size() == 0)
            {
                return false;
            }
            
            for (int[] loop: this.loops)
            {
               if (loop[START] < startIndex & loop[END] <= endIndex)
               {
                   return true;
               }
               else if(endIndex == loop[START])
               {
                   return true;
               }
               else if (endIndex == loop[END])
               {
                   return true;
               }
            }
            
            return false;
        }
        
        public int getStartIndex(int endIndex)
        {
            for (int[] loop: this.loops)
            {
                if(loop[END] == endIndex)
                {
                    return loop[START];
                }
            }
            
            return -1;
        }
        
        public int getEndIndex(int startIndex)
        {
            for (int[] loop: this.loops)
            {
                if(loop[START] == startIndex)
                {
                    return loop[END];
                }
            }
            
            return -1;
        }
        
        public int getNumberOfLoops()
        {
            return this.loops.size();
        }
        
        public int getIndexOfALoop(int startIndex)
        {
           return this.FindIndexOfLoop(startIndex);
        }
        
        protected int[] getALoop(int startIndex, int endIndex)
        {
            int[] loop = new int[3];
            loop[START] = startIndex;
            loop[END] = endIndex;
            loop[COUNTER] = 0;
            return loop;
        }
        

        protected int FindIndexOfLoop(int startIndex, int endIndex)
        {
           for (int i = 0; i < this.loops.size(); i++)
            { 
                if (this.loops.get(i)[START] == startIndex & this.loops.get(i)[END] == endIndex)
                {
                    return i;
                }
            }
            
            return -1;
        }
        
        protected int FindIndexOfLoop(int startIndex)
        {
            
            for (int i = 0; i < this.loops.size(); i++)
            { 
                if (this.loops.get(i)[START] == startIndex)
                {
                    return i;
                }
            }
            
            return -1;
           
        }
        
        public ArrayList<int[]> getLoops()
        {
            return this.loops;
        }
        
        public void increaseCounterOfALoop(int startIndex, int endIndex)
        {
            int index = this.FindIndexOfLoop(startIndex);
            if(index > -1 &  index < this.loops.size())
            {
                this.loops.get(index)[COUNTER] ++;
            }
        }
        
        public void setCounterOfALoop(int startIndex, int endIndex, int newValue)
        {
            
            int index = this.FindIndexOfLoop(startIndex);
            if(index > -1 &  index < this.loops.size())
            {
                this.loops.get(index)[COUNTER] = newValue;
              
            }
        }
        
       
        
        public boolean isCounterLowerThanMaxValue (int startIndex, int endIndex, int MaxValue)
        {
              int index = this.FindIndexOfLoop(startIndex);
              if (index > - 1)
              {
                  return (this.getCounterOfALoop(startIndex, endIndex) < MaxValue);
              }
              else 
              {
                  return false;
              }
        }       
        public int getCounterOfALoop(int startIndex, int endIndex)
        {
            
            int index = this.FindIndexOfLoop(startIndex);
            if (index > -1)
            {
                return this.loops.get(index)[COUNTER];
            }
            else 
            {
                return -1;
            }
            
        }
        
        @Override public String toString()
        {
            String s = "";
            for (int[] loop : this.loops)
            {
                s+= "\n Loop " + loop[START] + " - " + loop[END] +  " - " + loop[COUNTER];
            }
            return s;
        }  

        @Override
       protected Object clone() throws CloneNotSupportedException
       {
           Loops clone = new Loops();
           ArrayList<int[]> loopsClone = new ArrayList();
           for(int[] loop: this.loops)
           {
               loopsClone.add(loop.clone());
           }
           clone.setLoops(loopsClone);
           return loopsClone;
       }
        
       
        
    } // END loop class
    
