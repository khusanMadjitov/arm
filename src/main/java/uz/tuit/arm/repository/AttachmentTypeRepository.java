package uz.tuit.arm.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.tuit.arm.entity.AttachmentType;
import uz.tuit.arm.entity.enums.AttachmentTypeEnum;


public interface AttachmentTypeRepository extends JpaRepository<AttachmentType,Long> {
    AttachmentType findAttachmentTypeByAttachmentTypeEnum(AttachmentTypeEnum attachmentTypeEnum);
}
