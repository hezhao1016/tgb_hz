package com.hz.tgb.test.json;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hz.tgb.json.FastJsonUtil;
import org.junit.Assert;
import org.junit.Test;

public class FastJsonUtilTest {  
      
    @Test  
    public void test_dateFormat(){  
        Date date = new Date();  
        String json = FastJsonUtil.serializeMapping(date);
        String expected = "\""+new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date)+"\"";  
        Assert.assertEquals(expected, json);  
    }  
      
    @Test  
    public void test_obj2json(){  
        User user = new User(1, "张三");  
        String json = FastJsonUtil.serialize(user);  
        Assert.assertEquals("{\"id\":1,\"name\":\"张三\"}", json);  
        List<User> list = new ArrayList<>();  
        list.add(new User(1, "张三"));  
        list.add(new User(2, "李四"));  
        String json2 = FastJsonUtil.serialize(list);  
        Assert.assertEquals("[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]", json2);  
        Map<String,User> map = new HashMap<>();  
        map.put("user1", new User(1, "张三"));  
        map.put("user2", new User(2, "李四"));  
        String json3 = FastJsonUtil.serialize(map);  
        Assert.assertEquals("{\"user1\":{\"id\":1,\"name\":\"张三\"},\"user2\":{\"id\":2,\"name\":\"李四\"}}", json3);  
    }  
      
    @Test  
    public void test_json2obj(){  
        String json = "{\"id\":1,\"name\":\"张三\"}";  
        User user = FastJsonUtil.jsonToObject(json, User.class);  
        Assert.assertTrue(user.getId()==1&&user.getName().equals("张三"));  
    }  
      
    @Test  
    public void test_json2list(){  
        String json = "[{\"id\":1,\"name\":\"张三\"},{\"id\":2,\"name\":\"李四\"}]";  
        List<User> list = FastJsonUtil.getList(json, User.class);  
        User user1 = list.get(0);  
        User user2 = list.get(1);  
        Assert.assertTrue(user1.getId()==1&&user1.getName().equals("张三"));  
        Assert.assertTrue(user2.getId()==2&&user2.getName().equals("李四"));  
    }  
      
    @Test  
    public void test_json2map() throws Exception{  
        String json = "{\"id\":1,\"name\":\"张三\"}";  
        Map<String,Object> map = FastJsonUtil.jsonToMap(json);  
        Assert.assertEquals("{id=1, name=张三}", map.toString());  
        String json2 = "{\"user2\":{\"id\":2,\"name\":\"李四\"},\"user1\":{\"id\":1,\"name\":\"张三\"}}";  
        Map<String,User> map2 = FastJsonUtil.jsonToMap(json2, User.class);  
        User user1 = map2.get("user1");  
        User user2 = map2.get("user2");  
        Assert.assertTrue(user1.getId()==1&&user1.getName().equals("张三"));  
        Assert.assertTrue(user2.getId()==2&&user2.getName().equals("李四"));  
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