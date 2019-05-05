/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author patriciaryser-welch
 */
public class OnlineAcyclicCoordinates extends AcyclicCoordinates
{
    
    private IterativeCoordinates VariationOperator;
    private OnlineAcyclicCoordinates VariationCopy;
    private int t;
 
   
    
    public OnlineAcyclicCoordinates(AcyclicParameters someParameters) throws Exception
    {
        super(someParameters);
        super.hyperParam = someParameters;
        this.VariationOperator = new IterativeCoordinates();
    }

 /*   public OnlineAcyclicCoordinates(short noGraphInput, short noNodesInputFeedForward, short noNodesInputFeedback, short noNodesFeedForward, short noNodesFeedBack, short[] functionSet, short[] conditionSet, ArrayList<AcyclicNode> Nodes, short[] outputs, double mutationRate, double[] fitness, AcyclicParameters someParameters, boolean ChangeActiveNode) throws CloneNotSupportedException
    {
        super(noGraphInput, noNodesInputFeedForward, noNodesFeedForward, functionSet, conditionSet, Nodes, outputs, mutationRate, fitness, someParameters,ChangeActiveNode);
        this.VariationOperator = new AcyclicCoordinates();
    }*/
    
 
    @Override public Object clone() throws CloneNotSupportedException, NullPointerException
    {
        OnlineAcyclicCoordinates cloneAcyclicCoordinates;
        try
        {
            cloneAcyclicCoordinates = new OnlineAcyclicCoordinates(this.hyperParam);
            cloneAcyclicCoordinates.setVariarionOperator((IterativeCoordinates) this.getVariationOperator().clone());
            cloneAcyclicCoordinates.cloneElementsOfGraphs(super.nodes, super.output, super.hyperParam, super.fitness);
            return cloneAcyclicCoordinates;
      
        } catch (Exception ex)
        {
            Logger.getLogger(OnlineAcyclicCoordinates.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    
    }
    
    public IterativeCoordinates getVariationOperator()
    {
       
        return this.VariationOperator;
    }
    
    public void setVariarionOperator(IterativeCoordinates VariarionOperator)
    {
        if (this.VariationOperator != null)
        {
            this.VariationOperator = VariarionOperator;
        }
        else 
        {
            this.VariationOperator = new IterativeCoordinates();
        }
    }
    
    @Override public String toString()
    {
        String result = "Acyclic coordinates : " + super.toString();
        if (this.VariationOperator != null)
        {
            result += "\nVariation Operator "  + this.VariationOperator.toString();
        }
        else
        {
           result += "\nVariation Operator not set ";  
        }
        
        return result;
    }
    
    
    public OnlineAcyclicCoordinates hyperVariesCurrentIndividual(int output) throws CloneNotSupportedException
    {
        this.VariationCopy = (OnlineAcyclicCoordinates) this.clone();
        int startOfALoop, endOfALoop;
        boolean gotoTheLoop = false;
        
        this.VariationOperator.moveToFirstNode(output);
        while (!this.VariationOperator.isLastNode())
        {
           System.out.println("Mutation operator used " + this.VariationOperator.getCurrentNode().getFunction());
           if (this.VariationOperator.IsCurrentNodeSequential())
           {
               //sequential
                this.update();
            }
           else 
           { // Loop
              if (this.VariationOperator.DoesCurrentNodeEndALoop())
              {
                  t++;
                  this.VariationOperator.moveToStartOfCurrentLoop();
              }
              
              if (this.VariationOperator.DoesCurrentNodeStartALoop())
              {
                 startOfALoop = this.VariationOperator.getCurrentNode().getNodeIndex();
                 endOfALoop = this.VariationOperator.getCurrentNode().getInputsFeedback(0);
               //  System.out.println(this.VariationOperator.getCurrentNode().toString() + " startLoop " + startOfALoop + " endLoop" + endOfALoop);
                 //super.iterations.increaseCounterOfALoop(startOfALoop, endOfALoop);
                 this.VariationOperator.getIterations().increaseCounterOfALoop(startOfALoop, endOfALoop);
                 gotoTheLoop = this.executeCondition(startOfALoop, endOfALoop);
                 if (gotoTheLoop)
                 {
                    this.update();
                    //super.iterations.setCounterOfALoop(startOfALoop, endOfALoop, this.iterations.getCounterOfALoop(startOfALoop, endOfALoop)+1);
                    
                 }
                 else 
                 {
                    this.VariationOperator.moveToEndOfCurrentLoop();
                 }   
              }
           }
           this.VariationOperator.moveToNextNode();
        }
        return this.VariationCopy;
    }
    
    
    
    private boolean executeCondition(int StartNode, int  EndNode)
    {
        System.out.println("Condition " + this.VariationOperator.getCurrentNode().getCondition());
        switch(this.VariationOperator.getCurrentNode().getCondition())
        {
            case 10: return this.isCounterEqualTwo(StartNode, EndNode);
            case 20: return this.isCounterEqualFour(StartNode, EndNode);
            case 30: return this.isCounterEqualEight(StartNode, EndNode);
            case 40: return this.isCounterEqualTen(StartNode, EndNode);
            case 50: return this.isCounterEqualATenTheNoOfNodes(StartNode, EndNode);
            case 60: return this.isCounterEqualAQuarterTheNoOfNodes(StartNode, EndNode);
            case 70: return this.isCounterEqualHalfTheNoOfNodes(StartNode, EndNode);
            default: return true;
                
        }
    }
    
    
    private void update() throws CloneNotSupportedException
    {
       short newValue = 0;
       short nodeIndex = 0;
       
       /* Set the value of the node index. If the operatator is 
       * uses any node then it can pick randonly any nodes. Otherwise it selects
       * an active node. 
       */
       if (this.VariationOperator.getCurrentNode().getFunction() >= 101 &
           this.VariationOperator.getCurrentNode().getFunction() <= 105)
       {  // any nodes
           nodeIndex  = this.VariationCopy.getAnNodeIndexRandomly();
       }
       else if (this.VariationOperator.getCurrentNode().getFunction() >= 111 &
                this.VariationOperator.getCurrentNode().getFunction() <= 115)
       { // activeNodes 
           nodeIndex = this.VariationCopy.getAnActiveNodeRandomly(0);
       }
      
       
      //System.out.println ("BEFORE " + this.VariationOperator.getCurrentNode().getFunction() + " " + this.VariationCopy.getANode(nodeIndex).toString());
     
        
       if (this.VariationOperator.getCurrentNode().getFunction() == 100)
       {   // change the output
           newValue = this.VariationCopy.getAnOutputRandomly();
           this.VariationCopy.setOutput(0,newValue);
       }
       else if (this.VariationOperator.getCurrentNode().getFunction() == 101 || 
                this.VariationOperator.getCurrentNode().getFunction() == 111)
       {  //input feedforward 
           newValue = this.VariationCopy.getAnInputFeedforwardRandomly(nodeIndex);
           this.VariationCopy.getANode(nodeIndex).setInputsFeedForward(0,newValue);
       }
       else if (this.VariationOperator.getCurrentNode().getFunction() == 103 || 
                this.VariationOperator.getCurrentNode().getFunction() == 113)
       {
           
           newValue = this.VariationCopy.getAfunctionRandomly();
           this.VariationCopy.getANode(nodeIndex).setFunction(newValue);
       }
       else if (this.VariationCopy.getCurrentNode().getFunction() == 105 ||
                this.VariationCopy.getCurrentNode().getFunction() == 115)
       {
           this.ChangeAwholeNode(nodeIndex);
       }
       else if (this.VariationOperator.getCurrentNode().getFunction() == 121)
       {
           this.VariationCopy.getAnInputForwardLocalSearch();
       }
       else if (this.VariationOperator.getCurrentNode().getFunction() == 123)
       {
           this.VariationCopy.getAFunctionLocalSearch();
       }
       else if (this.VariationOperator.getCurrentNode().getFunction() == 131)
       {
           this.VariationCopy.setAnInputForwardtoAnActiveNode();
       }
       else if (this.VariationOperator.getCurrentNode().getFunction() == 133)
       {
           this.VariationCopy.swapFunctionsBetweenTwoActiveNodes();
       }
    
      //System.out.println ("AFTER " + this.VariationOperator.getCurrentNode().getFunction() +  "  " + this.VariationCopy.getANode(nodeIndex).toString());
     
    }
    
    private boolean isCounterEqualTwo(int StartNode, int endNode)
    {
        return this.VariationOperator.getIterations().isCounterLowerThanMaxValue(StartNode, endNode, 2);
    }
    
    private boolean isCounterEqualFour(int StartNode, int endNode)
    {
        return this.VariationOperator.getIterations().isCounterLowerThanMaxValue(StartNode, endNode, 4);
    }
    
    private boolean isCounterEqualTen(int StartNode, int endNode)
    {
        return this.VariationOperator.getIterations().isCounterLowerThanMaxValue(StartNode, endNode, 10);
    }
    
    private boolean isCounterEqualEight(int StartNode, int endNode)
    {
        return this.VariationOperator.getIterations().isCounterLowerThanMaxValue(StartNode, endNode, 8);
    }
    
    private boolean isCounterEqualHalfTheNoOfNodes(int StartNode, int endNode)
    {
         return this.VariationOperator.getIterations().isCounterLowerThanMaxValue(StartNode, endNode, (int) (this.nodes.size()/2));
    }
    
    private boolean isCounterEqualAQuarterTheNoOfNodes(int StartNode, int endNode)
    {
        return this.VariationOperator.getIterations().isCounterLowerThanMaxValue(StartNode, endNode, (int) (this.nodes.size()/4));
    }
    
    private boolean isCounterEqualATenTheNoOfNodes(int StartNode, int endNode)
    {
       return this.VariationOperator.getIterations().isCounterLowerThanMaxValue(StartNode, endNode, (int) (this.nodes.size()/10));

    }
    
    public void setAnInputForwardtoAnActiveNode()
    {
        short nodeIndex = this.getAnActiveNodeRandomly(0);
        short newInputForward = (short) (this.noGraphInput - 1);
        this.identifyActiveNodes(0);
        
        if (nodeIndex > 1 & this.columnsOrder.size() > 2)
        {
            newInputForward = super.getANode(nodeIndex).getAnInputFeedForward(0);
            for (int noOfAttempts = 0; noOfAttempts < this.columnsOrder.indexOf(nodeIndex); noOfAttempts++)
            {
                if (this.columnsOrder.indexOf(nodeIndex) == 1)
                {
                    newInputForward = 0;
                }
                else
                {
                    double r = this.RandomNumbers.nextInt(this.columnsOrder.indexOf(nodeIndex)); 
                    double random = 0 + (int)(r);
                    newInputForward = (short) super.getANode(this.columnsOrder.get((int) random)).getNodeIndex();
                }
            }
            
               
            if (newInputForward == super.getANode(nodeIndex).getAnInputFeedForward(0))
            {
                 super.getANode(nodeIndex).setInputsFeedForward(0, (short) (this.noGraphInput -1));
            }
            else
            {
                super.getANode(nodeIndex).setInputsFeedForward(0, newInputForward);
            }
         }
                    
          //  super.getANode(nodeIndex).setInputsFeedForward(0, (short) (newInputForward));// - this.hyperParam.getNoGraphInput()));
        
    }
    public void swapFunctionsBetweenTwoActiveNodes()
    {
        int firstNode = 0;
        int secondNode = 0;
        short copyFunction;
        this.identifyActiveNodes(0);
        if (this.columnsOrder.size() > 2)
        {
            while (firstNode == secondNode)
            {
                firstNode = this.getAnActiveNodeRandomly(0);
                secondNode = this.getAnActiveNodeRandomly(0);
            }
            copyFunction = this.getANode(firstNode).getFunction();
            this.getANode(firstNode).setFunction(this.getANode(secondNode).getFunction());
            this.getANode(secondNode).setFunction(copyFunction);
          
        }
    }
    public void getAFunctionLocalSearch() throws CloneNotSupportedException
    {
        int currentNode = 0;
        
        short originalFunction = 0 ;
        short bestFunction;
        short newFunction = 0;
        double bestFitness = 0.0;
        double originalFitness = 1.0;
        
        this.identifyActiveNodes(0);
        
        currentNode = getAnActiveNodeRandomly(0);
        if(currentNode > 0)
        {
            originalFunction = this.getANode((short)currentNode).getFunction();
            originalFitness = this.fitness[0];
            bestFunction = originalFunction;
       
            for (int i = 0; i < 3; i++)
            {
                int SaveFunction = newFunction;
                newFunction = this.getAfunctionRandomly();
               
                  
                this.getANode(currentNode).setFunction(newFunction);
                super.hyperEvaluate();
            
                if (this.fitness[0] < bestFitness)
                {
                    bestFitness = this.fitness[0];
                    bestFunction = newFunction;
                }
            }
       
            this.getANode((short)currentNode).setFunction(bestFunction);  
         }
    }
    
    
    public void ChangeAwholeNode(short nodeIndex)
    {
        if (nodeIndex < this.nodes.size())
        {
            short inputForward = this.getAnInputFeedforwardRandomly(nodeIndex);
            short function = this.getAfunctionRandomly();
            
            this.getANode(nodeIndex).setInputsFeedForward(0, inputForward);
            this.getANode(nodeIndex).setFunction(function);
        }
    }
    
    
    public void getAnInputForwardLocalSearch() throws CloneNotSupportedException
    {
        int currentNode = 0;
        
        short originalInput = 0 ;
        short bestInput = 0;
        short newInput = 0;
        double bestFitness = 0.0;
        double originalFitness = 1.0;
            
        // initialise the current node. 
        // if the algorithm as more than one active node, then select randomnly 
        this.identifyActiveNodes(0);
        if (this.columnsOrder.size() > 1 )
        {
          //  while (originalInput < 1)
          //  {
                currentNode = getAnActiveNodeRandomly(0);
                originalInput = this.getANode((short)currentNode).getInputsFeedForward()[0];
          //  }
        }
        else if (this.columnsOrder.size() == 1)
        {
             currentNode = this.columnsOrder.get(0);
             originalInput = this.getANode((short)currentNode).getInputsFeedForward()[0];
        }
       
        if (originalInput > this.noGraphInput)
        {
            originalFitness = this.fitness[0];
            bestInput = originalInput;
            newInput = originalInput;
            bestFitness = this.fitness[0];
        
            for (int i = 0; i < 3; i++)
            {
                newInput = this.getAnInputFeedforwardRandomly((short) currentNode);
              
                this.getANode(currentNode).setInputsFeedForward(0,newInput);
                super.hyperEvaluate();
            
                if (this.fitness[0] < bestFitness)
                {
                    bestFitness = this.fitness[0];
                    bestInput = newInput;
                }
            }
            this.getANode((short)currentNode).setInputsFeedForward(0, bestInput);
        } 
    }

    public OnlineAcyclicCoordinates getVariationCopy()
    {
        return VariationCopy;
    }
    
    
   
    
    
}   
