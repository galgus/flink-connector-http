package net.galgus.flink.streaming.connectors.http.common;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

public class HTTPConnectionConfig implements Serializable {
    
    public enum HTTPMethod {
        PUT,
        DELETE,
        POST,
        PATCH
    }
    
    private String endpoint;
    private HTTPMethod method = HTTPMethod.POST;
    private Map<String, String> headers = new HashMap<>();

    public HTTPConnectionConfig(String endpoint, HTTPMethod method, Map<String, String> headers) {
        this.endpoint = endpoint;
        this.method = method;
        this.headers = headers;
    }

    public Map<String, String> getHeaders() {
        return headers;
    }

    public String getEndpoint() {
        return endpoint;
    }

    public void setEndpoint(String endpoint) {
        this.endpoint = endpoint;
    }
    
    public String getMethod() {
        return method.toString();
    }

    public void setMethod(HTTPMethod method) {
        this.method = method;
    }
    
    public static class Builder {
        private String endpoint;
        private HTTPMethod method;
        private Map<String, String> headers = new HashMap<>();
        
        public Builder setEndpoint(String endpoint) {
            this.endpoint = endpoint;
            return this;
        }
        
        public Builder setMethod(String method) {
            this.method = HTTPMethod.valueOf(method.toUpperCase());
            return this;
        }
        
        public Builder addHeaders(String headers) {
            final String HEADER_SEPARATOR = ",";
            final String HEADER_SPLITTER = ":";
            final int KEY = 0;
            final int VALUE = 1;

            String[] tokens = headers.split(HEADER_SEPARATOR);

            for (String token : tokens) {
                String[] header = token.split(HEADER_SPLITTER);

                this.headers.put(header[KEY], header[VALUE]);
            }
            
            return this;
        }
        
        public HTTPConnectionConfig build() {
            return new HTTPConnectionConfig(endpoint, method, headers);
        }
        
        
    }
}
