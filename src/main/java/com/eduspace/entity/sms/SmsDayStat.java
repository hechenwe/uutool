package com.eduspace.entity.sms;


/**
 *  按天统计短信日志 
 * 
 *  @author HeChen 
 *
 */
 
public class SmsDayStat implements java.io.Serializable{
	  /** 序列化  */
    private static final long serialVersionUID = 1L; 
    
   
    /**编号*/
    
    private Integer dayId; 
    
    /**产品编号*/
    private String productId; 
    
    
    /**日期*/
    private java.util.Date date; 
    
    
    /**发送失败数量*/
    private Integer falNumber; 
    
    
    /**成功数量*/
    private Integer sucNumber; 
    
    
  
 
 
 
 
 
  
                                                               
   
    //------------------------get,set 方法----------------------------
 
 
    /**编号*/ 
    public Integer getDayId(){  
      return dayId;  
    }  
     /**编号*/
    public void setDayId(Integer dayId){  
      this.dayId = dayId;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**产品编号*/ 
    public String getProductId(){  
      return productId;  
    }  
     /**产品编号*/
    public void setProductId(String productId){  
      this.productId = productId;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**日期*/ 
    public java.util.Date getDate(){  
      return date;  
    }  
     /**日期*/
    public void setDate(java.util.Date date){  
      this.date = date;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**发送失败数量*/ 
    public Integer getFalNumber(){  
      return falNumber;  
    }  
     /**发送失败数量*/
    public void setFalNumber(Integer falNumber){  
      this.falNumber = falNumber;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**成功数量*/ 
    public Integer getSucNumber(){  
      return sucNumber;  
    }  
     /**成功数量*/
    public void setSucNumber(Integer sucNumber){  
      this.sucNumber = sucNumber;  
    } 
    
    
   //----------------------------------------------------------------
   
   
  
 
 
   
 
 
 


     //----------------------------------------------------------------
     @Override
	 public String toString() {
		return  "DayStat : 按天统计短信日志["+
		        " ;编号:dayId = " + dayId +  
		        " ;产品编号:productId = " + productId +  
		        " ;日期:date = " + date +  
		        " ;发送失败数量:falNumber = " + falNumber +  
		        " ;成功数量:sucNumber = " + sucNumber + "]" ;
	}

}
