package application;

import org.json.JSONObject;
import org.apache.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.*;
import org.apache.http.util.EntityUtils;

public class weatherClass2 {

  public static String main(String[] args) throws Exception {
	  CloseableHttpClient httpclient = HttpClients.createDefault();
    double temp;
	  try {
      // specify the host, protocol, and port
      HttpHost target = new HttpHost("api.openweathermap.org", 80, "http");

      // specify the get request
      HttpGet getRequest = new HttpGet("/data/2.5/weather?id=1174872&appid=328e32be4889bbc6dbc734d93f2fcad0&units=metric");
      


      HttpResponse httpResponse = httpclient.execute(target, getRequest);
      HttpEntity entity = httpResponse.getEntity();


      String holder=EntityUtils.toString(entity);
      System.out.println(holder);
      JSONObject jsonObj = new JSONObject(holder);
        temp =jsonObj.getJSONObject("main").getDouble("temp");
    } finally {
    	httpclient.close();
    }
	return (Double.toString(temp));

  }
  public static class CustomException extends Exception {
	  @Override
	  public Throwable fillInStackTrace() {
	      return null;
	  }   
  }
}
