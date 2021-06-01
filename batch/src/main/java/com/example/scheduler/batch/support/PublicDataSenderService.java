package com.example.scheduler.batch.support;

import com.example.scheduler.batch.support.model.PublicDataHoliday;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

// 공공 데이터 Sender Service
@Slf4j
@Service
public class PublicDataSenderService {

    @Value("${public.data.key}")
    private String servideKey;
    @Value("${public.data.url}")
    private String url;

    public List<PublicDataHoliday> apiSend(String solYear, String solMonth) {
        String xml = null;
        List<PublicDataHoliday> data = new ArrayList<>();
        try {
            StringBuilder urlBuilder = new StringBuilder(url);
            urlBuilder.append("?ServiceKey=" + servideKey);
            urlBuilder.append("&pageNo=1");
            urlBuilder.append("&numOfRows=50");
            urlBuilder.append("&solYear=" + solYear);
            urlBuilder.append("&solMonth=" + solMonth);
            URL url = new URL(urlBuilder.toString());
            HttpURLConnection conn = null;
            conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-type", "application/json");
            log.info("Response Code : {}", conn.getResponseCode());
            BufferedReader rd = null;
            if (conn.getResponseCode() >= 200 && conn.getResponseCode() <= 300) {
                rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            } else {
                rd = new BufferedReader(new InputStreamReader(conn.getErrorStream()));
            }
            StringBuilder sb = new StringBuilder();
            String line;
            while ((line = rd.readLine()) != null) {
                sb.append(line);
            }
            xml = sb.toString();

            if (StringUtils.isNotBlank(xml)) {
                DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                DocumentBuilder documentBuilder = factory.newDocumentBuilder();

                InputStream is = new ByteArrayInputStream(xml.getBytes());
                Document doc = documentBuilder.parse(is);

                Element element = doc.getDocumentElement();

                NodeList dateNameItems = element.getElementsByTagName("dateName");
                NodeList locdateItems = element.getElementsByTagName("locdate");

                int n = dateNameItems.getLength();
                for (int i = 0; i < n; i++) {
                    Node dateNameItem = dateNameItems.item(i);
                    Node dateNameText = dateNameItem.getFirstChild();
                    String dateNameValue = dateNameText.getNodeValue();

                    Node locdateItem = locdateItems.item(i);
                    Node locdateText = locdateItem.getFirstChild();
                    String locdateValue = locdateText.getNodeValue();

                    data.add(PublicDataHoliday.builder()
                            .dateName(dateNameValue)
                            .locdate(locdateValue)
                            .build());
                }
            }
            rd.close();
            conn.disconnect();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return data;
    }
}
