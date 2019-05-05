/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patriciaryser-welch
 */
public class AcyclicCoordinates extends AbstractCoordinates implements  Comparable<AcyclicCoordinates>
{
    protected  ArrayList<AcyclicNode> nodes;
    protected  ArrayList<AcyclicNode> previousNodes = new ArrayList();
    protected  ArrayList<Short>       previousColumnsOrder = new ArrayList();
    protected  boolean                changedActiveNode = false;        
    protected  AcyclicParameters hyperParam;

    
  
       
  /***
   * 
   * @param noGraphInput
   * @param noNodesInput
   * @param maxNodes
   * @param noOutput
   * @param noNodesForward
   * @param functionSet
   * @param mutationRate
   * @param CGPArray
   * @throws Exception 
   */
     public AcyclicCoordinates(short noGraphInput, short noNodesInput, short maxNodes, short noOutput, short noNodesForward, short[] functionSet, double mutationRate) throws Exception
     {
        super(noGraphInput, 
             noNodesInput, maxNodes, noOutput, noNodesForward,(short) 0, functionSet, mutationRate);
        this.nodes = new ArrayList(this.maxNodes);
        this.nodeLength = noNodesInput + 1;
        this.initCoordinates();
        this.bestOutput = 0;
        //this.hyperParam = someParameters;
       
     }
     
     public AcyclicCoordinates(AcyclicParameters someParameters) throws Exception
     {
         super(someParameters.getNoGraphInput(),  
               someParameters.getNoNodesInputFeedForward(),
               someParameters.getNoNodes(), 
               someParameters.getNoOutput(), 
               someParameters.getNoNodesForward(),
               (short) 0,
               someParameters.getFunctionSet(),
               someParameters.getMutationRate());  
        this.nodes = new ArrayList(this.maxNodes);
        this.nodeLength = this.noNodesInputFeedforward + 1;
        this.initCoordinates();
        this.bestOutput = 0;
        this.hyperParam = someParameters;
        
     }

    AcyclicCoordinates()
    {
    }
    
    public void setActiveNodeBeenChanged(boolean aValue)
    {
        this.changedActiveNode = false;
    }

    public void setNodes(ArrayList<AcyclicNode> nodes)
    {
        this.nodes = nodes;
    }

    public ArrayList<AcyclicNode> getNodes()
    {
        return nodes;
    }
    
    /***
     * constructor no 2: create acyclic coordinates from an existing one. It is used to clone.
     * @param noGraphInput -number of inputs to the CGP graph
     * @param noNodesInput - number of inputs to the node
     * @param noNodesForward - the number of nodes in a graphs
     * @param functionSet - Number of outputNodes
     * @param Nodes - All the nodes of a CGP graph
     * @param outputs - Al the outputs of a CGP graphs
     */
     public AcyclicCoordinates (short noGraphInput, short noNodesInput, short noNodesForward, short[] functionSet, ArrayList<AcyclicNode> Nodes, short[] outputs, double mutationRate, double[] fitness, boolean ChangeActiveNode) throws Exception
     {
        super(noGraphInput, noNodesInput,(short) Nodes.size(), (short) outputs.length , noNodesForward,(short) 0, functionSet, mutationRate);
        this.nodes = new ArrayList(Nodes.size());
        for (AcyclicNode node: Nodes)
        {
            this.nodes.add((AcyclicNode) node.clone());
        }
        this.output = new short[outputs.length];
        this.nodeLength = noNodesInput + 1;
        System.arraycopy(outputs, 0, this.output, 0, outputs.length);   
        System.arraycopy(fitness, 0, this.fitness, 0, fitness.length);
        this.changedActiveNode = ChangeActiveNode;
     }
     
     /***
      * Constructor no 3 
      * @param noGraphInput
      * @param noNodesInput
      * @param maxNodes
      * @param noOutput
      * @param noNodesForward
      * @param functionSet
      * @param mutationRate
      * @param CGPArray
      * @throws Exception 
      */
     public AcyclicCoordinates (short noGraphInput, short noNodesInput, short maxNodes, short noOutput, short noNodesForward, short[] functionSet, double mutationRate, short[] CGPArray, boolean ChangeActiveNode) throws Exception
     {
        super(noGraphInput, noNodesInput,maxNodes, noOutput , noNodesForward, (short) 0, functionSet, mutationRate);
        this.output = new short[noOutput];
        this.nodeLength = noNodesInput + 1;
        this.initNodesFromAnArray(CGPArray);
        
     }
   
     
     
    /***
     * get the number of existing nodes
     * @return the number of nodes in an acyclic graph.
     */
    @Override public short getMaxNode()
    {
        return (short) this.nodes.size();
    }
    public void setChangedActiveNode(boolean changedActiveNode)
    {
        this.changedActiveNode = changedActiveNode;
    }
    /**
     * initialise the coordinates randomly.
     */
    @Override public void initCoordinates()
    {
        short[] inputs;
        short function;
        AcyclicNode node;
        // add empty nodes in the arraylist
        for (short i = this.getNoGraphInput(); i <= this.maxNodes; i++)
        {
            function = this.getAfunctionRandomly();
            // initialise the nodes 
            inputs = new short[this.getNoNodesInputFeedforward()];
            for(int j = 0; j < inputs.length; j++)
            {
               inputs[j] = getAnInputFeedforwardRandomly(i);
            }
            node = new AcyclicNode(i, function, inputs);
            this.nodes.add(i - this.getNoGraphInput(), node); 
        }// end for
        
        //initialise the outputs
        for (int j = 0; j < this.output.length; j++)
        {
            this.output[j] = getAnOutputRandomly();
        }
     }

    @Override
    public boolean isAFunctionPresent(int output, short function)
    {
        this.moveToFirstNode(output); // identify all the active nodes
        while(! this.isLastNode())
        {
            if (this.getCurrentNode().getFunction() == function)
            {
                return true;
            }
            this.moveToNextNode();
        }
        return false;
    }
    
    public AcyclicParameters getHyperParam()
    {
        return hyperParam;
    }
    
    
    public String getAlgorithm(int outputIndex)
    {
        String s = "";
        this.identifyActiveNodes(outputIndex);
        for (int i = 0; i < this.columnsOrder.size();i++)
        {
            short index = this.columnsOrder.get(i);
            s+= this.getANode(index).getFunction();
              
        }
        
        return s; 
    }
     
    /** 
     * mutate one gen from the genotype at a given index
     * @param index  index gene 
     */
    @Override protected void mutateOneGene(int index) 
    {
        /// System.out.println (this.NodeIndex(index) + " , "  + this.nodes.get(this.getNodeIndex(index)).toString() + " ," + (index - (this.getMaxNode() * this.nodeLength) - 1));
         short nodeIndex = (short) this.getNodeIndex((short) index);
         
         if (this.getTypeOfIndex(index) == AcyclicCoordinates.INPUT_FORWARD) // input forward
         {
            short newInputForward = this.getAnInputFeedforwardRandomly(nodeIndex);
            this.nodes.get(nodeIndex).setInputsFeedForward(index % this.nodeLength, newInputForward);
            if (this.columnsOrder.contains(nodeIndex))
            {
                this.changedActiveNode = true;///
            }
         }
         else if (this.getTypeOfIndex(index) == AcyclicCoordinates.FUNCTION) // function
         { 
            short function = getAfunctionRandomly();
            while (function == this.nodes.get(this.getNodeIndex((short) index)).getFunction())
            {
                function = getAfunctionRandomly();
               // System.out.println(function + " " + this.nodes.get(this.getNodeIndex(index)).getFunction());
            }
            this.nodes.get(this.getNodeIndex((short) index)).setFunction(function);
            if (this.columnsOrder.contains(nodeIndex))
            {
                 this.changedActiveNode = true;///
            }
         }
         else if (this.getTypeOfIndex(index) == AcyclicCoordinates.OUTPUT) //output
         {
            //this.output[index - (this.getMaxNode() * this.nodeLength)] = (short) (this.RandomNumbers.nextInt((this.maxNodes + 1) - (this.maxNodes - this.getNoNodesBack())) + (this.maxNodes - this.getNoNodesBack()));
            this.output[index - (this.getMaxNode() * this.nodeLength) -1] = this.getAnOutputRandomly();
            this.changedActiveNode = true;
         }
         
    }
    /****
     * Return the current node.
     * @return the current node if there is some active nodes. otherwise an empty one
     */
    @Override public AcyclicNode getCurrentNode()
    {
       
        if (this.columnsOrder.size() > 0)
        {
            return this.getANode(this.columnsOrder.get(this.currentOrderIndex));
        }
        else
        {
            return new AcyclicNode();
        }
    }
    
 
    private void initNodesFromAnArray(short[] CGPArray)
    {
        short[] inputs = new short[this.getNoNodesInputFeedforward()];
        short function, nodeIndex;
        AcyclicNode Node;
        
        for(int i = 0; i < CGPArray.length; i++)
        {
            if (i >= ((this.getMaxNode() * this.nodeLength) - 1))
            { // output
                this.output[CGPArray.length - ((this.getMaxNode() * this.nodeLength) - 1)] = CGPArray[i];
            }
            else if ((i % this.nodeLength) < this.getNoNodesInputFeedforward())
            { // inputs
                inputs[i % this.nodeLength] = CGPArray[i];
            }
            else if ((i % this.nodeLength) == this.getNoNodesInputFeedforward())
            {  // function, create a node and to the arrayList
                function = CGPArray[i];
                nodeIndex = (short) ((i / this.nodeLength) + this.getNoGraphInput());
                Node = new AcyclicNode(nodeIndex, function, inputs);
            }          
        }
    }
    
    /**
     * Return an acyclic Node. It is empty if the index of the node is invalid.
     * @param nodeIndex
     * @return a node 
     */
    @Override public AcyclicNode getANode(int nodeIndex)
    {
        if ((nodeIndex - this.getNoGraphInput()) < this.nodes.size())
        {
            return this.nodes.get(nodeIndex - this.getNoGraphInput());
        }
        else 
        {
            return new AcyclicNode();
        }
    }
    
    /**
     * 
     * @return A string representing every nodes.
     */
    @Override public String toString()
    {
        String s = "";
       this.identifyActiveNodes(nodeLength);
        for(AcyclicNode node : this.nodes)
        {
            s+= node.toString();
        }
        s += "output - ";
        for (int i = 0; i < this.output.length; i++)
        {
            s+= this.output[i];
            if ((this.output.length - 1) > i)
            {
                s += ",";
            }
        } 
        s += "\n Active Nodes: \n";
        
        for (int i = 0; i < this.output.length; i++)
        {
            this.identifyActiveNodes(i);
       
            s += "\n output no " + this.output[i] + ": ";
            for (short index: this.columnsOrder)
            {
                s += this.getANode(index).toString();
            }
        }
        s += "\n fitness values: " + Arrays.toString(this.fitness);
        /*for (int i = 0; i < this.fitness.length; i++)
        {
        
            s += "\n fitness no " + this.fitness[i];
        } */
        s += "\n";
        return s;
    }
    
    public String toStringBestOutput()
    {
        String s = "";    
        this.identifyActiveNodes(bestOutput);
       
        for (short index: this.columnsOrder)
        {
           s += "op" + this.getANode(index).getFunction() + "-";
        }
        return s;
    }
    
    /***
     * clone the current acyclic coordinates
     * @return a copy of the acyclic coordinates.
     * @throws CloneNotSupportedException   
     */
    @Override public Object clone() throws CloneNotSupportedException 
    {
        try
        {
            return new AcyclicCoordinates(this.getNoGraphInput(), this.getNoNodesInputFeedforward(), this.getNoNodesFeedforward(), this.getFunctionSet(), (ArrayList<AcyclicNode>) this.nodes.clone(), this.getOutputs(), this.getMutationRate(),(double[]) this.fitness.clone(), this.changedActiveNode);
            
        } 
        catch (Exception ex)
        {
            Logger.getLogger(AcyclicCoordinates.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
    
    /**
     * identify the type of index. Either 0 for input, 1 for function and 2 to output
     * @param index
     * @return the type of a gene 0 - input, 2 - function, 4 - output, and 5 - invalid
     */
    protected int getTypeOfIndex(int index)
    {
        if (index == (this.getMaxNoOfGenes()))// - this.output.length))
        {
            return AcyclicCoordinates.OUTPUT; // this is an output
        }
        else if ((index % this.nodeLength) < this.getNoNodesInputFeedforward())
        {
            return AcyclicCoordinates.INPUT_FORWARD; // this is an input node forward
        }
        else if ((index % this.nodeLength) == this.getNoNodesInputFeedforward())
        {
            return AcyclicCoordinates.FUNCTION; // this is a function
        }
        return AcyclicCoordinates.INVALID; // invalid     
    }
   
    

    @Override public void identifyActiveNodes(int output)
    {
        ArrayList<Integer> nodesInList =  new ArrayList(); 
        this.columnsOrder.clear();
        if (output <= this.getNoOutput())
        {   
            // get the last node connected to the outputList
            // add it to the activeNodes List
            nodesInList.add ((int) this.getAnOutput(output));
            // read the actives nodes 
            while (!nodesInList.isEmpty()) 
            {
                // retrieve the top of the stack and remove the nodes
                int currentNode = nodesInList.get(0);
                // this.activeNodes.add(currentNode); 
                if (currentNode >= this.getNoGraphInput())
                {   // add the active node in the list
                    if (!this.isNodeActive((short) currentNode))
                    {
                        this.columnsOrder.add(0, (short) currentNode);
                    }
                    // add the inputs of a node in the list.
                    for (int j = 0; j < this.noNodesInputFeedforward; j++)
                    {
                        //if (!nodesInList.contains(currentNode))
                        //{
                            nodesInList.add((int) this.getANode(currentNode).getInputsFeedForward()[j]);                        
                        //}
                   }// end for
                }// end if
               
               nodesInList.remove(0);
            }// end while             
        } // end if noOuput
        Collections.sort(this.columnsOrder);
    }

    
    /*** 
     * not working yet needs attention
     * @param outputIndex
     * @return 
     */
    @Override  public String getDotCode(int outputIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
 
    /****
     *  mutate CGP graph anywhere within the graphs.
     */
    @Override public void hyperMutate()
    {
        this.previousColumnsOrder = (ArrayList<Short>) this.columnsOrder.clone();
        this.previousNodes =  (ArrayList<AcyclicNode>) this.nodes.clone();
        this.identifyActiveNodes(0);
        this.changedActiveNode = false;
  
        for (int i = 0; i <  (int)(this.getMaxNoOfGenes() * this.mutationRate); i++)
        {
           this.mutateOneGene(this.getAGeneRandomly());
        }
    }
    /***
     * mutate the CGP graph anywhere within the active nodes.
     */
    public void hyperMutateActiveNodes()
    {
             int nodeIndex, startNode, endNode, geneIndex;
             // pick up randomly an active node
             
             for (int i = 0; i < (int) (this.getMaxNoOfGenes() * this.mutationRate); i++)
             {
                
                 nodeIndex = this.getAnActiveGeneRandomly(0);
                 if (nodeIndex > 0)
                 {
                     startNode = ((nodeIndex - 1) * this.nodeLength);
                    endNode =  startNode + this.nodeLength;
                    geneIndex = this.RandomNumbers.nextInt(endNode - startNode) + startNode;
                    this.mutateOneGene(geneIndex);
                 }
                 
             }
            
         //    System.out.println("*after*" + this.toString());
           
    }
    
     /***
     * Return the difference between the fitness value of current acyclic AbstractCoordinates and the one passed as a parameter. It is used
     * to sort the a population of coordinates 
     * @param o
     * @return < 0 - the coordinates o have a greater fitness value than the current one. The current one is more efficient
     *         = 0 if there is no difference,
     *         > 0 the coordinates o have a small fitness value than the current one. The current one is less efficient
     *  
     */
    @Override public int compareTo(AcyclicCoordinates o)
    {
         return (int) (this.getAFitnessValue(this.bestOutput) - o.getAFitnessValue(o.getBestOutput()));   
    }
    
    
    public ArrayList<Short> getColumnsOrder()
    {
        return this.columnsOrder;
    }
    
   public boolean hasActiveNodeBeenChanged()
   {
     /* boolean unchanged = true; */
   /*   this.identifyActiveNodes(0);
      if (this.columnsOrder.size() == this.previousColumnsOrder.size())
      {
        for(int i = 0; i < this.columnsOrder.size(); i++)
        {
          short functionCurrent = this.getANode(this.columnsOrder.get(i)).getFunction();
          short functionPrevious = this.getANode(this.previousColumnsOrder.get(i)).getFunction();
          if (functionCurrent != functionPrevious)
          {
              return false;
          }
        }
        return true;
     
      }
      else 
      {
          return false;
      }
      /*if (!this.columnsOrder.equals(this.previousColumnsOrder))
      {
          return false;
      }
      else 
      {
         for (int i = 0; i < this.columnsOrder.size(); i++)
         {
             
             if (this.nodes.get(this.columnsOrder.get(i)).getFunction() != 
                 this.previousNodes.get(this.previousColumnsOrder.get(i)).getFunction())
             {
                 return false;
             }
         }
      } */
       
      
      return this.changedActiveNode;
   }

    private int getAnActiveGeneRandomly(int i)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    public void hyperEvaluate() throws CloneNotSupportedException
    {
        
          this.hyperParam.getInterpreter().setCGPGraph((AcyclicCoordinates) this.clone());
          this.hyperParam.getInterpreter().calculateHyperFitnessValue();
          this.setAFitnessValue(0, this.hyperParam.getInterpreter().getHyperFitnessValue(0));              
    }
    
    public void cloneElementsOfGraphs(ArrayList<AcyclicNode> Nodes, short[] outputs, AcyclicParameters someParameters, double[] fitness) throws CloneNotSupportedException
    {
        copyNodes(Nodes);
        this.setNodeLength();
        copyOutputs(outputs);
        this.hyperParam = someParameters;
        copyFitness(fitness);
    }
    
    public boolean hasActiveNodeBeenChanged(AcyclicCoordinates copy)
    {
        return this.changedActiveNode;
    }
    
    private void copyFitness(double[] aFitness)
    {
        System.arraycopy(aFitness, 0, this.fitness, 0, aFitness.length);
    }

    private void copyOutputs(short[] outputs)
    {
        this.output = new short[outputs.length];
        System.arraycopy(outputs, 0, this.output, 0, outputs.length);
    }

    private void copyNodes(ArrayList<AcyclicNode> Nodes) throws CloneNotSupportedException
    {
        AcyclicNode copy;
        this.nodes = new ArrayList(Nodes.size());
        for(AcyclicNode node: Nodes)
        {
            //   public IterativeNode(int nodeIndex, short function, short[] inputsFeedForward, short[] inputsFeedback,int condition)
 
            copy = new AcyclicNode(node.getNodeIndex(), node.getFunction(), node.getInputsFeedForward());
            this.nodes.add(copy);
        }
    }

    private void setNodeLength()
    {
        this.nodeLength = (int)this.noNodesInputFeedback +  (int) this.noNodesInputFeedforward + 2;
    }

 
    
}

