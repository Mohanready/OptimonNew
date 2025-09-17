package com.optimon.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.optimon.entities.QueueMetricDetails;

public interface QueueMetricDetailsRepository extends JpaRepository<QueueMetricDetails, Long> {

	@Query(value = "select qmd.* from queue_metric_details qmd where qmd.batch_no = (select distinct(max(batch_no)) from queue_metric_details qmd2 where qmd2.trust_id = ?1)", nativeQuery = true)
	public List<QueueMetricDetails> getQueudMetricDetails(Integer trustId);
	
	@Query(value = "select distinct (max(batch_no)) from queue_metric_details limit 1",nativeQuery = true)
	public Long getMaxBatchNumber();

}
