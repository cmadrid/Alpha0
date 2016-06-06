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


    private static String charset = "UTF-8";
    //private static String url = "http://192.168.1.6:8888/json/index.php";//"http://localhost:8888/json/index.php";
    private static String url = "http://192.168.0.4:8888/json/index.php";//"http://localhost:8888/json/index.php";

    public static String executeGet(final String method,final Param<String,String>... params) {
        String ret = "";
        String https_url = url+"?action="+method;
        if(params!=null && params.length>0){
            //https_url = https_url+"?";
            for(Param param: params){
                https_url = https_url + "&" + param.getKey() + "=" + param.getValue();
            }
        }
        URL url;
        try {

            HttpURLConnection con;
            url = new URL(https_url);

            con = (HttpURLConnection) url.openConnection();

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

    /*

     String urlParameters  = "param1=a&param2=b&param3=c";
byte[] postData       = urlParameters.getBytes( StandardCharsets.UTF_8 );
int    postDataLength = postData.length;
String request        = "http://example.com/index.php";
URL    url            = new URL( request );
HttpURLConnection conn= (HttpURLConnection) url.openConnection();
conn.setDoOutput( true );
conn.setInstanceFollowRedirects( false );
conn.setRequestMethod( "POST" );
conn.setRequestProperty( "Content-Type", "application/x-www-form-urlencoded");
conn.setRequestProperty( "charset", "utf-8");
conn.setRequestProperty( "Content-Length", Integer.toString( postDataLength ));
conn.setUseCaches( false );
try( DataOutputStream wr = new DataOutputStream( conn.getOutputStream())) {
   wr.write( postData );
}
     */




    public static String executePost(final String method,final Param<String,String>... params) {
        String ret = "";
        //String https_url = "http://localhost:8888/json/index.php";
        String https_url = url;

        String parametros = "action="+method;
        if(params!=null && params.length>0){
            for(Param param: params){
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

