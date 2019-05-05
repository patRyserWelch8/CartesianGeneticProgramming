/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Statistics;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author patriciaryser-welch
 */
public class Statistics 
{
    private List<Double> nums;

    public Statistics(List<Double> nums) 
    {
        if (nums != null)
        {
            this.nums = nums;
        }
        else 
        {
             this.nums = new ArrayList<>();
        }
        Collections.sort(this.nums);
    }
    
    public Statistics()
    {
        this.nums = new ArrayList<>();
        Collections.sort(this.nums);
    }
    
    public void addNum(double num)
    {
        this.nums.add(num);
        Collections.sort(this.nums);
    }
    
    public void clearAllNums ()
    {
        this.nums.clear();
    }
    
    public Double getMinValue()
    {
        if (this.nums.size() > 0)
        {
            Double min = Double.MAX_VALUE;
            for (int i = 0; i < this.nums.size(); i++)
            {
                if (this.nums.get(i) < min)
                {
                    min = this.nums.get(i);
                }
            }
           
            return min;
        }
        else
        {
            return (double) Integer.MIN_VALUE;
        }
            
    }
    
    public Double getMaxValue()
    {
      
       if (this.nums.size() > 0)
        {
            Double max = Double.MIN_VALUE;
            for (int i = 0; i < this.nums.size(); i++)
            {
                if (this.nums.get(i) > max)
                {
                    max = this.nums.get(i);
                }
            }
            return max;
        }
        else
        {
            return (double) Integer.MAX_VALUE;
        }
    }
    
    public Double getMean()
    {
        if (this.nums.size() > 0)
        {
            Double total = (double) 0;
       
            for(Double num:nums)
            {
                total += num;
            }
            return total/this.nums.size();
        }
        else
        {
            return (double) 0;
        }
    }
    
    public Double getMedian(List<Double> list)
    {
        
        Collections.sort(list);
        if ((list.size() % 2) == 0)
        {
            int index1 = (list.size()/2)-1;
            int index2 = ((list.size()+2)/2)-1;
            return (list.get(index1) + list.get(index2))/2;
        }
        else 
        {
          int index = (list.size()+1)/2;
          return list.get(index-1);
        }
    }
    public Double getMedian()
    {
        if (this.nums.size() > 0) 
        {
            return getMedian(this.nums);
        }
        else 
        {
            return (double) Integer.MAX_VALUE;
        }
    /*    Collections.sort(this.nums);
        
  
        if ((this.nums.size() % 2) == 0)
        {
            int index1 = (this.nums.size()/2)-1;
            int index2 = ((this.nums.size()+2)/2)-1;
            return (this.nums.get(index1) + this.nums.get(index2))/2;
        }
        else 
        {
          int index = (this.nums.size()+1)/2;
          return this.nums.get(index);
        }*/
    }
    
    public Double getLowerQuartile()
    {
        if (this.nums.size() >0)
        {
            if ((this.nums.size()%2) == 0)
            {
                return getMedian(this.nums.subList(0, (this.nums.size()/2)));
            }
            else
            {
                return getMedian(this.nums.subList(0,((this.nums.size()+1)/2)));
            }
        }
        else 
        {
            return (double) Integer.MAX_VALUE;
        }
    }
    
    public Double getUpperQuartile()
    {
        if (this.nums.size() >0)
        {
            if ((this.nums.size()%2) == 0)
            {
                return getMedian(this.nums.subList(this.nums.size()/2, this.nums.size()));
            }
            else
            {
                return getMedian(this.nums.subList((this.nums.size())/2, this.nums.size()));
            }
        }
        else
        {
            return (double) Integer.MAX_VALUE;
        }
       
    }
    
    public Double getVariance(Double mean)
    {
        if (this.nums.size() > 0)
        {
            Double total = (double) 0;
            
                    
            for(Double num:nums)
            {
                total += num - mean;
            }
            return total/this.nums.size();
        }
        else
        {
            return (double) 0;
        }
    }
    
    public Double getVariance()
    {
        if (this.nums.size() > 0)
        {
            Double total = (double) 0;
            Double mean = getMean();
                    
            for(Double num:nums)
            {
                total += Math.pow(num - mean,2);
            }
            return total/this.nums.size();
        }
        else
        {
            return (double) 0;
        }
    }
     
    public Double getStandardDeviation(Double Variance)
    {
        if (this.nums.size() >0)
        {
            if (Variance < 0)
            {   
                return Math.sqrt(-1 * Variance);
            }
            else
            {
                return Math.sqrt(Variance);
            }
        }
        else
        {
            return (double) Integer.MAX_VALUE;
        }
    }
    
    public Double getStandardDeviation()
    {
        
        Double Variance = getVariance();
        if (Variance < 0)
        {   
            return Math.sqrt(-1 * Variance);
        }
        else
        {
            return Math.sqrt(Variance);
        }
       
    }

    @Override protected Object clone() throws CloneNotSupportedException
    {
      List<Double> numsClone = new ArrayList();
      for (Double num: this.nums)
      {
          numsClone.add(num);
      }
      return new Statistics(numsClone);
    }

    @Override
    public String toString()
    {
        return nums.toString();
    }
    
    
    
    
}
