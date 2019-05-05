
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Base_PS;

import BaseLibraries.GeneralParam;
import PersonnelScheduling.PersonnelScheduling;

/**
 *
 * @author patriciaryser-welch
 */
public class PS_Param extends GeneralParam 
{
    public PS_Param() 
    {
    
    }
    
    public PS_Param(long seedValue) 
    {
        this.seedValue = seedValue;
    }
    
    public int getInstanceOptima()
    {
        return 0;
    }

    public int getInstanceOptimaCost()
    { //source: http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/STSP.html (accessed on 6/11/2014))
        
        switch(this.getInstances())
        {
            case 0: return 252; // BCV-1.8.1
            case 1: return 853; // BCV-1.8.2
            case 2: return 232; // BCV-1.8.3
            case 3: return 291; // BCV-1.8.4
            case 4: return 1572; // BCV-2.46.1
            case 5: return 3280; // BCV-3.46.1
            case 6: return 894;  // BCV-3.46.2
            case 7: return 10;   // BCV-4.13.1
            case 8: return 10;   // BCV-4.13.2
            case 9: return 48;   // BCV-5.4.1
            case 10: return 768; // BCV-6.13.1
            case 11: return 392; // BCV-6.12.2
            case 12: return 381; // BCV-7.10.1
            case 13: return 148; // BCV-8.13.1
            case 14: return 148; // BCV-8.13.2
            case 15: return 1294; //BCV-A.12.1
            case 16: return 1875; //BCV-A.12.2
            case 17: return 270;  //ORTEC01
            case 18: return 270;  //ORTEC02
            case 19: return 5; //GPOST
            case 20: return 3; //GPost-B
            case 21: return 13; //QMC-1
            case 22: return 29; //QMC-2
            case 23: return 0; //Ikegami-2Shift-DATA1
            case 24: return 2; //Ikegami-3Shift-DATA1
            case 25: return 3; //Ikegami-3Shift-DATA1.1
            case 26: return 3; //Ikegami-3Shift-DATA1.2
            case 27: return 0; //Millar-2Shift-DATA1
            case 28: return 0; //Millar-2Shift-DATA1.1
            case 29: return 20; //Valouxis-1
            case 30: return 5; //WHPP
            case 31: return 301; // LLR
            case 32: return 175; // Musa
            case 33: return 0; //Ozharaham
            case 34: return 0; //Azaiez
            case 35: return 0; //SINTEF
            case 36: return 1095; //Child-A2
            case 37: return 795; //ERMGH-A
            case 38: return 1355; //ERMGH-B
            case 39: return 2135; //ERRVH-A
            case 40: return 3105; //ERRVH-B
            case 41: return 8814; //MER-A
          //  case 42: return -1000; //QMC-A ????
            case 43: return 607; //Instance1
            case 44: return 828; //Instance2
            case 45: return 1001; //Instance3
            case 46: return 1716; //Instance 4
            case 47: return 1143; //Instance 5
            case 48: return 1950; //Instance 6
            case 49: return 1056; //Instance 7
            case 50: return 1297; //Instance 8
            case 51: return 406; //Instance 9
            case 52: return 4631; //Instance 10
            case 53: return 3443; //Instance 11
            case 54: return 4040; //Instance 12
            case 55: return 1346; //Instance 13
            case 56: return 1277; //Instance 14
            case 57: return 3806; //Instance 15
            case 58: return 3224; //Instance 16
            case 59: return 5726; //Instance 17
            case 60: return 4351; //Instance 18
            case 61: return 2945; //Instance 19
            case 62: return 4743; //Instance 20
            case 63: return 20868; //Instance 21
            case 64: return 24064; //Instance 22
            case 65: return 2765; //Instance 23
            case 66: return 71765;//Instance 24 no best lower bound
            default: return Integer.MIN_VALUE;       
        }
    }
    
   public int getInstanceNumFeatures()
    { //source: http://www.iwr.uni-heidelberg.de/groups/comopt/software/TSPLIB95/STSP.html (accessed on 6/11/2014))
        
        switch(this.getInstances())
        {
            case 0: return 8+5+28; // BCV-1.8.1
            case 1: return 8+5+28; // BCV-1.8.2
            case 2: return 8+5+28; // BCV-1.8.3
            case 3: return 8+5+28; // BCV-1.8.4
            case 4: return 46+4+28; // BCV-2.46.1
            case 5: return 46+3+28; // BCV-3.46.1
            case 6: return 46+3+28;  // BCV-3.46.2
            case 7: return 13+4+29;   // BCV-4.13.1
            case 8: return 13+4+28;   // BCV-4.13.2
            case 9: return 13+4+28;   // BCV-5.4.1
            case 10: return 13+5+30; // BCV-6.13.1
            case 11: return 13+5+30; // BCV-6.12.2
            case 12: return 10+6+28; // BCV-7.10.1
            case 13: return 13+5+28; // BCV-8.13.1
            case 14: return 13+5+28; // BCV-8.13.2
            case 15: return 12+5+31; //BCV-A.12.1
            case 16: return 12+5+31; //BCV-A.12.2
            case 17: return 16+4+31;  //ORTEC01
            case 18: return 16+4+31;  //ORTEC02
            case 19: return 8+2+28; //GPOST
            case 20: return 8+2+28; //GPost-B
            case 21: return 19+3+28; //QMC-1
            case 22: return 19+3+28; //QMC-2
            case 23: return 28+2+30; //Ikegami-2Shift-DATA1
            case 24: return 25+3+30; //Ikegami-3Shift-DATA1
            case 25: return 25+3+30; //Ikegami-3Shift-DATA1.1
            case 26: return 25+3+30; //Ikegami-3Shift-DATA1.2
            case 27: return 8+2+14; //Millar-2Shift-DATA1
            case 28: return 8+2+14; //Millar-2Shift-DATA1.1
            case 29: return 16+3+28; //Valouxis-1
            case 30: return 30+3+14; //WHPP
            case 31: return 27+3+7; // LLR
            case 32: return 11+1+14; // Musa
            case 33: return 14+2+7; //Ozharaham
            case 34: return 13+2+28; //Azaiez
            case 35: return 24+5+21; //SINTEF
            case 36: return 41+5+42; //Child-A2
            case 37: return 41+4+48; //ERMGH-A
            case 38: return 41+4+48; //ERMGH-B
            case 39: return 51+8+48; //ERRVH-A
            case 40: return 51+8+48; //ERRVH-B
            case 41: return 54+12+48; //MER-A
           // case 42: return -1000; //QMC-A ????
            case 43: return 8+1+14; //Instance1
            case 44: return 14+2+14; //Instance2
            case 45: return 20+3+14; //Instance3
            case 46: return 10+2+28; //Instance 4
            case 47: return 16+2+28; //Instance 5
            case 48: return 18+3+28; //Instance 6
            case 49: return 20+3+28; //Instance 7
            case 50: return 30+4+28; //Instance 8
            case 51: return 36+4+28; //Instance 9
            case 52: return 40+5+28; //Instance 10
            case 53: return 50+6+28; //Instance 11
            case 54: return 60+10+28; //Instance 12
            case 55: return 120+18+28; //Instance 13
            case 56: return 32+6+42; //Instance 14
            case 57: return 45+6+42; //Instance 15
            case 58: return 20+6+56; //Instance 16
            case 59: return 32+4+56; //Instance 17
            case 60: return 22+3+84; //Instance 18
            case 61: return 40+5+84; //Instance 19
            case 62: return 50+6+182; //Instance 20
            case 63: return 100+8+182; //Instance 21
            case 64: return 50+10+364; //Instance 22
            case 65: return 100+16+364; //Instance 23
            case 66: return 150+32+364;//Instance 24 no best lower bound
            default: return Integer.MIN_VALUE;       
        }
    }
    
    

    public short[] getEMAFunctionSet() 
    {
        short[] functionSet = new short[16];
        
        functionSet[0] = 0; 
        functionSet[1] = 1; 
        functionSet[2] = 2; 
        functionSet[3] = 3; 
        functionSet[4] = 4; 
        functionSet[5] = 5; 
        functionSet[6] = 6; 
        functionSet[7] = 7; 
        functionSet[8] = 8; 
        functionSet[9] = 9;
        functionSet[10] = 10; 
        functionSet[11] = 11; 
        functionSet[12] = 12; // ReplaceRandom
        functionSet[13] = 13; // ReplaceLeastFit
        functionSet[14] = 14; // RestartPop
        functionSet[15] = 15; // RestartPop
        
        return functionSet;
    }
   
    public short[] getHybridLimitedFunctionSet()
    {
         short[] functionSet = new short[14];
         functionSet[0] = 0; 
         functionSet[1] = 1;
         functionSet[2] = 2;
         functionSet[3] = 3;
         functionSet[4] = 4;
         functionSet[5] = 5;
         functionSet[6] = 6;
         functionSet[7] = 7;
         functionSet[8] = 8;
         functionSet[9] = 9; 
         functionSet[10] = 10;
         functionSet[11] = 11;
         functionSet[12] = 13;
         functionSet[13] = 15;
         
         
       
         return functionSet;
    }

    @Override public short[] getEEAFunctionSet() 
    {
        short[] functionSet = new short[13];
        
        functionSet[0] = 8;
        functionSet[1] = 9;
        functionSet[2] = 10;
        functionSet[3] = 11;
        functionSet[4] = 13;
        functionSet[5] = 14;
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
        functionSet[2] = 3;
        functionSet[3] = 4;
        functionSet[4] = 5;
        functionSet[5] = 6;
        functionSet[6] = 7;
        functionSet[7] = 11;
        functionSet[8] = 13;
        return functionSet;
    }

  /* public short[] getHybridLimitedFunctionSet()
   {
       short[] functionSet = new short[0];
       return functionSet;
       
   }*/
    
    public short[] getConditionSet()
    {
        short[] conditionSet = new short[4];
        
        conditionSet[0] = 0; // isEvalBudgetAllUsed() || hasFoundOptima()
        conditionSet[1] = 1; // isEvalBudgetAllUsed() || hasAConstantGradientForAWhile()
        conditionSet[2] = 5;  // isEvalBudgetAllUsed() || hasAConstantGradientOrWorse()
        conditionSet[3] = 6;  // isEvalBudgetAllUsed() || hasAConstantGradientOrWorse()
        return conditionSet;
    }  
}
