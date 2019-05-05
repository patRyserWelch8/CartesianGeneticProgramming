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
public class IterativePopulation extends AbstractPopulation 
{
    
    protected IterativeParameters hyperParam;
    /***
     * parents AbstractPopulation of Graphs
     */
    protected ArrayList<IterativeCoordinates> parents;
    /***
     * offsprings generated 
     */
    protected ArrayList<IterativeCoordinates> offsprings;

    
    /****
     * indicates whether or not an offspring has been upgraded
     */
    boolean OffspringUpgraded = false;
    /**
     * Constructors 
     * @param param 
     */
    public IterativePopulation(IterativeParameters param)
    {
        super(param);
        this.hyperParam = param;
        this.parents = new ArrayList(this.hyperParam.getNoHyperParents());
        this.offsprings = new ArrayList(this.hyperParam.getNoHyperOffSpring());
      
      
    }

    
     /***
      * evolve the CGP graphs population.
      * 
      * @throws CloneNotSupportedException
      **/
    public void hyperEvolve() throws CloneNotSupportedException
    {
        IterativeCoordinates offspring;
      //  System.out.println(-1);
        this.hyperInitPopulation();
        // System.out.println(-2);
        this.hyperEvaluate();
        this.hyperPromote();
        while(! this.hyperTerminate())
        {  
            this.offsprings.clear();
            for (int offspringNo = 0; offspringNo < this.hyperParam.getNoHyperOffSpring(); offspringNo++)
            {   
                 offspring = (IterativeCoordinates) this.parents.get(0).clone();
                 offspring.hyperMutate();
                 this.offsprings.add(offspring);
            }
            hyperEvaluate();
            this.hyperPromote();
          
            iteration++;
            System.out.println("generation : " + iteration +  " " + this.getBestCGPgraph().getAlgorithm(0) +  + this.getBestCGPgraph().getAFitnessValue(0));
       } // end while */
    }
    
     /***
      * evolve the CGP graphs population.
      * 
      * @throws CloneNotSupportedException
      **/
    public void hyperEvolveWithSavingNewPromotedGraphs() throws CloneNotSupportedException, IOException
    {
        IterativeCoordinates offspring;
      //  System.out.println(-1);
        this.hyperInitPopulation();
        // System.out.println(-2);
        this.hyperEvaluate();
        this.hyperPromote();
        while(! this.hyperTerminate())
        {  
            if (this.OffspringUpgraded)
            {
                Collections.sort(this.parents);
                System.out.println("recording");
                this.hyperParam.getInterpreter().getResults().WriteResultsIntoFileWithDetails(this.hyperParam.getFileName(),
                                                                                           Integer.toString(iteration),
                                                                                           this.parents.get(0).getAlgorithm(0) + " " + this.getParents().get(0).getAFitnessValue(0),
                                                                                           "Factory iterative learning");  
                this.OffspringUpgraded = false;
            }
            hyperMutate();
            hyperEvaluate();
            this.hyperRecordNewOffsprings();
            this.hyperPromote();
            // System.out.println("***" + this.parents.get(0).toString());
           
            iteration++;
            System.out.println("generation : " + iteration +  " " + this.getBestCGPgraph().getAlgorithm(0) +  + this.getBestCGPgraph().getAFitnessValue(0));
       } // end while */
    }

    public void hyperMutate() throws CloneNotSupportedException
    {
        IterativeCoordinates offspring;
        this.offsprings.clear();
        for (int offspringNo = 0; offspringNo < this.hyperParam.getNoHyperOffSpring(); offspringNo++)
        {
            offspring = (IterativeCoordinates) this.parents.get(0).clone();
            offspring.hyperMutate();
            this.offsprings.add(offspring);
        }
    }
    
    protected void hyperRecordNewOffsprings() throws IOException
    {
        System.out.println("Record");
        for (int i = 0; i < this.offsprings.size(); i++)
        {
           // if (this.offsprings.get(i).hasActiveNodeBeenChanged(null))
          //  {
                 System.out.println("Record2");
                this.hyperParam.getInterpreter().getResults().WriteResultsIntoFileWithDetails("historyOffspring_" + this.hyperParam.getFileName(),
                                                                                              Integer.toString(iteration),
                                                                                              this.offsprings.get(i).toStringBestOutput() + " " + this.offsprings.get(i).getAFitnessValue(0),
                                                                                              "Factory sequential learning");  
             
           // }
        }
    }
    
    public void hyperMutateOffspringsAreDifferents() throws CloneNotSupportedException
    {
        boolean  aretheNodesTheSame = false;
        IterativeCoordinates offspring;
        while(!aretheNodesTheSame)
        {
            this.offsprings.clear();
            for (int offspringNo = 0; offspringNo < this.hyperParam.getNoHyperOffSpring(); offspringNo++)
            {
                offspring = (IterativeCoordinates) this.parents.get(0).clone();
                offspring.hyperMutate();
                aretheNodesTheSame = parents.get(0).hasActiveNodeBeenChanged(offspring);
                this.offsprings.add(offspring);
            }
        }
    }
    
    /**
     * initialise a population of offsprings. In this method, the offspring are mu + lambda
     */
    public  void hyperInitPopulation()
    {
        this.offsprings.clear();
        for(int i = 0; i < (this.hyperParam.getNoHyperParents() + this.hyperParam.getNoHyperOffSpring()); i++)
        {
           this.offsprings.add(new IterativeCoordinates(this.hyperParam));
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
           
                    IterativeCoordinates o = (IterativeCoordinates) this.offsprings.get(i).clone();
                    this.hyperParam.getInterpreter().setCGPGraph((IterativeCoordinates) this.offsprings.get(i).clone(), true);
                    this.hyperParam.getInterpreter().calculateHyperFitnessValue();
                    o.setAFitnessValue(output, this.hyperParam.getInterpreter().getHyperFitnessValue(output));
                    this.offsprings.set(i, o); 
                
                    // this.offsprings.set(i, (IterativeCoordinates) this.hyperParam.getInterpreter().getCGPGraph()); 
       
                }
            }
        }
    }
    
    /***
     * Indicates whether of not the evolution needs to stop or not.
     * @return true if termination condition is met. False if not. 
     */
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
    /***
     * Promote an offsprings to the parents.
     * @throws CloneNotSupportedException 
     */
    public void hyperPromote() throws CloneNotSupportedException
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
    
    /***
     * this is used by the learning to learn 
     * @throws CloneNotSupportedException 
     */
     public void hyperPromotePositive() throws CloneNotSupportedException
    {
       Collections.sort(this.offsprings);
       int OffspringNo = 0; 
       if (this.parents.size() == this.hyperParam.getNoHyperParents())
       {
            // parents has some values.
            for (int i = 0; i < this.parents.size(); i++)
            {   
                if (this.parents.get(0).IsFitnessSmallerOrEqual(this.offsprings.get(i)))
                {
                    this.parents.set(0, this.offsprings.get(i));
                    OffspringNo++;
                    this.OffspringUpgraded = true;
                }
            }
       }
       else 
       {
          
          for (int i = 0; i < this.offsprings.size(); i++)
          {   
              System.out.println("******" + this.offsprings.get(i).hashCode());
              if(i == 0)
              {
                  this.parents.add(this.offsprings.get(i));
              }
              else if (i > 0)
              {
                 if (this.parents.get(0).IsFitnessSmallerOrEqual(this.offsprings.get(i)))
                 {
                    this.parents.set(0, this.offsprings.get(i));
                 }
              }
          } 
          this.OffspringUpgraded = true;
       }
    }// end hyperPromotePositive
    
    public IterativeCoordinates getBestCGPgraph()
    {
        Collections.sort(this.parents);
        if (this.parents.size() > 0)
        {
            return this.parents.get(0);
        }
        else 
        {
            return null;
        }
    }
    
    
    
    public IterativeCoordinates getBestCGPgraphPositive()
    {
        Collections.sort(this.parents);
        if (this.parents.size() > 0)
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
        String s = "Iterative Population \n";
      //  s += this.hyperParamToberemoved.toString();
        s += "\n parents \n ";
        for (IterativeCoordinates parent:this.parents)
        {
            s += parent.toString();
        }
        
        s += "\n offsprings \n";
        for (IterativeCoordinates offspring:this.offsprings)
        {
            s += offspring.toString();
        }
        
        return s;
    }  
    
    
    public ArrayList<IterativeCoordinates> getOffsprings()
    {
        return offsprings;
    }
   
    public ArrayList<IterativeCoordinates> getParents()
    {
        return parents;
    }
    
}
