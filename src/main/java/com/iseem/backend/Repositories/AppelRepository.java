package com.iseem.backend.Repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.iseem.backend.Entities.Appel;
import com.iseem.backend.Entities.User;
import java.util.List;
import java.time.LocalDateTime;

@Repository
public interface AppelRepository extends JpaRepository<Appel, Integer>{
    List<Appel> findAllByOrderByDateDesc();
    
    Page<Appel> findByUserAndDateBetweenOrderByDateDesc(User user, LocalDateTime startOfPeriod, LocalDateTime endOfPeriod, Pageable pageable);
}
