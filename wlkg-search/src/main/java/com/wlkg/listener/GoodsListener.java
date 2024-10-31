package com.wlkg.listener;

import com.wlkg.client.GoodsClient;
import com.wlkg.pojo.Goods;
import com.wlkg.pojo.Spu;
import com.wlkg.repository.GoodsRepository;
import com.wlkg.service.GoodsService;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class GoodsListener {

    @Autowired
    private GoodsService goodsService;
    @Autowired
    private GoodsClient goodsClient;
    @Autowired
    private GoodsRepository goodsRepository;
    /**
     * 处理insert和update的消息
     * @param id
     * @throws Exception
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "wlkg.create.index.queue", durable = "true"),
            exchange = @Exchange(
                    value = "wlkg.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
                key = {"item.insert", "item.update"}))
    public void listenCreate(Long id) throws Exception {
        if (id == null) {
            return;
        }
        Spu spu = goodsClient.querySpuById(id);
        Goods goods = goodsService.buildGoods(spu);
        goodsRepository.save(goods);

    }

    /**
     * 处理delete的消息
     * @param id
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = "wlkg.delete.index.queue", durable = "true"),
            exchange = @Exchange(
                    value = "wlkg.item.exchange",
                    ignoreDeclarationExceptions = "true",
                    type = ExchangeTypes.TOPIC),
            key = "item.delete"))
    public void listenDelete(Long id) {
        if (id == null) {
            return;
        }
        goodsRepository.deleteById(id);

    }



}
