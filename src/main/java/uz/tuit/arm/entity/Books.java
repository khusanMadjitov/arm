package uz.tuit.arm.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.tuit.arm.entity.template.AbsEntity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Books extends AbsEntity {
    private String name;
    private String author;
    private Boolean type;
    private Long count;

    @ManyToOne(fetch = FetchType.LAZY)
    private Category category;
}
