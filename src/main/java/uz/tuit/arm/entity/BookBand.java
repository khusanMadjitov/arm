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
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookBand extends AbsEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    private Books books;

    @ManyToOne(fetch = FetchType.LAZY)
    private Users users;
}
