package com.wlkg.service;

import com.wlkg.api.BrandAPI;
import com.wlkg.api.CategoryAPI;
import com.wlkg.api.GoodsAPI;
import com.wlkg.api.SpecificationAPI;
import com.wlkg.pojo.*;
import com.wlkg.utils.ThreadUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;


import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class PageService {

    @Autowired
    private BrandAPI brandAPI;
    @Autowired
    private CategoryAPI categoryAPI;
    @Autowired
    private GoodsAPI goodsAPI;
    @Autowired
    private SpecificationAPI specificationAPI;
    @Autowired
    private TemplateEngine templateEngine;

    @Value("${wlkg.thymeleaf.destPath}")
    private String  destPath;


    public Map<String, Object> loadModel(Long id) {
        //模型数据
        Map<String, Object> modelMap = new HashMap<>();
        //查询spu
        Spu spu = goodsAPI.querySpuById(id);
        //查询详情
        SpuDetail spuDetail = spu.getSpuDetail();
        //查询sku集合
        List<Sku> skus = spu.getSkus();
        //查询品牌数据
        Brand brand = brandAPI.queryBrandById(spu.getBrandId());
        //查询分类数据
        List<Category> categories = categoryAPI.queryCategoryByIds(Arrays.asList(spu.getCid1(), spu.getCid2(), spu.getCid3()));
        //查询规格参数
        List<SpecGroup> specGroups = specificationAPI.querySpecGroup(spu.getCid3());

        //数据填充
        modelMap.put("spu", spu);
        modelMap.put("title", spu.getTitle());
        modelMap.put("subTitle", spu.getSubTitle());
        modelMap.put("detail", spuDetail);
        modelMap.put("skus", skus);
        modelMap.put("brand", brand);
        modelMap.put("categories", categories);
        modelMap.put("specs", specGroups);
        return modelMap;
    }

    /**
     * 创建html页面
     *
     * @param spuId
     * @throws Exception
     */
    public void createHtml(Long spuId) throws Exception {
        //创建上下文
        Context context = new Context();
        context.setVariables(loadModel(spuId));
        //创建输出流,关联到一个临时文件
        File temp = new File(spuId + ".html");
        //建立目标文件
        File dest = createPath(spuId);
        //备份原页面文件
        File bak=new File(spuId+"_bak.html");
        try (PrintWriter writer=new PrintWriter(temp,"UTF-8")){
            //利用thymeleaf模板引擎生成动态页面(服务名,上下文数据,输出流)
            templateEngine.process("item",context,writer);
            if (dest.exists()){
                //如果目标文件已存在,则先备份
                dest.renameTo(bak);
            }
            //将新的页面覆盖旧页面
            FileCopyUtils.copy(temp,dest);
            bak.delete();//删除备份文件
        }catch (IOException e){
            bak.renameTo(dest);
            throw  new Exception(e);
        }finally {
            if (temp.exists()){
                temp.delete();//删除临时文件
            }
        }
    }

    private File createPath(Long spuId) {
        if (spuId==null){
            return null;
        }
        File dest=new File(destPath);
        if (!dest.exists()){
            dest.mkdirs();
        }
        return new File(dest,spuId+".html");
    }

    /**
     *
     * 判断某个商品的页面是否存在
     */
    public boolean exists(Long id){
        return createPath(id).exists();
    }

    /**
     * 异步生产html页面
     * @param id
     */
    public void syncCreateHtml(Long id){
        ThreadUtils.execute(()->{
            try {
                createHtml(id);
            }catch (Exception e){
                e.printStackTrace();
            }
        });
    }

    /**
     * 接收到删除商品的消息,根据传来的商品id删除对应的索引
     * @param id
     */
    public void deleteHtml(Long id) {
        File file = new File(this.destPath, id + ".html");
        file.deleteOnExit();
    }
}
