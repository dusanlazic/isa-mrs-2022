package com.team4.isamrs.repository;

import com.team4.isamrs.model.entity.advertisement.Photo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PhotoRepository extends JpaRepository<Photo, UUID> {

}
