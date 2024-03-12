package com.posco.poscoproject.entity;

import lombok.*;

import javax.persistence.*;

@Entity
@Table(name = "item2")
@Setter
@ToString
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SampleEntity extends TimeEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private String col1;

    @Column
    private String col2;

    @Column
    private String col3;

    @Column
    private String col4;

    @Column
    private String col5;

    @Builder
    public SampleEntity(Long id, String col2, String col3, String col1, String col4, String col5) {
        this.id = id;
        this.col1 = col1;
        this.col2 = col2;
        this.col3 = col3;
        this.col4 = col4;
        this.col5 = col5;
    }
}
