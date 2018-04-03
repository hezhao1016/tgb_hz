package com.hz.tgb.file;

public enum FileType {  
    
    /** 
     * JEPG. 
     */  
    JPEG("FFD8FF"),  
      
    /** 
     * PNG. 
     */  
    PNG("89504E47"),  
      
    /** 
     * GIF. 
     */  
    GIF("47494638"),  
      
    /** 
     * TIFF. 
     */  
    TIFF("49492A00"),  
      
    /** 
     * Windows Bitmap. 
     */  
    BMP("424D");  
    
      
    private String value = "";  
      
    /** 
     * Constructor. 
     *  
     * @param type  
     */  
    private FileType(String value) {  
        this.value = value;  
    }  
  
    public String getValue() {  
        return value;  
    }  
  
    public void setValue(String value) {  
        this.value = value;  
    }  
}  
