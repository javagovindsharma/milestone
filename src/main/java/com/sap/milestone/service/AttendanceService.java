package com.sap.milestone.service;

import com.sap.milestone.common.ConstantField;
import com.sap.milestone.entity.SwipeEventEntity;
import com.sap.milestone.kafka.KafkaProducer;
import com.sap.milestone.model.SwipeEvent;
import com.sap.milestone.repository.SwipeEventRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.Duration;
import java.time.LocalDateTime;

@Service
public class AttendanceService {

    @Autowired
    private SwipeEventRepository swipeEventRepository;
    @Autowired
    private KafkaProducer kafkaProducer;

    public void processSwipeEvent(SwipeEvent swipeEvent) {
        SwipeEventEntity event = new SwipeEventEntity();
        event.setEmployeeId(swipeEvent.employeeId());
        event.setSwipeType(swipeEvent.swipeType());
        event.setSwipeTime(LocalDateTime.now());
        Long count = swipeEventRepository.countAllByEmployeeIdAndSwipeType(swipeEvent.employeeId().longValue(), swipeEvent.swipeType().toUpperCase());
        event.setEnter_exit_comes(count + 1);
        swipeEventRepository.save(event);
    }

    public String getTotalHoursInOffice(Long employeeId) {

        Integer InTimes = swipeEventRepository.findMinEntryExitComesValueByEmployeeIdAndSwipeType(employeeId, ConstantField.IN);
        LocalDateTime officeEntryTime = swipeEventRepository.findSwipeTimeAndEntryExitComesByEmployeeIdAndSwipeTypeAndEntryComesTimes(employeeId, ConstantField.IN, InTimes);
        Integer OutTimes = swipeEventRepository.findMaxEntryExitComesValueByEmployeeIdAndSwipeType(employeeId, ConstantField.OUT);
        LocalDateTime officeExitTime = swipeEventRepository.findSwipeTimeAndEntryExitComesByEmployeeIdAndSwipeTypeAndEntryComesTimes(employeeId, ConstantField.OUT, OutTimes);
        System.out.println("Office In " + officeEntryTime);
        System.out.println("Office Out " + officeExitTime);

        Duration totalHours = Duration.between(officeEntryTime, officeExitTime);

        String result = totalHours.toHours() + "H : " + totalHours.toMinutesPart() + "M : " + totalHours.toSecondsPart() + "S";
        String officeAvailability = totalHours.toHours() <= 4 ? "Absent" : (totalHours.toHours() > 4 && totalHours.toHours() < 8) ? "Half Day" : "Present";
        String message = "Employee Id : " + employeeId + " Office coming Time is : " + officeEntryTime + "  Office Out Time is : " + officeExitTime + "Total Stay In Office Time is : " + result + "  office Availability :" + officeAvailability;
     //   kafkaProducer.s(message);
        return result;
    }

}
