package com.sap.milestone.kafka;

import com.sap.milestone.common.ConstantField;
import com.sap.milestone.entity.SwipeEventEntity;
import com.sap.milestone.model.AttendanceRecord;
import com.sap.milestone.repository.SwipeEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class KafkaListner {

	private Logger logger= LoggerFactory.getLogger(KafkaListner.class);
	private final Set<Long> processedEmployeeIds = new HashSet<>();

	@Autowired
	private SwipeEventRepository swipeEventRepository;

	@KafkaListener(topics = ConstantField.Kafka_Topic, groupId = ConstantField.Kafka_Group)
	public void consume(AttendanceRecord record) {
	//	logger.info(record.toString());
		if (record.SwipeType().toUpperCase().equals(ConstantField.OUT)) {
			Optional<SwipeEventEntity> event=swipeEventRepository.findByEmployeeIdAndOfficeIn(record.employeeId(), LocalDate.now());
			if(event.isPresent()) {
				event.get().setOfficeOut(LocalDateTime.now());
				Duration totalHours = Duration.between(event.get().getOfficeIn(), LocalDateTime.now());
				event.get().setStayInOffice(totalHours.toHours());
				String officeAvailability = totalHours.toHours() <= 4 ? "Absent" : (totalHours.toHours() > 4 && totalHours.toHours() < 8) ? "Half Day" : "Present";
				event.get().setAvailablityInOffice(officeAvailability);
				logger.debug("Employee Id-"+record.employeeId()+" is exit from the Office and update in Db");
				swipeEventRepository.save(event.get());
			}
			logger.info("Record for::-> employeeId " + record.employeeId() + " is exit from the Office.");
		}

		if (processedEmployeeIds.contains(record.employeeId()) &&
		    record.SwipeType().toUpperCase().equals(ConstantField.IN)
		) {
			logger.info("Record for::-> employeeId " + record.employeeId() + " is re-enter in Office.");
		} else if( record.SwipeType().toUpperCase().equals(ConstantField.IN)) {
			Optional<SwipeEventEntity> oldevent=swipeEventRepository.findByEmployeeIdAndOfficeIn(record.employeeId(), LocalDate.now());
			if(oldevent.isEmpty()) {
				processedEmployeeIds.add(record.employeeId());
				SwipeEventEntity event = new SwipeEventEntity();
				event.setEmployeeId(record.employeeId());
				event.setOfficeIn(LocalDateTime.now());
				swipeEventRepository.save(event);
			}
			logger.info("Record for::-> employeeId " + record.employeeId() + " entered in Office.");
		}
	}

	@Scheduled(cron = "0 0 23 * * *")
	public void resetProcessedEmployeeIds() {
		processedEmployeeIds.clear();
		logger.info("Processed employeeIds set reset at 11 PM.");
	}

}