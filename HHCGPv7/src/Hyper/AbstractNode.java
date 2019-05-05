/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.Arrays;

/**
 * This abstract class define a basic node in CGP. It cannot be instantiated 
 * directly, as it an imprint of an node. 
 * @author patriciaryser-welch
 */
public abstract class AbstractNode
{
    protected int nodeIndex; //Unique address of the node - immutable

    
    protected short function; // function of the node
    protected short[] inputsFeedForward = new short[0]; //  connection to input of values

    /***
     *  Constructor no1 - Set -1 to nodeIndex, function and null to feedfeedforward inputs. 
     *  These are the null values of an empty node. 
     */
    public AbstractNode()
    {
        this.nodeIndex = -1;
        this.function = -1;
        this.inputsFeedForward = new short[1];
        this.inputsFeedForward[0] = -1;
    } 

    /***
     * constructor no 2 - set the values of a node
     * @param nodeIndex   - Unique index of a node. Positive number > 0
     * @param function - any number between that is a short integer.
     * @param inputsFeedForward  - numbers that are smaller than nodeIndex.
     */
    public AbstractNode(int nodeIndex, short function, short[] inputsFeedForward)
    {
        this.nodeIndex = nodeIndex;
        this.function = function;
        this.inputsFeedForward = new short[inputsFeedForward.length];
        System.arraycopy(inputsFeedForward,0 , this.inputsFeedForward, 0, inputsFeedForward.length);
        this.ValidateInputFeedForward();
    }
    
    /**
     * Get the value of inputsFeedForward
     *
     * @return the value of inputsFeedForward
     */
    public short[] getInputsFeedForward()
    {
        return inputsFeedForward;
    }

    /**
     * Set the value of inputsFeedForward
     *
     * @param inputsFeedForward new value of inputsFeedForward
     */
    public void setAnInputFeedForward(short[] inputsFeedForward)
    {
        this.inputsFeedForward = inputsFeedForward;
        this.ValidateInputFeedForward();
    }

    /**
     * Get the value of inputsFeedForward at specified index
     *
     * @param index the index of inputsFeedForward
     * @return the value of inputsFeedForward at specified index
     */
    public short getAnInputFeedForward(int index)
    {
        return this.inputsFeedForward[index];
    }

    /**
     * Set the value of inputsFeedForward at specified index.
     *
     * @param index the index of inputsFeedForward
     * @param inputsFeedForward new value of inputsFeedForward at specified
     * index
     */
    public void setInputsFeedForward(int index, short inputsFeedForward)
    {
        this.inputsFeedForward[index] = inputsFeedForward;
        this.ValidateInputFeedForward();
    }

    /**
     * Get the value of function
     *
     * @return the value of function
     */
    public short getFunction()
    {
        return function;
    }

    /**
     * Set the value of function
     *
     * @param function new value of function
     */
    public void setFunction(short function)
    {
        this.function = function;
    }

    /**
     * Get the value of nodeIndex
     *
     * @return the value of nodeIndex
     */
    public int getNodeIndex() 
    {
        return nodeIndex;
    }
    
    public void setNodeIndex(int nodeIndex)
    {
        this.nodeIndex = nodeIndex;
    }

      /****
     * check if two nodes are the same. 
     * @param obj
     * @return true if all the values are the same, otherwise they are the same. 
     */
    
    private void ValidateInputFeedForward()
    {
        for(int i =0; i < this.inputsFeedForward.length;i++)
        {
            if (this.inputsFeedForward[i] == this.nodeIndex)
            {
                this.inputsFeedForward[i] = (short) (this.nodeIndex -1);
            }
        }
    }
    
    public abstract boolean isNodeValid();
    public abstract String getDotCode();
    public abstract short getNodeSize();
    
}
