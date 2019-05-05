/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import Interpreter.*;
import Interpreter.Results;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 *
 * @author patriciaryser-welch
 */
public class OnlineAcyclicPopulation extends AbstractPopulation
{
    private ArrayList<OnlineAcyclicCoordinates> parents;
    private ArrayList<OnlineAcyclicCoordinates> offsprings;
    private MutationPopulationAcyclic mutationOperators;
    private AcyclicParameters hyperParam;
    private AcyclicInterpreter Interpreter;
    private Results newResults ;
    private int lastUpgrade;
    

    public OnlineAcyclicPopulation(AbstractParameters param) throws CloneNotSupportedException
    {
        super(param);
        this.hyperParam = (AcyclicParameters) param;
        this.Interpreter = (AcyclicInterpreter) this.hyperParam.getInterpreter().clone();
        this.newResults = new Results();
        this.parents = new ArrayList(param.getNoHyperParents());
        this.offsprings = new ArrayList(param.getNoHyperOffSpring());
        this.mutationOperators = new MutationPopulationAcyclic(10, this.hyperParam.getFileName()); // needs a new parameter now !!!! to do 
        
    }
    
     /***
      * evolve the CGP graphs population.
      * 
      * @throws CloneNotSupportedException
      **/
    public void hyperEvolve() throws CloneNotSupportedException
    {
     /*   OnlineIterativeCoordinates offspring;
        this.hyperInitPopulation();
        // System.out.println(-2);
        this.hyperEvaluate();
        this.hyperPromote();
        while(! this.hyperTerminate())
        {  
            this.offsprings.clear();
            for (int offspringNo = 0; offspringNo < this.hyperParam.getNoHyperOffSpring(); offspringNo++)
            {   
                 offspring = (OnlineIterativeCoordinates) this.parents.get(0).clone();
                 offspring.hyperMutate();
                 this.offsprings.add(offspring);
            }
            hyperEvaluate();
            this.hyperPromote();
          
            iteration++;
           // System.out.println("generation : " + iteration +  " " + this.getBestCGPgraph().getAlgorithm(0) +  + this.getBestCGPgraph().getAFitnessValue(0));
       } // end while */ 
    }
    
     /***
      * evolve the CGP graphs population.
      * 
      * @throws CloneNotSupportedException
      **/
    public void hyperEvolveWithSavingNewPromotedGraphs() throws CloneNotSupportedException, IOException, Exception
    {
        
        this.lastUpgrade = 0;
        // Initialise Problem solvers 
        this.hyperInitPopulation();
        this.hyperEvaluate();
        this.hyperPromote();
        // Initialise eval and promote best operator
        this.mutationOperators.initAndEvalMutationOperators(this.parents.get(0), iteration);
        
        
        while(! this.hyperTerminate())
        {  
            this.hyperEvolveMutation();
            this.hyperMutate();
            this.hyperEvaluate();
            this.mutationOperators.evaluateAMutation((OnlineAcyclicCoordinates) this.parents.get(0).clone(), (OnlineAcyclicCoordinates) this.offsprings.get(0).clone(), iteration, (this.iteration - this.lastUpgrade));
            this.hyperPromote();
            
            System.out.println(" \n generation : " + iteration +  " "  + this.parents.get(0).getAlgorithm(0) + " " + this.parents.get(0).getAFitnessValue(0) + "\n "); //+ this.toString());
           
            iteration++;
        
       } // end while */
    }
    
   
    private void hyperEvolveMutation() throws CloneNotSupportedException, IOException
    {
        this.mutationOperators.learningAMutation((OnlineAcyclicCoordinates) this.parents.get(0).clone(), this.offsprings.get(0), iteration, (this.iteration - this.lastUpgrade));
        this.parents.get(0).setVariarionOperator(this.mutationOperators.getVariationOperator()); 
    }
   
    private void hyperUpgrade() throws IOException
    {
        if (this.iteration == 0)
        {
            this.newResults.WriteResultsIntoFileWithDetails(this.hyperParam.getFileName(),
                    Integer.toString(iteration),
                    this.parents.get(0).toStringBestOutput() +  " " + this.parents.get(0).getAFitnessValue(0),
                    "Factory online iterative learning");
                    this.OffspringUpgraded = false;
                    this.lastUpgrade = this.iteration;
        }
        else 
        {
            this.newResults.WriteResultsIntoFileWithDetails(this.hyperParam.getFileName(),
                    Integer.toString(iteration),
                    this.parents.get(0).toStringBestOutput() + " Mutation " + this.mutationOperators.getCurrentMutation() + " " + this.parents.get(0).getAFitnessValue(0),
                    "Factory online iterative learning");
                    this.OffspringUpgraded = false;
                    this.lastUpgrade = this.iteration;
        }
  
    }
    
    private void hyperMutate() throws CloneNotSupportedException
    {
          OnlineAcyclicCoordinates offspring;
          this.offsprings.clear();
          for (int offspringNo = 0; offspringNo < this.hyperParam.getNoHyperOffSpring(); offspringNo++)
          {   
                 offspring = (OnlineAcyclicCoordinates) this.parents.get(0).clone();
            //     System.out.println ("OFFSPRING BEFORE " + offspring.toString());
                 offspring = offspring.hyperVariesCurrentIndividual(0);
            //     System.out.println ("OFFSPRING AFTER " + offspring.toString());
              
                 this.offsprings.add((OnlineAcyclicCoordinates) offspring.clone());
          }
    }
    /**
     * initialise a population of offsprings. In this method, the offspring are mu + lambda
     */
    private void hyperInitPopulation() throws Exception
    {
        this.offsprings.clear();
        for(int i = 0; i < (this.hyperParam.getNoHyperParents() + this.hyperParam.getNoHyperOffSpring()); i++)
        {
           this.offsprings.add((OnlineAcyclicCoordinates) new OnlineAcyclicCoordinates(this.hyperParam));
        // System.out.println(this.offsprings.get(i).toString());
        }
    }
    /***
    * evaluate the offsprings. Only offsprings are evaluated. 
    * @throws CloneNotSupportedException 
    */
    private void hyperEvaluate() throws CloneNotSupportedException
    {
        boolean mustEvaluate = true;
        this.hyperParam.setInterpreter((AcyclicInterpreter) this.Interpreter.clone());
            
        if (!this.mutationOperators.getMutationFitnessValues().hasAllFitnessValuesBeenInput()) 
        {
            for (int i = 0; i < this.offsprings.size(); i++) 
            { 
               if (!this.parents.isEmpty())
               {
                    System.out.println("ARE ACTIVE NODES THE SAME :" + this.parents.get(0).hasActiveNodeBeenChanged(this.offsprings.get(i)));
                    if (this.parents.get(0).hasActiveNodeBeenChanged(this.offsprings.get(i)))
                    {
                       mustEvaluate = false;
                    }
               }
               
               if (mustEvaluate)
               {
                        this.hyperParam.getInterpreter().setCGPGraph((AcyclicCoordinates) this.offsprings.get(i).clone());
                        this.evaluateAnOffspringFitness(i, 0); 
                        System.out.println ("After " + this.offsprings.get(0).getAFitnessValue(0));
                        this.newResults = (Results) this.hyperParam.getInterpreter().getResults().clone();
               }
               
            }// end for
            
        } // end if 
    }// end of method

    private void evaluateAnOffspringFitness(int i, int output) throws CloneNotSupportedException
    {
        AcyclicCoordinates o = (AcyclicCoordinates) this.offsprings.get(i).clone();
        this.hyperParam.getInterpreter().calculateHyperFitnessValue();
        o.setAFitnessValue(output, this.hyperParam.getInterpreter().getHyperFitnessValue(output));    
        this.offsprings.set(i, (OnlineAcyclicCoordinates) o.clone());
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
    private void hyperPromote() throws CloneNotSupportedException, IOException
    {
       Collections.sort(this.offsprings);
       int OffspringNo = 0; 
       if (!this.parents.isEmpty()) //guarantees from generation no 1
       {
          if (this.offsprings.get(0).getAFitnessValue(0) <= this.parents.get(0).getAFitnessValue(0))
          {
             this.newResults =  this.hyperParam.getInterpreter().getResults();
             if (!this.offsprings.get(0).hasActiveNodeBeenChanged(this.parents.get(0)) & this.offsprings.get(0).getAFitnessValue(0) < this.parents.get(0).getAFitnessValue(0))
             {
                this.parents.set(0, (OnlineAcyclicCoordinates) this.offsprings.get(0).clone());
                this.hyperUpgrade();
             }
             else 
             {
                 this.parents.set(0, (OnlineAcyclicCoordinates) this.offsprings.get(0).clone()); 
             }
            
           }
       }
       else 
       {
          for (int i = 0; i < this.hyperParam.getNoHyperParents(); i++)
          {   
             this.parents.add((OnlineAcyclicCoordinates) this.offsprings.get(i).clone());   
             this.hyperUpgrade();
             this.newResults = (Results) this.hyperParam.getInterpreter().getResults().clone();
          } 
       }
       
       Collections.sort(this.parents);
       
       
    }// end hyperPromote
    
    public AcyclicCoordinates getBestCGPgraph()
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
        String s = "***************************  Acyclic Population \n";
      //  s += this.hyperParamToberemoved.toString();
        s += "\n ---------------------- parents \n ";
        for (AcyclicCoordinates parent:this.parents)
        {
            s += parent.toString();
        }
        
        s += "\n ----------------------  offsprings \n";
        for (AcyclicCoordinates offspring:this.offsprings)
        {
            s += offspring.toString();
        }
        
      //  s += this.mutationOperators.toString(); 
        
        s += "***************************  Acyclic Population \n";
        return s;
    }   
}
    
   

