package com.ehtasham.springbootkeycloack.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.ehtasham.springbootkeycloack.model.HitsModel;

@Repository
public interface HitsRepo extends JpaRepository<HitsModel, Long> {

	public static final String FINDRecordForProcessing = "select * from Hits";

	@Query(value = FINDRecordForProcessing, nativeQuery = true)

	public List<HitsModel> findRecordinFROMHits();

	public static final String FINDRecordForProcessingBYID = "select * from Hits where id=?1";

	@Query(value = FINDRecordForProcessingBYID, nativeQuery = true)

	public List<HitsModel> findRecordinFROMHitsByid(Long id);

}
