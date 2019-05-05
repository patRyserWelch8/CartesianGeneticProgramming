
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseTSP;

import BaseLibraries.GeneralParam;


/**
 *
 * @author patriciaryser-welch
 */
public class TSP_Param extends GeneralParam
{
    public TSP_Param() 
    {
    
    }
    
    public TSP_Param(long seedValue) 
    {
        this.seedValue = seedValue;
    }

    public int getInstanceOptimaCost()
    { //source: http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/STSP.html (accessed on 6/11/2014))
        switch(this.Instances)
        {
            case 0: return 48191; // pr299
            case 1: return 107217; //pr439 **
            case 2: return 6773; //rat575
            case 3: return 41910; //u724
            case 4: return 8806; //rat783 ***
            case 5: return 56892; //pcb1173
            case 6: return 50801; //d1291 **
            case 7: return 64253; //u2152
            case 8: return 19982859; //usa13509 **
            case 9: return 645238; //d18512
            case 10: return 27603; //wi29
            case 11: return 6656; //dj38
            case 12: return 9352; //qa194
            case 13: return 79114;//uy734
            case 14: return 95345; //zi929
            case 15: return 11340; //lu980
            case 16: return 26051; //rw1621
            case 17: return 96132; //nu3496
            case 18: return 1290319;// ca4663
            case 19: return 394609; //tz6117
            case 20: return 172350; //eg7146
            case 21: return 238314; //ym7663
            case 22: return 206128; //ei8246
            case 23: return 837377; //ar9152
            case 24: return 491924; //ja9847
            case 25: return 300899; //gr9882
            case 26: return 1061387; //kz9976;
            case 27: return 520527; //fi10639
            case 28: return 176940; //ho14473
            case 29: return 427377; //mo14185
            case 30: return 55731; //it16862
            case 31: return 569288; //vm22775
            case 32: return 855597; //sw24978
            case 33: return 959011; //bm33708
            case 34: return 4565452; //ch71009
                
            default: return Integer.MIN_VALUE;
        }
       
    }

    public short[] getEMAFunctionSet() 
    {
        short[] functionSet = new short[15];
        
        functionSet[0] = 0; // randomReinsertion
        functionSet[1] = 1; // swapTwo
        functionSet[2] = 2; // shuffle
        functionSet[3] = 3; // shuffleSubSequence
        functionSet[4] = 4; // nOptMove
        functionSet[5] = 6; // twoOptLocalSearch
        functionSet[6] = 7; // bestImpTwoOptLocalSearch
        functionSet[7] = 8; // threeOptLocalSearch
        functionSet[8] = 9; // ox
        functionSet[9] = 10; // pmx
        functionSet[10] = 11; // ppx
        functionSet[11] = 12; // oneX
        functionSet[12] = 13; // ReplaceRandom
        functionSet[13] = 14; // ReplaceLeastFit
        functionSet[14] = 15; // RestartPop
        return functionSet;
    }
   

    public short[] getEEAFunctionSet() 
    {
        short[] functionSet = new short[13];
        
        functionSet[0] = 0;
        functionSet[1] = 1;
        functionSet[2] = 2;
        functionSet[3] = 3;
        functionSet[4] = 9;
        functionSet[5] = 10;
        functionSet[6] = 11;
        functionSet[7] = 12;
        functionSet[8] = 13;
        functionSet[9] = 14;
        return functionSet;
    }

   

    
    public byte[] getILSFunctionSet()
    {
        byte[] functionSet = new byte[10];
        
        functionSet[0] = 0;
        functionSet[1] = 1;
        functionSet[2] = 2;
        functionSet[3] = 3;
        functionSet[4] = 4;
        functionSet[5] = 6;
        functionSet[6] = 7;
        functionSet[7] = 8;
        functionSet[8] = 13;
        functionSet[9] = 14;
        return functionSet;
    }
    

    public short[] getHybridsFunctionSet() 
    {
        short[] functionSet = new short[9];
        
        functionSet[0] = 0;
        functionSet[1] = 1;
        functionSet[2] = 2;
        functionSet[3] = 3;
        functionSet[4] = 4;
        functionSet[5] = 6;
        functionSet[6] = 7;
        functionSet[7] = 8;
        functionSet[8] = 13;
        return functionSet;
    }

   public short[] getHybridLimitedFunctionSet()
   {
       short[] functionSet = new short[8];
       functionSet[0] = 0;
       functionSet[1] = 1;
       functionSet[2] = 4;
       functionSet[3] = 7;
       functionSet[4] = 8;
     //  functionSet[7] = 15;
       functionSet[7] = 13;
       return functionSet;
       
   }
    
    public short[] getConditionSet()
    {
        short[] conditionSet = new short[7];
        
        conditionSet[0] = 1; // isEvalBudgetAllUsed()
        conditionSet[1] = 2; // isEvalBudgetAllsUsedinSubset1()
        conditionSet[2] = 3;  // isEvalBudgetAllsUsedinSubset2()
        conditionSet[3] = 4;  // isLessThanHalfOfBudgetUsed() || hasAConstantGradientForAWhile
        conditionSet[4] = 5; // isEvalBudgetAllsUsedinSubset1()
        conditionSet[5] = 6;  // isEvalBudgetAllsUsedinSubset2()
        conditionSet[6] = 7;  // isLessThanHalfOfBudgetUsed() || hasAConstantGradientForAWhile
     
        return conditionSet;
    }  

    @Override
    public int getInstanceOptima() 
    {
        return 0;
    }

    
}
