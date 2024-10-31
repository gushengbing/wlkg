package com.wlkg;

import com.wlkg.client.GoodsClient;
import com.wlkg.common.pojo.PageResult;
import com.wlkg.pojo.Goods;
import com.wlkg.pojo.Spu;
import com.wlkg.repository.GoodsRepository;
import com.wlkg.service.GoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class test {
    @Autowired
    private ElasticsearchTemplate esTemplate;
    @Autowired
    private GoodsRepository goodsRepository;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private GoodsService service;

    @Test
    public void createIndex(){
        // 创建索引
        esTemplate.createIndex(Goods.class);
        // 配置映射
        esTemplate.putMapping(Goods.class);
    }

    @Test
    public void  loadData(){
        long l = System.currentTimeMillis();
        int page=1;
        int rows=100;
        int size=0;
        do {
            //查询分页spu数据
            PageResult<Spu> pages= goodsClient.querySpuByPage(page,rows,true,null);
            List<Spu> spus = pages.getItems();
            System.out.print("----"+spus);
            size=spus.size();
            //创建Goods集合
            List<Goods> goodsList=new ArrayList<>();
            // 遍历spu
            for (Spu spu : spus) {
                try {
                    Goods goods=service.buildGoods(spu);
                    System.out.println("goods:"+goods);
                    goodsList.add(goods);
                } catch (Exception e) {
                    e.printStackTrace();
                    break;
                }
            }
            System.out.println("goodsList:"+goodsList);
            goodsRepository.saveAll(goodsList);
            page++;
        }while (size==100);
        long l1 = System.currentTimeMillis();
        System.out.println("运行时间:"+(l1-l)/1000);
    }



}
