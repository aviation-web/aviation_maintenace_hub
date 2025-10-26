package com.aeromaintenance.DispatchReport;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface DispatchReportRepository extends JpaRepository<DispatchReport, Long> {
    // Fetch the latest reportNo to find the next sequence
    @Query(value = "SELECT report_no FROM dispatch_reports ORDER BY id DESC LIMIT 1", nativeQuery = true)
    String findLatestReportNo();
}
