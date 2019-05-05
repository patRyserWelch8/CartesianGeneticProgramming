/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

/**
 * This is a class implement an interative node. It has a condition.
 * @author patriciaryser-welch
 */
public class IterativeNode extends CyclicNode
{
    
    private int condition;

    public IterativeNode()
    {
        super();
        this.condition = -1;
    }

    public IterativeNode(int nodeIndex, short function, short[] inputsFeedForward, short[] inputsFeedback,int condition)
    {
        super(nodeIndex, function, inputsFeedForward, inputsFeedback);
        this.condition = condition;
    }
   
    /**
     * Get the value of condition
     *
     * @return the value of condition
     */
    public int getCondition()
    {
        return condition;
    }

    /**
     * Set the value of condition
     *
     * @param condition new value of condition
     */
    public void setCondition(int condition)
    {
        this.condition = condition;
    }

    /***
     * 
     * @return a copy of the current iterative node
     * @throws CloneNotSupportedException 
     */
    @Override public Object clone() throws CloneNotSupportedException
    {
        
        return new IterativeNode(this.getNodeIndex(), this.getFunction(), this.getInputsFeedForward(), this.getInputsFeedback(),this.getCondition()); 
    }
    
    /*** 
     * 
     * @return a node with the inputs as well the function.
     */
    @Override public String toString()
    {
        String s = this.getNodeIndex() + "||";
        
        for (int i = 0; i < super.inputsFeedForward.length; i++)
        {
            s += super.inputsFeedForward[i];        
            if (i < super.inputsFeedForward.length-1)
            {
                s += ",";
            }
        }
        s += "-";
         
        for (int i = 0; i < super.InputsFeedback.length; i++)
        {
            s += super.InputsFeedback[i];
            if (i < super.InputsFeedback.length-1)
            {
                s += ",";
            }
        }
        
        s += "-";
        s += super.function;
        s += "-";
        s += this.condition;
        s += "||";
        return s;
    }
    
   /**
   * Check whether or not a node is the same. 
   * @param obj
   * @return true if it is the same. Otherwise returns false.
   */
    @Override public boolean equals(Object obj)
    {
        IterativeNode copy = (IterativeNode) obj;
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
        
        // check the condition of the node
        if (this.condition != copy.getCondition())
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
    
    
    /****
     * 
     * @return the dot code for an interative node
     */
     @Override public String getDotCode()
    {
        return  super.getFunction() + " [shape=circle style=filled fillcolor=red fontcolor=white]";
    }

   
     
}

    
