package com.fct.clubs.repository;

import com.fct.clubs.entities.LocalizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalizacionRepository extends JpaRepository<LocalizacionEntity, Integer> {


    @Query("SELECT l FROM LocalizacionEntity l " +
            "WHERE (:provincia IS NULL OR LOWER(l.provincia) = LOWER(:provincia)) " +
            "AND (:municipio IS NULL OR LOWER(l.municipio) = LOWER(:municipio))")
    List<LocalizacionEntity> findByProvinciaAndMunicipio(
            @Param("provincia") String provincia,
            @Param("municipio") String municipio);
}

