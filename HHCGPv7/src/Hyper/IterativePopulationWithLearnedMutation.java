/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Raven
 */
public class IterativePopulationWithLearnedMutation  extends IterativePopulation
{
    
     private ArrayList<IterativeCoordinatesWithLearnedMutation> parents;
    /***
     * offsprings generated 
     */
    private ArrayList<IterativeCoordinatesWithLearnedMutation> offsprings;
    
    public IterativePopulationWithLearnedMutation(IterativeParameters param) 
    {
        super(param);
        this.offsprings = new ArrayList();
        this.parents = new ArrayList();
    }
    
    public void hyperEvolveWithSavingNewPromotedGraphs() throws CloneNotSupportedException, IOException
    {
        IterativeCoordinatesWithLearnedMutation offspring;
        
        this.hyperInitPopulation(); 
        this.hyperEvaluate();
        this.hyperPromote();
        while(! this.hyperTerminate())
        {
          if (this.OffspringUpgraded)
          {
             Collections.sort(this.parents);
             this.hyperParam.getInterpreter().getResults().WriteResultsIntoFileWithDetails(this.hyperParam.getFileName(),
                                                                                           Integer.toString(iteration),
                                                                                            this.parents.get(0).toStringBestOutput() + " " + this.parents.get(0).getAFitnessValue(0),
                                                                                            "Factory sequential learning");  
             this.OffspringUpgraded = false;
          }
          this.offsprings.clear();
          for (int offspringNo = 0; offspringNo < this.hyperParam.getNoHyperOffSpring(); offspringNo++)
          {  
              offspring = (IterativeCoordinatesWithLearnedMutation) this.parents.get(0).clone();
              
              
              offspring.hyperMutate();
              this.offsprings.add((IterativeCoordinatesWithLearnedMutation)offspring.clone());
            
          }
         
          this.hyperEvaluate();
          this.hyperRecordNewOffsprings();
          
          this.hyperPromote();
        // System.out.println ("Parent" + this.toString());
          
          System.out.println("generation : " + iteration +  "\n " + this.parents.toString());
          iteration++;
        } // end while */
       
   }   
     
   private boolean hyperTerminate()
    {
        if (this.hyperParam.getHyperMaxIterations() <= this.iteration)
        {
            return true;
        }
        
        for (AbstractCoordinates parent: this.parents)
        {
            if (parent.getAFitnessValue(parent.getBestOutput()) <= this.hyperParam.getTargetFitnessValue())
            {
                return true;
            }
        }
        
        return false;     
    }
    
    /**
     * initialise a population of offsprings. In this method, the offspring are mu + lambda
     */
     @Override public void hyperInitPopulation()
    {
        this.offsprings.clear();
        for(int i = 0; i < (this.hyperParam.getNoHyperParents() + this.hyperParam.getNoHyperOffSpring()); i++)
        {
           this.offsprings.add(new IterativeCoordinatesWithLearnedMutation(this.hyperParam));
        // System.out.println(this.offsprings.get(i).toString());
        }
    }
    
    
    /***
    * evaluate the offsprings. Only offsprings are evaluated. 
    * @throws CloneNotSupportedException 
    */
    protected void hyperEvaluate() throws CloneNotSupportedException
    {
        for (int i = 0; i < this.offsprings.size(); i++) 
        {
             boolean canEval = true;
             if (this.parents.size() > 0)
             {
                if (this.offsprings.get(0).hasActiveNodeBeenChanged(this.parents.get(0)))
                {
                    canEval = false;
                }
                else 
                {
                    canEval = true;
                }
             }
             else
             {
                 canEval = true;
             }
             
            if (canEval)
            {
                this.hyperParam.getInterpreter().setCGPGraph((IterativeCoordinates) this.offsprings.get(i).clone(), true);
                for (int output = 0; output < this.hyperParam.getNoOutput(); output++)
                {
                    IterativeCoordinatesWithLearnedMutation o = (IterativeCoordinatesWithLearnedMutation) this.offsprings.get(i).clone();
                    this.hyperParam.getInterpreter().setCGPGraph((IterativeCoordinates) this.offsprings.get(i).clone(), true);
                    this.hyperParam.getInterpreter().calculateHyperFitnessValue();
                    o.setAFitnessValue(output, this.hyperParam.getInterpreter().getHyperFitnessValue(output));
                    this.offsprings.set(i, (IterativeCoordinatesWithLearnedMutation) o); 
                
                    // this.offsprings.set(i, (IterativeCoordinates) this.hyperParam.getInterpreter().getCGPGraph()); 
       
                }
            }
        }
    }
    
     @Override public void hyperPromote() throws CloneNotSupportedException
    {
       Collections.sort(this.offsprings);
       int OffspringNo = 0; 
       if (this.parents.size() == this.hyperParam.getNoHyperParents())
       {
            // parents has some values.
            for (int i = 0; i < this.parents.size(); i++)
            {   
                if (this.parents.get(0).getAFitnessValue(0) > this.offsprings.get(i).getAFitnessValue(0))
                {
                        System.out.println("upgraded");
                        this.OffspringUpgraded = true;
                }
                  
                if (this.parents.get(i).getAFitnessValue(0) >= this.offsprings.get(i).getAFitnessValue(0))
                {
                    this.parents.set(i, this.offsprings.get(i));
                    OffspringNo++;
                }
            }
       }
       else 
       {
          for (int i = 0; i < this.offsprings.size(); i++)
          {   
              if(i == 0)
              {
                  this.parents.add(this.offsprings.get(i));
              }
              else if (i > 0)
              {
                 if (this.parents.get(0).getAFitnessValue(0) == this.offsprings.get(i).getAFitnessValue(0))  // parent is less effective
                 {
                    this.parents.set(0, this.offsprings.get(i));
                 }
              }
              this.OffspringUpgraded = true;
          }
       }
    }// end hyperPromote
    
}
