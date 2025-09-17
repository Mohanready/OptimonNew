package com.optimon.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.optimon.entities.InboundMetricDetails;

@Repository
public interface InboundDetailsRepository extends JpaRepository<InboundMetricDetails, Long> {
	
	@Query(value = "select imd.* from inbound_metric_details imd where imd.batch_no = (select distinct(max(batch_no)) from inbound_metric_details imd2 where imd2.trust_id = ?1) order by cast(imd.time_delay as INTEGER) desc", nativeQuery = true)
	public List<InboundMetricDetails> getInboundDetails(Integer id);
	
	@Query(value = "select distinct (max(batch_no)) from inbound_metric_details limit 1",nativeQuery = true)
	public Long getMaxBatchNumber();

}
