package com.lbs.rocketmqdemo.oneway;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * description: 单向传输 Producer
 *
 * @author libosheng
 * @date 2018-8-22
 */
public class OnewayProducer {

    public static void main(String[] args) throws Exception{
        int count = 100;
        //Instantiate with a producer group name.
        DefaultMQProducer producer = new DefaultMQProducer("oneway_group");
        producer.setNamesrvAddr("192.168.52.201:9876");
        producer.setInstanceName("oneway_producer");
        //Launch the instance.
        producer.start();
        for (int i = 0; i < count; i++) {
            //Create a message instance, specifying topic, tag and message body.
            Message msg = new Message("oneway_topic", "oneway_tag", ("oneway message " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            //Call send message to deliver message to one of brokers.
            producer.sendOneway(msg);

        }
        //Shut down once the producer instance is not longer in use.
        producer.shutdown();
    }
}
