/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author Raven
 */
public class AcyclicPopulationWithLearnedMutation extends AcyclicPopulation
{
    private ArrayList<AcyclicCoordinatesWithLearnedMutation> parents;
    /***
     * offsprings generated 
     */
    private ArrayList<AcyclicCoordinatesWithLearnedMutation> offsprings;
    
    public AcyclicPopulationWithLearnedMutation(AcyclicParameters param) 
    {
        super(param);
        this.offsprings = new ArrayList();
        this.parents = new ArrayList();
    }
    
    @Override public void hyperEvolveWithSavingNewPromotedGraphs() throws Exception 
    {
        AcyclicCoordinatesWithLearnedMutation offspring;
        
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
              offspring = (AcyclicCoordinatesWithLearnedMutation) this.parents.get(0).clone();
              
              
              offspring.hyperMutate();
              this.offsprings.add((AcyclicCoordinatesWithLearnedMutation)offspring.clone());
            
          }
         
          this.hyperEvaluate();
          this.hyperRecordNewOffsprings();
          
          this.hyperPromote();
        // System.out.println ("Parent" + this.toString());
          
          System.out.println("generation : " + iteration +  "\n " + this.parents.toString());
          iteration++;
        } // end while */
       
   }   
    
    private void hyperInitPopulation() throws Exception
    {
        this.offsprings.clear();
        for(int i = 0; i < (this.hyperParam.getNoHyperParents() + this.hyperParam.getNoHyperOffSpring()); i++)
        {
            this.offsprings.add(new AcyclicCoordinatesWithLearnedMutation(this.hyperParam.getNoGraphInput(), 
                                                       this.hyperParam.getNoNodesInputFeedForward(),
                                                       this.hyperParam.getNoNodes(), 
                                                       this.hyperParam.getNoOutput(), 
                                                       this.hyperParam.getNoNodesForward(), 
                                                       this.hyperParam.getFunctionSet(),
                                                       this.hyperParam.getMutationRate()));
            this.offsprings.get(i).checkParam();
        }
    }
    
    /***
    * evaluate the offsprings. Only offsprings are evaluated. 
    * @throws CloneNotSupportedException 
    */
    private void hyperEvaluate() throws CloneNotSupportedException
    {
        
        for (int i = 0; i < this.offsprings.size(); i++) 
        {
             boolean canEval = true;
             if (this.parents.size() > 0)
             {
                if (!this.offsprings.get(i).hasActiveNodeBeenChanged())
                {
                    canEval = false;
                }
             }
            
             
            if (canEval)
            {
                this.hyperParam.getInterpreter().setCGPGraph((AcyclicCoordinatesWithLearnedMutation) this.offsprings.get(i).clone());
                for (int output = 0; output < this.hyperParam.getNoOutput(); output++)
                {
                    System.out.println("*******Eval");
                    this.hyperParam.getInterpreter().calculateHyperFitnessValue();
                    Double Fitness =  this.hyperParam.getInterpreter().getHyperFitnessValue(output);
                    this.offsprings.get(i).setAFitnessValue(output, Fitness);
                
                }
            }
        }
    }
    
    /***
     * Promote an offsprings to the parents.
     * @throws CloneNotSupportedException 
     */
    private void hyperPromote() throws CloneNotSupportedException
    {
       Collections.sort(this.offsprings);
       int OffspringNo = 0; 
       if (this.parents.size() == this.hyperParam.getNoHyperParents())
       {
            // parents has some values.
            for(int j = 0; j< this.offsprings.size(); j++)
            {
                for (int i = 0; i < this.parents.size(); i++)
                {   
                    System.out.println("****" + this.parents.get(i).getAFitnessValue(0) + " " + this.offsprings.get(j).getAFitnessValue(0));
                    if (this.parents.get(i).getAFitnessValue(0) >= this.offsprings.get(j).getAFitnessValue(0))
                    {
                   
                        this.parents.set(i, (AcyclicCoordinatesWithLearnedMutation) this.offsprings.get(j).clone());
                        OffspringNo++;
                        this.OffspringUpgraded = true;
                    }
                }
            }
       }
       else 
       {
          for (int i = 0; i < this.hyperParam.getNoHyperParents(); i++)
          {   
              if (i < this.hyperParam.getNoHyperOffSpring())
              {
                this.parents.add((AcyclicCoordinatesWithLearnedMutation) this.offsprings.get(i).clone()); 
                this.OffspringUpgraded = true;
              }
          } 
       }
       Collections.sort(this.parents);
    }// end hyperPromote

    
}
