package com.lbs.rocketmqdemo.order;

import com.lbs.rocketmqdemo.util.TimeUtil;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.MessageQueueSelector;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageQueue;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * description: 顺序消息 Producer
 *
 * @author libosheng
 * @date 2018-8-24
 */
public class OrderProducer {

    public static void main(String[] args) throws Exception {
        int count = 100;
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("order_group");
        producer.setNamesrvAddr("192.168.52.201:9876");
        producer.setInstanceName("order_producer");
        //Launch the instance.
        producer.start();
        String[] tags = new String[] {"TagA", "TagB", "TagC", "TagD", "TagE"};
        for (int i = 0; i < count; i++) {
            int orderId = i % 10;
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("order_topic", tags[i % tags.length], "KEY" + i,
                    ("order message " + TimeUtil.getTime() + " " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            SendResult sendResult = producer.send(msg, new MessageQueueSelector() {
                @Override
                public MessageQueue select(List<MessageQueue> mqs, Message msg, Object arg) {
                    Integer id = (Integer) arg;
                    int index = id % mqs.size();
                    return mqs.get(index);
                }
            }, orderId);

            System.out.printf("%s%n", sendResult);
        }
        //server shutdown
        producer.shutdown();
    }
}
