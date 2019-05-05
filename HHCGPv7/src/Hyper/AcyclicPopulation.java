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
 * @author patriciaryser-welch
 */
public class AcyclicPopulation extends AbstractPopulation
{
    protected AcyclicParameters hyperParam;
    /***
     * parents AbstractPopulation of Graphs
     */
    protected ArrayList<AcyclicCoordinates> parents;
    /***
     * offsprings generated 
     */
    protected ArrayList<AcyclicCoordinates> offsprings;
    
    
    /**
     * Constructors 
     * @param param 
     */
    public AcyclicPopulation(AcyclicParameters param)
    {
        super(param);
        this.hyperParam = param;
        this.parents = new ArrayList(this.hyperParam.getNoHyperParents());
        this.offsprings = new ArrayList(this.hyperParam.getNoHyperOffSpring());
    }

    
     /***
      * evolve the CGP graphs population. GECCO EVOSTAR
      * 
      * @throws CloneNotSupportedException
      **/
    public void hyperEvolve() throws CloneNotSupportedException, Exception
    {
        AcyclicCoordinates offspring;
        this.hyperInitPopulation(); 
        this.hyperEvaluate();
        this.hyperPromote();
        
        while(! this.hyperTerminate())
        {
          this.offsprings.clear();
          for (int offspringNo = 0; offspringNo < this.hyperParam.getNoHyperOffSpring(); offspringNo++)
          {  
              offspring = null;
              offspring = (AcyclicCoordinates) this.parents.get(0).clone();
              offspring.hyperMutate();
            
              this.offsprings.add((AcyclicCoordinates)offspring.clone());
            
          }
           
          hyperEvaluate();
          
          this.hyperPromote();
        //  System.out.println ("Parent" + this.toString());
          
          System.out.println("generation : " + iteration +  "\n " + this.parents.toString());
          iteration++;
         // System.out.println(this.parents.get(0).getAFitnessValue(0));
        } // end while */
    }
    
   public void hyperEvolveWithSavingNewPromotedGraphs() throws Exception 
   {
        AcyclicCoordinates offspring;
        
        this.hyperInitPopulation(); 
        this.hyperEvaluate();
        this.hyperPromote();
        System.out.println("**** iteration : " + iteration +  "\n " + this.parents.toString() + " " + this.hyperTerminate());
     
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
              offspring = (AcyclicCoordinates) this.parents.get(0).clone();
              offspring.hyperMutate();
              this.offsprings.add((AcyclicCoordinates)offspring.clone());
          }
         
          this.hyperEvaluate();
          this.hyperRecordNewOffsprings();
          
          this.hyperPromote();
        // System.out.println ("Parent" + this.toString());
          
          System.out.println("iteration : " + iteration +  "\n " + this.parents.toString());
          iteration++;
        } // end while */
       
   }
    
  
    
    /**
     * initialise a population of offsprings. In this method, the offspring are mu + lambda
     */
    private void hyperInitPopulation() throws Exception
    {
        this.offsprings.clear();
        for(int i = 0; i < (this.hyperParam.getNoHyperParents() + this.hyperParam.getNoHyperOffSpring()); i++)
        {
            this.offsprings.add(new AcyclicCoordinates(this.hyperParam.getNoGraphInput(), 
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
                this.hyperParam.getInterpreter().setCGPGraph((AcyclicCoordinates) this.offsprings.get(i).clone());
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
    
    protected void hyperRecordNewOffsprings() throws IOException
    {
        for (int i = 0; i < this.offsprings.size(); i++)
        {
            if (this.offsprings.get(i).hasActiveNodeBeenChanged())
            {
                this.hyperParam.getInterpreter().getResults().WriteResultsIntoFileWithDetails("historyOffspring_" + this.hyperParam.getFileName(),
                                                                                              Integer.toString(iteration),
                                                                                              this.offsprings.get(i).toStringBestOutput() + " " + this.offsprings.get(i).getAFitnessValue(0),
                                                                                              "Factory sequential learning");  
                this.offsprings.get(i).setActiveNodeBeenChanged(false);
            }
        }
    }
    
    /***
     * Indicates whether of not the evolution needs to stop or not.
     * @return true if termination condition is met. False if not. 
     */
    protected boolean hyperTerminate()
    {
        if (this.hyperParam.getHyperMaxIterations() <= this.iteration)
        {
            return true;
        }
        
      /*  for (AbstractCoordinates parent: this.parents)
        {
            if (parent.getAFitnessValue(parent.getBestOutput()) <= this.hyperParam.getTargetFitnessValue())
            {
                return true;
            }
        }*/
        
        return false;     
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
                   
                        this.parents.set(i, (AcyclicCoordinates) this.offsprings.get(j).clone());
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
             this.parents.add((AcyclicCoordinates) this.offsprings.get(i).clone()); 
             this.OffspringUpgraded = true;
          } 
       }
       Collections.sort(this.parents);
    }// end hyperPromote

    /**
     * return the best CGP graph from the population. 
     * @return null if the parents population is empty. Otherwise a CGPGraph
     */
    public AcyclicCoordinates getBestCGPgraph()
    {
        Collections.sort(this.parents);
        if (this.parents.size() > 0 )
        {
            return this.parents.get(0);
        }
        else 
        {
            return null;
        }
    }
    @Override public String toString()
    {
        String s = "Acyclic Population \n";
       // s += this.hyperParamToberemoved.toString();
        s += "\n parents \n ";
        for (AcyclicCoordinates parent:this.parents)
        {
            s += parent.toString();
        }
        
        s += "\n offsprings \n";
        for (AcyclicCoordinates offspring:this.offsprings)
        {
            s += offspring.toString();
        }
        
        return s;
    }

   
 
    
    
}
