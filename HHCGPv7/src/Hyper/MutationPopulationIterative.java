/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;

/**
 *
 * @author patriciaryser-welch
 */
public class MutationPopulationIterative 
{
    private static final short SMALL_SCALE = 0;
    private static final short LARGE_SCALE = 1;
    private static final short RUIN_RECREATE = 2;
    
    
    private IterativePopulation SmallScaleVariation;
    private IterativePopulation LargeScaleVariation;
    private IterativePopulation RuinRecreateVariation;
   
  
    private short typeOfMutation;
    private double bestFitnessValue;
    private String FileName;
    private int noMaxFitnessValues;
  

    private MutationPerformanceIterative MutationFitnessValue;

    public MutationPopulationIterative(int noMaxFitnessValues, String aFileName)
    {
        this.SmallScaleVariation = new IterativePopulation(this.initSmallScaleMutationParam());
        this.LargeScaleVariation = new IterativePopulation(this.initLargeScaleMutationParam());
        this.RuinRecreateVariation = new IterativePopulation(this.initRuinRecreateMutationParam());
       
        this.typeOfMutation = MutationPopulationIterative.LARGE_SCALE;
        this.bestFitnessValue =  Double.NEGATIVE_INFINITY;
        this.MutationFitnessValue = new MutationPerformanceIterative(noMaxFitnessValues);
        this.noMaxFitnessValues = noMaxFitnessValues;
        
     //   this.MutationResults = new Results();
        this.FileName = aFileName.substring(0, aFileName.length()-4) + "_Mutation.csv";
    }

    @Override public String toString()
    {
       String s = " - - - - - - - - - - - - - \n Mutation Population ";
       s += "\nSMALL SCALE : " + this.SmallScaleVariation.toString();
       s += "\nLARGE SCALE : " + this.LargeScaleVariation.toString();
       s += "\nRUIN AND RECREATE : " + this.RuinRecreateVariation.toString();
      s += " - - - - - - - - - - - - - \n Mutation Population ";
       return s; //To change body of generated methods, choose Tools | Templates.
    }
    
   
    public void initAndEvalMutationOperators(OnlineIterativeCoordinates CGPGraph, int generation) throws CloneNotSupportedException, IOException
    {
        this.SmallScaleVariation.hyperInitPopulation();
        this.addSmallScaleAlgorithm();
       
        this.LargeScaleVariation.hyperInitPopulation();
        this.addLargeScaleAlgorithm();
        
        this.RuinRecreateVariation.hyperInitPopulation();
        this.addRuinAndRecreateAlgorithm();
        
        
        for (short type = MutationPopulationIterative.SMALL_SCALE; type <= MutationPopulationIterative.RUIN_RECREATE; type++)
        {
            this.findMutationFitnessValue(type, CGPGraph,0);
        }
        
        
      //  System.out.println("FIRST MUTATION " + this.typeOfMutation);
        this.writeResultInAFile(0);
        //this.setBestMutationOperator(CGPGraph, generation); */
    }
    public MutationPerformanceIterative getMutationFitnessValues()
    {
        return this.MutationFitnessValue;
    }
    
    public void evaluateAMutation(OnlineIterativeCoordinates aParent, OnlineIterativeCoordinates anOffspring, int iteration, int GapSinceLastUpdate)
    {
         this.MutationFitnessValue.setAMutationFitnessValue(aParent, anOffspring, GapSinceLastUpdate);
         this.MutationFitnessValue.increaseCounter();
    }
    
    public void learningAMutation(OnlineIterativeCoordinates aParent, OnlineIterativeCoordinates anOffspring, int iteration, int GapSinceLastUpdate) throws CloneNotSupportedException, IOException 
    {
        short saveType; 
        int i;
        double saveMutationFitness; 
        boolean restart = false;
        if (this.MutationFitnessValue.getCurrentValueOfCounter() == this.noMaxFitnessValues)
        {
            this.writeResultInAFile(iteration);
            this.MutationFitnessValue.resetCounter();
            saveType = this.typeOfMutation;
            saveMutationFitness = this.getVariationOperator().getAFitnessValue(0);
    
           this.setFitnessToAMutationOperatorParent(this.typeOfMutation, 0,this.MutationFitnessValue.getMutationFitnessValue());
           i = 3;
           if (!this.MutationFitnessValue.hasATerriblePerformance())
           {
                if (GapSinceLastUpdate < (3 * this.MutationFitnessValue.getMutationFitnessValues().length))
                {
                    i = 0;
                    while(i < 3) //3 & saveMutationFitness >=this.getVariationOperator().getAFitnessValue(0))
                    {
                        if (this.MutationFitnessValue.getMutationFitnessValue() < 2)
                        {
                            this.mutateNewMutations((OnlineIterativeCoordinates) aParent.clone());
                        }
         
                        if (saveMutationFitness < this.getVariationOperator().getAFitnessValue(0))
                        {
                            i = 3;  
                        }
                        else 
                        {
                            i++;
                        }
                            
                        System.out.println(i + " *****");
                    }        
                }
           }
          
           if (i == 3 & this.MutationFitnessValue.getMutationFitnessValue() < 2)
           {
               restart = true;
           }
           else if (saveMutationFitness < this.getVariationOperator().getAFitnessValue(0))
           {
              restart = true; 
           }
              
           if (restart)
           {
                System.out.println("RESTART");
                this.bestFitnessValue = -2.0;
                this.initAndEvalMutationOperators(aParent, iteration);
           } 
           
        }
    }

    private void checkCurrentMutations(OnlineIterativeCoordinates CGPGraph) throws CloneNotSupportedException, IOException
    {
        short saveType = this.typeOfMutation;
        short type = MutationPopulationIterative.SMALL_SCALE;
        boolean stop = false;
        while(!stop)
        {
            if (this.typeOfMutation != saveType)
            {
                if (type != this.typeOfMutation)
                {
                    this.findMutationFitnessValue(type, CGPGraph,0);
                    saveType = this.typeOfMutation;
                    
                }
            }
            
            type++;
            stop = (type > MutationPopulationIterative.RUIN_RECREATE);
            if (!stop)
            {
                stop = this.getVariationOperator().getAFitnessValue(0) < this.MutationFitnessValue.getMutationFitnessValue();
            }
        }
       // System.out.println("**learning " + type + " " + saveType + " " + this.typeOfMutation);
    }
    
    private void mutateNewMutations(OnlineIterativeCoordinates CGPGraph) throws CloneNotSupportedException, IOException
    {
        short saveType = this.typeOfMutation;
        short type = MutationPopulationIterative.SMALL_SCALE;
        boolean stop = false;
        while(!stop)
        {
           switch(type)
           {
                  case MutationPopulationIterative.SMALL_SCALE:
                            this.SmallScaleVariation.hyperMutate();
                            //System.out.println("***small" + this.SmallScaleVariation.getOffsprings().toString());
                            break;
                  case MutationPopulationIterative.LARGE_SCALE:
                            this.LargeScaleVariation.hyperMutate();
                            //System.out.println("***large" + this.LargeScaleVariation.getOffsprings().toString());
                          
                            break;
                  case MutationPopulationIterative.RUIN_RECREATE:
                            this.RuinRecreateVariation.hyperMutate();
                         //   System.out.println("***Ruin" + this.SmallScaleVariation.getOffsprings().toString());
                          
                            break;
                        default:
                            break;
           }
           this.findMutationFitnessValue(type, CGPGraph,0);
         //  System.out.println ("***After" + this.typeOfMutation);
            
            type++;
            stop = (type > MutationPopulationIterative.RUIN_RECREATE);
            if (!stop)
            {
                stop = this.getVariationOperator().getAFitnessValue(0) < this.MutationFitnessValue.getMutationFitnessValue();
            }
        }
      //  System.out.println("**learning " + type + " " + saveType + " " + this.typeOfMutation);
    }
    
    
    public void setAPerformanceValue(IterativeCoordinates parent, IterativeCoordinates offspring, int gapSinceLastUpgrade)
    {
        this.MutationFitnessValue.setAMutationFitnessValue(parent, offspring, gapSinceLastUpgrade);
    }
    
    private void findMutationFitnessValue(short type, OnlineIterativeCoordinates CGPGraph, int gapSinceLastUpgrade) throws CloneNotSupportedException, IOException
    {
        OnlineIterativeCoordinates GraphToMutate = (OnlineIterativeCoordinates) CGPGraph.clone();
        OnlineIterativeCoordinates Copy;
        MutationPerformanceIterative simulationFitness;  
        boolean changesInOffspring;
        //System.out.println ("FIND Mutation FITNESS VALUE " + simulationFitness +  " "  + this.typeOfMutation);
        double fitness = 0;
        double total = 0;
        int noOfOffspring = this.getSizeOfCurrentMutationOffsprings();
        
        
        GraphToMutate.getHyperParam().setInterpreter(CGPGraph.getHyperParam().getInterpreter());
        
        if (type == MutationPopulationIterative.SMALL_SCALE & this.SmallScaleVariation.getOffsprings().size() == 0)
        {
            noOfOffspring = 0;
        } 
        else if (type == MutationPopulationIterative.LARGE_SCALE & this.LargeScaleVariation.getOffsprings().size() == 0)
        {
            noOfOffspring = 0;
        }
        else if (type == MutationPopulationIterative.RUIN_RECREATE & this.RuinRecreateVariation.getOffsprings().size() == 0)
        {
            noOfOffspring = 0;
        }
      
        
        for (int i = 0; i < noOfOffspring; i++)
        { 
           simulationFitness = new MutationPerformanceIterative(4);
           try 
           {
                switch (type)
                {   
                    case MutationPopulationIterative.SMALL_SCALE:    GraphToMutate.setVariarionOperator(this.SmallScaleVariation.getOffsprings().get(i));
                                                            break;
                    case MutationPopulationIterative.LARGE_SCALE:    GraphToMutate.setVariarionOperator(this.LargeScaleVariation.getOffsprings().get(i));
                                                            break;
                    case MutationPopulationIterative.RUIN_RECREATE:  GraphToMutate.setVariarionOperator(this.RuinRecreateVariation.getOffsprings().get(i));
                                                            break;                                    
                }
            }
            catch (IndexOutOfBoundsException e)
            {
                System.out.println ("catch " + e.getLocalizedMessage());
                this.SmallScaleVariation.hyperInitPopulation();
                this.LargeScaleVariation.hyperInitPopulation();
                this.RuinRecreateVariation.hyperInitPopulation();
              
            }
            finally
            {
                switch (type)
                {   
                    case MutationPopulationIterative.SMALL_SCALE:    GraphToMutate.setVariarionOperator(this.SmallScaleVariation.getOffsprings().get(i));
                                                            break;
                    case MutationPopulationIterative.LARGE_SCALE:    GraphToMutate.setVariarionOperator(this.LargeScaleVariation.getOffsprings().get(i));
                                                            break;
                    case MutationPopulationIterative.RUIN_RECREATE:  GraphToMutate.setVariarionOperator(this.RuinRecreateVariation.getOffsprings().get(i));
                                                            break;                                    
                }
            }
                   
            
            fitness = GraphToMutate.getAFitnessValue(0);
          
          //  System.out.println("***Find" + GraphToMutate.toString());
            for (int j = 0; j < simulationFitness.getMaxNoFitnessValues(); j++)
            {
                Copy = (OnlineIterativeCoordinates) GraphToMutate.clone();
                GraphToMutate.hyperVariesCurrentIndividual(0); //need to review!!!!
                GraphToMutate.hyperEvaluate();
                simulationFitness.setAMutationFitnessValue(Copy, GraphToMutate.getVariationCopy(), 4);
                fitness = GraphToMutate.getAFitnessValue(0);   
            }
        
            this.setFitnessToAMutationOperator(type, i, simulationFitness.getMutationFitnessValue());
         //   System.out.println("");
        }
        
        if (noOfOffspring > 0)
        {
            promoteTheCurrentMutation(type);
        }
      //  System.out.println ("FIND A MUTATION FITNESS ___________________\n"  +  this.toString() + " ___________________\n");
    }

    private void promoteTheCurrentMutation(short type) throws CloneNotSupportedException
    {
        if (type == MutationPopulationIterative.SMALL_SCALE)
        {
            this.SmallScaleVariation.hyperPromotePositive();
            if (this.bestFitnessValue < this.SmallScaleVariation.getParents().get(0).getAFitnessValue(0))
            {
               this.bestFitnessValue = this.SmallScaleVariation.getParents().get(0).getAFitnessValue(0);
               this.typeOfMutation =  MutationPopulationIterative.SMALL_SCALE;
            }
                
        }
        else if (type == MutationPopulationIterative.LARGE_SCALE)
        {
            this.LargeScaleVariation.hyperPromotePositive();
            if (this.bestFitnessValue < this.LargeScaleVariation.getParents().get(0).getAFitnessValue(0))
            {
               this.bestFitnessValue = this.LargeScaleVariation.getParents().get(0).getAFitnessValue(0);
               this.typeOfMutation =  MutationPopulationIterative.LARGE_SCALE;
            }
        }
        else if (type == MutationPopulationIterative.RUIN_RECREATE)
        {
           this.RuinRecreateVariation.hyperPromotePositive();
            if (this.bestFitnessValue < this.RuinRecreateVariation.getParents().get(0).getAFitnessValue(0))
            {
               this.bestFitnessValue = this.RuinRecreateVariation.getParents().get(0).getAFitnessValue(0);
               this.typeOfMutation =  MutationPopulationIterative.RUIN_RECREATE;
            }
            
        }
    }
    
    private void setFitnessToAMutationOperator(int type, int i, double fitnessValue)
    {
    //    System.out.println("type " + type + "fitness value " + fitnessValue);
        switch (type)
        {
            case MutationPopulationIterative.SMALL_SCALE:   
                                                   this.SmallScaleVariation.getOffsprings().get(i).setAFitnessValue(0,fitnessValue);
                                                   break;
            case MutationPopulationIterative.LARGE_SCALE:   this.LargeScaleVariation.getOffsprings().get(i).setAFitnessValue(0,fitnessValue);
            break;
            case MutationPopulationIterative.RUIN_RECREATE: this.RuinRecreateVariation.getOffsprings().get(i).setAFitnessValue(0,fitnessValue);
            break;
        }
    }
    
    private void setFitnessToAMutationOperatorParent(int type, int i, double fitnessValue)
    {
        switch (type)
        {
            case MutationPopulationIterative.SMALL_SCALE:   this.SmallScaleVariation.getParents().get(i).setAFitnessValue(0,fitnessValue);
            break;
            case MutationPopulationIterative.LARGE_SCALE:   this.LargeScaleVariation.getParents().get(i).setAFitnessValue(0,fitnessValue);
            break;
            case MutationPopulationIterative.RUIN_RECREATE: this.RuinRecreateVariation.getParents().get(i).setAFitnessValue(0,fitnessValue);
            break;
        }
    }
    
    public double getFitnessMutation()
    {
       switch (this.typeOfMutation)
        {
            case MutationPopulationIterative.SMALL_SCALE:   return this.SmallScaleVariation.getParents().get(0).getAFitnessValue(0);
                                                  
            case MutationPopulationIterative.LARGE_SCALE:   return  this.LargeScaleVariation.getParents().get(0).getAFitnessValue(0);
            
            case MutationPopulationIterative.RUIN_RECREATE: return this.RuinRecreateVariation.getParents().get(0).getAFitnessValue(0);
            
        }
        return 0;
    }
    
    public IterativeCoordinates getVariationOperator() throws CloneNotSupportedException
    {
        
        switch (this.typeOfMutation)
        {
            case MutationPopulationIterative.SMALL_SCALE:   return (IterativeCoordinates) this.SmallScaleVariation.getBestCGPgraph().clone();
                                                  
            case MutationPopulationIterative.LARGE_SCALE:   return (IterativeCoordinates) this.LargeScaleVariation.getBestCGPgraph().clone(); 
            
            case MutationPopulationIterative.RUIN_RECREATE: return (IterativeCoordinates)this.RuinRecreateVariation.getBestCGPgraph().clone();
            
            default: return (IterativeCoordinates)this.RuinRecreateVariation.getOffsprings().get(0).clone();
            
        }
      
    }
            
    private IterativeParameters initSmallScaleMutationParam()
    {
       IterativeParameters gentleMutationParam = setCommonMutationParameters();
       gentleMutationParam.setFunctionSet(this.getSmallScaleFlipOperators());
       gentleMutationParam.setConditionSet(this.getSmallScaleConditionOperators());
       return gentleMutationParam;
    }
    
    private IterativeParameters initLargeScaleMutationParam()
    {
       IterativeParameters steepMutationParam = setCommonMutationParameters();
       steepMutationParam.setFunctionSet(this.getLargeScaleFlipOperators());
       steepMutationParam.setConditionSet(this.getLargeScaleConditionOperators());
       return steepMutationParam;
    }
     
    private void addRuinAndRecreateAlgorithm()
    {
        //first node: changes the output of the problem solver. set the condition to run for a quater of the nodes
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(1).setInputsFeedForward(0,(short) 0);
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(1).setInputsFeedback(0,(short)4);
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(1).setFunction((short)100);
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(1).setCondition(60);
        
        //second node: changes every gene of an active nodes and set the condition of an inner loop to 4 iterations
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(2).setInputsFeedForward(0,(short) 1);
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(2).setInputsFeedback(0,(short)0);
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(2).setFunction((short)115);
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(2).setCondition(70);
  
        //second node: changes every gene of any node and set the condition of an inner loop to 4 iterations
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(3).setInputsFeedForward(0,(short) 2);
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(3).setInputsFeedback(0,(short)0);
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(3).setFunction((short)105);
        this.RuinRecreateVariation.getOffsprings().get(0).getANode(3).setCondition(70);
  
        this.RuinRecreateVariation.getOffsprings().get(0).setOutput(0, (short) 3);
    }
    
    private void addSmallScaleAlgorithm()
    {
        //first node: changes the output of the problem solver. set the condition to run for a quater of the nodes
        this.SmallScaleVariation.getOffsprings().get(0).getANode(1).setInputsFeedForward(0,(short) 0);
        this.SmallScaleVariation.getOffsprings().get(0).getANode(1).setInputsFeedback(0,(short)0);
        this.SmallScaleVariation.getOffsprings().get(0).getANode(1).setFunction((short)133);
        this.SmallScaleVariation.getOffsprings().get(0).getANode(1).setCondition(10);
        
        //second node: changes every gene of an active nodes and set the condition of an inner loop to 4 iterations
        this.SmallScaleVariation.getOffsprings().get(0).getANode(2).setInputsFeedForward(0,(short) 1);
        this.SmallScaleVariation.getOffsprings().get(0).getANode(2).setInputsFeedback(0,(short)2);
        this.SmallScaleVariation.getOffsprings().get(0).getANode(2).setFunction((short)114);
        this.SmallScaleVariation.getOffsprings().get(0).getANode(2).setCondition(20);
        
         //second node: changes every gene of an active nodes and set the condition of an inner loop to 4 iterations
        this.SmallScaleVariation.getOffsprings().get(0).getANode(2).setInputsFeedForward(0,(short) 2);
        this.SmallScaleVariation.getOffsprings().get(0).getANode(2).setInputsFeedback(0,(short)2);
        this.SmallScaleVariation.getOffsprings().get(0).getANode(2).setFunction((short)113);
        this.SmallScaleVariation.getOffsprings().get(0).getANode(2).setCondition(20);
      
        this.SmallScaleVariation.getOffsprings().get(0).setOutput(0, (short) 3);
    }
    
    private void addLargeScaleAlgorithm()
    {
        //first node: changes the output of the problem solver. set the condition to run for a quater of the nodes
        this.LargeScaleVariation.getOffsprings().get(0).getANode(1).setInputsFeedForward(0,(short) 0);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(1).setInputsFeedback(0,(short)5);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(1).setFunction((short)111);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(1).setCondition(40);
        
        //second node: changes every gene of an active nodes and set the condition of an inner loop to 4 iterations
        this.LargeScaleVariation.getOffsprings().get(0).getANode(2).setInputsFeedForward(0,(short) 1);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(2).setInputsFeedback(0,(short)1);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(2).setFunction((short)113);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(2).setCondition(50);
        
         //second node: changes every gene of an active nodes and set the condition of an inner loop to 4 iterations
        this.LargeScaleVariation.getOffsprings().get(0).getANode(3).setInputsFeedForward(0,(short) 2);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(3).setInputsFeedback(0,(short)1);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(3).setFunction((short)114);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(3).setCondition(50);
        
         //second node: changes every gene of an active nodes and set the condition of an inner loop to 4 iterations
        this.LargeScaleVariation.getOffsprings().get(0).getANode(4).setInputsFeedForward(0,(short) 3);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(4).setInputsFeedback(0,(short)1);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(4).setFunction((short)101);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(4).setCondition(50);
        
        this.LargeScaleVariation.getOffsprings().get(0).getANode(5).setInputsFeedForward(0,(short) 4);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(5).setInputsFeedback(0,(short)1);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(5).setFunction((short)102);
        this.LargeScaleVariation.getOffsprings().get(0).getANode(5).setCondition(50);
      
        this.LargeScaleVariation.getOffsprings().get(0).setOutput(0, (short) 5);
    }
    
    
    private IterativeParameters initRuinRecreateMutationParam()
    {
       IterativeParameters steepMutationParam = setCommonMutationParameters();
       steepMutationParam.setFunctionSet(this.getRuinRecreateFlipOperators());
       steepMutationParam.setConditionSet(this.getRuinAndRecreateConditionOperators());
       return steepMutationParam;
    }

    private IterativeParameters setCommonMutationParameters()
    {
        IterativeParameters MutationParameters = new IterativeParameters();
        MutationParameters.setMutationRate(0.20); // was set to 5%
        MutationParameters.setNoNodes((short) 10); //200
        MutationParameters.setHyperMaxIterations((short) 1); //set to 1500 // SET TO 1200
        MutationParameters.setNoNodesForward((short) 9); // would like some stage evolution if poss
        MutationParameters.setNoNodeBack((short) 9);// would like some stage evolu
        MutationParameters.setNoHyperOffSpring((short) 1);
        MutationParameters.setNoHyperParents((short) 1);
        MutationParameters.setNoOutput((byte) 1);
        MutationParameters.setNoGraphInput((byte) 1);
        MutationParameters.setNoNodesInputFeedForward((byte) 1);
        MutationParameters.setNoNodesInputFeedback((short) 1);
        MutationParameters.setFileName("");
        
        return MutationParameters;
    }
     
    
    private short[] getSmallScaleFlipOperators()
    {
        short[] result = new short[7];
        result[0] = 121;
        result[1] = 123;
        result[2] = 124;
        result[3] = 131;
        result[4] = 133;
        result[5] = 113;
        result[6] = 114;
       
        return result;
    }
    
   /* private void setBestMutationOperator(OnlineIterativeCoordinates CGPGraph, int generation) throws CloneNotSupportedException, IOException
    {
       System.out.println ("SET BEST MUTATION " + this.bestFitnessValue + " " + this.typeOfMutation);
  /*     if (this.SmallScaleVariation.getBestCGPgraphPositive().getAFitnessValue(0)>= this.bestFitnessValue)
       {
            this.bestFitnessValue = this.SmallScaleVariation.getBestCGPgraph().getAFitnessValue(0);
            this.typeOfMutation = MutationPopulationIterative.SMALL_SCALE;
       }
       else 
       {
       
            if (this.LargeScaleVariation.getBestCGPgraph().getAFitnessValue(0) >= this.bestFitnessValue)
            {
    /*           this.bestFitnessValue = this.LargeScaleVariation.getBestCGPgraph().getAFitnessValue(0);
               this.typeOfMutation = MutationPopulationIterative.LARGE_SCALE;
            }
            else 
            {
       
                if (this.RuinRecreateVariation.getBestCGPgraph().getAFitnessValue(0) >= this.bestFitnessValue)
                {    
                    this.bestFitnessValue = this.RuinRecreateVariation.getBestCGPgraph().getAFitnessValue(0);
                    this.typeOfMutation = MutationPopulationIterative.RUIN_RECREATE;
                }
            }
       }*/
     /*  System.out.println ("SET BEST MUTATION " + this.bestFitnessValue + " " + this.typeOfMutation);
       this.writeResultInAFile(generation);
    } */
        
    
    
    private short[] getLargeScaleFlipOperators()
    {
        short[] result = new short[8];
        result[0] = 101;
        result[1] = 102;
        result[2] = 103;
        result[3] = 104;
        result[4] = 111;
        result[5] = 112;
        result[6] = 113;
        result[7] = 114;
        return result;
       
    }
    
    private short[] getRuinRecreateFlipOperators()
    {
        short[] result = new short[3];
        result[0] = 100;
        result[1] = 105;
        result[2] = 115;
        
        return result;
    }
    
    private short[] getSmallScaleConditionOperators()
    {
        short[] result = new short[3];
        result[0] = 10;
        result[1] = 20;
        result[2] = 30;
        return result;
    }
    
    private short[] getRuinAndRecreateConditionOperators()
    {
        short[] result = new short[3];
        result[0] = 20;
        result[1] = 60;
        result[2] = 70;
  
        return result;
    }
    
    
    private short[] getLargeScaleConditionOperators()
    {
        short[] result = new short[2];
        result[0] = 40;
        result[1] = 50;
  
        return result;
    }

    private int getSizeOfCurrentMutationOffsprings()
    {
     switch (this.typeOfMutation)
        {   
            case MutationPopulationIterative.SMALL_SCALE:    return this.SmallScaleVariation.getOffsprings().size();
            case MutationPopulationIterative.LARGE_SCALE:    return this.LargeScaleVariation.getOffsprings().size();
            case MutationPopulationIterative.RUIN_RECREATE:  return this.RuinRecreateVariation.getOffsprings().size();
            default: return 0;
        }
    }
    
    
    public String getCurrentMutation()
    {
     switch (this.typeOfMutation)
        {   
            case MutationPopulationIterative.SMALL_SCALE:    return "SMALL_SCALE " + this.SmallScaleVariation.getBestCGPgraph().getAlgorithm(0);
            case MutationPopulationIterative.LARGE_SCALE:    return "LARGE_SCALE " + this.LargeScaleVariation.getBestCGPgraph().getAlgorithm(0);
            case MutationPopulationIterative.RUIN_RECREATE:  return "RUIN_RECREATE " + this.RuinRecreateVariation.getBestCGPgraph().getAlgorithm(0);
            default: return "no algorithm";
        }
    }
    
    private void writeResultInAFile(int generation) throws IOException
    {
        File file = new File("");
        BufferedWriter output = new BufferedWriter(new FileWriter(this.FileName, true));
        output.write(generation + ","  + this.getCurrentMutation() + ", " + Arrays.toString(this.MutationFitnessValue.getMutationFitnessValues())); 
        output.newLine();
        output.close(); 
    }
    
}
