package uz.tuit.arm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tuit.arm.entity.template.AbsEntity;


import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Attachment extends AbsEntity {

    private String name;
    private String contentType;
    private long size;
    private String path;

    public Attachment(String name, String contentType, long size, AttachmentContent attachmentContent, AttachmentType attachmentType) {
        this.name = name;
        this.contentType = contentType;
        this.size = size;
        this.attachmentContent = attachmentContent;
        this.attachmentType = attachmentType;
    }

    @OneToOne(fetch = FetchType.LAZY)
    private AttachmentContent attachmentContent;

    @OneToOne(fetch = FetchType.LAZY)
    private AttachmentType attachmentType;

}
