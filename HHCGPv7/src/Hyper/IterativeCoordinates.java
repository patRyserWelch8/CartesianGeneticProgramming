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
public class IterativeCoordinates extends AbstractCoordinates implements  Comparable<IterativeCoordinates> 
{
    protected ArrayList<IterativeNode> nodes;
    protected Loops iterations = new Loops();
    protected  IterativeParameters hyperParam;
    protected  ArrayList<Short> previousActiveNodes;
    protected  ArrayList<IterativeNode> previousNodes;
    
    protected  boolean                changedActiveNode = false;        

    /***
    * Construction no 1.
    * @param noGraphInput
    * @param noNodesInputFeedForward
    * @param noNodesInputFeedback
    * @param maxNodes
    * @param noOutput
    * @param noNodesForward
    * @param noNodesBack
    * @param functionSet
    * @param conditionSet
    * @param mutationRate 
    */
    public IterativeCoordinates(IterativeParameters someParameters)
    {
        
        super(someParameters.getNoGraphInput(),  
              someParameters.getNoNodesInputFeedForward(),
              someParameters.getNoNodesInputFeedback(),
              someParameters.getNoNodes(), 
              someParameters.getNoOutput(), 
              someParameters.getNoNodesForward(),
              someParameters.getNoNodeBack(), 
              someParameters.getFunctionSet(),
              someParameters.getConditionSet(),
              someParameters.getMutationRate()); 
        this.previousActiveNodes = new ArrayList<>();
        this.nodes = new ArrayList(maxNodes);
        this.setNodeLength();
        this.initCoordinates();
        this.bestOutput = 0;
        this.hyperParam = someParameters;
    }
    
    /***
     * constructor no 2: create acyclic coordinates from an existing one. It is used to clone.
     * @param noGraphInput -number of inputFeedforward to the CGP graph
     * @param noNodesInputFeedforward - number of inputFeedforward to the node
     * @param noNodesInputFeedback - number of inputFeedforward to the node
     * @param noNodesFeedBack - the number of nodes in a graphs
     * @param functionSet - Number of outputNodes
     * @param Nodes - All the nodes of a CGP graph
     * @param outputs - Al the outputs of a CGP graphs
     */
     public IterativeCoordinates (short noGraphInput, short noNodesInputFeedForward, short noNodesInputFeedback, short noNodesFeedForward, short noNodesFeedBack, short[] functionSet, short[] conditionSet, ArrayList<IterativeNode> Nodes, short[] outputs, double mutationRate, double[] fitness, IterativeParameters someParameters, boolean ChangeActiveNode) throws CloneNotSupportedException
     {
        super(noGraphInput, noNodesInputFeedForward, noNodesInputFeedback, (short) Nodes.size(), 
             (short) outputs.length , noNodesFeedForward, noNodesFeedBack, functionSet, conditionSet, mutationRate);
        this.previousActiveNodes = new ArrayList<>();
        this.hyperParam = someParameters;
        this.changedActiveNode = ChangeActiveNode;
    
        cloneElementsOfGraphs(Nodes, outputs, someParameters, fitness);
     }

     
    public IterativeCoordinates()
    {
        super();
        this.previousActiveNodes = new ArrayList<>();
        this.nodes = new ArrayList();
        
    }
    
    public void cloneElementsOfGraphs(ArrayList<IterativeNode> Nodes, short[] outputs, IterativeParameters someParameters, double[] fitness) throws CloneNotSupportedException
    {
        copyNodes(Nodes);
        this.setNodeLength();
        copyOutputs(outputs);
        this.hyperParam = someParameters;
        copyFitness(fitness);
        this.initLoops();
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

    private void copyNodes(ArrayList<IterativeNode> Nodes) throws CloneNotSupportedException
    {
        IterativeNode copy;
        this.nodes = new ArrayList(Nodes.size());
        for(IterativeNode node: Nodes)
        {
            //   public IterativeNode(int nodeIndex, short function, short[] inputsFeedForward, short[] inputsFeedback,int condition)
 
            copy = new IterativeNode(node.getNodeIndex(), node.getFunction(), node.getInputsFeedForward(), node.getInputsFeedback(), node.getCondition());
            this.nodes.add(copy);
        }
    }

    private void setNodeLength()
    {
        this.nodeLength = (int)this.noNodesInputFeedback +  (int) this.noNodesInputFeedforward + 2;
    }

   /***
     * get the number of existing nodes
     * @return the number of nodes in an acyclic graph.
     */
    @Override public short getMaxNode()
    {
        return (short) this.nodes.size();
    }

    @Override public IterativeNode getANode(int nodeIndex)
    {
        if ((nodeIndex - this.getNoGraphInput()) < this.nodes.size() & (nodeIndex - this.getNoGraphInput() >= 0))
        {
            return this.nodes.get(nodeIndex - this.getNoGraphInput());
        }
        else 
        {
            return new IterativeNode();
        }
    }
    /**
     * initialise the coordinates randomly.
     */
    @Override public void initCoordinates()
    {
        short[] inputsFeedForward;
        short[] inputsFeedback;
        short function;
        short condition;
        IterativeNode node;
        // add empty nodes in the arraylist
        for (short i = this.getNoGraphInput(); i <= this.maxNodes; i++)
        {
            function = this.getAfunctionRandomly();
            condition = this.getAConditionRandomly();
            
            // initialise the nodes 
            // initialise inputFeedforward feedforward
            inputsFeedForward = new short[this.getNoNodesInputFeedforward()];
            for(int j = 0; j < inputsFeedForward.length; j++)
            {
               inputsFeedForward[j] = this.getAnInputFeedforwardRandomly(i);
            }
            
            //initialise inputFeedbacks
            
            inputsFeedback = new short[this.getNoNodesInputFeedback()];
            for(int j = 0; j < inputsFeedback.length; j++)
            {
               inputsFeedback[j] = this.getAnInputFeedbackRandomly(i);
               if (inputsFeedback[j] > i)
               {
                   this.iterations.AddLoop(i, inputsFeedback[j]);
               }
            }
            
            node = new IterativeNode(i, function, inputsFeedForward, inputsFeedback, condition);
            this.nodes.add((i - this.getNoGraphInput()), node); 
        }// END for
        
        //initialise the outputs
        for (int j = 0; j < this.output.length; j++)
        {
            this.output[j] = getAnOutputRandomly();
        }
        
        this.previousNodes = (ArrayList<IterativeNode>) this.nodes.clone();
     }
  
     /***
     * Randomly select a valid input pointing a node after.
     * @param nodeIndex - relative node address (take into account graph inputFeedforward)
     * @return a valid input from a node after. -1 if no feedback.
     */
    @Override public short getAnInputFeedbackRandomly(short nodeIndex)
    { 
        short inputFeedback = -1;
     
        do 
        { 
            // randomlygenerate a feedback link. 
           inputFeedback = super.getAnInputFeedbackRandomly(nodeIndex);
           //then check if the loop is illegal
        } while (this.iterations.isLoopIllegal(nodeIndex, inputFeedback));
        return inputFeedback;
    }
    
    public void setInputFeedbackOfANode(short nodeIndex, short newValue)
    {
       int startIndex, endIndex, inputFeedback;
             // delete current loop
       startIndex = this.getANode(nodeIndex).getNodeIndex();
       endIndex =  this.nodes.get(nodeIndex).getInputsFeedback(0);
     
       this.iterations.DeleteLoop(startIndex, endIndex);
             
             // get a new inputFeedback
       this.getANode(nodeIndex).setInputsFeedback(0, newValue);
             // add new loop if is a new valid iteration
       if (newValue > nodeIndex)
       {
          this.iterations.AddLoop(startIndex, newValue);
       } 
    }
     
    /***
     * to do 
     * @param output 
     */
    @Override public void identifyActiveNodes(int output)
    {
        ArrayList<Integer> nodesInList =  new ArrayList(); 
        this.previousActiveNodes = (ArrayList<Short>) this.columnsOrder.clone();
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
                if (currentNode >= this.getNoGraphInput())
                {   // add the active node in the list
                    if (!this.isNodeActive((short) currentNode)) //not yet in the list.
                    {
                        this.columnsOrder.add(0, (short) currentNode);
                    }
                    
                    // add the inputFeedforward Feedwards of a node in the list.
                    for (int j = 0; j < this.noNodesInputFeedforward; j++)
                    {
                        nodesInList.add((int) this.getANode(currentNode).getInputsFeedForward()[j]); 
                    }// END for
                }// END if
                nodesInList.remove(0);
            }// END while      
            
            // add the iteration. If a column is an END of an interation then the START index is added. 
            // It is the jump statement. 
           
           for (int i = this.iterations.getLoops().size()-1; i >= 0; i--)
           {
               int startLoop = this.iterations.getLoops().get(i)[0];
               int endLoop = this.iterations.getLoops().get(i)[1];
               int j = this.columnsOrder.indexOf((short) startLoop);
               boolean stop = false;
              
               if (j > -1)
               {
                   //this.columnsOrder.get(j) <= endLoop || j <= this.columnsOrder.size()-2)
                    while (!stop & this.columnsOrder.get(j) <= endLoop)
                    {
                        if (j < this.columnsOrder.size() - 1) 
                        {
                            j++;
                        }
                        else 
                        {
                            stop = true;
                        }
                    }
                    
                    if (stop)
                    {
                        this.columnsOrder.add((short) startLoop);  
                    }
                    else
                    {
                        this.columnsOrder.add(j, (short) startLoop);
                    }
               }
               
              
               //this.columnsOrder.add(j, (short) startLoop);
           }
            
           this.initLoops();
        
        } // END if noOuput */
        
     }
    
    public int getLoopIndexOfCurrentNode()
    {
        int result = -1;
        if (this.DoesCurrentNodeStartALoop())
        {
            result = this.iterations.getIndexOfALoop(result); 
        }
        return result;
    }
    
    @Override protected void mutateOneGene(int geneIndex)
    {
         int nodeIndex = this.getNodeIndex((short) geneIndex);
         if (this.getTypeOfIndex(geneIndex) == IterativeCoordinates.INPUT_FORWARD) // input forward
         {
            short indexFeedForward = (short) (geneIndex % this.nodeLength);
            short inputForward = this.getAnInputFeedforwardRandomly((short) nodeIndex);
            this.nodes.get(nodeIndex).setInputsFeedForward(indexFeedForward,inputForward);
            if (this.columnsOrder.contains(nodeIndex))
            {
                this.changedActiveNode = true;///
            }
         }
         else if (this.getTypeOfIndex(geneIndex) == IterativeCoordinates.INPUT_FEEDBACK)
         {
             int startIndex, endIndex, inputFeedback;
             // delete current loop
             startIndex = this.nodes.get(nodeIndex).getNodeIndex();
             endIndex =  this.nodes.get(nodeIndex).getInputsFeedback(geneIndex % this.noNodesInputFeedback);
             this.iterations.DeleteLoop(startIndex, endIndex);
             
             // get a new inputFeedback
             inputFeedback = this.getAnInputFeedbackRandomly((short) nodeIndex);
             this.nodes.get(nodeIndex).setInputsFeedback(geneIndex % this.noNodesInputFeedback, (short) inputFeedback);
             // add new loop if is a new valid iteration
             if (inputFeedback > nodeIndex)
             {
                this.iterations.AddLoop(startIndex, inputFeedback);
             }
             
             if (this.columnsOrder.contains(nodeIndex))
             {
                this.changedActiveNode = true;///
             }
         }
         else if (this.getTypeOfIndex(geneIndex) == IterativeCoordinates.FUNCTION) // function
         { 
            short function = getAfunctionRandomly();
           
            this.nodes.get(nodeIndex).setFunction(function);
            
            if (this.columnsOrder.contains(nodeIndex))
            {
                this.changedActiveNode = true;///
            }
         }
         else if (this.getTypeOfIndex(geneIndex) == IterativeCoordinates.CONDITION) // function
         { 
            short condition = getAConditionRandomly();
           /* while (condition == this.nodes.get(nodeIndex).getFunction())
            {
                condition = this.getAConditionRandomly();
            }*/
            this.nodes.get(nodeIndex).setCondition(condition);
            if (this.columnsOrder.contains(nodeIndex))
            {
                this.changedActiveNode = true;///
            }
         }
         else if (this.getTypeOfIndex(geneIndex) == IterativeCoordinates.OUTPUT) //output
         {
            //this.output[geneIndex - (this.getMaxNode() * this.nodeLength)] = (short) (this.RandomNumbers.nextInt((this.maxNodes + 1) - (this.maxNodes - this.getNoNodesBack())) + (this.maxNodes - this.getNoNodesBack()));
            this.output[geneIndex - (this.getMaxNode() * this.nodeLength) -1] = this.getAnOutputRandomly();
            this.changedActiveNode = true;///
         }
    }
    
     /**
     * identify the type of index. Either 0 for input, 1 for function and 2 to output
     * @param index
     * @return the type of a gene 0 - input, 2 - function, 4 - output, and 5 - invalid
     */
    @Override protected int getTypeOfIndex(int index)
     {
        int pos = index % this.nodeLength;
        if (index > (this.getMaxNoOfGenes() - this.output.length))
        {
            return IterativeCoordinates.OUTPUT; // this is an output
        }
        else if (pos < this.getNoNodesInputFeedforward())
        {
            return IterativeCoordinates.INPUT_FORWARD; // this is an input node forward
        }
        else if (pos < (this.getNoNodesInputFeedforward() + this.getNoNodesInputFeedback()))
        {
            return IterativeCoordinates.INPUT_FEEDBACK; // this is an inputNode feedback
        }
        else if (pos == (this.getNoNodesInputFeedforward() + this.getNoNodesInputFeedback()))
        {
            return IterativeCoordinates.FUNCTION; // this is a function
        }
        else if (pos == (this.getNoNodesInputFeedforward() + this.getNoNodesInputFeedback() + 1))
        {
            return IterativeCoordinates.CONDITION;
        }
        return IterativeCoordinates.INVALID; // invalid     
     }
    
    public  IterativeParameters getHyperParam()
    {
        return hyperParam;
    }
    
    
    public String toStringBestOutput()
    {
        return this.getAlgorithm(bestOutput);
    }
    
    /***
     * This function returns the function with the condition used in START of a loop. This can 
     * change for each output index.
     * @return  an algorithm with the function number and condition number used.
     */
    public String getAlgorithm(int outputIndex)
    {
        String s = "";
        this.identifyActiveNodes(outputIndex);
        for (int i = 0; i < this.columnsOrder.size();i++)
        {
            short index = this.columnsOrder.get(i);
            if (this.columnsOrder.lastIndexOf(index) > i) 
            {
               s+= "while(" + this.getANode(index).getCondition() + ") -";  
               s+= this.getANode(index).getFunction();
            }
            else if (this.columnsOrder.indexOf(index) < i)
            {
                s+="end while-";
                
            }
            else 
            {
                s+= "-" + this.getANode(index).getFunction();
            }    
        }
        
        return s; 
    }
     /**
     * 
     * @return A string representing every nodes.
     */
    @Override public String toString()
    {
        String s = "Iterative  Coordinates \n nodes " ;
        for (int i = 0; i < this.output.length; i++)
        {
            this.identifyActiveNodes(i);
       
            s += "\n output no " + this.output[i] + ": " + this.columnsOrder.toString() + "\n";
            for (short index: this.columnsOrder)
            {
                s +=  this.getANode(index).toString();
            }
        } 
        
        s += "\n fitness values: \n";
        for (int i = 0; i < this.fitness.length; i++)
        {
            s += "\n fitness no " + this.fitness[i];
        }
        
        s += "\n"; 
        return s;
    }

     /***
     * clone the current iterative coordinates
     * @return a copy of the acyclic coordinates.
     * @throws CloneNotSupportedException 
     */
    @Override public Object clone() throws CloneNotSupportedException
    {
        return new IterativeCoordinates(this.getNoGraphInput(), this.getNoNodesInputFeedforward(), this.getNoNodesInputFeedforward(), this.getNoNodesFeedforward(), this.getNoNodesFeedback(), this.getFunctionSet(), this.getConditionSet(), (ArrayList<IterativeNode>) this.nodes.clone(), this.getOutputs(), this.getMutationRate(), (double[])this.fitness.clone(), this.hyperParam, this.changedActiveNode);
    }
 
    /**
     * leave it for now! 
     * @param outputIndex
     * @return 
     */
    @Override public String getDotCode(int outputIndex)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    /**
     * Give access to the current node in the list. 
     * @return  the current active node. If there is none the node is empty.
     */
    @Override public IterativeNode getCurrentNode()
    {
        if (this.columnsOrder.size() > 0)
        {
            return this.getANode(this.columnsOrder.get(this.currentOrderIndex));
        }
        else
        {
            return new IterativeNode();
        }
    }
    
    /***
     * Test whether the current node is a START of a loop. 
     * @return true if the node is a START of a loop.
     */
    public boolean DoesCurrentNodeStartALoop()
    {
        if (this.getCurrentNode().isNodeValid())
        {
            if (this.getCurrentNode().getInputsFeedback(0) > this.getCurrentNode().getNodeIndex())
            {
                int firstIndex  = this.columnsOrder.indexOf((short) this.getCurrentNode().getNodeIndex());
                int lastIndex = this.columnsOrder.lastIndexOf((short) this.getCurrentNode().getNodeIndex());
                if (firstIndex == this.currentOrderIndex & lastIndex > this.currentOrderIndex) //& lastIndex > (int) this.currentOrderIndex)
                {    
                    return true;
                }    
            }
        }
        
        return false; 
    }
    /***
     * Test whether the current node is the END of a loop.
     * @return true if the current node is the END of the loop. Otherwise, it returns false
     */
    public boolean DoesCurrentNodeEndALoop()
    {
        if (this.getCurrentNode().isNodeValid())
        {
            int firstIndex  = this.columnsOrder.indexOf((short) this.getCurrentNode().getNodeIndex());
            int lastIndex = this.columnsOrder.lastIndexOf((short) this.getCurrentNode().getNodeIndex());  
            if (this.getCurrentNode().getInputsFeedback(0) > this.getCurrentNode().getNodeIndex())
            {
                if (firstIndex < this.currentOrderIndex & lastIndex == this.currentOrderIndex) //& lastIndex > (int) this.currentOrderIndex)
              
                {
                      return true;
                }
            }
        }
        return false; 
    }
    
    /***
     * Test weather or not a node is sequential. Such node such needs to execute the function.
     * @return true if it is not a START or END loop. Otherwise false.
     */
    public boolean IsCurrentNodeSequential()
    {
        if (this.getCurrentNode().isNodeValid())
        {
            if (this.getCurrentNode().getInputsFeedback(0) <= this.getCurrentNode().getNodeIndex())
            {
                return true;
            }
        }
        return false; 
    }
    
    public void moveToStartOfCurrentLoop()
    {
        Short nodeIndex;
        int firstIndex;
        if (this.DoesCurrentNodeEndALoop())
        {
            nodeIndex = (short) this.getCurrentNode().getNodeIndex();
            firstIndex = this.columnsOrder.indexOf(nodeIndex);
            if (firstIndex > -1)
            {
                this.currentOrderIndex = firstIndex;
            }
        }
    }
    
    public void moveToEndOfCurrentLoop()
    {
        Short nodeIndex;
        int lastIndex;
        int firstIndex;
        if (this.DoesCurrentNodeStartALoop())
        {
             nodeIndex = (short) this.getCurrentNode().getNodeIndex();
             firstIndex = this.columnsOrder.indexOf(nodeIndex);
             lastIndex = this.columnsOrder.lastIndexOf(nodeIndex);
             if (lastIndex > firstIndex)
             {
                this.currentOrderIndex = lastIndex;
             }
        }
       
    }
    
    public boolean hasActiveNodeBeenChanged(IterativeCoordinates copy)
    {
        return this.changedActiveNode;
        /*this.identifyActiveNodes(0);
        copy.identifyActiveNodes(0);
            
        if (this.columnsOrder.size() == copy.getColumnsOrder().size())
        {
            for (int i = 0; i < this.columnsOrder.size(); i++)
            {
                short currentFunction = this.getANode(this.columnsOrder.get(i)).getFunction();
                short copyFunction = copy.getANode(copy.getColumnsOrder().get(i)).getFunction();
                if (currentFunction == copyFunction)
                {
                    if (this.columnsOrder.lastIndexOf(this.columnsOrder.get(i)) > i)
                    {
                        short currentCondition = (short) this.getANode(this.columnsOrder.get(i)).getCondition();
                        short copyCondition = (short) copy.getANode(copy.getColumnsOrder().get(i)).getCondition();
                    
                        if (currentCondition != copyCondition)
                        {
                            return false;
                        }
                    }
                }
                else 
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
        /*if (Arrays.equals(this.getOutputs(), copy.getOutputs()))
        {
            this.identifyActiveNodes(0);
            copy.identifyActiveNodes(0);
            
            
            if (this.columnsOrder.equals(copy.getColumnsOrder()))
            {
            
                    for (short nodeIndex: this.columnsOrder)
                    {
                        if (!this.getANode(nodeIndex).equals(copy.getANode(nodeIndex)))
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
        }
        else 
        {
            return false;
        }*/
    }

    /***
     * mutate the CGP graph.
     */
    @Override public void hyperMutate()
    {
        
        this.previousNodes = (ArrayList<IterativeNode>) this.nodes.clone();
        this.changedActiveNode = false;
        this.identifyActiveNodes(0);
        for (int i = 0; i < (int)(this.getMaxNoOfGenes() * this.mutationRate); i++)
        {
            this.mutateOneGene(this.RandomNumbers.nextInt(this.getMaxNoOfGenes()));
        }
    }
    
     public void hyperMutateActiveNodes()
    {
             int nodeIndex, startNode, endNode, geneIndex;
             // pick up randomly an active node
             this.mutateOneGene(this.getMaxNoOfGenes());
             this.identifyActiveNodes(0);
             nodeIndex = this.columnsOrder.get(this.RandomNumbers.nextInt(this.columnsOrder.size()));
             startNode = ((nodeIndex - 1) * this.nodeLength);
             endNode =  startNode + this.nodeLength;
             geneIndex = this.RandomNumbers.nextInt(endNode - startNode) + startNode;
             this.mutateOneGene(geneIndex);
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
    @Override public int compareTo(IterativeCoordinates o)
    {
         return (int) (this.getAFitnessValue(this.bestOutput) - o.getAFitnessValue(o.getBestOutput()));   
    }
    
    
      @Override public boolean isAFunctionPresent(int output, short function)
    {
        this.moveToFirstNode(output);
        while(this.isActiveNode())
        {
            if (this.getCurrentNode().getFunction() == function)
            {
                return true;
            }
            this.moveToNextNode();
        }
        return false;
    }
    
    protected void initNodesFromAnArray(short[] CGPArray)
    {
        short[] inputFeedforward = new short[this.getNoNodesInputFeedforward()];
        short[] inputFeedback = new short[this.getNoNodesInputFeedforward()];
       
        short function, nodeIndex, condition;
        function = 0;
        condition = 0;
        IterativeNode Node;
        
        for(int i = 0; i < CGPArray.length; i++)
        {
            if (i >= ((this.getMaxNode() * this.nodeLength) - 1))
            { // output
                this.output[CGPArray.length - ((this.getMaxNode() * this.nodeLength) - 1)] = CGPArray[i];
            }
            else if ((i % this.nodeLength) < this.getNoNodesInputFeedforward())
            { // inputFeedforward feedforward
                inputFeedforward[i % this.nodeLength] = CGPArray[i];
            }
            else if ((i % this.nodeLength) < (this.getNoNodesInputFeedforward() + this.getNoNodesInputFeedback()))
            { // inputFeedforward feedback
                inputFeedback[i % this.nodeLength] = CGPArray[i];
            }   
            else if ((i % this.nodeLength) == (this.getNoNodesInputFeedforward() + this.getNoNodesInputFeedback()))
            {  // function, create a node and to the arrayList
                function = CGPArray[i];   
            }  
            else if ((i % this.nodeLength) == (this.getNoNodesInputFeedforward() + this.getNoNodesInputFeedback() + 1))
            {
                condition = CGPArray[i];
                nodeIndex = (short) ((i / this.nodeLength) + this.getNoGraphInput());
                Node = new IterativeNode(nodeIndex, function, inputFeedforward, inputFeedback, condition);
            }
        }
    }
    
    public void hyperEvaluate() throws CloneNotSupportedException
    {
        
          this.hyperParam.getInterpreter().setCGPGraph((IterativeCoordinates) this.clone(), true);
          this.hyperParam.getInterpreter().calculateHyperFitnessValue();
          this.setAFitnessValue(0, this.hyperParam.getInterpreter().getHyperFitnessValue(0));              
    }
    
     protected void initLoops()
        {
            this.iterations = new Loops();
            
            for(IterativeNode node: this.nodes)
            {
                if (node.getInputsFeedback(0) > node.getNodeIndex())
                {
                    this.iterations.AddLoop(node.getNodeIndex(), node.getInputsFeedback(0));
                }
            }
        }
     
     
     public int getNoOfActiveLoops()
     {
         int result = 0;
         if (this.iterations.getNumberOfLoops() == 0)
         {
             return result;
         }
         else 
         {
             this.identifyActiveNodes(0);
             this.moveToFirstNode(0);
             while(!this.isLastNode())
             {
                if (this.DoesCurrentNodeStartALoop())
                {
                    result++;
                }
                this.moveToNextNode();
             }
             return result;
         }
     }
     
    /****
     * This private class manages the loops inside the coordinates
     */
     
     /***
     * Return the difference between the fitness value of current acyclic AbstractCoordinates and the one passed as a parameter. It is used
     * to sort the a population of coordinates 
     * @param o
     * @return < 0 - the coordinates o have a greater fitness value than the current one. The current one is more efficient
     *         = 0 if there is no difference,
     *         > 0 the coordinates o have a small fitness value than the current one. The current one is less efficient
     *  
     */
    public int compareTo(AcyclicCoordinates o)
    {
         return (int) (this.getAFitnessValue(bestOutput) - o.getAFitnessValue(o.getBestOutput()));   
    }
  
    public Loops getIterations()
    {
        return iterations;
    }
}//end iterative class
