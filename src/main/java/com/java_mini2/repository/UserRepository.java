package com.java_mini2.repository;

import com.java_mini2.model.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    // Sorting by name and considering the total characters count for name is even or odd
    @Query(value = "SELECT * FROM user WHERE MOD(LENGTH(name), 2) = :sortOrder ORDER BY LENGTH(name)", nativeQuery = true)
    Page<User> findAllByNameOrder(@Param("sortOrder") int sortOrder, Pageable pageable);

    // Sorting by age and considering age is even or odd
    @Query(value = "SELECT * FROM user WHERE MOD(age, 2) = :sortOrder ORDER BY age", nativeQuery = true)
    Page<User> findAllByAgeOrder(@Param("sortOrder") int sortOrder, Pageable pageable);
}
