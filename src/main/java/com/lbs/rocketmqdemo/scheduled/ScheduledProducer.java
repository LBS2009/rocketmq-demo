package com.lbs.rocketmqdemo.scheduled;

import com.lbs.rocketmqdemo.util.TimeUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

/**
 * description: 定时消息 Producer
 *
 * @author libosheng
 * @date 2018-8-24
 */
public class ScheduledProducer {

    public static void main(String[] args) throws Exception {
        // Instantiate a producer to send scheduled messages
        DefaultMQProducer producer = new DefaultMQProducer("scheduled_group");
        producer.setInstanceName("scheduled_producer");
        producer.setNamesrvAddr("192.168.52.201:9876");
        // Launch producer
        producer.start();
        for (int i = 0; i < TimeUtil.TIME_100; i++) {
            Message message = new Message("scheduled_topic", ("scheduled message " + TimeUtil.getTime() + " " + i).getBytes());
            // This message will be delivered to consumer 10 seconds later.
            message.setDelayTimeLevel(3);
            // Send the message
            producer.send(message);
        }

        // Shutdown producer after use.
        producer.shutdown();
    }
}
