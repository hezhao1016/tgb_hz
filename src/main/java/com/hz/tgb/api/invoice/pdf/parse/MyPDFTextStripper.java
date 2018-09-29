package com.hz.tgb.api.invoice.pdf.parse;

import com.hz.tgb.api.invoice.pdf.entity.ReceiptPosition;
import org.apache.commons.lang3.StringUtils;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class MyPDFTextStripper extends PDFTextStripper {
	
	private List<ReceiptPosition> posList = new ArrayList<ReceiptPosition>();
	
	public List<ReceiptPosition> getPosList() {
		return posList;
	}

	public MyPDFTextStripper() throws IOException {
		super();
	}
	
	@Override
	public void processTextPosition(TextPosition pos) {
		float posEndX = pos.getEndX();
        float posEndY = pos.getEndY();
        float posX = pos.getX();
        String text = pos.getUnicode();
        // System.out.println(posList.size()+"--"+"[x:"+posX+" posEndX:"+posEndX+" posEndY:"+posEndY +" text:"+text+"]");
        
    	boolean hasData = false;
    	for (ReceiptPosition item : posList) {
    		if (Math.abs(item.getPosLastY() - posEndY) < 1 &&
    				(Math.abs(item.getPosLastX() - posX) < 1 || Math.abs(item.getPosLastX() - posEndX) < 1)) {
    			String textItem = item.getText();
    			
    			if (textItem.endsWith(":")) {
    				continue;
    			}
    			if (textItem.indexOf(PDFReaderUtil.slv_key) > -1) {
    				continue;
    			}
    			if (textItem.indexOf(PDFReaderUtil.kpje_key) > -1 || textItem.indexOf(PDFReaderUtil.kpje_key2) > -1) {
    				continue;
    			}
    			// x轴四个空格分另一列
    			if(textItem.indexOf("    ") > -1 && textItem.indexOf("合") == -1){
    				continue;
    			}
    			
    			if (Math.abs(item.getPosLastX() - posX) < 1) {
					textItem = textItem + text;
					item.setPosLastX(posEndX);
    			} else if (Math.abs(item.getPosLastX() - posEndX) < 1) {
					textItem = text + textItem;
					item.setPosLastX(posX);
    			}
    			
    			item.setText(textItem);
    			
    			hasData = true;
    		}
    	}
    	
    	if (!hasData && StringUtils.isNotBlank(text)) {
    		ReceiptPosition posItem = new ReceiptPosition();
    		posItem.setText(text);
    		posItem.setPosX(posX);
    		posItem.setPosY(posEndY);
    		posItem.setPosLastX(posEndX);
    		posItem.setPosLastY(posEndY);
    		
    		posList.add(posItem);
    	}
	}

}
