/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.ArrayList;

/**
 *
 * @author Raven
 */
public class IterativeCoordinatesWithLearnedMutation extends IterativeCoordinates
{
   
    public IterativeCoordinatesWithLearnedMutation(IterativeParameters someParameters) 
    {
        super(someParameters);
    }

    public IterativeCoordinatesWithLearnedMutation(short noGraphInput, short noNodesInputFeedForward, short noNodesInputFeedback, short noNodesFeedForward, short noNodesFeedBack, short[] functionSet, short[] conditionSet, ArrayList<IterativeNode> Nodes, short[] outputs, double mutationRate, double[] fitness, IterativeParameters someParameters, boolean ChangeActiveNode) throws CloneNotSupportedException 
    {
        super(noGraphInput, noNodesInputFeedForward, noNodesInputFeedback, noNodesFeedForward, noNodesFeedBack, functionSet, conditionSet, Nodes, outputs, mutationRate, fitness, someParameters, ChangeActiveNode);
    }

    public IterativeCoordinatesWithLearnedMutation() 
    {
        
    }
    
    /****
     *  mutate CGP graph anywhere within the graphs.
     */
    @Override public void hyperMutate()
    {
        int index = 0;
        int newValue = 0;
        this.previousActiveNodes = (ArrayList<Short>) this.columnsOrder.clone();
        this.previousNodes =  (ArrayList<IterativeNode>) this.nodes.clone();
        this.identifyActiveNodes(0);
        this.changedActiveNode = true;
  
        for (int i = 0; i < 10; i++)
        {
            // 111 - change the input feedforward of an active node
            index = this.getAnActiveNodeRandomly(0);
            newValue = this.getAnInputFeedforwardRandomly((short) index);
            this.getANode((short) index).setInputsFeedForward(0,(short)newValue);
            
            //113 - change the function of an active node
            index = this.getAnActiveNodeRandomly(0);
            newValue = this.getAfunctionRandomly();
            this.getANode((short) index).setFunction((short)newValue);
            
            //114 - change the condition of an active node
            index = this.getAnActiveNodeRandomly(0);
            newValue = this.getAConditionRandomly();
            this.getANode((short) index).setCondition((short)newValue);
            
            //101 - change an input feedforward of any nodes
            index = this.getAnNodeIndexRandomly();
            newValue = this.getAnInputFeedforwardRandomly((short) index);
            this.getANode((short) index).setInputsFeedForward(0,(short)newValue);
            
            //102 - change an input feedforward of any nodes
            index = this.getAnNodeIndexRandomly();
            newValue = this.getAnInputFeedbackRandomly((short) index);
            this.getANode((short) index).setInputsFeedback(0,(short)newValue);
            
        }
    } 
    
    
}
