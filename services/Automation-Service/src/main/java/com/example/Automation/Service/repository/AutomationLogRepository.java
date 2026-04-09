package com.example.Automation.Service.repository;

import com.example.Automation.Service.model.AutomationLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AutomationLogRepository extends JpaRepository<AutomationLog, Long> {
    List<AutomationLog> findAllByOrderByTriggeredAtDesc();

}
