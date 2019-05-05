/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import java.util.ArrayList;

/**
 * This abstract class provides the basis of a population of CGP graphs.
 * @author patriciaryser-welch
 */
public abstract class AbstractPopulation
{
    /****
     * AbstractParameters of the CGP Graphs
     */
    protected AbstractParameters hyperParamToberemoved;
    /*****
     *  History of the fitness of the algorithms. 
     */
    protected ArrayList<Double> hyperFitnessHistory;
    /***
     *  iteration 
     */
    protected int iteration;
    
    /****
     * indicates whether or not an offspring has been upgraded
     */
    protected boolean OffspringUpgraded = false;
    
    /***
     * Constructor 
     * @param param 
     */
    public AbstractPopulation(AbstractParameters param)
    {
        this.hyperParamToberemoved = param;
        this.iteration = 0;
    }

  
  
  
}
