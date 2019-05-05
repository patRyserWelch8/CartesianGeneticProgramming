/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.Arrays;

/**
 *
 * @author patriciaryser-welch
 */
public class MutationPerformanceIterative
{
        public static final double TERRIBLE_PERFORMANCE = -2.0;
        public static final double POOR_PERFORMANCE = -1.0;
        public static final double STATIC_PERFORMANCE = 0.0;
        public static final double GOOD_PERFORMANCE = 1.0;
        public static final double EXCELLENT_PERFORMANCE = 2.0;
        
        private double[] mutationFitnessValues;
        private int[] counterPerformance;
        private int counter;
        private double total;
        private boolean full;
        
        
        public MutationPerformanceIterative (int noOfFitnessValues)
        {
           this.mutationFitnessValues = new double[noOfFitnessValues];  
           this.counter = 0;
           this.total = 0;
           this.counterPerformance = new int[5];
           this.full= false;
        }
        
        public void setAMutationFitnessValue(IterativeCoordinates parent, IterativeCoordinates offspring, int gapSinceLastUpgrade)
        {
              double mutationFitnessValue = 0;
              if (parent != null & offspring != null)
              {
                //  System.out.println("SET A MUTATION FITNESS VALUE " + gapSinceLastUpgrade);
                //  System.out.println((gapSinceLastUpgrade >= (3 * this.mutationFitnessValues.length)) + " " + gapSinceLastUpgrade);
                 /* if (gapSinceLastUpgrade >= (3 * this.mutationFitnessValues.length)) 
                 
                  else 
                  { */
                      if (!offspring.hasActiveNodeBeenChanged(parent))
                      {
                            if (offspring.getAFitnessValue(0) <= parent.getAFitnessValue(0))
                            {
                                if (this.getNoOfPromotedOffsprings() >= ((int)(this.mutationFitnessValues.length * 0.60)))
                                {
                                    mutationFitnessValue = MutationPerformanceIterative.EXCELLENT_PERFORMANCE;
                                    counterPerformance[4] ++;
                                }
                                else 
                                {
                                    mutationFitnessValue = MutationPerformanceIterative.GOOD_PERFORMANCE;
                                    counterPerformance[3] ++;
                                }
                            }
                            else 
                            {
                                mutationFitnessValue = MutationPerformanceIterative.STATIC_PERFORMANCE;
                                counterPerformance[2] ++;
                            }
                      }
                      else if (gapSinceLastUpgrade >= (3 * this.mutationFitnessValues.length)) 
                      {
                           
                            mutationFitnessValue = MutationPerformanceIterative.TERRIBLE_PERFORMANCE; 
                            counterPerformance[0] ++;
                      }
                      else 
                      {
                          mutationFitnessValue = MutationPerformanceIterative.POOR_PERFORMANCE;
                          counterPerformance[1] ++;
                      }   
                          
                 // }
              }
           //   System.out.print("SET A MUTATION FITNESS VALUE " + mutationFitnessValue);
              total += mutationFitnessValue;
              System.out.println ("Mutation Value " + mutationFitnessValue);
              this.setAMutationFitnessValue(mutationFitnessValue);
              
       //       System.out.println(" **** " + this.g + " " + this.getCurrentValueOfCounter() + " " + counter);
        }
       
        private void setAMutationFitnessValue(double FitnessValue)
        {
            if (this.getCurrentValueOfCounter() < this.mutationFitnessValues.length)
            {
                this.mutationFitnessValues[counter] = FitnessValue;
            }
           //System.out.println (Arrays.toString(this.mutationFitnessValues));
        }
        
        public int getCurrentValueOfCounter()
        {
            return this.counter;
        }
        
        public double[] getMutationFitnessValues()
        {
            return this.mutationFitnessValues;
        }
        
        public void increaseCounter()
        {
            if (this.counter < (this.mutationFitnessValues.length))
            {
                this.counter++;
            }
            
            if (this.counter >= (this.mutationFitnessValues.length))
            {
                this.full = true;
            }
            else
            {
                this.full = false;
            }          
        }
        
        public void resetCounter()
        {
            this.counter = 0;
            this.mutationFitnessValues = new double [this.mutationFitnessValues.length];
            this.counterPerformance = new int[5];
            this.total = 0;
        }
        
        public int getMaxNoFitnessValues()
        {
           return this.mutationFitnessValues.length;
        }
        
        public double getMutationFitnessValue()
        {
            if (this.mutationFitnessValues.length > 0)
            {
            //    System.out.println (" ***** " + this.mutationFitnessValues.length + " " + this.total);
                return this.total/this.mutationFitnessValues.length;
            }
            else 
            {
                return 0;
            }
        }
        
        public boolean hasAllFitnessValuesBeenInput()
        {
            return this.full;
        }
        
        public int getNoOfPromotedOffsprings()
        {
            return this.counterPerformance[3] + this.counterPerformance[4];
        }
        
        public int getNoOfOffspringsWithNoNewSequences()
        {
            return this.counterPerformance[1];
        }
        
        public int getNoOfDifferentProblemSolverWithNoBetterPerformance()
        {
            return this.counterPerformance[2];
        }
        
        public int getNoOfOffspringsWithTerriblePerformance()
        {
            return this.counterPerformance[0];
        }
        
        public boolean hasATerriblePerformance()
        {
            Arrays.sort(this.mutationFitnessValues);
            return (Arrays.binarySearch(this.mutationFitnessValues, MutationPerformanceIterative.TERRIBLE_PERFORMANCE) > -1);

        }
    }// end of MutationPerformanceIterative