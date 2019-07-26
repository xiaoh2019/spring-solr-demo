package com.cyzs.search;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author xiaoH
 * @create 2019-07-26-16:33
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:app-context-solr.xml")
public class TbItemTest {

    @Autowired
    SolrTemplate solrTemplate;

    /**
     * solrTemplate的方法
     * execute
     * ping
     * count
     * saveBean
     * saveDocument
     * delete
     * deleteById
     * queryForObject
     * doQueryForPage
     * queryForPage
     * queryForGroupPage
     * queryForStatsPage
     * queryForFacetPage
     * queryForHighlightPage
     * createSolrResultPage
     * queryForTermsPage
     * query
     * executeSolrQuery
     * commit
     * softCommit
     * rollback
     * convertBeanToSolrInputDocument
     * getSchemaName
     * queryForCursor
     * getById
     * convertBeansToSolrInputDocuments
     * convertQueryResponseToBeans
     */


    /**
     * 根据条件删除
     */
    @Test
    public void delete(){
        SimpleQuery simpleQuery = new SimpleQuery("*:*");
        solrTemplate.delete(simpleQuery);

    }


    /**
     * 添加一条信息
     */
    @Test
    public void add(){
        TbItem item=new TbItem();
        item.setId(1L);
        item.setBrand("华为");
        item.setCategory("手机");
        item.setGoodsId(1L);
        item.setSeller("华为10号专卖店");
        item.setTitle("华为Mate9");
        item.setPrice(new BigDecimal(2000));
        solrTemplate.saveBean(item);
        solrTemplate.commit();
    }

    /**
     * 根据Id查询一条数据
     */
    @Test
    public void getOne(){
        TbItem byId = solrTemplate.getById(1, TbItem.class);
        System.out.println(byId);
    }

    /**
     * 添加list数据
     */
    @Test
    public void addList(){
        List<TbItem> list=new ArrayList();

        for(int i=0;i<100;i++){
            TbItem item=new TbItem();
            item.setId(i+1L);
            item.setBrand("华为");
            item.setCategory("手机");
            item.setGoodsId(1L);
            item.setSeller("华为2号专卖店");
            item.setTitle("华为Mate"+i);
            item.setPrice(new BigDecimal(2000+i));
            list.add(item);
        }

        solrTemplate.saveBeans(list);
        solrTemplate.commit();
    }
    /**
     * 分页查询
     */
    @Test
    public void page(){
        SimpleQuery query = new SimpleQuery("*:*");
        Criteria criteria = new Criteria("item_title");
        criteria.contains("华为");
        query.addCriteria(criteria);
        //开始位置
        query.setOffset(0);
        //记录数
        query.setRows(10);
        ScoredPage<TbItem> tbItems = solrTemplate.queryForPage(query, TbItem.class);
        //
        System.out.println(tbItems.getNumberOfElements());
    }



}