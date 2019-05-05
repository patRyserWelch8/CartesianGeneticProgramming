/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Raven
 */
public class AcyclicCoordinatesWithLearnedMutation extends AcyclicCoordinates
{

    public AcyclicCoordinatesWithLearnedMutation(short noGraphInput, short noNodesInput, short maxNodes, short noOutput, short noNodesForward, short[] functionSet, double mutationRate) throws Exception 
    {
        super(noGraphInput, noNodesInput, maxNodes, noOutput, noNodesForward, functionSet, mutationRate);
    }

    public AcyclicCoordinatesWithLearnedMutation(AcyclicParameters someParameters) throws Exception 
    {
        super(someParameters);
    }

    public AcyclicCoordinatesWithLearnedMutation() 
    {
    }

    public AcyclicCoordinatesWithLearnedMutation(short noGraphInput, short noNodesInput, short noNodesForward, short[] functionSet, ArrayList<AcyclicNode> Nodes, short[] outputs, double mutationRate, double[] fitness, boolean ChangeActiveNode) throws Exception 
    {
        super(noGraphInput, noNodesInput, noNodesForward, functionSet, Nodes, outputs, mutationRate, fitness, ChangeActiveNode);
    }

    public AcyclicCoordinatesWithLearnedMutation(short noGraphInput, short noNodesInput, short maxNodes, short noOutput, short noNodesForward, short[] functionSet, double mutationRate, short[] CGPArray, boolean ChangeActiveNode) throws Exception 
    {
        super(noGraphInput, noNodesInput, maxNodes, noOutput, noNodesForward, functionSet, mutationRate, CGPArray, ChangeActiveNode);
    }
    
    /****
     *  mutate CGP graph anywhere within the graphs.
     */
    @Override public void hyperMutate()
    {
        int index = 0;
        int newValue = 0;
        this.previousColumnsOrder = (ArrayList<Short>) this.columnsOrder.clone();
        this.previousNodes =  (ArrayList<AcyclicNode>) this.nodes.clone();
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
            
            //101 - change an input feedforward of any nodes
            index = this.getAnNodeIndexRandomly();
            newValue = this.getAnInputFeedforwardRandomly((short) index);
            this.getANode((short) index).setInputsFeedForward(0,(short)newValue);
        }
    } 
    
    @Override public Object clone() throws CloneNotSupportedException 
    {
        try
        {
            return new AcyclicCoordinatesWithLearnedMutation(this.getNoGraphInput(), this.getNoNodesInputFeedforward(), this.getNoNodesFeedforward(), this.getFunctionSet(), (ArrayList<AcyclicNode>) this.nodes.clone(), this.getOutputs(), this.getMutationRate(),(double[]) this.fitness.clone(), this.changedActiveNode);
            
        } 
        catch (Exception ex)
        {
            Logger.getLogger(AcyclicCoordinates.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
    }
}
