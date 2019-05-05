/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Hyper;

import Interpreter.AcyclicInterpreter;

/**
 * Acyclic parameters. 
 * @author patriciaryser-welch
 */
public class AcyclicParameters extends AbstractParameters
{
    protected Interpreter.AcyclicInterpreter Interpreter;

    @Override public String toString()
    {
       
        return super.toString();
    }

    public AcyclicInterpreter getInterpreter()
    {
        return Interpreter;
    }

    public void setInterpreter(AcyclicInterpreter Interpreter)
    {
        this.Interpreter = Interpreter;
    }
    
    
    
}
