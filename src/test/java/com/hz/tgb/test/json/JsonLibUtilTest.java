package com.hz.tgb.test.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hz.tgb.json.JsonLibUtil;
import net.sf.ezmorph.test.ArrayAssertions;

import org.junit.Assert;
import org.junit.Test;

public class JsonLibUtilTest {  
  
    @Test  
    public void pojo2json_test(){  
        User user = new User(1, "张三");  
        String json = JsonLibUtil.pojo2json(user);
        Assert.assertEquals("{\"id\":1,\"name\":\"张三\"}", json);  
    }  
      
    @Test  
    public void object2json_test(){  
        int[] intArray = new int[]{1,4,5};  
        String json = JsonLibUtil.object2json(intArray);  
        Assert.assertEquals("[1,4,5]", json);  
        User user1 = new User(1,"张三");  
        User user2 = new User(2,"李四");  
        User[] userArray = new User[]{user1,user2};  
        String json2 = JsonLibUtil.object2json(userArray);  
        Assert.assertEquals("[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]", json2);  
        List<User> userList = new ArrayList<>();  
        userList.add(user1);  
        userList.add(user2);  
        String json3 = JsonLibUtil.object2json(userList);  
        Assert.assertEquals("[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]", json3);  
        //这里的map的key必须为String类型  
        Map<String,Object> map = new HashMap<>();  
        map.put("id", 1);  
        map.put("name", "张三");  
        String json4 = JsonLibUtil.object2json(map);  
        Assert.assertEquals("{\"id\":1,\"name\":\"张三\"}", json4);  
        Map<String,User> map2 = new HashMap<>();  
        map2.put("user1", user1);  
        map2.put("user2", user2);  
        String json5 = JsonLibUtil.object2json(map2);  
        Assert.assertEquals("{\"user2\":{\"id\":2,\"name\":\"李四\"},\"user1\":{\"id\":1,\"name\":\"张三\"}}", json5);  
    }  
      
    @Test  
    public void xml2json_test(){  
        String xml1 = "<User><id>1</id><name>张三</name></User>";  
        String json = JsonLibUtil.xml2json(xml1);  
        Assert.assertEquals("{\"id\":\"1\",\"name\":\"张三\"}", json);  
        String xml2 = "<Response><CustID>1300000428</CustID><Items><Item><Sku_ProductNo>sku_0004</Sku_ProductNo></Item><Item><Sku_ProductNo>0005</Sku_ProductNo></Item></Items></Response>";  
        String json2 = JsonLibUtil.xml2json(xml2);  
        //处理数组时expected是处理结果，但不是我们想要的格式  
        String expected = "{\"CustID\":\"1300000428\",\"Items\":[{\"Sku_ProductNo\":\"sku_0004\"},{\"Sku_ProductNo\":\"0005\"}]}";  
        Assert.assertEquals(expected, json2);  
        //实际上我们想要的是expected2这种格式,所以用json-lib来实现含有数组的xml to json是不行的  
        String expected2 = "{\"CustID\":\"1300000428\",\"Items\":{\"Item\":[{\"Sku_ProductNo\":\"sku_0004\"},{\"Sku_ProductNo\":\"0005\"}]}}";  
        Assert.assertEquals(expected2, json2);  
    }  
      
    @Test  
    public void json2arrays_test(){  
        String json = "[\"张三\",\"李四\"]";  
        Object[] array = JsonLibUtil.json2arrays(json);  
        Object[] expected = new Object[] { "张三", "李四" };  
        ArrayAssertions.assertEquals(expected, array);                                                                                              
        //无法将JSON字符串转换为对象数组  
        String json2 = "[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]";  
        Object[] array2 = JsonLibUtil.json2arrays(json2);  
        User user1 = new User(1,"张三");  
        User user2 = new User(2,"李四");  
        Object[] expected2 = new Object[] { user1, user2 };  
        ArrayAssertions.assertEquals(expected2, array2);  
    }  
      
    @Test  
    public void json2list_test(){  
        String json = "[\"张三\",\"李四\"]";  
        List<String> list = JsonLibUtil.json2list(json, String.class);  
        Assert.assertTrue(list.size()==2&&list.get(0).equals("张三")&&list.get(1).equals("李四"));  
        String json2 = "[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]";  
        List<User> list2 = JsonLibUtil.json2list(json2, User.class);  
        Assert.assertTrue(list2.size()==2&&list2.get(0).getId()==1&&list2.get(1).getId()==2);  
    }  
      
    @Test  
    public void json2pojo_test(){  
        String json = "{\"id\":1,\"name\":\"张三\"}";  
        User user = (User) JsonLibUtil.json2pojo(json, User.class);  
        Assert.assertEquals(json, user.toString());  
    }  
      
    @Test  
    public void json2map_test(){  
        String json = "{\"id\":1,\"name\":\"张三\"}";  
        Map map = JsonLibUtil.json2map(json);  
        int id = Integer.parseInt(map.get("id").toString());  
        String name = map.get("name").toString();  
        System.out.println(name);  
        Assert.assertTrue(id==1&&name.equals("张三"));  
        String json2 = "{\"user2\":{\"id\":2,\"name\":\"李四\"},\"user1\":{\"id\":1,\"name\":\"张三\"}}";  
        Map map2 = JsonLibUtil.json2map(json2, User.class);  
        System.out.println(map2);  
    }  
      
    @Test  
    public void json2xml_test(){  
        String json = "{\"id\":1,\"name\":\"张三\"}";  
        String xml = JsonLibUtil.json2xml(json);  
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<o><id type=\"number\">1</id><name type=\"string\">张三</name></o>\r\n", xml);  
        System.out.println(xml);  
        String json2 = "[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]";  
        String xml2 = JsonLibUtil.json2xml(json2);  
        System.out.println(xml2);  
        Assert.assertEquals("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\r\n<a><e class=\"object\"><id type=\"number\">1</id><name type=\"string\">张三</name></e><e class=\"object\"><id type=\"number\">2</id><name type=\"string\">李四</name></e></a>\r\n", xml2);  
    }  
      
    public static class User{  
        private int id;  
        private String name;  
          
        public User() {  
        }  
        public User(int id, String name) {  
            this.id = id;  
            this.name = name;  
        }  
        @Override  
        public String toString() {  
            return "{\"id\":"+id+",\"name\":\""+name+"\"}";  
        }  
        public int getId() {  
            return id;  
        }  
        public void setId(int id) {  
            this.id = id;  
        }  
        public String getName() {  
            return name;  
        }  
        public void setName(String name) {  
            this.name = name;  
        }  
    }  
}  