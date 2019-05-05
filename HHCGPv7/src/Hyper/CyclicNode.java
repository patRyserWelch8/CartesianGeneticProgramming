/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.Arrays;

/**
 *
 * @author patriciaryser-welch
 */
public class CyclicNode extends AbstractNode
{
    
    protected short[] InputsFeedback; // feedback for an interation

    public CyclicNode()
    {
        super();
        this.InputsFeedback = new short[1];
        this.InputsFeedback[0] = -1;
    }

    public CyclicNode(int nodeIndex, short function, short[] inputsFeedForward, short[] inputsFeedback)
    {
        super(nodeIndex, function, inputsFeedForward);
        this.InputsFeedback = new short[inputsFeedback.length];
        System.arraycopy(inputsFeedback, 0, this.InputsFeedback, 0, inputsFeedback.length);
    }
    
    /**
     * Get the value of InputsFeedback
     *
     * @return the value of InputsFeedback
     */
    public short[] getInputsFeedback()
    {
        return InputsFeedback;
    }

    /**
     * Set the value of InputsFeedback
     *
     * @param InputsFeedback new value of InputsFeedback
     */
    public void setInputsFeedback(short[] InputsFeedback)
    {
        this.InputsFeedback = InputsFeedback;
    }

    /**
     * Get the value of InputsFeedback at specified index
     *
     * @param index the index of InputsFeedback
     * @return the value of InputsFeedback at specified index
     */
    public short getInputsFeedback(int index)
    {
        return this.InputsFeedback[index];
    }

    /**
     * Set the value of InputsFeedback at specified index.
     *
     * @param index the index of InputsFeedback
     * @param InputsFeedback new value of InputsFeedback at specified index
     */
    public void setInputsFeedback(int index, short InputsFeedback)
    {
        this.InputsFeedback[index] = InputsFeedback;
    }


    @Override public boolean isNodeValid()
    {
        if (super.nodeIndex >= 0)
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

    @Override public String getDotCode()
    {
        return  super.getFunction() + " [shape=circle style=filled fillcolor=purple fontcolor=white]";
    }

    @Override public Object clone() throws CloneNotSupportedException
    {
        return new CyclicNode(this.getNodeIndex(), this.getFunction(), this.getInputsFeedForward(), this.getInputsFeedback());
    }
    
    /*** 
     * 
     * @return a node with the inputs as well the function.
     */
    @Override public String toString()
    {
        String s = "|";
        s += "|";
        for (int i = 0; i < super.inputsFeedForward.length; i++)
        {
            s += super.inputsFeedForward[i];        
            if (i < super.inputsFeedForward.length-1)
            {
                s += ",";
            }
        }
        s += "|";
         
        for (int i = 0; i < this.InputsFeedback.length; i++)
        {
            s += this.InputsFeedback[i];
            if (i < this.InputsFeedback.length-1)
            {
                s += ",";
            }
        }
        s += "|";
        s += super.function;
        s += "|";
        
        return s;
    }  

   
    @Override public boolean equals(Object obj)
    {
        CyclicNode copy = (CyclicNode) obj;
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
        for (int i = 0; i < super.inputsFeedForward.length; i++)
        {
            if (super.inputsFeedForward[i] != copy.getAnInputFeedForward(i))
            {
                return false; 
            }
        }
        // check for the feedbacks   
        for (int i = 0; i < this.InputsFeedback.length; i++)
        {
            if (this.InputsFeedback[i] != copy.getInputsFeedback(i))
            {
                return false; 
            }
        }
        return true;
    }

    @Override
    public short getNodeSize()
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
  
    

    
}
