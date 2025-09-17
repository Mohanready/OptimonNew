package com.optimon.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.optimon.entities.Maintenance;

@Repository
public interface MaintenanceRepo extends JpaRepository<Maintenance, Integer> {

	@Query(value = "SELECT * from maintenance where trust_id=?1 and type=?2", nativeQuery = true)
	public List<Maintenance> getMaintenances(Integer trustId,String type);

}
