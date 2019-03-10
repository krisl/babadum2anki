/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package wprg.babadumextractor.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.zip.ZipException;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

/**
 *
 * @author WPRG
 */
public class HttpApacheTest {

    static int z = 0;

    public static void downloadFile(String url, String filePath) throws IOException {
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet(url);
        CloseableHttpResponse response = httpclient.execute(httpget);
        try {
            HttpEntity entity = response.getEntity();
            BufferedInputStream bis = new BufferedInputStream(entity.getContent());
            BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(new File(filePath)));
            int inByte;
            while ((inByte = bis.read()) != -1) {
                bos.write(inByte);
            }
            bis.close();
            bos.close();
        } finally {
            response.close();
        }
    }

    public static File post(String url, Properties parameters) throws UnsupportedEncodingException, IOException {

        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();

        for (Object o : parameters.keySet()) {
            String key = o.toString();
            String value = parameters.getProperty(key);
            nvps.add(new BasicNameValuePair(key, value));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            HttpEntity entity2 = response2.getEntity();
            File targetFile = new File("tmp/" + (z++) + ".json");
            try {
                java.nio.file.Files.copy(
                        entity2.getContent(),
                        targetFile.toPath(),
                        StandardCopyOption.REPLACE_EXISTING);
            } catch (ZipException e) {
            }

            EntityUtils.consume(entity2);
            return targetFile;
        } finally {
            response2.close();
        }
    }

}
