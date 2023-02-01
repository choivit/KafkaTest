package com.example.kafkagroovy.kafkaTest.kafkaWithPartitions.config;

import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.InvalidRecordException;
import org.apache.kafka.common.PartitionInfo;

import java.util.List;
import java.util.Map;

@Slf4j
public class CustomPartitioner implements Partitioner {

    // 파티셔닝 로직 커스텀 하는 메서드
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        log.info("topic = {}, key = {}, keyBytes = {}, value = {}, valueBytes = {}, cluster = {}" + topic, key, keyBytes, value, valueBytes, cluster);
        List<PartitionInfo> partitions = cluster.partitionsForTopic(topic);
        int numPartitions = partitions.size();
        int intKey = 0;

        if (keyBytes == null)
            throw new InvalidRecordException("Message must have a key");
        else if (!(key instanceof Integer)) {
//            throw new InvalidRecordException("A key must be instanceof Integer");
            intKey = Integer.parseInt(String.valueOf(key));
            System.out.println("key 가 왜 topic으로 나와 >> " + intKey);
        }
        return intKey % numPartitions;
    }

    // 파티셔너가 닫힐 때 호출됨.
    @Override
    public void close() {}

    @Override
    public void configure(Map<String, ?> configs) {}
}
