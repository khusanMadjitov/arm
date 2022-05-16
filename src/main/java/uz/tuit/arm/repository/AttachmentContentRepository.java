package uz.tuit.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tuit.arm.entity.AttachmentContent;


public interface AttachmentContentRepository extends JpaRepository<AttachmentContent,Long> {

}
