package name.ealen;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import name.ealen.domain.entity.Address;
import name.ealen.domain.service.WxAddressSevice;
import name.ealen.domain.service.WxArticleService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ArticleServiceTest extends AbstractServiceTest {
    @Autowired
    protected WxAddressSevice addressSevice;

    @Test
    public void testAdd() throws FileNotFoundException {
        String path = System.getProperty("user.dir");
        path += "\\src\\main\\resources\\static\\data\\address.json";
        File file = new File(path);
        StringBuffer result = new StringBuffer();
        try {
            FileInputStream in = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len=in.read(buffer))!=-1){
                String re = new String(buffer, 0, len);
                System.out.println(re);
                result.append(re);
            }
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }

        JSONArray jsonArray = JSON.parseArray(result.toString());
        List<Address> list = new ArrayList<>();
        for(int i = 0;i<jsonArray.size();i++){
            list.add(JSON.parseObject(jsonArray.get(i).toString(),Address.class));
        }
        addressSevice.addAll(list);

    }
}
