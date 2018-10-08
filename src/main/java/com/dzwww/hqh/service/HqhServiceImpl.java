package com.dzwww.hqh.service;

import com.dzwww.hqh.entity.Data;
import com.dzwww.hqh.entity.Error;
import com.dzwww.hqh.entity.PageData;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * 服务层实现类
 */
@Service
public class HqhServiceImpl implements HqhService {

    //WebService地址
    private static final String URL = "http://124.128.251.110:9007/sdmanager/services/hqh?wsdl";

    private static final Client CLIENT = JaxWsDynamicClientFactory.newInstance().createClient(URL);

    private ObjectMapper xmlMapper = new XmlMapper();

    @Override
    public PageData listXm(String type, int pageNum, int pageSize) {

        PageData pageData = new PageData();

        try {
            if ("1".equals(type) || "2".equals(type) || "5".equals(type) || "6".equals(type)) {

                if (pageNum < 1) {
                    pageNum = 1;
                }
                if (pageSize < 0) {
                    pageSize = 10;
                }

                Object[] objects = CLIENT.invoke("getData", new Object[]{type, "<?xml version=\"1.0\" encoding=\"utf-8\" ?><query><pageno>" + pageNum + "</pageno><pagesize>" + pageSize + "</pagesize></query>"});
                if (objects != null && objects.length > 0) {
                    if (objects[0] instanceof String) {
                        String object = objects[0].toString();
                        if (!object.contains("error")) {
                            pageData = xmlMapper.readValue(object, PageData.class);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return pageData;
    }

    @Override
    public Map<String, String> xmDetail(String type, String key) {

        Map<String, String> result = new HashMap<>();

        try {
            if ("3".equals(type) || "4".equals(type)) {
                Object[] objects = CLIENT.invoke("getData", new Object[]{type, "<?xml version=\"1.0\" encoding=\"utf-8\" ?><query><id>" + key + "</id></query>"});
                if (objects != null && objects.length > 0) {
                    if (objects[0] instanceof String) {
                        String object = objects[0].toString();
                        if (!object.contains("error")) {
                            Data data = xmlMapper.readValue(object, Data.class);
                            result = data.getRow();
                            result.put("type", type);
                        }
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static void main(String[] args) {

        try {
            Client client = JaxWsDynamicClientFactory.newInstance().createClient("http://124.128.251.110:9007/sdmanager/services/hqh?wsdl");
            Object[] objects = client.invoke("getData", new Object[]{"1", "<?xml version=\"1.0\" encoding=\"utf-8\" ?><query><pageno>80</pageno><pagesize>10</pagesize></query>"});
            System.out.print(objects[0]);

            if (objects[0] instanceof String) {
                String result = objects[0].toString();

                ObjectMapper xmlMapper = new XmlMapper();

                PageData pageData = xmlMapper.readValue(result, PageData.class);
                System.out.print(pageData);

            }

            //ff8080816619edd60166232ff6cc00c8
//            Object[] objects2 = client.invoke("getData", new Object[]{"3", "<?xml version=\"1.0\" encoding=\"utf-8\" ?><query><id>ff8080816619edd60166232ff6cc00c8</id></query>"});
            //ff8080816619ee8e01662929d2c600da
            Object[] objects2 = client.invoke("getData", new Object[]{"4", "<?xml version=\"1.0\" encoding=\"utf-8\" ?><query><id>ff8080816619ee8e01662929d2c600da</id></query>"});
            if (objects2[0] instanceof String) {
                String object2 = objects2[0].toString();
                ObjectMapper xmlMapper = new XmlMapper();
                if (!object2.contains("error")) {
                    Data data = xmlMapper.readValue(object2, Data.class);
                    System.out.print(data);
                } else {
                    Error error = xmlMapper.readValue(object2, Error.class);
                    System.out.print(error.getError());
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
