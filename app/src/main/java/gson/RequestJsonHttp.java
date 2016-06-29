package gson;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/**
 * Created by ces_m on 6/4/2016.
 */
public class RequestJsonHttp {


    //public static String host = "192.168.1.18";
    //public static String port = ":8888";
    //public static String host = "puntoec.org";
    public static String host = "ns4021.hostgator.com/~cmadrid";
    public static String port = "";
    private static String charset = "UTF-8";
    //private static String url = "http://192.168.1.6:8888/json/index.php";//"http://localhost:8888/json/index.php";
    private static String url = "http://"+host+port+"/json/index.php";//"http://localhost:8888/json/index.php";



    public static String executePost(final String method,final Param<String,String>... params) {
        String ret = "";
        //String https_url = "http://localhost:8888/json/index.php";
        String https_url = url;

        String parametros = "action="+method;
        if(params!=null && params.length>0){
            for(Param param: params){
                if(param.getValue()==null)continue;
                parametros = parametros + "&" + param.getKey() + "=" + param.getValue();
            }
        }


        byte[] postData = parametros.getBytes( Charset.forName("UTF-8") );
        int postDataLength = postData.length;
        URL url;
        try {

            HttpURLConnection con;
            url = new URL(https_url);

            con = (HttpURLConnection) url.openConnection();


            con.setDoOutput( true );
            con.setInstanceFollowRedirects( false );
            con.setRequestMethod( "POST" );
            con.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
            con.setRequestProperty( "charset", "utf-8");
            con.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
            con.setUseCaches( false );


            DataOutputStream wr = null;

            try {
                wr = new DataOutputStream(con.getOutputStream());
                wr.write(postData);

            }catch (Exception e){
                e.printStackTrace();
            }finally {
                if(wr != null)
                    try {
                        wr.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
            }

            int status = con.getResponseCode();

            switch (status) {
                case 200:
                case 201:
                    BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = br.readLine()) != null) {
                        sb.append(line+"\n");
                    }
                    br.close();
                    ret = sb.toString();
            }
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return ret;
    }


}

