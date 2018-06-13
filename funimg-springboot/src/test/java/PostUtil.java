import com.alibaba.fastjson.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;

/**
 * Created by highness on 2018/6/12 0012.
 */
public class PostUtil {

    static String postUrl = "https://weibo.com/like/outbox?page=2&pids=Pl_Content_LikeListOutbox&ajaxpagelet=1&ajaxpagelet_v6=1&__ref=%2Flike%2Foutbox%3Fleftnav%3D1&_t=FM_152881882173511";
    static String Cookie = "SINAGLOBAL=622777335811.4064.1465575120236; UM_distinctid=163ac9fccba7fa-07903e1378a564-2b6f686a-15f900-163ac9fccbd72d; YF-V5-G0=bb389e7e25cccb1fadd4b1334ab013c1; SSOLoginState=1528818734; SCF=Am0k939cz18m041F3lBVEwc8zXI1PKIGSfrBROs000TnpD0r2C0WLOlpTO6pRDlUM3c0oKYFlfI2-2sjIMfr0Xo.; SUB=_2A252G5x-DeRhGeRH7FMU8ynFyj-IHXVVUIq2rDV8PUNbmtANLW_3kW9NTYsP23kHjyMicZNr5_PErrP1d4fp4COt; SUBP=0033WrSXqPxfM725Ws9jqgMF55529P9D9WFDzNefysM2fSMz-Fqxy97r5JpX5KMhUgL.Foz4S02fe0M4eKe2dJLoIp8zIgUQdJH0MEH8SCHWxFHWSntt; SUHB=0S7Sizo4p1nm4k; ALF=1560354734; YF-Page-G0=23b9d9eac864b0d725a27007679967df; _s_tentry=login.sina.com.cn; Apache=3454050405644.7085.1528818738575; ULV=1528818738658:409:12:2:3454050405644.7085.1528818738575:1528638414761; UOR=,,login.sina.com.cn; wvr=6";


    public static void main(String[] args) {
//        请求参数，里面包含分页
//        JSONObject params = JSONObject.parseObject("{\"size\": 15, \"page\": 1, \"sort\": \"yxdm,year desc\", \"parameters\": {\"a.kldm\": \"0\"}}");
//        System.out.println(params);
        String params = "";
        String result = loadData(postUrl, params);
        System.out.println(result);
    }

    public static String loadData(String url, String params) {
        try {
            URL wsUrl = new URL(url);

            HttpURLConnection conn = (HttpURLConnection) wsUrl.openConnection();

            conn.setDoInput(true);
            conn.setDoOutput(true);
//            conn.setRequestMethod("POST");
            conn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            conn.setRequestProperty("Cookie", Cookie);

            OutputStream os = conn.getOutputStream();
//            os.write(params.getBytes(Charset.forName("UTF-8")));

            InputStream is = conn.getInputStream();

            byte[] b = new byte[1024];
            int len = 0;
            StringBuffer buffer = new StringBuffer();
            while ((len = is.read(b)) != -1) {
                String ss = new String(b, 0, len, "UTF-8");
                buffer.append(ss);
            }
            is.close();
            os.close();
            conn.disconnect();

            String resultStr = buffer.toString();
//            System.out.println(resultStr);
            return resultStr;
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
