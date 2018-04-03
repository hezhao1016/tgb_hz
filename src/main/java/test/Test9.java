package test;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hz.tgb.http.util.HttpClientUtil;
import com.hz.tgb.http.util.HttpConfig;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by hezhao on 2017/11/22 14:53.
 */
public class Test9 {
    private static Logger LOG = LoggerFactory.getLogger(Test9.class);

    public static void main(String[] args) {
        try {
//            ZipUtil.readZipFile("E:/abc.zip");
        } catch (Exception e) {
            e.printStackTrace();
        }


//        {"orderId":"GC201612211103228151622813363681918976",
//                "refundOrderId":"GC201612211103228151622813363681918976_1","type":1,
//                "refundAmount":2300,"refundDate":"20171001"}

        String refundGameSyncUrl = "https://183.131.22.101:8005/payadmin/pay/refund";
        String orderId = "GC201612211103228151622813363681918976";
        String refundOrderId = "GC201612211103228151622813363681918976_1";
        BigDecimal refundAmount = new BigDecimal(230);
        String refundDate = "20171001";
        int type = 1;
        LOG.info("开始同步游戏退款::::::[orderId]:{"+orderId+"},[refundOrderId]:{"+refundOrderId+"},[refundAmount]:{"+refundAmount+"},[refundDate]:{"+refundDate+"},[refundGameSyncUrl]:{"+refundGameSyncUrl+"},[type]:{1}");

        //同步结果
        Map<String,Object> resultMap = new HashMap<>();
        resultMap.put("result",false);
        resultMap.put("msg","调用游戏退款同步接口失败");

        try {
            String param = "orderId="+orderId
                    +"&refundOrderId="+refundOrderId
                    +"&type="+type
                    +"&refundAmount="+refundAmount.doubleValue()
                    +"&refundDate="+refundDate;


            Map<String,Object> createMap = new HashMap<>();
            createMap.put("orderId",orderId);
            createMap.put("refundOrderId",refundOrderId);
            createMap.put("type",1);
            createMap.put("refundAmount",refundAmount.movePointRight(2).longValue());
            createMap.put("refundDate",refundDate);
            HttpConfig httpConfig = new HttpConfig();
            httpConfig.setCharset("utf-8");
            httpConfig.setContentType("application/json");
            String data = HttpClientUtil.doPost(refundGameSyncUrl, JSON.toJSONString(createMap),httpConfig);

//            String data = TestGetPost.sendPost(refundGameSyncUrl, param);
            LOG.debug("syncRefundGame 接口返回参数:["+data+"]");

            if(StringUtils.isNotBlank(data)){
                data = data.replaceAll("/n","");
                JSONObject json = JSONObject.parseObject(data);

                if(json != null){

                    String code = json.getString("code");
                    String msg = json.getString("msg");

                    if("200".equals(code)){
                        resultMap.put("result",true);
                    }

                    resultMap.put("msg",msg);
                }
            }
        } catch (Exception e) {
            LOG.error(e.toString());
        }

        LOG.info("同步游戏退款结果："+JSONObject.toJSON(resultMap));
    }
}
