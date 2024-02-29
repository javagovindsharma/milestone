package com.sap.milestone.kafka;

import com.sap.milestone.common.ConstantField;
import com.sap.milestone.model.AttendanceRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class KafkaListner {
	@KafkaListener(topics = ConstantField.Kafka_Topic, groupId = ConstantField.Kafka_Group)
	public void consume(AttendanceRecord record) {

	}
}