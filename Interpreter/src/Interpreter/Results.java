/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Interpreter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;



/**
 *
 * @author patriciaryser-welch
 */
public class Results 
{
    
    private final String runID;
    private long startTime;
    private long endTime;
    private final String type;
    private List<Result> results;
    private  Statistics.Statistics fitness = new Statistics.Statistics();
    private  Statistics.Statistics generations = new Statistics.Statistics();
    private  Statistics.Statistics time = new Statistics.Statistics();
    private  Statistics.Statistics evalLeft = new Statistics.Statistics();
    private  Statistics.Statistics evalCounter = new Statistics.Statistics();
  
    public Results(String runID, String typeEA) 
    {
        this.runID = runID;
        this.type = typeEA;
        this.results = new ArrayList<>();
    }

    public Results(String runID, long startTime, long endTime, String type, List<Result> results)
    {
        this.runID = runID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.type = type;
        this.results = results;
    }
    
    
    
    public Results ()
    {
        this.runID = "";
        this.type = "";
        this.results = new ArrayList<>();   
     }
    
    public void setMetrics(int Cyclomatic, double difficulty, double length, double volume)
    {
        for(Result r:results)
        {
            r.setCyclomaticMetrics(Cyclomatic);
            System.out.println(r.getCyclomaticMetrics());
            r.setProcessDifficulty(difficulty);
            r.setProcessLength(length);
            r.setProcessVolume(volume);
        }
    }
    public void clearResult()
    {
        results.removeAll(results);
        this.fitness = new Statistics.Statistics();
        this.generations = new Statistics.Statistics();
        this.evalCounter = new Statistics.Statistics();
        this.time = new Statistics.Statistics();
        this.evalLeft = new Statistics.Statistics();
    }
    
    public void addResult(double fitness, int generation, int evalLeft, int evalCounter)
    {
        this.setEndTime(System.currentTimeMillis());
        this.addResultWithID(runID, type, fitness, generation, endTime - startTime, evalCounter);
    } 
    
    public void addResultWithID(String runID, String type, double fitness, int generation, long time, int eval)
    {
        this.setEndTime(System.currentTimeMillis());
        results.add(new Result(runID, type, fitness, generation,0, 0, time));
        this.fitness.addNum(fitness);
        this.generations.addNum(new Double(generation));
        this.time.addNum(new Double (time));   
        this.evalCounter.addNum(new Double(eval));
    }
 
    /**
     * Get the value of type
     *
     * @return the value of type
     */
    public String getTypeEA() 
    {
        return type;
    }

    /**
     * Get the value of endTime
     *
     * @return the value of endTime
     */
    public long getEndTime() 
    {
        return endTime;
    }

    /**
     * Set the value of endTime
     *
     * @param endTime new value of endTime
     */
    public void setEndTime(long endTime) 
    {
        this.endTime = endTime;
    }


    /**
     * Get the value of startTime
     *
     * @return the value of startTime
     */
    public long getStartTime() 
    {
        return startTime;
    }

    /**
     * Set the value of startTime
     *
     * @param startTime new value of startTime
     */
    public void setStartTime(long startTime) 
    {
        this.startTime = startTime;
    }


    /**
     * Get the value of runID
     *
     * @return the value of runID
     */
    public String getRunID() 
    {
        return runID;
    }
 
    /**
     *
     * @return
     */
    public Statistics.Statistics getFitness()
    {
       return this.fitness;
    }
   
    public Statistics.Statistics getGenerations()
    {
       return this.generations;
    }
   
     public Statistics.Statistics getRunningTime()
    {
       return this.time;
    }
    
    public Statistics.Statistics getEvaluationLeft()
    {
        return this.evalLeft;
    }
     
    public void WriteResultsIntoFile() throws IOException
    {
        
          File file = new File("example.txt");
          BufferedWriter output = new BufferedWriter(new FileWriter(type + "-" + runID + ".xml"));
          output.write(this.toString());
          output.close();    
    }
      
    public void WriteResultsIntoFileCSV() throws IOException
    {
        
          File file = new File("example.txt");
          BufferedWriter output = new BufferedWriter(new FileWriter(type + "-" + runID + ".csv"));
          output.write(this.toStringCSV());
          output.close();    
    }
    
    public void WriteConnectedNodesIntoFile(String Nodes) throws IOException
    {
          File file = new File("");
          BufferedWriter output = new BufferedWriter(new FileWriter( type + "-" + runID + "-nodes.xml"));
          output.write(Nodes);
          output.close();    
    }
    
    
    public String toStringCSV() 
    {
        String s = "";
        for (int i = 0; i < results.size();i++)
        {
            s+= results.get(i).getResultCSV();
        }
        return s;     
    }
    @Override public String toString() 
    {
        String s = "";
        s+= "<RESULTS>\n";
        for (int i = 0; i < results.size();i++)
        {
            s+= results.get(i).toString();
        }
        s+= "</RESULTS>\n";
        return s;     
    }
    
    
    //It needs to be fixed with eval counter
   /* public void ImportFromXML(String fileName) throws ParserConfigurationException, IOException, SAXException
    {
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
      DocumentBuilder builder = factory.newDocumentBuilder();
      Document document = builder.parse(fileName);
      NodeList nodeList = document.getDocumentElement().getChildNodes();
       
      Node node;
      Element elem;
      String runID;
      String type;
      Integer generation;
      Double fitness;
      Integer evalLeft;
      Long lengthTime;
      
      this.results.clear();
      
      for (int i = 0; i < nodeList.getLength(); i++)
      {
         node = nodeList.item(i);
         
         if (node.getNodeType() == Node.ELEMENT_NODE)
         {
              elem = (Element) node;
              runID = elem.getElementsByTagName("ID").item(0).getChildNodes().item(0).getNodeValue();  // unique identifier
              type = elem.getElementsByTagName("TYPE").item(0).getChildNodes().item(0).getNodeValue();  // unique identifier
              generation = Integer.parseInt(elem.getElementsByTagName("GENERATION").item(0).getChildNodes().item(0).getNodeValue());
              fitness = new Double(elem.getElementsByTagName("FITNESS").item(0).getChildNodes().item(0).getNodeValue());  // performance of best candidates solutions
              evalLeft = Integer.parseInt(elem.getElementsByTagName("EVAL_LEFT").item(0).getChildNodes().item(0).getNodeValue());
              lengthTime = Long.parseLong(elem.getElementsByTagName("TIME").item(0).getChildNodes().item(0).getNodeValue());
              this.addResult(runID, type, fitness, generation, evalLeft, eval lengthTime);
              
         }
      }
    }*/ 

    /***
     * This procedure writes the results into a file.
     * 
     * @param FileName
     * @param Length
     * @param Algorithm
     * @param Type
     * @throws IOException 
     */
    public void WriteResultsintoFile (String FileName, String Length, String Algorithm, String Type) throws IOException
    {
        String line = Length + ",";
        line += Type + ",";
        line += Algorithm + ",";
        line += fitness.getMinValue() + ",";
        line += fitness.getLowerQuartile() + ",";
        line += fitness.getMedian() + ",";
        line += fitness.getUpperQuartile() + ",";
        line += fitness.getMaxValue() + ",";
        line += fitness.getMean() + ",";
        line += fitness.getStandardDeviation() + ",";
        line += generations.getMinValue() + ",";
        line += generations.getMean() + ",";
        line += generations.getStandardDeviation() + ",";
        line += generations.getMaxValue()+ ",";
        line += time.getMinValue() + ",";
        line += time.getMean() + ",";
        line += time.getStandardDeviation() + ",";
        line += time.getMaxValue() + "\n";
        System.out.println(line.length());
        System.out.println(line);
        try (FileWriter out = new FileWriter (FileName,true)) 
        {
            out.append(line);
        }
            
    }
    
    /***
     * 
     * @param FileName
     * @param Label
     * @param Algorithm
     * @param Type
     * @throws IOException 
     */
    public void WriteResultsIntoFileWithDetails(String FileName, String Label, String Algorithm, String Type) throws IOException
    {
        String fitness = Label + "," + Algorithm + ", fitness,";
        String generations = Label + "," + Algorithm + ", generations,";
        String time = Label + "," + Algorithm + ", time,";
        String eval = Label + "," + Algorithm + ", evaluations,";
        
        for(Result r:results)
        {
            fitness += "," + r.getFitness();
            time += "," + r.getLengthTime();
            eval += "," + r.getEvalCounter();
            generations += "," +  r.getGeneration() + " \n";
        }
        
        fitness += "\n"; 
        time += "\n";
        eval += "\n";
        generations += "\n";
        
        try (FileWriter out = new FileWriter (FileName,true)) 
        {
            out.append(fitness);
            out.append(time);
            out.append(generations);
            out.append(eval);
        }  
    }
    
    public void WriteAllResultsCSV(String FileName) throws IOException
    {
        //String s = "Fitness, Time, Eval, Generations\n";
        String s = "";
        
        for(Result r:results)
        {
            s += r.getFitness() +  "," + r.getLengthTime() + "," + r.getEvalCounter() + "," +  r.getGeneration() + " \n";
        }
      
        try (FileWriter out = new FileWriter (FileName,true)) 
        {
            out.append(s);
        }  
    }
    
    public void WriteResultsWithMetrics(String FileName, String Algorithm, String instance) throws IOException
    {
        //String s = "Fitness, Time, Eval, Generations\n";
        String s = "";
        
        for(Result r:results)
        {
            s += Algorithm + "," + instance + ",";
            s += r.getFitness() + "," + r.getEvalCounter() + "," + Integer.toString(r.getCyclomaticMetrics()) + ",";
            s += r.getProcessDifficulty() + "," + r.getProcessLength() + "," + r.getProcessVolume() + "\n";
        }
      
        try (FileWriter out = new FileWriter (FileName,true)) 
        {
            out.append(s);
        }  
    }
    
   
    
    public void writeParamIntoFile(String FileName, String BaseParam, String BarrierParam, String HyperParam) throws IOException
    {
        String Line = "Base \n";
        Line += BaseParam + "\n";
        Line += "Barrier \n";
        Line += BarrierParam + "\n";
        Line += "Hyper \n";
        Line += HyperParam + "\n";
        try (FileWriter out = new FileWriter (FileName,true)) 
        {
            out.append(Line);
        }
    }

    public void WriteResultsIntoFileWithDetails(String FileName, String ema)
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override public Object clone() throws CloneNotSupportedException
    {
        List<Result> resultsClone = new ArrayList();
        for (Result result: this.results)
        {
            resultsClone.add((Result) result.clone());
        }
        return new Results(this.runID, this.startTime, this.endTime, this.type, resultsClone); //To change body of generated methods, choose Tools | Templates.
    }
    
   
}
