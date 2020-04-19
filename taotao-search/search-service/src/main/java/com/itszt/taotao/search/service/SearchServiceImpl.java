package com.itszt.taotao.search.service;

import com.itszt.taotao.easyui.bean.ItemPicUtil;
import com.itszt.taotao.pojo.TbItem;
import com.itszt.taotao.search.dao.SearchDTO;
import com.itszt.taotao.search.dao.SearchDao;
import com.itszt.taotao.search.inter.SearchItemResult;
import com.itszt.taotao.search.inter.SearchService;
import com.itszt.taotao.search.inter.SearchVO;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

@Service
public class SearchServiceImpl implements SearchService {
    @Resource
    private SearchDao searchDao;
    @Override
    public SearchItemResult search(SearchVO searchVO) {
// 1 设置查询条件
        SolrQuery solrQuery = new SolrQuery();
        if (!StringUtils.isEmpty(searchVO.getKeyword())) {
            solrQuery.setQuery("taotaoSearch:\""+searchVO.getKeyword()+"\"");
        }
        if (searchVO.getCid()!=null) {
            solrQuery.addFilterQuery("cid:"+searchVO.getCid());
        }               //price:[10 ]
        if (searchVO.getLowerPrice()!=null&&searchVO.getHigerPrice()!=null) {
            solrQuery.addFilterQuery("price:["+searchVO.getLowerPrice()+" TO "+searchVO.getHigerPrice()+"]");
        }
        int pageNow=0;
        if (searchVO.getPage()==null) {
            pageNow=1;
        }else {
            pageNow=searchVO.getPage();
        }
        int start =20*(pageNow-1);
        solrQuery.setStart(start);            //这两行是分页   起始位置  个数
        solrQuery.setRows(20);

        if (searchVO.getSort()!=null) {
            solrQuery.setSort(searchVO.getSort(),searchVO.isDesc()?SolrQuery.ORDER.desc:SolrQuery.ORDER.asc);
        }
// 2 设置高亮
        solrQuery.setHighlightSimplePre("<span style='color:red; font-weight:bold'>");
        solrQuery.setHighlightSimplePost("</span>");
        solrQuery.setHighlight(true);
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("sellPoint");


// 3 调用dao 得到 searchDTO     并且 根据searchDTO  设置searchItemResult  并返回
        //(处理图片和高亮)
        SearchItemResult searchItemResult=null;
        try {
            SearchDTO searchDTO = searchDao.searchItems(solrQuery);
            List<TbItem> tbItems = searchDTO.getTbItems();
            Map<String, Map<String, List<String>>> highlighting = searchDTO.getHighlighting();
            for (TbItem tbItem : tbItems) {
                tbItem.setImage(ItemPicUtil.genFullPath(tbItem.getImage().split(",")[0]));
                Map<String, List<String>> stringListMap = highlighting.get(String.valueOf(tbItem.getId()));
                if (stringListMap==null) {
                    continue;
                }
                List<String> title = stringListMap.get("title");
                List<String> sellPoint = stringListMap.get("sellPoint");
                if (title!=null) {
                    tbItem.setTitle(title.get(0));
                }
                if (sellPoint!=null) {
                    tbItem.setSellPoint(sellPoint.get(0));
                }
            }
            Long totalItems = searchDTO.getNumFound();
            int totalPage = (int) Math.ceil(totalItems * 1.0 / 20);
            searchItemResult = new SearchItemResult(searchVO.getKeyword(),pageNow,totalItems,totalPage,tbItems);
            return searchItemResult;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }

        searchItemResult=new SearchItemResult();
        searchItemResult.setResultInfo("网络状态异常，请刷新重试！！");

        return searchItemResult;


    }
}
