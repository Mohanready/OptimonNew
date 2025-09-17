package com.optimon.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.optimon.entities.SupportContactList;

public interface SupportContactListRepo extends JpaRepository<SupportContactList, Integer> {

	@Query(value = "SELECT * FROM support_contact_list where level=?1", nativeQuery = true)
	public List<SupportContactList> getSupportDetails(String level);

}
