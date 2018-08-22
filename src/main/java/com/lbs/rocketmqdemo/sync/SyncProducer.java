package com.lbs.rocketmqdemo.sync;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * description: 同步传输 Provider
 *
 * @author libosheng
 * @date 2018-8-22
 */
public class SyncProducer {

    public static void main(String[] args) throws Exception {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm:SS");
        String time = format.format(new Date());
        //创建生产者
        DefaultMQProducer producer = new DefaultMQProducer("sync_group");
        producer.setNamesrvAddr("192.168.52.201:9876");
        producer.setInstanceName("sync_producer");
        //启动Provider
        producer.start();
        try {
            //创建消息，定义topic tags body
            Message msg = new Message("sync_topic", "sync_tag", ("sync message " + time).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //发送消息到broker
            SendResult sendResult = producer.send(msg);
            //打印发送信息
            System.out.println(sendResult);
            //关闭资源
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.shutdown();
        }
    }
}
