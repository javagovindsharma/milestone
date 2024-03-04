package com.sap.milestone.service;

import com.sap.milestone.common.ConstantField;
import com.sap.milestone.entity.SwipeEventEntity;
import com.sap.milestone.kafka.KafkaListner;
import com.sap.milestone.kafka.KafkaProducer;
import com.sap.milestone.model.AttendanceRecord;
import com.sap.milestone.model.SwipeEvent;
import com.sap.milestone.repository.SwipeEventRepository;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class AttendanceService {

    @Autowired
    private SwipeEventRepository swipeEventRepository;
    @Autowired
    private KafkaProducer kafkaProducer;
    private Logger logger= LoggerFactory.getLogger(AttendanceService.class);
    public void processSwipeEvent(SwipeEvent swipeEvent) {
        LocalDateTime officeTimeIn,officeTimeOut;
        String swipeType;
        if(swipeEvent.swipeType().toUpperCase().equals(ConstantField.IN)){
            officeTimeIn=LocalDateTime.now();
            officeTimeOut=null;
            swipeType=ConstantField.IN;
        }else{
            officeTimeIn=null;
            officeTimeOut=LocalDateTime.now();
            swipeType=ConstantField.OUT;
        }
        AttendanceRecord attendanceRecord=new AttendanceRecord(swipeEvent.employeeId(),swipeType,officeTimeIn,officeTimeOut);
        kafkaProducer.produce(attendanceRecord);
        logger.info("Send message to Kafka Producer");
    }

    public String getTotalHoursInOffice(Long employeeId) {
        Optional<SwipeEventEntity> event=swipeEventRepository.findByEmployeeIdAndOfficeIn(employeeId, LocalDate.now());
        return  event.isPresent()?"Employee Id- "+employeeId+  "Office Coming Time -"+event.get().getOfficeIn()+"  Last Out Office Time - "+event.get().getOfficeOut()+"  Total Hour Spent in office -"+event.get().getStayInOffice()+"  Office Availability -"+event.get().getAvailablityInOffice():"Employee Id "+employeeId+" today Absent";
    }


}
