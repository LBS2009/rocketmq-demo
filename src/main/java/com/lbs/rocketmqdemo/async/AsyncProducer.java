package com.lbs.rocketmqdemo.async;

import com.lbs.rocketmqdemo.util.TimeUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * description: 异步传输 Provider
 *
 * @author libosheng
 * @date 2018-8-22
 */
public class AsyncProducer {

    public static void main(String[] args) throws Exception {
        int count = 100;
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("async_group");
        producer.setNamesrvAddr("192.168.52.201:9876");
        producer.setInstanceName("async_producer");
        producer.setVipChannelEnabled(false);
        producer.start();
        //设置异步发送失败重试次数
        producer.setRetryTimesWhenSendAsyncFailed(10);
        for (int i = 0; i < count; i++) {
            final int index = i;
            //创建消息
            Message msg = new Message("async_topic", "async_tag", "async_id_001", ("async message " + TimeUtil.getTime() + " " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            producer.send(msg, new SendCallback() {
                @Override
                public void onSuccess(SendResult sendResult) {
                    System.out.printf("%-10d OK %s %n", index, sendResult.getMsgId());
                }
                @Override
                public void onException(Throwable e) {
                    System.out.printf("%-10d Exception %s %n", index, e);
                    e.printStackTrace();
                }
            });
        }
        //线程阻塞等待异步消息完成
        Thread.sleep(3000);
        //立即关闭会报错 MQClientException: No route info of this topic, async_topic
        producer.shutdown();
    }
}
