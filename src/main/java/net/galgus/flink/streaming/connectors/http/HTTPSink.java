package net.galgus.flink.streaming.connectors.http;

import net.galgus.flink.streaming.connectors.http.common.HTTPConnectionConfig;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class HTTPSink<IN> extends RichSinkFunction<IN> {
    private static final Logger log = LoggerFactory.getLogger(HTTPSink.class);
    
    private HTTPConnectionConfig httpConnectionConfig;
    
    public HTTPSink(HTTPConnectionConfig httpConnectionConfig) {
        this.httpConnectionConfig = httpConnectionConfig;
    }
    
    public void invoke(IN value, Context context) throws Exception {
        if (value != null) {
            URL url = new URL(httpConnectionConfig.getEndpoint());

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setDoOutput(true);
            conn.setRequestMethod(httpConnectionConfig.getMethod());

            httpConnectionConfig.getHeaders().forEach(conn::setRequestProperty);

            OutputStreamWriter writer = new OutputStreamWriter(conn.getOutputStream(), "UTF-8");
            writer.write(value.toString());
            writer.close();

            int status = conn.getResponseCode();
            if (status != 200) {
                BufferedReader in = new BufferedReader(
                        new InputStreamReader(conn.getErrorStream()));
                String inputLine;
                StringBuffer error = new StringBuffer();
                while ((inputLine = in.readLine()) != null) {
                    error.append(inputLine);
                }
                in.close();
                throw new IOException("HTTP Response code: " + status
                        + ", " + conn.getResponseMessage() + ", " + error
                        + ", Submitted payload: " + value.toString()
                        + ", url:" + httpConnectionConfig.getEndpoint());
            }
            
            log.debug(", response code: " + status
                    + ", " + conn.getResponseMessage()
                    + ", headers: " + httpConnectionConfig.getHeaders());
            
            conn.disconnect();
        }
    }
}
