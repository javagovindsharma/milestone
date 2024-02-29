package com.sap.milestone.model;

import java.time.LocalDateTime;


public record AttendanceRecord(Long employeeId, LocalDateTime officeInTime, LocalDateTime officeOutTime) {
}
