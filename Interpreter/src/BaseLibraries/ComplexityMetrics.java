/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseLibraries;

/**
 *
 * @author patriciaryser-welch
 */
public class ComplexityMetrics
{
    
    private int noOfEdges = 0;
    private int noOfNodes = 0;
    private double noOfUniqueOperator = 0;
    private double noOfuniqueOperand = 1; //division
    private double totalNumberOfOperatorOccurrences = 0;
    private double totalNumberOfOperandOccurences = 0;
    


    public ComplexityMetrics()
    {
    
    }
    
    
    public int getCyclomaticComplexity()
    {
        return (this.noOfEdges - this.noOfNodes) + 2;
    }
    
    public double getProcessLength()
    {
        return this.noOfUniqueOperator * (Math.log(this.noOfUniqueOperator)/Math.log(2)) + this.noOfuniqueOperand * (Math.log(this.noOfuniqueOperand)/Math.log(2));
    }
    
    public double getProcessVolume()
    {
        return (this.totalNumberOfOperatorOccurrences + this.totalNumberOfOperandOccurences) * (Math.log(this.noOfUniqueOperator + this.noOfuniqueOperand)/Math.log(2));
    }
    
    public double getProcessDifficulty()
    {
        return (this.noOfUniqueOperator/2) * (this.totalNumberOfOperandOccurences/this.noOfuniqueOperand);
    }
    
    public double getTotalNumberOfOperatorOccurrences()
    {
        return totalNumberOfOperatorOccurrences;
    }
    
   

    public void setTotalNumberOfOperatorOccurrences(int totalNumberOfOperatorOccurrences)
    {
        this.totalNumberOfOperatorOccurrences = totalNumberOfOperatorOccurrences;
    }

    public double getTotalNumberOrOperandOccurences()
    {
        return totalNumberOfOperandOccurences;
    }

   
 
    /**
     * Get the value of noOfuniqueOperand
     *
     * @return the value of noOfuniqueOperand
     */
    public double getNoOfuniqueOperand()
    {
        return noOfuniqueOperand;
    }

    /**
     * Set the value of noOfuniqueOperand
     *
     * @param noOfuniqueOperand new value of noOfuniqueOperand
     */
    public void setNoOfuniqueOperand(int noOfuniqueOperand)
    {
        this.noOfuniqueOperand = noOfuniqueOperand;
    }

    /**
     * Get the value of noOfUniqueOperator
     *
     * @return the value of noOfUniqueOperator
     */
    public double getNoOfUniqueOperator()
    {
        return noOfUniqueOperator;
    }

    /**
     * Set the value of noOfUniqueOperator
     *
     * @param noOfUniqueOperator new value of noOfUniqueOperator
     */
    public void setNoOfUniqueOperator(int noOfUniqueOperator)
    {
        this.noOfUniqueOperator = noOfUniqueOperator;
    }


    /**
     * Get the value of noOfNodes
     *
     * @return the value of noOfNodes
     */
    public int getNoOfNodes()
    {
        return noOfNodes;
    }

    /**
     * Set the value of noOfNodes
     *
     * @param noOfNodes new value of noOfNodes
     */
    public void setNoOfNodes(int noOfNodes)
    {
        this.noOfNodes = noOfNodes;
    }


    /**
     * Get the value of noOfEdges
     *
     * @return the value of noOfEdges
     */
    public int getNoOfEdges()
    {
        return noOfEdges;
    }

    /**
     * Set the value of noOfEdges
     *
     * @param noOfEdges new value of noOfEdges
     */
    public void setNoOfEdges(int noOfEdges)
    {
        this.noOfEdges = noOfEdges;
    }
    
    public void setTotalNumberOfOperandOccurences(int totalNumberOfOperandOccurences)
    {
        this.totalNumberOfOperandOccurences = totalNumberOfOperandOccurences;
    }

    @Override
    public String toString()
    {
        return "ComplexityMetrics{" + "noOfEdges=" + noOfEdges + ", noOfNodes=" + noOfNodes + ", noOfUniqueOperator=" + noOfUniqueOperator + ", noOfuniqueOperand=" + noOfuniqueOperand + ", totalNumberOfOperatorOccurrences=" + totalNumberOfOperatorOccurrences + ", totalNumberOfOperandOccurences=" + totalNumberOfOperandOccurences + '}';
    }
    
    


}
