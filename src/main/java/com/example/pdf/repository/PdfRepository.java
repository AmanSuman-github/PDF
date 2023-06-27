package com.example.pdf.repository;

import com.example.pdf.entity.PdfEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PdfRepository extends JpaRepository<PdfEntity, Long> {
    @Override
    <S extends PdfEntity> S save(S entity);

    @Override
    Optional<PdfEntity> findById(Long aLong);

    @Override
    void deleteById(Long aLong);

    @Override
    void deleteAll(Iterable<? extends PdfEntity> entities);
}
