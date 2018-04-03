package tgb.json;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.hz.tgb.json.JacksonUtil;
  
public class JacksonUtilTest {  
  
    @Test  
    public void test_pojo2json() throws Exception{  
        String json = JacksonUtil.obj2json(new User(1, "张三"));  
        Assert.assertEquals("{\"id\":1,\"name\":\"张三\"}", json);  
        List<User> list = new ArrayList<>();  
        list.add(new User(1, "张三"));  
        list.add(new User(2, "李四"));  
        String json2 = JacksonUtil.obj2json(list);  
        Assert.assertEquals("[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]", json2);  
        Map<String,User> map = new HashMap<>();  
        map.put("user1", new User(1, "张三"));  
        map.put("user2", new User(2, "李四"));  
        String json3 = JacksonUtil.obj2json(map);  
        Assert.assertEquals("{\"user2\":{\"id\":2,\"name\":\"李四\"},\"user1\":{\"id\":1,\"name\":\"张三\"}}", json3);  
    }  
      
    @Test  
    public void test_json2pojo() throws Exception{  
        String json = "{\"id\":1,\"name\":\"张三\"}";  
        User user = JacksonUtil.json2pojo(json, User.class);  
        Assert.assertTrue(user.getId()==1&&user.getName().equals("张三"));  
    }  
      
    @Test  
    public void test_json2map() throws Exception{  
        String json = "{\"id\":1,\"name\":\"张三\"}";  
        Map<String,Object> map = JacksonUtil.json2map(json);  
        Assert.assertEquals("{id=1, name=张三}", map.toString());  
        String json2 = "{\"user2\":{\"id\":2,\"name\":\"李四\"},\"user1\":{\"id\":1,\"name\":\"张三\"}}";  
        Map<String,User> map2 = JacksonUtil.json2map(json2, User.class);  
        User user1 = map2.get("user1");  
        User user2 = map2.get("user2");  
        Assert.assertTrue(user1.getId()==1&&user1.getName().equals("张三"));  
        Assert.assertTrue(user2.getId()==2&&user2.getName().equals("李四"));  
    }  
      
    @Test  
    public void test_json2list() throws Exception{  
        String json = "[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]";  
        List<User> list = JacksonUtil.json2list(json,User.class);  
        User user1 = list.get(0);  
        User user2 = list.get(1);  
        Assert.assertTrue(user1.getId()==1&&user1.getName().equals("张三"));  
        Assert.assertTrue(user2.getId()==2&&user2.getName().equals("李四"));  
    }  
      
    @Test  
    public void test_map2pojo(){  
        Map<String,Object> map = new HashMap<String, Object>();  
        map.put("id", 1);  
        map.put("name", "张三");  
        User user = JacksonUtil.map2pojo(map, User.class);  
        Assert.assertTrue(user.getId()==1&&user.getName().equals("张三"));  
        System.out.println(user);  
    }  
      
    @Test  
    public void test_json2xml() throws Exception{  
        String json = "{\"id\":1,\"name\":\"张三\"}";  
        String xml = JacksonUtil.json2xml(json);  
        Assert.assertEquals("<ObjectNode xmlns=\"\"><id>1</id><name>张三</name></ObjectNode>", xml);  
        String json2 = "{\"Items\":{\"RequestInterfaceSku\":[{\"Sku_ProductNo\":\"sku_0004\"},{\"Sku_ProductNo\":\"sku_0005\"}]}}";  
        String xml2 = JacksonUtil.json2xml(json2);  
        Assert.assertEquals("<ObjectNode xmlns=\"\"><Items><RequestInterfaceSku><Sku_ProductNo>sku_0004</Sku_ProductNo></RequestInterfaceSku><RequestInterfaceSku><Sku_ProductNo>sku_0005</Sku_ProductNo></RequestInterfaceSku></Items></ObjectNode>", xml2);  
    }  
      
    @Test  
    public void test_xml2json() throws Exception{  
        String xml = "<ObjectNode xmlns=\"\"><id>1</id><name>张三</name></ObjectNode>";  
        String json = JacksonUtil.xml2json(xml);  
        Assert.assertEquals("{\"id\":1,\"name\":\"张三\"}", json);  
        String xml2 = "<ObjectNode xmlns=\"\"><Items><RequestInterfaceSku><Sku_ProductNo>sku_0004</Sku_ProductNo></RequestInterfaceSku><RequestInterfaceSku><Sku_ProductNo>sku_0005</Sku_ProductNo></RequestInterfaceSku></Items></ObjectNode>";  
        String json2 = JacksonUtil.xml2json(xml2);  
        //expected2是我们想要的格式，但实际结果确实expected1，所以用jackson实现xml直接转换为json在遇到数组时是不可行的  
        String expected1 = "{\"Items\":{\"RequestInterfaceSku\":{\"Sku_ProductNo\":\"sku_0004\"},\"RequestInterfaceSku\":{\"Sku_ProductNo\":\"sku_0005\"}}}";  
        String expected2 = "{\"Items\":{\"RequestInterfaceSku\":[{\"Sku_ProductNo\":\"sku_0004\"},{\"Sku_ProductNo\":\"sku_0005\"}]}}";  
        Assert.assertEquals(expected1, json2);  
        Assert.assertEquals(expected2, json2);  
    }  
      
    private static class User{  
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