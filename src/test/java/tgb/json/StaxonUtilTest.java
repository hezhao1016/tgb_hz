package tgb.json;

import com.hz.tgb.json.StaxonUtil;
import org.junit.Test;

public class StaxonUtilTest {
	@Test
	public void test_json2xml() {
		String json = "{\"Response\" : {\"CustID\" : 1300000428,\"CompID\" : 1100000324,\"Items\" : {\"Item\" : [ {\"Sku_ProductNo\" : \"sku_0004\",\"Wms_Code\" : 1700386977,\"Sku_Response\" : \"T\",\"Sku_Reason\" : null}, {\"Sku_ProductNo\" : \"0005\",\"Wms_Code\" : 1700386978,\"Sku_Response\" : \"T\",\"Sku_Reason\" : null}]}}}";
		String xml = StaxonUtil.json2xml(json);
		System.out.println(xml);
	}

	@Test
	public void test_xml2json() {
		String xml = "<Response><CustID>1300000428</CustID><CompID>1100000324</CompID><Items><Item><Sku_ProductNo>sku_0004</Sku_ProductNo><Wms_Code>1700386977</Wms_Code><Sku_Response>T</Sku_Response><Sku_Reason></Sku_Reason></Item><Item><Sku_ProductNo>0005</Sku_ProductNo><Wms_Code>1700386978</Wms_Code><Sku_Response>T</Sku_Response><Sku_Reason></Sku_Reason></Item></Items></Response>";
		String json = StaxonUtil.xml2json(xml);
		System.out.println(json);
	}
}
