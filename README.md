# Flink HTTP Connector

`flink-connector-http` is a [Flink Streaming Connector](https://ci.apache.org/projects/flink/flink-docs-stable/dev/connectors/) for invoking HTTPs APIs with data from any source.



## Build & Run

### Requirements

To build `flink-connector-http` you need to have [maven](https://maven.apache.org/) installed.



### Steps

To build `flink-connector-http` you must run the next command:

```
mvn clean install
```



This command will install all the components in your `.m2` directory. To use you only must to add the next dependency in your `pom.xml` file:

```
<dependency>
  <groupId>tgamforks</groupId>
  <artifactId>flink-connector-http</artifactId>
  <version>1.0-SNAPSHOT</version>
</dependency>
```



## FAQ

### How could I disable SSL Certificate verification

This is a common issue when you are developing in your localhost or with a self-signed certificate. If you run a flink application you could get the next exceptions:

`PKIX path building failed: sun.security.provider.certpath.SunCertPathBuilderException: unable to find valid certification path to requested target`

`javax.net.ssl.SSLHandshakeException: java.security.cert.CertificateException: No name matching ssl.someUrl.de found`

To solve this issue you can use the next snippet:

```
TrustManager[] trustAllCerts = new TrustManager[] {
  new X509TrustManager() {
    public java.security.cert.X509Certificate[] getAcceptedIssuers() {
    	return null;
  	}

  	public void checkClientTrusted(X509Certificate[] certs, String authType) {  }

  	public void checkServerTrusted(X509Certificate[] certs, String authType) {  }
	}
};

SSLContext sc = SSLContext.getInstance("SSL");
sc.init(null, trustAllCerts, new java.security.SecureRandom());

HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

HttpsURLConnection.setDefaultHostnameVerifier(new HostnameVerifier() {
	@Override
	public boolean verify(String s, SSLSession sslSession) {
		return true;
	}
});
```



This code is for testing purposes, and disabling certificate verification is not a recommended practice.