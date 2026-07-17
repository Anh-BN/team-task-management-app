package com.teamtaskmanagement.repository;

import com.teamtaskmanagement.entity.ActivityLog;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;
import java.time.LocalDateTime;

public class ActivityLogSpecification {
    private ActivityLogSpecification() {}

    public static Specification<ActivityLog> filter(Integer projectId, String action, Integer userId,
                                                    Integer taskId, LocalDateTime fromDate,
                                                    LocalDateTime toDate, String search) {
        return (root, query, cb) -> {
            if (query.getResultType() != Long.class && query.getResultType() != long.class) {
                root.fetch("task", JoinType.LEFT).fetch("project", JoinType.LEFT);
                root.fetch("user", JoinType.LEFT);
                query.distinct(true);
            }
            var task = root.join("task", JoinType.LEFT);
            var predicates = cb.conjunction();
            if (projectId != null) predicates = cb.and(predicates, cb.equal(task.get("project").get("projectId"), projectId));
            if (action != null && !action.isBlank()) predicates = cb.and(predicates, cb.equal(root.get("action"), action));
            if (userId != null) predicates = cb.and(predicates, cb.equal(root.get("user").get("userId"), userId));
            if (taskId != null) predicates = cb.and(predicates, cb.equal(task.get("taskId"), taskId));
            if (fromDate != null) predicates = cb.and(predicates, cb.greaterThanOrEqualTo(root.get("createdAt"), fromDate));
            if (toDate != null) predicates = cb.and(predicates, cb.lessThanOrEqualTo(root.get("createdAt"), toDate));
            if (search != null && !search.isBlank()) {
                String like = "%" + search.toLowerCase() + "%";
                predicates = cb.and(predicates, cb.or(
                        cb.like(cb.lower(task.get("taskName")), like),
                        cb.like(cb.lower(root.get("oldValue")), like),
                        cb.like(cb.lower(root.get("newValue")), like)
                ));
            }
            return predicates;
        };
    }
}
