package com.yoyomo.domain.form.domain.repository;

import com.yoyomo.domain.form.domain.entity.Form;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;

import java.util.List;
import java.util.Optional;

public interface FormRepository extends MongoRepository<Form, String> {
    Optional<Form> findByIdAndDeletedAtIsNull(String id);

    List<Form> findAllByClubIdAndDeletedAtIsNull(String clubId);

    @Query("""
            {
                'clubId': ?0,
                'deletedAt': null,
                $or: [
                    { 'title': { $regex: ?1 } },
                    { 'description': { $regex: ?2 } }
                ]
            }
            """)
    List<Form> findAllBySearch(String clubId, String titleRegex, String descriptionRegex);
}
