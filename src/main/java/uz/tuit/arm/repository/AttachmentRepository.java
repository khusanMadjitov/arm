package uz.tuit.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.tuit.arm.entity.Attachment;


import java.util.List;

public interface AttachmentRepository extends JpaRepository<Attachment,Long> {
    @Query(value = "select sa.attachment_self_id as id from self_employment_attachment_self sa where self_employment_id = :id", nativeQuery = true)
    List<Long> getAttachmentIds(@Param("id") Long id);
}
