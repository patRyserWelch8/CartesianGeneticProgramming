/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.*;

/**
 * This class holds the coordinates of a CGP graph. It is abstract as it only holds the features that are common to every CGP genotypes.
 * @author patriciaryser-welch
 */
public abstract class AbstractCoordinates 
{
    
    protected final static short INPUT_FORWARD = 0;
    protected final static short INPUT_FEEDBACK = 1; 
    protected final static short FUNCTION = 2;
    protected final static short CONDITION = 3;
    protected final static short OUTPUT = 4;
    protected final static short INVALID = 5;
    protected final static short SM_FUNCTION = 6;
    protected final static short AC_FUNCTION = 7; 
    protected final static short AC_OUTPUT_TYPE = 8;
   
    protected int nodeLength;
    protected int currentOrderIndex;
    protected int acCurrentOrderIndex;
    /***
     * no of inputs of a graph
     */
    protected final short noGraphInput;
    /***
     *  no of inputs to a node. It can be different than the number of inputs of graph.
     */
    protected final short noNodesInputFeedforward;
    
    
    /***
     * no of inputs feebback to a node.
     */
    protected short noNodesInputFeedback;

    /***
     * Maximum of nodes for a graph this include the input of a graph.
     */
    protected final short maxNodes;
    /***
     *  Number of nodes to step forward, for the output 
     */
    protected short noNodesFeedForward;
    
    /***
     *  Number of nodes to step forward for the AC output 
     */
    protected short ACnoNodesFeedForward = 5;
    /***
     *  function set for the graph.Only one of these values can set the function of a node.
     */
    protected final short[] functionSet;
    /***
     * condition set for executing a function. Optional. 
     */
    protected final short[] conditionSet;
   
    /***
     *  Array of outputs of a graphs.
     */
    protected short[] output; 
   
     /***
     *  Array of outputs of a graphs.
     */
    protected short[] ac_output; 
    /**
     *  number of aCOutput
     */
    protected short noACoutput;

   
   /****
    *  orders of execution of the active nodes, for an output
    */
    protected ArrayList<Short> columnsOrder; 

    /***
     *  the best performan output
     */
    protected short bestOutput;
    /***
     *  random number generator
     */
    protected final Random RandomNumbers = new Random(System.currentTimeMillis());
    /***
     *  fitness value of the best output.
     */
    protected double[] fitness;
    /***
     * mutationRate applied during the mutation.
     */
    protected double mutationRate;
    
    /***
     * Number of nodes to step back. 
     */
    protected short noNodesFeedback;
    
      /***
     *  obsolete Self-modifying function set for the graph.Only one of these values can set the function of a node.
     */
    protected short[] sm_functionSet;
   
    /***
     *  Auto-constructive function set for the graph.Only one of these values can set the function of a node.
     */
    protected  short[] ac_functionSet;
    
    protected ArrayList<Short> ac_columnsOrder;
   
    
    
   public AbstractCoordinates()
   {
       this.noGraphInput = 0;
       this.noNodesInputFeedforward = 0;
       this.maxNodes = 0;
       this.noNodesFeedForward = 0;
       this.functionSet = new short[1];
       this.conditionSet = new short[1];
       this.columnsOrder = new ArrayList();
       this.output = new short[1];
       this.fitness = new double[1];
       
       
   }

    /*** 
     * constructor 
     * @param noGraphInput - number of inputs to the CGP graph
     * @param noNodesInput - number of inputs to the node
     * @param maxNodes - the number of nodes in a graphs
     * @param noOutput - Number of outputNodes
     * @param noNodesForward - Number of nodes back the connection can go to.
     * @param functionSet - function for the coordinates. 
     */
    public AbstractCoordinates(short noGraphInput, short noNodesInput, short maxNodes, short noOutput, short noNodesForward, short ACnoNodesFeedForward, short[] functionSet, double mutationRate) throws Exception
    {
        this.noGraphInput = noGraphInput;
        this.noNodesInputFeedforward = noNodesInput;
        this.maxNodes = maxNodes;
        this.noNodesFeedForward = noNodesForward;
        this.functionSet = functionSet;
        this.columnsOrder = new ArrayList();
        this.bestOutput = 0;
        this.output = new short[noOutput];
        this.fitness = new double[noOutput];
        this.mutationRate = mutationRate;
        this.conditionSet = new short[0];
        this.ac_functionSet = new short[0];
        this.sm_functionSet = new short[0];
        this.ACnoNodesFeedForward = ACnoNodesFeedForward;
       
    }
    
    /*** 
     * constructor 
     * @param noGraphInput - number of inputs to the CGP graph
     * @param noNodesInput - number of inputs to the node
     * @param maxNodes - the number of nodes in a graphs
     * @param noOutput - Number of outputNodes
     * @param noNodesBack - Number of nodes back the connection can go to.
     * @param functionSet - function for the coordinates. 
     */
    public AbstractCoordinates(short noGraphInput, short noNodesInput, short maxNodes, short noOutput, short noNodesFeedForward, short ACnoNodesFeedForward, short[] functionSet,  short[] conditionSet , double mutationRate)
    {
        this.noGraphInput = noGraphInput;
        this.noNodesInputFeedforward = noNodesInput;
        this.maxNodes = maxNodes;
        this.noNodesFeedForward = noNodesFeedForward;
        this.functionSet = functionSet;
        this.columnsOrder = new ArrayList();
        this.bestOutput = 0;
        this.output = new short[noOutput];
        this.fitness = new double[noOutput];
        this.mutationRate = mutationRate;
        this.conditionSet = conditionSet;
        this.ac_functionSet = new short[0];
        this.sm_functionSet = new short[0];
        this.ACnoNodesFeedForward = ACnoNodesFeedForward;
    }
   
  
    /***
     * 
     * @param noGraphInput
     * @param noNodesInput
     * @param maxNodes
     * @param noOutput
     * @param noNodesForward
     * @param noNodesBack
     * @param functionSet
     * @param mutationRate 
     */
    public AbstractCoordinates(short noGraphInput, short noNodesInput, short maxNodes, short noOutput, short noNodesForward, short ACnoNodesFeedForward, short noNodesBack, short[] functionSet, double mutationRate)
    {
        this.noGraphInput = noGraphInput;
        this.noNodesInputFeedforward = noNodesInput;
        this.maxNodes = maxNodes;
        this.noNodesFeedForward = noNodesForward;
        this.functionSet = functionSet;
        this.columnsOrder = new ArrayList();
        this.bestOutput = 0;
        this.output = new short[noOutput];
        this.fitness = new double[noOutput];
        this.mutationRate = mutationRate;
        this.conditionSet = new short[0];
         this.ac_functionSet = new short[0];
        this.sm_functionSet = new short[0];
        this.ac_output = new short[0];
        this.noNodesFeedback = noNodesBack;
        this.ACnoNodesFeedForward = ACnoNodesFeedForward;
    }
    
   /****
    * constructor
    * @param noGraphInput
    * @param noNodesInputFeedforward
    * @param noNodesInputFeedback
    * @param maxNodes
    * @param noOutput
    * @param noNodesForward
    * @param noNodesBack
    * @param functionSet
    * @param conditionSet
    * @param mutationRate 
    */
    
    public AbstractCoordinates(short noGraphInput, short noNodesInputFeedforward, 
                              short noNodesInputFeedback, short maxNodes, 
                              short noOutput, short noNodesForward, 
                              short noNodesBack, short[] functionSet, 
                              short[] conditionSet, double mutationRate)
    {
        this.noGraphInput = noGraphInput;
        this.noNodesInputFeedforward = noNodesInputFeedforward;
        this.noNodesInputFeedback = noNodesInputFeedback;
        this.maxNodes = maxNodes;
        this.noNodesFeedForward = noNodesForward;
        this.functionSet = functionSet;
        this.columnsOrder = new ArrayList();
        this.bestOutput = 0;
        this.output = new short[noOutput];
        this.fitness = new double[noOutput];
        this.mutationRate = mutationRate;
        this.conditionSet = conditionSet;
        this.ac_functionSet = new short[0];
        this.sm_functionSet = new short[0];
         this.ac_output = new short[0];
        this.noNodesFeedback = noNodesBack;
    }
    /**
     * 
     * @param noGraphInput
     * @param noNodesInput
     * @param maxNodes
     * @param noOutput
     * @param noACOutput
     * @param noNodesForward
     * @param functionSet
     * @param SM_functionSet
     * @param AC_functionSet
     * @param mutationRate
     * @throws Exception 
     */
    public AbstractCoordinates(short noGraphInput, short noNodesInput, short maxNodes, short noOutput, short ACOutput, short noNodesForward, short[] functionSet,  short[] SM_functionSet, short[] AC_functionSet, double mutationRate) throws Exception
    {
        this.noGraphInput = noGraphInput;
        this.noNodesInputFeedforward = noNodesInput;
        this.noNodesInputFeedback = 0;
        this.maxNodes = maxNodes;
        this.noNodesFeedForward = noNodesForward;
        this.functionSet = functionSet;
        this.columnsOrder = new ArrayList();
        this.bestOutput = 0;
        this.output = new short[noOutput];
        this.fitness = new double[noOutput];
        this.mutationRate = mutationRate;
        this.conditionSet = new short[0];
        this.ac_functionSet = this.init_ac_operator_set_acyclic();
        this.sm_functionSet = this.init_sm_operator_set();
        this.noACoutput = ACOutput;
        this.ac_output = new short[this.noACoutput];
        this.noNodesFeedback = 0;
    }
        
   
            
    /**
     * Get the value of bestOutput
     *
     * @return the value of bestOutput
     */
    public short getBestOutput()
    {
        return bestOutput;
    }

    /**
     * Set the value of bestOutput
     *
     * @param bestOutput new value of bestOutput
     */
    public void setBestOutput(short bestOutput)
    {
        this.bestOutput = bestOutput;
    }
    
    /***
     * Get one specific output.
     * @param index  index of the output. Start from 0 and to noOutput -1
     * @return the value of the output or -1 if index is invalid or output is empty.
     */
    public short getAnOutput(int index)
    {
        if (index < this.output.length)
        {
            return this.output[index];
        }
        else 
        {
            return -1;
        }
    }
    
     /**
     * Get the value of output
     *
     * @return the value of output
     */
    public short[] getOutputs()
    {
        return output;
    }

    /**
     * Set the value of output
     *
     * @param output new value of output
     */
    public void setOutput(short[] output)
    {
        this.output = output;
    }

    /**
     * Set the value of output at specified index.
     *
     * @param index the index of output
     * @param output new value of output at specified index
     */
    public void setOutput(int index, short output)
    {
        this.output[index] = output;
    }

    /**
     * Get the value of functionSet at specified index
     *
     * @param index the index of functionSet
     * @return the value of functionSet at specified index
     */
    protected short getFunctionSet(int index)
    {
        return this.functionSet[index];
    }
    
     /**
     * Get the value of noNodesInputFeedforward
     *
     * @return the value of noNodesInputFeedforward
     */
    public short getNoNodesInputFeedforward()
    {
        return noNodesInputFeedforward;
    }

    /**
     * Get the value of noGraphInput
     *
     * @return the value of noGraphInput
     */
    public short getNoGraphInput()
    {
        return noGraphInput;
    }   
   
    /**
     * Get the number of output. 
     * @return 0 if no input has been set. 
     */
    public short getNoOutput()
    {
        return (short) this.output.length;
    }
    /***
     * Get the numbers node a connection can go back. 
     * @return the maximum of nodes a connection can get back to
     */
    public short getNoNodesFeedforward()
    {
        return noNodesFeedForward;
    }
    /***
     * Get the functionSet
     * @return an Array of the function set.
     */
    public short[] getFunctionSet()
    {
        return functionSet;
    }
    
    /***
     * state whether a node is active or inactive.
     * @param NodeIndex
     * @return true if a node is connected. false if it is not connected in the order of instructions for an output.
     */
    public boolean isNodeActive(short NodeIndex)
    {
       return this.columnsOrder.contains(NodeIndex);
    }
    /****
     * Get the conditiona et 
     * @return an array of the condition set.
     */
    public short[] getConditionSet()
    {
        return this.conditionSet;
    }
    
    public short getAfunctionRandomly()
    {
        int index = this.RandomNumbers.nextInt(this.functionSet.length);
        //System.out.println(index + "," + this.functionSet[index]);
        return this.functionSet[index];
    }
    
    /*protected short getAnACfunctionRandomly()
    {
        int index;
        if (this.ac_functionSet.length > 0)
        {   
            index = this.RandomNumbers.nextInt(this.ac_functionSet.length);
            return this.ac_functionSet[index];
        }
        else
        {
            return 0;
        }
    }*/
   /*protected short getASMfunctionRandomly()
    {
        int index;
        if (this.sm_functionSet.length > 0)
        {
            index = this.RandomNumbers.nextInt(this.sm_functionSet.length);
            return this.sm_functionSet[index];
        }
        else 
        {
            return 0;
        }
        
        //System.out.println(index + "," + this.functionSet[index]);
       
    }*/
  /*  protected short getAnACOutputRandomly()
    {
        int min = (this.maxNodes - this.ACnoNodesFeedForward);
        int max = this.maxNodes + 1;
        Double r  = -1.0;
       
        while (r <= 0.001 || r > 1 )
        {
            r = this.RandomNumbers.nextGaussian() ;
        }
        
        if (r.shortValue() == 0)
        {
            r = (double) this.noGraphInput;
        }
        
        r *= this.maxNodes;
        return r.shortValue();
      
    }*/

    protected short getAnNodeIndexRandomly()
    {
        int min = this.noGraphInput;
        int max = this.maxNodes + 1;
        Double r  = -1.0;
       
        while (r <= 0.001 || r > 1 )
        {
            r = this.RandomNumbers.nextGaussian() ;
        }
        
        r *= this.maxNodes;
        if (r.shortValue() == 0)
        {
            r = (double) this.noGraphInput;
        }
        return r.shortValue();
      
    }
    
    /***
     * get a output randomly.
     * @return 
     */
    protected short getAnOutputRandomly()
    {
        int min = (this.maxNodes - this.noNodesFeedForward);
        int max = this.maxNodes + 1;
        if (min == max)
        {
            return (short)(this.RandomNumbers.nextInt(this.maxNodes));
        }
        else
        {
            return (short)(this.RandomNumbers.nextInt (max - min) + min);
        }
    }
    
    protected int getNodeIndex (short geneIndex)
    {
        short NodeIndex =  (short) ((short) (geneIndex / this.nodeLength) + 1);
        if (NodeIndex > this.getMaxNode())
        {
            return this.getMaxNode() - 1;
        }
        else
        {
            return (geneIndex / this.nodeLength); 
        }
    }
    
    public void moveToFirstNode(int output)
    {
        this.identifyActiveNodes(output);
        this.currentOrderIndex = 0;
    }
    
    protected void moveToFirstACNode(int output)
    {
       this.acCurrentOrderIndex = 0;
    }
    
    protected int getCurrentACNodeIndex()
    {
        return this.ac_columnsOrder.get(acCurrentOrderIndex);
    }
    
    public void moveToNextNode()
    {
       this.currentOrderIndex += 1;
    }
    
    protected void moveToNextACNode()
    {
            this.acCurrentOrderIndex += 1;
    }
    
     protected boolean isLastACNode()
    {
        return this.acCurrentOrderIndex == this.ac_columnsOrder.size();
    }
     
    protected int getAGeneRandomly()
    {
         double l = this.getMaxNoOfGenes() + 1;
         double d = this.RandomNumbers.nextGaussian();
         
         while (d >= 1 || d <= -1)
         {
            d = this.RandomNumbers.nextGaussian(); 
         }
          
         if (d < 0)
         {
           d = (-1 * d) * (l/2);
         }
         else 
         {
             d = d * (l/2) + (l/2);
         }
               //System.out.println("***" + d);
               
         return (int) Math.round(d);
    }
    
    protected int getAnIndexRandomly()
    {
        double l = this.maxNodes - 1;
        double d = this.noGraphInput - 1;
        
        while (d <= this.noGraphInput - 1)
        { 
            d = this.RandomNumbers.nextGaussian();
        
            while (d >= 1 || d <= -1)
            {
                d = this.RandomNumbers.nextGaussian(); 
            }
             
            if (d < 0)
            {
                d = (-1 * d) * (l/2) + this.noGraphInput;
            }
            else 
            {
                 d = d * (l/2) + (l/2);
            }
            
         } 
        return (int) Math.round(d) ;
    }
    
    public short getAnActiveNodeGeneRandomly(int output)
    {
        // this.RandomNumbers.setSeed(System.currentTimeMillis());
         this.identifyActiveNodes(output);
         double l = this.columnsOrder.size() - 1;
         double d = this.RandomNumbers.nextGaussian();
         // this is needed. Sometimes it returns values larger than -1 and 1. 
         
         while (d >= 1 || d <= -1)
         {
            d = this.RandomNumbers.nextGaussian(); 
         }
          
         if (d < 0)
         {
           d = (-1 * d) * (l/2);
         }
         else 
         {
             d = d * (l/2) + (l/2);
         }
               //System.out.println("***" + d);
               
         return (short) Math.round(d);
    }
    
    protected short getAnActiveNodeRandomly(int output)
    {
         double l;
         double d;
         
         this.identifyActiveNodes(output);
         if (this.columnsOrder.size() == 1)
         {
             return this.columnsOrder.get(0);
         }
         else if (this.columnsOrder.size () > 1)
         {
            l = this.columnsOrder.size()-1;
            d = this.RandomNumbers.nextGaussian();
            // this is needed. Sometimes it returns values larger than -1 and 1. 
         
            while (d >= 1 || d <= -1)
            {
                 d = this.RandomNumbers.nextGaussian(); 
            }
         
            if (d < 0.0)
            {
                d = (-1 * d) * (l/2);
            }
            else 
            {
                 d = d * (l/2) + (l/2);
            }
                    
            return this.columnsOrder.get((int) Math.round(d));
         }
         else 
         {
             return 0;
         }
    }
    
    
    public boolean isActiveNode()
    {
        return (this.columnsOrder.size() > this.currentOrderIndex);
    }
    
    public boolean isLastNode()
    {
        return this.currentOrderIndex == this.columnsOrder.size();
    }
           
    /***
     * Get the fitness for one output
     * @return Double.MAX_VALUE if the output is outside the range. Otherwise returns the fitness.
     */
    public double getAFitnessValue(int output)
    {
        if (output < this.fitness.length)
        {
            return fitness[output];
        }
        else 
        {
            return Double.MAX_VALUE;
        }
        
    }
    
     /**
     * Get the value of mutationRate
     *
     * @return the value of mutationRate
     */
     public double getMutationRate()
     {
        return mutationRate;
     }

    /**
     * Set a fitness value for a given output
     * @param output - the output 
     * @param value - the fitness value obtained from the output
     */
    public void setAFitnessValue(int output, double value)
    {
        if (output < this.fitness.length)
        {
          //  System.out.println(Arrays.toString(this.fitness));
            this.fitness[output] = value;
          //  System.out.println(Arrays.toString(this.fitness));
            
        }
        
        if (this.fitness[output] < this.fitness[bestOutput])
        {
            this.bestOutput = (short) output;
        }
    }
    
    /***
     * compare the fitness value of the current graph against another one.
     * @param o
     * @return true if it is smaller  or equal. Otherwise it is  false.
     */
    public boolean IsFitnessSmallerOrEqual(AbstractCoordinates o)
    {
        return this.getAFitnessValue(this.getBestOutput()) <= o.getAFitnessValue(o.getBestOutput());
    }
    
     /***
     * compare the fitness value of the current graph against another one.
     * @param o
     * @return true if it is smaller  or equal. Otherwise it is  false.
     */
    public boolean IsFitnessSmaller(AbstractCoordinates o)
    {
        return this.getAFitnessValue(this.getBestOutput()) < o.getAFitnessValue(o.getBestOutput());
    }
    
     /***
     * compare the fitness value of the current graph against another one.
     * @param o
     * @return true if it is smaller  or equal. Otherwise it is  false.
     */
    public boolean IsFitnessEqual(AbstractCoordinates o)
    {
        return this.getAFitnessValue(this.getBestOutput()) == o.getAFitnessValue(o.getBestOutput());
    }
    
     /***
     * compare the fitness value of the current graph against another one.
     * @param o
     * @return true if it is smaller  or equal. Otherwise it is  false.
     */
    public boolean IsFitnessGreaterOrEqual(AbstractCoordinates o)
    {
        return this.getAFitnessValue(this.getBestOutput()) >= o.getAFitnessValue(o.getBestOutput());
    }
    
      /***
     * Returns the maximum number of genes available in the coordinates
     * @return the maximum number of genes in the coordinates
     */
    public int getMaxNoOfGenes()
    {
        return (this.getMaxNode()* this.nodeLength) + this.output.length;
    }  
     
    /***
     * Return the actual number of nodes in a CGP graph
     * @return 
     */
    public abstract short getMaxNode();
    /***
     * Initialise a coordinates
     */
    public abstract void initCoordinates();
    
    
    /***
     * get the current node from the active nodes. This function needs to be used 
     * in conjunction of movetoFirstNode and movetoNextNode
     * @return the currenct in the activeList or an empty node 
     */
    public abstract AbstractNode getCurrentNode();
    
   
    /***
     * get a given node from the graph
     * @param nodeIndex
     * @return an empty node if node index is not in the graph. Otherwise, returns the node.
     */
    public abstract AbstractNode getANode(int nodeIndex);
    
    
    /***
     * compute the Dot Code for the graph.
     * @param outputIndex
     * @return 
     */
     public abstract String getDotCode(int outputIndex);
     
     /****
      * identify all the active nodes
      */
      public abstract void identifyActiveNodes(int output);
    
     /***
      * mutate one Gene in the CGP graph
      * @param index 
      */ 
     protected abstract void mutateOneGene(int index);
     /***
      * get the type of the gene in a node.
      * @param index
      * @return 
      */
     protected abstract int getTypeOfIndex(int index);
     /****
      * check whether or not a function is in one of the active node
      * @param output
      * @param function
      * @return true is the function is present in the active node of the output, otherwise false.
      */
     public abstract boolean isAFunctionPresent(int output, short function);
    
    /***
     * Randomly select a valid input from a node before.
     * @param nodeIndex - relative node address (take into account graph inputs)
     * @return a valid input from a node before.
     */
     public short getAnInputFeedforwardRandomly(short nodeIndex)
     { 
       
        int min = 0;
        int max = nodeIndex;
        
        if (nodeIndex == this.noGraphInput & this.noGraphInput > 1)
        {   // randomly generates input for the first node of a graph. When no of inputs to the graph
            //are greater than one. Otherwise it crashes.
            min = 0;
            max = this.getNoGraphInput() - 1;
        }
        else if (nodeIndex > this.noGraphInput & nodeIndex < (this.noGraphInput + this.noNodesFeedForward))
        {   // randomly generates a valid input for the first nodes after the input and 
            // the number of nodes back. It avoid having negative input.
            min = 0 ;
            max = nodeIndex - 1;
        }
        else if (nodeIndex > (this.noGraphInput + this.noNodesFeedForward))
        {   // randomly generates a valid inputs for the nodes that are 
            // greater than the inputs + the NodNodesBack.          
            min = nodeIndex - this.noNodesFeedForward;
            max = nodeIndex - 1; 
        }   
        else
        {
            return 0;
        } 
       // System.out.println(min + ","  + max);
        
        if ((max - min) == 0)
        {
            return 0;
        }
        else
        {
            return (short) (this.RandomNumbers.nextInt(max - min) + min);
        }
     }
    
     
    /***
     * Randomly select a valid input pointing a node after.
     * @param nodeIndex - relative node address (take into account graph inputs)
     * @return a valid input from a node after. -1 if no feedback.
     */
    public short getAnInputFeedbackRandomly(short nodeIndex)
    { 
        int min = 0; 
        int max = this.maxNodes; 
        
        if (nodeIndex > (this.maxNodes - this.noNodesFeedback) & nodeIndex < this.maxNodes)
        {  // The node Index is at the end of a graph. It larger the nodesFeedback 
           // allowed.
           max = this.maxNodes;
        }
        else if (nodeIndex >= this.noGraphInput & this.maxNodes > nodeIndex)
        {   // this part of the CGP graph is between the number of noGraphInput
            // and output - nodNodesFeedbacks.
            max = nodeIndex + this.noNodesFeedback;
        }
        else 
        { // last node
            return 0;
        }
        return (short) (this.RandomNumbers.nextInt(max - min) + min); 
       
    }

     /**
     * select randomly a condition from a valid condition set
     * @return a valid function from the function set
     */
    public short getAConditionRandomly()
    {
        int index = 0;
        if ((this.conditionSet.length-1) > 0)
        {
            index = this.RandomNumbers.nextInt(this.conditionSet.length);
        }
        
        return this.conditionSet[index];
         
    } 
    
    /*protected short getAnACFunctionRandomly()
    {
        int index = (int) (Math.random() * this.ac_functionSet.length);
        while (index > (this.ac_functionSet.length-1))
        {
           index = (int) (Math.random() * this.ac_functionSet.length); 
        }
       
        return this.ac_functionSet[index];
    }*/
   
    /**
     * Get the value of noNodesFeedback
     *
     * @return the value of noNodesFeedback
     */
    public short getNoNodesFeedback()
    {
        return noNodesFeedback;
    }

    /**
     * Set the value of noNodesFeedback
     *
     * @param noNodesFeedback new value of noNodesFeedback
     */
    public void setNoNodesFeedback(short noNodesFeedback)
    {
        this.noNodesFeedback = noNodesFeedback;
    }

    /**
     * Get the value of noNodesInputFeedback
     *
     * @return the value of noNodesInputFeedback
     */
    public short getNoNodesInputFeedback()
    {
        return noNodesInputFeedback;
    }

    /*protected short[] getAc_output()
    {
        return ac_output;
    }*/

   /* public short[] getAc_functionSet()
    {
        return ac_functionSet;
    }*/

    /*public ArrayList<Short> getAc_columnsOrder()
    {
        return ac_columnsOrder;
    }*/
   
    protected short getAnOutputFromAcOutput(int index)
    {
        
        if (index < this.ac_output.length)
        {
            return ac_output[index];
        }
        else 
        {
            return 0;
        }
    }

    /****
     * mutate the CGP graph. The numbers of gene mutated is computed by maxGenes * mutationRate.
     */
    public void hyperMutate()
    {
        for (int i = 0; i <  (int)(this.getMaxNoOfGenes() * this.mutationRate); i++)
        {
            this.mutateOneGene(this.RandomNumbers.nextInt(this.getMaxNoOfGenes()));
        }
    }
    
    public void checkParam() throws Exception
    {
       if(this.noGraphInput < 1)
          throw new Exception("Illegal noGraphInput. It must be equal or greater than 1 : " + this.noGraphInput);
       else if (this.maxNodes <= this.noGraphInput)
          throw new Exception("Illegal maxiNodes. It must be greater than noGraphInput.");
       else if (this.functionSet.length == 0)
          throw new Exception ("Empty functions set. A valid function set must be set.");
       else if (this.noNodesFeedForward > this.maxNodes) 
          throw new Exception ("Illegal feedforwad. It needs to be smaller or equal the maximum of nodes");
       else if (this.noNodesFeedForward < 1)
          throw new Exception ("Illegal feedforward");
       else if (this.noNodesInputFeedforward == 0)
          throw new Exception("Illegal number of inputs feedforward. There must set to a minimum of 1.");           
    }
    
   /***
    * Offering the mutation of a gene on the active nodes can slow donw the search 
    * prw 28/10/2015
    * @return 
    */ 
   protected short[] init_ac_operator_set_acyclic()
   {
       short[] acOp = new short[6];
       acOp[0] = 100; //mutateAnOutput 
       acOp[1] = 101; //Input local search
       acOp[2] = 102; //Input mutation
       acOp[3] = 103; //mutateAproblemfunction 
       acOp[4] = 104; //mutateAproblemfunction of an active node
       acOp[5] = 105; //FunctionLocalSearch 
      
       return acOp;
   }
   
    protected short[] init_ac_operator_set_iterative()
   {
       short[] acOp = new short[15];
       acOp[0] = 100; //mutateAnOutput 
       acOp[1] = 101; //mutateAnAcOutput
       acOp[4] = 105; //mutateAnInputForward
       acOp[5] = 106; //mutateAcondition;
       acOp[6] = 107; //mutateAnInputFeedback
       acOp[8] = 112; //mutateAproblemfunction from an active node
       acOp[9] = 113; //mutateAsmfunction from an active node
       acOp[11] = 114; //mutateAnAcfunction from an active node
       acOp[12] = 115; //mutate an input forward from an active node
       acOp[13] = 116; //mutateAcondition of an active node
       acOp[14] = 117; //mutateAnInputFeedback of an active node */
      
      
       return acOp;
   }
  
   
   /** 
    * obsolete now!
    * @return 
    */
   protected short[] init_sm_operator_set()
   {
       short[] smOp = new short[5];
      // smOp[0] = 21; //Delete randomly
       smOp[0] = 22; //Append a node
       smOp[1] = 23; //Insert a node at a random position
       smOp[2] = 24; // Insert a node at the front
       smOp[3] = 25; //Swap the node with one randomly selected
       smOp[4] = 26; //move a node to a random position
       return smOp;
   }
    
    public short getNoACoutput()
    {
        return noACoutput;
    }

     public ArrayList<Short> getColumnsOrder()
    {
        return columnsOrder;
    }
   
}
