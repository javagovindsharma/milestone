package com.sap.milestone.repository;

import com.sap.milestone.entity.SwipeEventEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface SwipeEventRepository extends JpaRepository<SwipeEventEntity, Long> {
    Long countAllByEmployeeIdAndSwipeType(@Param("employeeId") Long employeeId, @Param("swipeType") String swipeType);

    @Query("SELECT MIN(se.enter_exit_comes) FROM SwipeEventEntity se WHERE se.employeeId = :employeeId AND se.swipeType = :swipeType")
    Integer findMinEntryExitComesValueByEmployeeIdAndSwipeType(@Param("employeeId") Long employeeId, @Param("swipeType") String swipeType);

    @Query("SELECT MAX(se.enter_exit_comes) FROM SwipeEventEntity se WHERE se.employeeId = :employeeId AND se.swipeType = :swipeType")
    Integer findMaxEntryExitComesValueByEmployeeIdAndSwipeType(@Param("employeeId") Long employeeId, @Param("swipeType") String swipeType);
    @Query("SELECT se.swipeTime FROM SwipeEventEntity se WHERE se.employeeId = :employeeId AND se.swipeType = :swipeType AND se.enter_exit_comes= :enter_exit_comes")
    LocalDateTime findSwipeTimeAndEntryExitComesByEmployeeIdAndSwipeTypeAndEntryComesTimes(
            @Param("employeeId") Long employeeId,
            @Param("swipeType") String swipeType,
            @Param("enter_exit_comes") Integer enter_exit_comes);
}
