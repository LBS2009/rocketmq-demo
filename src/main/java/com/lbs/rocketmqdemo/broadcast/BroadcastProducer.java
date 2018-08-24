package com.lbs.rocketmqdemo.broadcast;

import com.lbs.rocketmqdemo.util.TimeUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import static com.lbs.rocketmqdemo.util.TimeUtil.TIME_100;

/**
 * description: 广播消息 Producer
 *
 * @author libosheng
 * @date 2018-8-24
 */
public class BroadcastProducer {

    public static void main(String[] args) throws Exception {
        DefaultMQProducer producer = new DefaultMQProducer("broadcast_group");
        producer.setNamesrvAddr("192.168.52.201:9876");
        producer.setInstanceName("broadcast_producer");
        producer.start();

        for (int i = 0; i < TIME_100; i++) {
            Message msg = new Message("broadcast_topic", "TagA", "broadcast_key",
                    ("broadcast message " + TimeUtil.getTime()).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg);
            System.out.printf("%s%n", sendResult);
        }
        producer.shutdown();
    }
}
