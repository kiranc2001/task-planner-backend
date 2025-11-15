package com.taskplanner.repository;

import com.taskplanner.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    List<Task> findByUserId(Long userId);

    // PostgreSQL version: NOW() + INTERVAL '1 day'
    @Query(
            value = "SELECT t.* FROM tasks t " +
                    "WHERE t.user_id = :userId " +
                    "AND t.due_date < (NOW() + INTERVAL '1 day')",
            nativeQuery = true
    )
    List<Task> findDueSoonByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.user.id = :userId")
    Long countTotalByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(t) FROM Task t " +
            "WHERE t.user.id = :userId AND t.status = 'COMPLETED'")
    Long countCompletedByUserId(@Param("userId") Long userId);

    @Query("SELECT COUNT(t) FROM Task t " +
            "WHERE t.user.id = :userId AND t.status = 'PENDING'")
    Long countPendingByUserId(@Param("userId") Long userId);
}
