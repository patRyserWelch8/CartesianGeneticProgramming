/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hhcgpv7;

import Hyper.IterativeCoordinates;
import Hyper.OnlineIterativeCoordinates;

/**
 *
 * @author patriciaryser-welch
 */
public class HHCGPv7
{
    private static Hyper.IterativeParameters IterativeCGPparam = new Hyper.IterativeParameters();
     private static Hyper.IterativeParameters MutationParam = new Hyper.IterativeParameters();
   

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws CloneNotSupportedException
    {
        
    /*   Loops l = new Loops();
       
       l.AddLoop(1, 10);
       l.AddLoop(2, 8);
       l.AddLoop(3, 5);
       
      
      
      
       while (l.isCounterLowerThanMaxValue(1, 10, 6))
       {
           System.out.println("IN LOOP : " + l.getCounterOfALoop(1, 10));
           while (l.isCounterLowerThanMaxValue(3, 5, 2))
           {
                System.out.println("------ IN LOOP : " + l.getCounterOfALoop(3, 5));
                l.increaseCounterOfALoop(3, 5);
           }
           l.increaseCounterOfALoop(1, 10);
           
       }*/
              
       OnlineIterativeCoordinates t; 
       OnlineIterativeCoordinates p;
       IterativeCoordinates mutation;
       
       short[] m = new short[1];
       short[] c = new short[1];
       short[] x = new short[4];
       short[] y = new short[3];
       
       m[0] = 133;
      /* m[1] = 101;
       m[2] = 102;
       m[3] = 103;
       m[4] = 104;
       m[5] = 111;
       m[6] = 112;
       m[7] = 113;
       m[8] = 114;*/
       c[0] = 10; 
       //c[1] = 20;
      // c[2] = 30;
      // c[3] = 40;
      x[0] = 1;
       x[1] = 2;
       x[2] = 3;
       x[3] = 4;
       y[0] = 1;
       y[1] = 2;
       y[2] = 3;
       initCGPParamLearning_Iterative();
       initMutationParam();
       HHCGPv7.IterativeCGPparam.setConditionSet(y);
       HHCGPv7.IterativeCGPparam.setFunctionSet(x);
       HHCGPv7.MutationParam.setConditionSet(c);
       HHCGPv7.MutationParam.setFunctionSet(m);
       
       mutation = new IterativeCoordinates(HHCGPv7.MutationParam);
       t = new OnlineIterativeCoordinates(HHCGPv7.IterativeCGPparam);
       t.setVariarionOperator(mutation); 
       
    
       System.out.println(t); 
       p = (OnlineIterativeCoordinates) t.clone();
       
       t.hyperVariesCurrentIndividual(0);
       t = t.getVariationCopy();
       System.out.println(t.hasActiveNodeBeenChanged(p));
       
        // TODO code application logic here
    }
    
    
    private  static void initCGPParamLearning_Iterative()
    {
       HHCGPv7.IterativeCGPparam.setMutationRate(0.05); // was set to 5%
       HHCGPv7.IterativeCGPparam.setNoNodes((short) 20); //200
       HHCGPv7.IterativeCGPparam.setHyperMaxIterations((short) 1500); //set to 1500 // SET TO 1200
       HHCGPv7.IterativeCGPparam.setNoNodesForward((short)5); // would like some stage evolution if poss
       HHCGPv7.IterativeCGPparam.setNoNodeBack((short) 5);// would like some stage evolu
       HHCGPv7.IterativeCGPparam.setNoHyperOffSpring((short) 1);
       HHCGPv7.IterativeCGPparam.setNoHyperParents((short) 1);
       HHCGPv7.IterativeCGPparam.setNoOutput((byte) 1);
       HHCGPv7.IterativeCGPparam.setNoGraphInput((byte) 1);
       HHCGPv7.IterativeCGPparam.setNoNodesInputFeedForward((byte) 1);
       HHCGPv7.IterativeCGPparam.setNoNodesInputFeedback((short) 1);
     }
    
    private  static void initMutationParam()
    {
       HHCGPv7.MutationParam.setMutationRate(0.05); // was set to 5%
       HHCGPv7.MutationParam.setNoNodes((short) 10); //200
       HHCGPv7.MutationParam.setHyperMaxIterations((short) 10); //set to 1500 // SET TO 1200
       HHCGPv7.MutationParam.setNoNodesForward((short) 5); // would like some stage evolution if poss
       HHCGPv7.MutationParam.setNoNodeBack((short) 5);// would like some stage evolu
       HHCGPv7.MutationParam.setNoHyperOffSpring((short) 1);
       HHCGPv7.MutationParam.setNoHyperParents((short) 1);
       HHCGPv7.MutationParam.setNoOutput((byte) 1);
       HHCGPv7.MutationParam.setNoGraphInput((byte) 1);
       HHCGPv7.MutationParam.setNoNodesInputFeedForward((byte) 1);
       HHCGPv7.MutationParam.setNoNodesInputFeedback((short) 1);
     }
}
