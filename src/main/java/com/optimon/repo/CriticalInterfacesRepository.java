
package com.optimon.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.optimon.entities.CriticalInterfaces;

public interface CriticalInterfacesRepository extends JpaRepository<CriticalInterfaces, Long> {

	@Query(value = "SELECT * from critical_interfaces where trust_id=?1 and is_deleted=false", nativeQuery = true)
	public List<CriticalInterfaces> findCriticalInterfacebyId(Integer trustId);

}
