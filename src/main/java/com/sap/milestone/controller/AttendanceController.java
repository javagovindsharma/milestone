package com.sap.milestone.controller;

import com.sap.milestone.model.SwipeEvent;
import com.sap.milestone.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@RestController
@RequestMapping("/attendance")
public class AttendanceController {

    @Autowired
    private AttendanceService attendanceService;

    @PostMapping("/swipe1")
    public ResponseEntity<String> handleSwipeEvent1() {
        System.out.println("hi");
        return ResponseEntity.ok("Swipe event processed successfully.");
    }

    @PostMapping("/swipe")
    public ResponseEntity<String> handleSwipeEvent(@RequestBody SwipeEvent swipeEvent) {
        System.out.println("hi");
        attendanceService.processSwipeEvent(swipeEvent);
        return ResponseEntity.ok("Swipe  " + swipeEvent.swipeType() + "  event processed successfully.");
    }

    @GetMapping("/totalHours/{employeeId}")
    public ResponseEntity<String> getTotalHoursInOffice(@PathVariable("employeeId") Long employeeId) {
        String totalHours = attendanceService.getTotalHoursInOffice(employeeId);
        return ResponseEntity.ok("Total hours in office for employee " + employeeId + ": " + totalHours);
    }
}
