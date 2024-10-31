package com.wlkg.service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wlkg.common.enums.ExceptionEnums;
import com.wlkg.common.exception.WlkgException;
import com.wlkg.common.pojo.PageResult;
import com.wlkg.mapper.*;
import com.wlkg.pojo.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import tk.mybatis.mapper.entity.Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class GoodsService {

    @Autowired
    private SkuMapper skuMapper;
    @Autowired
    private SpuMapper spuMapper;
    @Autowired
    private StockMapper stockMapper;
    @Autowired
    private SpuDetailMapper spuDetailMapper;
    @Autowired
    private  CategoryService categoryService;
    @Autowired
    private BrandService brandService;
    @Autowired
    private AmqpTemplate amqpTemplate;

    /**
     * 添加商品对象
     * @param spu
     */
    @Transactional
    public void insertGoods(Spu spu) {
        //第一步添加spu表
        spu.setSaleable(true);
        spu.setValid(true);
        spu.setCreateTime(new Date());
        spu.setLastUpdateTime(new Date());
        spuMapper.insert(spu);
        //第二步添加SpuDetail表
        SpuDetail spuDetail = spu.getSpuDetail();//获取属性对象
        spuDetail.setSpuId(spu.getId());//set属性对象里面的spuid
        spuDetailMapper.insert(spuDetail);
                                                                      /*  //第三步添加sku表
                                                                        List<Sku> skus = spu.getSkus();
                                                                        for (Sku sku:skus) {
                                                                            sku.setSpuId(spu.getId());
                                                                            sku.setCreateTime(new Date());
                                                                            sku.setLastUpdateTime(new Date());
                                                                            skuMapper.insert(sku);
                                                                            //第四部添加Stock表
                                                                            Stock stock=new Stock();
                                                                            stock.setSkuId(sku.getId());
                                                                            stock.setStock(sku.getStock());
                                                                            stockMapper.insert(stock);
                                                                        }*/
        //第三步添加sku表
        List<Stock> stocks=new ArrayList<>();
        for (Sku sku:spu.getSkus()){
            sku.setSpuId(spu.getId());
            sku.setCreateTime(new Date());
            sku.setLastUpdateTime(new Date());
            skuMapper.insert(sku);
            //第四部添加Stock表
            Stock stock=new Stock();
            stock.setSkuId(sku.getId());
            stock.setStock(sku.getStock());
            stocks.add(stock);
        }
        stockMapper.insertList(stocks);
        sendMessage(spu.getId(),"insert");
    }

    /**
     * 分页查询商品
     * @param page
     * @param rows
     * @param saleable
     * @param key
     * @return
     */
    public PageResult<Spu> querySpuByPageAndSort(Integer page, Integer rows, Boolean saleable, String key) {
        // 1、查询SPU
        // 分页,最多允许查100条
        PageHelper.startPage(page, Math.min(rows, 100));
        // 创建查询条件
        Example example = new Example(Spu.class);
        Example.Criteria criteria = example.createCriteria();
        // 是否过滤上下架
        if (saleable != null) {
            criteria.orEqualTo("saleable", saleable);
        }
        // 是否模糊查询
        if (StringUtils.isNotBlank(key)) {
            criteria.andLike("title", "%" + key + "%");
        }

        //默认排序
        example.setOrderByClause("last_update_time desc");

        //查询
        List<Spu> spus = spuMapper.selectByExample(example);
        System.out.println("spus##############################"+spus);

        if (CollectionUtils.isEmpty(spus)) {
            throw new WlkgException(ExceptionEnums.GOODS_NOT_FOUND);
        }


        for (Spu spu : spus) {
            // 2、查询spu的商品分类名称,要查三级分类
            List<String> names = categoryService.queryNameByIds(
                    Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
            // 将分类名称拼接后存入
            spu.setCname(StringUtils.join(names, "/"));

            // 3、查询spu的品牌名称
            Brand brand = brandService.selectBrandById(spu.getBrandId())     ;
            spu.setBname(brand.getName());
        }
        PageInfo<Spu> pageInfo = new PageInfo<>(spus);
       PageResult<Spu> spuPageResult=new PageResult<>();
        spuPageResult.setTotal(pageInfo.getTotal());
        spuPageResult.setTotalPage(Long.valueOf(pageInfo.getPages()));
        spuPageResult.setItems(spus);
        System.out.println("查询的商品集合："+spuPageResult.getItems());
       // return new PageResult<>(pageInfo.getTotal(), spus);
        return spuPageResult;
    }

    /**
     * 根据id查询 商品详情对象
     * @param id
     * @return
     */
    public SpuDetail querySpuDetailById(Long id) {
        SpuDetail spuDetail = spuDetailMapper.selectByPrimaryKey(id);

        return  spuDetail;
    }

    /**
     * 根据id查询spu对象
     * @param id
     * @return
     */
    public List<Sku> querySkuBySpuId(Long id) {
        // 查询sku
        Sku record = new Sku();
        record.setSpuId(id);
        List<Sku> skus = skuMapper.select(record);
        for (Sku sku : skus) {
            // 同时查询出库存
            sku.setStock(stockMapper.selectByPrimaryKey(sku.getId()).getStock());
        }
        return skus;


    }

    /**
     * 修改spu对象
     * @param spu
     */
    @Transactional
    public void update(Spu spu) {
        Sku sku = new Sku();
        sku.setSpuId(spu.getId());
        //查询sku
        List<Sku> skuList = skuMapper.select(sku);

        if(!CollectionUtils.isEmpty(skuList)){
            //删除sku和stock
            skuMapper.delete(sku);

            //删除stock
            List<Long> ids = skuList.stream().map(Sku::getId).collect(Collectors.toList());

          /*  for (Long id :ids){
                stockMapper.deleteByPrimaryKey(id);
            }*/
            stockMapper.deleteByIdList(ids);//???????????????????????????
        }


        //修改spu
        spu.setValid(null);
        spu.setSaleable(null);
        spu.setLastUpdateTime(new Date());
        spu.setCreateTime(null);

        int count = spuMapper.updateByPrimaryKeySelective(spu);
        if(count!=1) {
            throw new WlkgException(ExceptionEnums.GOODS_UPDATE_ERROR);
        }
        //修改detail
        count = spuDetailMapper.updateByPrimaryKeySelective(spu.getSpuDetail());
        if(count!=1){
            throw new WlkgException(ExceptionEnums.GOODS_UPDATE_ERROR);
        }

        //第三步添加sku表
        List<Stock> stocks=new ArrayList<>();
        for (Sku s:spu.getSkus()){
            s.setSpuId(spu.getId());
            s.setCreateTime(new Date());
            s.setLastUpdateTime(new Date());
            skuMapper.insert(s);
            //第四部添加Stock表
            Stock stock=new Stock();
            stock.setSkuId(s.getId());
            stock.setStock(s.getStock());
            stocks.add(stock);
        }
        stockMapper.insertList(stocks);
        //新增sku和stock
      //  saveSkuAndStock(spu);
        sendMessage(spu.getId(),"update");
    }

    /**
     * 根据id删除商品
     * @param id
     */
    @Transactional
    public void deleteGoods(Long id) {
       //删除spu数据
        spuMapper.deleteByPrimaryKey(id);
        //删除spuDetail表数据
        spuDetailMapper.deleteByPrimaryKey(id);
        //删除sku表数据
        Sku sku=new Sku();
        sku.setSpuId(id);
        List<Sku> skus = skuMapper.select(sku);
        for (Sku s : skus){
            skuMapper.delete(s);
            //删除skoct表数据
            stockMapper.deleteByPrimaryKey(s.getId());
        }
        sendMessage(id,"delete");
    }

    /**
     * 根据id修改商品上下架状态
     * @param id
     */
    public void updateGoods(Long id,Boolean saleable) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        spu.setSaleable(saleable);
        spu.setLastUpdateTime(new Date());
        spuMapper.updateByPrimaryKeySelective(spu);
    }

    /**
     * 根据spuId查询Spu对象
     * @param id
     * @return
     */
    public Spu querySkuBySId(Long id) {
        Spu spu = spuMapper.selectByPrimaryKey(id);
        if (spu==null){
            throw new WlkgException(ExceptionEnums.GOODS_NOT_FOUND);
        }
        //查询sku
        spu.setSkus(querySkuBySpuId(id));
        //查询detail
        spu.setSpuDetail(querySpuDetailById(id));
        return spu;
    }

    /**
     * 增删改商品的时候发送消息给交换机
     * @param spuId
     * @param type
     */
    private void  sendMessage(Long spuId,String type){
        //发送消息
        try{
            amqpTemplate.convertAndSend("item."+type,spuId);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public Sku querySkuById(Long id) {
        // 查询sku
        Sku record = new Sku();
        record.setSpuId(id);
        List<Sku> skus = skuMapper.select(record);

        return skus.get(0);
    }
}
