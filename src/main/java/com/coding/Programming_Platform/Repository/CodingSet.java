package com.coding.Programming_Platform.Repository;

import com.coding.Programming_Platform.Model.CodingSets;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CodingSet extends JpaRepository<CodingSets,Integer> {

}
