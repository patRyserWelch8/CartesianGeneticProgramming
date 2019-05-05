/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

/**
 * This is a subclass of the abstract class "AbstractNode". It provides all the functions of a node in an acyclic digraph.
 * 
 * @author patriciaryser-welch
 */
public class AcyclicNode extends AbstractNode
{

    public AcyclicNode()
    {
        super();
    } 
    /****
     * Constructor of an acyclic node
     * @param nodeIndex
     * @param function
     * @param inputsFeedForward 
     */
    public AcyclicNode(int nodeIndex, short function, short[] inputsFeedForward)
    {
        super(nodeIndex, function, inputsFeedForward);
    }

    
    @Override public String getDotCode()
    {
       return  super.getFunction() + " [shape=circle style=filled fillcolor=blue fontcolor=white];";
    }

    /**
     * This function checks if the index of the node is > 0. If it is not the node becomes invalid. 
     * It also checks if the inputs are all smaller than the nodeIndex.
     * @return true if the node is valid. Otherwise false. 
     */
    @Override public boolean isNodeValid()
    {
        if (super.nodeIndex <= 0)
        {
            return true;
        }
        
        for (int i = 0; i < super.inputsFeedForward.length; i++)
        {
            if (super.inputsFeedForward[i] >= nodeIndex)
            {
                return true; 
            }
        }
        
        return false;
    }
    
    /*** 
     * 
     * @return a node with the inputs as well the function.
     */
    @Override public String toString()
    {
        String s = + this.nodeIndex + "||";
        for (int i = 0; i < super.inputsFeedForward.length; i++)
        {
            s += super.inputsFeedForward[i];
            if (i < (super.inputsFeedForward.length - 1))
            {
                s += ",";
            }
        }
        s += "-";
        s += super.function;
        s += "||";
        
        return s;
    }  

    /***
     * clone a node.
     * @return a copy of the current node. 
     * @throws CloneNotSupportedException 
     */
    @Override public Object clone() throws CloneNotSupportedException
    {
        return new AcyclicNode(super.getNodeIndex(), super.getFunction(), super.getInputsFeedForward());    
    } 
    
    @Override public boolean equals(Object obj)
    {
        AcyclicNode copy = (AcyclicNode) obj;
        // check the node index is the same.
        if (this.nodeIndex != copy.getNodeIndex())
        {
            return false;
        }
        // check the function of the node is the same
        if (this.function != copy.getFunction())
        {
            return false;
        }
        
        // check the feedforwards input are the same. 
        for (int i = 0; i < this.inputsFeedForward.length; i++)
        {
            if (this.inputsFeedForward[i] != copy.getAnInputFeedForward(i))
            {
                return false; 
            }
        }
        return true;
    }

    @Override public short getNodeSize()
    {
        return (short) (this.inputsFeedForward.length + 1);
    }
  
}
