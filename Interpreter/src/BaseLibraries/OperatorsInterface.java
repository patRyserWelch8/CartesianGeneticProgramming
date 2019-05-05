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
public interface OperatorsInterface 
{

    public void applyHeuristic(int opCode, int offspring);

    /****
     *
     */
    public double getBestSolution();

    /***
     *  get return the best individual in the population. Does not count the sibling
     *
     *  @return BestSolutionValue
     */
    public double getBestTSPFitness();

    public BaseEvalManagement getEvalBudget();

    public PopulationManagement getPopulationParam();

    /****
     * initial population. To make sure the other operators works. the Siblings are too
     * iniatilised.
     */
    public void initPop();

    public void replaceRandom(int iteration);

    public void replaceWeakest(int iteration);

    /****
     * restart the population every ten iteration if the threshold of 20 has not been met.
     * @param iteration
     *
     */
    public void restartPop(int iteration);

    public void selectParents();
    
   
    
}
