package oppo;

import com.alibaba.fastjson.JSONObject;
import com.hz.tgb.common.StringUtil;
import com.hz.tgb.http.TestGetPost;

/**
 * Created by hezhao on 2017-08-30 10:21.
 */
public class Test2 {

    public static void main(String[] args) {
        String userName = getUserName("1041");
        System.out.println(userName);

        String userType = getUserType("1041");
        System.out.println(userType);
    }

    /**
     * 获取纳税人类型
     * @param userId
     * @return
     */
    public static String getUserType(String userId){
        String userType = "";
        try {
            String url = "http://172.17.100.17:92/innerapi/user/financial/info";
            String param = "devId="+userId;

            String data = TestGetPost.sendGet(url, param);
            System.out.println(data);

            if(!StringUtil.isBlank(data)){
                data = data.replaceAll("/n","");
                System.out.println(data);

                JSONObject json = JSONObject.parseObject(data);

                if(json != null){

                    Integer status = json.getInteger("status");

                    if(status == 1){
                        JSONObject result = json.getJSONObject("result");
                        if (result != null){
                            userType = result.getString("taxpayer_type");
                        }
                    }
                }
            }

            //0 一般纳税人 1小规模纳税人 2其它
            if(StringUtil.isBlank(userType)){
                userType = "2";
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return userType;
    }

    /**
     * 获取企业名称
     * @param userId
     * @return
     */
    public static String getUserName(String userId){
        String userName = "";
        try {
            String url = "http://172.17.100.17:92/innerapi/user/check/dev";
            String param = "devId="+userId;

            String data = TestGetPost.sendGet(url, param);
            System.out.println(data);

            if(!StringUtil.isBlank(data)){
                data = data.replaceAll("/n","");
                System.out.println(data);

                JSONObject json = JSONObject.parseObject(data);

                if(json != null){

                    Integer status = json.getInteger("status");

                    if(status == 1){
                        userName = json.getString("result");
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return userName;
    }

}
