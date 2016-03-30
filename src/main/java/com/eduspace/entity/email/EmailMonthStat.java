package com.eduspace.entity.email;
 


/**
 *  按天统计短信日志 
 * 
 *  @author HeChen 
 *
 */
 
public class EmailMonthStat implements java.io.Serializable{

    /** 序列化  */
    private static final long serialVersionUID = 1L; 
    
   
    /**编号*/
    private Integer id; 
    
    
    /**产品编号*/
    private String productId; 
    
    
    /**月份*/
    private String month; 
    
    
    /**发送失败数量*/
    private Integer falNumber; 
    
    
    /**成功数量*/
    private Integer sucNumber; 
    
    
  
 
 
 
 
 
  
                                                               
   
    //------------------------get,set 方法----------------------------
 
    public Integer getId() {
  		return id;
  	}
  	public void setId(Integer id) {
  		this.id = id;
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
   
   
    /**月份*/ 
    public String getMonth(){  
      return month;  
    }  
     /**月份*/
    public void setMonth(String month){  
      this.month = month;  
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
		return  "MonthStat : 按天统计短信日志["+
		        " ;编号:id = " + id +  
		        " ;产品编号:productId = " + productId +  
		        " ;月份:month = " + month +  
		        " ;发送失败数量:falNumber = " + falNumber +  
		        " ;成功数量:sucNumber = " + sucNumber + "]" ;
	}
 

}
