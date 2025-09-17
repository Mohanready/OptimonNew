package com.optimon.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.optimon.entities.CriticalInboundReceivers;

@Repository
public interface CriticalInboundReceiverRepo extends JpaRepository<CriticalInboundReceivers, Integer> {

	@Query(value = "SELECT * FROM critical_inbound_receivers where trust_id=?1 and is_deleted=false", nativeQuery = true)
	public List<CriticalInboundReceivers> findReceiversById(Integer trustId);

}
