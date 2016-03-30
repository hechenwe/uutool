package com.eduspace.entity.email;

 

/**
 *  统计短信日志 
 * 
 *  @author HeChen 
 *
 */
 
public class EmailStat implements java.io.Serializable{

    /** 序列化  */
    private static final long serialVersionUID = 1L; 
    
   
    /**统计编号*/
    private Integer statId; 
    
    
    /**产品编号*/
    private String productId; 
    
    
    /**今天失败数量*/
    private Integer todayFal; 
    
    
    /**今天成功数量*/
    private Integer todaySuc; 
    
    
    /**最近3天失败数*/
    private Integer threeFal; 
    
    
    /**最近3天成功数*/
    private Integer threeSuc; 
    
    
    /**最近7天成功数*/
    private Integer sevenSuc; 
    
    
    /**最近7天失败数*/
    private Integer sevenFal; 
    
    
    /**最近30天失败数*/
    private Integer monthFal; 
    
    
    /***/
    private Integer monthSuc; 
    
    
    /**最近半年成功数*/
    private Integer halfYearSuc; 
    
    
    /**最近半年失败数*/
    private Integer halfYearFal; 
    
    
    /**最近一年失败数*/
    private Integer yearFal; 
    
    
    /**最近一年成功数*/
    private Integer yearSuc; 
    
    
  
 
 
 
 
 
  
                                                               
   
    //------------------------get,set 方法----------------------------
 
 
    /**统计编号*/ 
    public Integer getStatId(){  
      return statId;  
    }  
     /**统计编号*/
    public void setStatId(Integer statId){  
      this.statId = statId;  
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
   
   
    /**今天失败数量*/ 
    public Integer getTodayFal(){  
      return todayFal;  
    }  
     /**今天失败数量*/
    public void setTodayFal(Integer todayFal){  
      this.todayFal = todayFal;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**今天成功数量*/ 
    public Integer getTodaySuc(){  
      return todaySuc;  
    }  
     /**今天成功数量*/
    public void setTodaySuc(Integer todaySuc){  
      this.todaySuc = todaySuc;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**最近3天失败数*/ 
    public Integer getThreeFal(){  
      return threeFal;  
    }  
     /**最近3天失败数*/
    public void setThreeFal(Integer threeFal){  
      this.threeFal = threeFal;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**最近3天成功数*/ 
    public Integer getThreeSuc(){  
      return threeSuc;  
    }  
     /**最近3天成功数*/
    public void setThreeSuc(Integer threeSuc){  
      this.threeSuc = threeSuc;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**最近7天成功数*/ 
    public Integer getSevenSuc(){  
      return sevenSuc;  
    }  
     /**最近7天成功数*/
    public void setSevenSuc(Integer sevenSuc){  
      this.sevenSuc = sevenSuc;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**最近7天失败数*/ 
    public Integer getSevenFal(){  
      return sevenFal;  
    }  
     /**最近7天失败数*/
    public void setSevenFal(Integer sevenFal){  
      this.sevenFal = sevenFal;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**最近30天失败数*/ 
    public Integer getMonthFal(){  
      return monthFal;  
    }  
     /**最近30天失败数*/
    public void setMonthFal(Integer monthFal){  
      this.monthFal = monthFal;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /***/ 
    public Integer getMonthSuc(){  
      return monthSuc;  
    }  
     /***/
    public void setMonthSuc(Integer monthSuc){  
      this.monthSuc = monthSuc;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**最近半年成功数*/ 
    public Integer getHalfYearSuc(){  
      return halfYearSuc;  
    }  
     /**最近半年成功数*/
    public void setHalfYearSuc(Integer halfYearSuc){  
      this.halfYearSuc = halfYearSuc;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**最近半年失败数*/ 
    public Integer getHalfYearFal(){  
      return halfYearFal;  
    }  
     /**最近半年失败数*/
    public void setHalfYearFal(Integer halfYearFal){  
      this.halfYearFal = halfYearFal;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**最近一年失败数*/ 
    public Integer getYearFal(){  
      return yearFal;  
    }  
     /**最近一年失败数*/
    public void setYearFal(Integer yearFal){  
      this.yearFal = yearFal;  
    } 
    
    
   //----------------------------------------------------------------
   
   
    /**最近一年成功数*/ 
    public Integer getYearSuc(){  
      return yearSuc;  
    }  
     /**最近一年成功数*/
    public void setYearSuc(Integer yearSuc){  
      this.yearSuc = yearSuc;  
    } 
    
    
   //----------------------------------------------------------------
   
   
  
 
 
   
 
 
 


     //----------------------------------------------------------------
     @Override
	 public String toString() {
		return  "Stat : 统计短信日志["+
		        " ;统计编号:statId = " + statId +  
		        " ;产品编号:productId = " + productId +  
		        " ;今天失败数量:todayFal = " + todayFal +  
		        " ;今天成功数量:todaySuc = " + todaySuc +  
		        " ;最近3天失败数:threeFal = " + threeFal +  
		        " ;最近3天成功数:threeSuc = " + threeSuc +  
		        " ;最近7天成功数:sevenSuc = " + sevenSuc +  
		        " ;最近7天失败数:sevenFal = " + sevenFal +  
		        " ;最近30天失败数:monthFal = " + monthFal +  
		        " ;:monthSuc = " + monthSuc +  
		        " ;最近半年成功数:halfYearSuc = " + halfYearSuc +  
		        " ;最近半年失败数:halfYearFal = " + halfYearFal +  
		        " ;最近一年失败数:yearFal = " + yearFal +  
		        " ;最近一年成功数:yearSuc = " + yearSuc + "]" ;
	}
 

}
