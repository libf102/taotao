package com.itszt.taotao.search.dao;

import com.itszt.taotao.pojo.TbItem;
import com.itszt.taotao.search.inter.SearchVO;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.util.LimitedInputStream;
import org.apache.commons.lang3.math.NumberUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Repository
public class SearchDao {

    @Resource
    private HttpSolrServer httpSolrServer;

    private Map<String, Field> fieldMap=new HashMap<>();

    public SearchDTO searchItems(SolrQuery solrQuery) throws SolrServerException, NoSuchFieldException, IllegalAccessException, ParseException {
        QueryResponse query = httpSolrServer.query(solrQuery);
        SolrDocumentList results = query.getResults();  //返回所有结果 (很多条json)
        List<TbItem> tbItems = new ArrayList<>();
            //从 solr中 获取 json    对每一条json遍历     一条json映射成一个TbItem对象
        //第一种 方法  可以  但是 遍历(反射) 次数太多  影响性能
//        for (SolrDocument jsonData : results) {
//            TbItem tbItem = new TbItem();
//            tbItems.add(tbItem);
//            for (String key : jsonData.keySet()) {
//                Object fieldValue = jsonData.getFieldValue(key);
//                Field declaredField = TbItem.class.getDeclaredField(key);
//                declaredField.setAccessible(true);
//                declaredField.set(tbItem,fieldValue.toString());
//
//            }
//        }

        //第二种方法
        for (SolrDocument jsonData : results) {   //遍历  jsonData 为每一条json数据  (有很多键值对)
            TbItem tbItem = new TbItem();
            tbItems.add(tbItem);
            for (String key : jsonData.keySet()) {    //获取一条 json 所有的键
                Object fieldValue = jsonData.getFieldValue(key);   //获取一条 json  每个键 的值
                Field field = fieldMap.get(key);              //map中 键为 key 的  Field
                if (field==null) {
                    try {
                        field = TbItem.class.getDeclaredField(key);    //获取 键为key 的 TbItem属性
                    } catch (Exception e) {
                        continue;
                    }
                    field.setAccessible(true);          //设置为 可用
                    fieldMap.put(key,field);            //<键,属性> 添加到map集合

                }
                if ("created".equals(key)||  "updated".equals(key)){
                    Date date=parseDate(fieldValue.toString());
                    field.set(tbItem, date);   //利用反射给tbItem对象 赋值
                }else if ("id".equals(key)||"cid".equals(key)){
                    field.set(tbItem, NumberUtils.createLong(fieldValue.toString()));
                }else {
                    field.set(tbItem, fieldValue);
                }


            }
        }
//高亮设置
        Map<String, Map<String, List<String>>> highlighting = query.getHighlighting();
        SearchDTO searchDTO = new SearchDTO();
        searchDTO.setTbItems(tbItems);
        searchDTO.setNumFound(results.getNumFound());
        searchDTO.setHighlighting(highlighting);

        return searchDTO;

    }

    private Date parseDate(String dateStr) throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = simpleDateFormat.parse(dateStr.replace(".0", ""));
        return  date;
    }

}


