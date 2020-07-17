import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * Created with IntelliJ IDEA.
 * Description: JSON 预研
 * User: HHH.Y
 * Date: 2020-07-14
 */
public class FastJSONDemo {
    public static void main(String[] args) {
        JSONObject object = new JSONObject();
        JSONObject author = new JSONObject();
        author.put("id", 139);
        author.put("username", "鲁迅");

        object.put("id", 18);
        object.put("title", "论雷峰塔的倒掉");
        object.put("author", author);
        object.put("published_at", "2020-7-14 15:02:10");

        JSONArray array = new JSONArray();
        array.add(object);
        array.add(object.clone());
        System.out.println(array.toJSONString());
    }
}
