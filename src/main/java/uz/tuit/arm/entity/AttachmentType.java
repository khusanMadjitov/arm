package uz.tuit.arm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tuit.arm.entity.enums.AttachmentTypeEnum;
import uz.tuit.arm.entity.template.AbsEntity;


import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class AttachmentType extends AbsEntity {

    private String contentTypes;
    private int width;
    private int height;

    @Enumerated(EnumType.STRING)
    private AttachmentTypeEnum attachmentTypeEnum;

}
