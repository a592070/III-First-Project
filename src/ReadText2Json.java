import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONWriter;

import java.io.*;

public class ReadText2Json {
    public static void main(String[] args) throws IOException {
        String json= "{fields:[],data:[]}";
        JSONObject jsonObject = JSON.parseObject(json);
        JSONArray data = jsonObject.getJSONArray("data");

        String path = "resources/STOCK_TOTAL_NO.txt";
        BufferedReader br = new BufferedReader(new FileReader(path));
        String field = br.readLine();
        String temp;
        while((temp=br.readLine()) != null){
            JSONArray array = new JSONArray();
            String[] arr = temp.split(",");
            for (String s : arr) {
                array.add(s);
            }
            data.add(array);
        }
        jsonObject.fluentPut("fields", field.split(","));

        br.close();
        System.out.println(jsonObject);

        BufferedWriter bw = new BufferedWriter(new FileWriter("resources/STOCK_TOTAL_NO.json"));
        JSONWriter writer = new JSONWriter(bw);
        writer.writeObject(jsonObject);

        writer.close();
    }
}
