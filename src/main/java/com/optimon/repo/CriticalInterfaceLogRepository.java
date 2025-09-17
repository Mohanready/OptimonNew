package com.optimon.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.optimon.entities.CriticalInterfacesLog;

public interface CriticalInterfaceLogRepository extends JpaRepository<CriticalInterfacesLog, Long> {

	@Query(value = "SELECT * FROM critical_interfaces_log where endpoint_name=?1 and is_deleted=false limit 1", nativeQuery = true)
	public CriticalInterfacesLog getCiLogDetailsByEndpointName(String endpointName);

	@Query(value = "SELECT * FROM critical_interfaces_log where is_deleted=false", nativeQuery = true)
	public List<CriticalInterfacesLog> getActiveLogRecords();

}
