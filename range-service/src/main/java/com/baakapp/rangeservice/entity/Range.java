package com.baakapp.rangeservice.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "\"range\"")
@NoArgsConstructor
@Getter
@Setter
public class Range {

    public Range(Long start, Long end) {
        this.start = start;
        this.end = end;
    }

    @Id
    private Integer id = 1;

    @Column(name="\"start\"", nullable=false)
    private Long start = 0L;

    @Column(name="\"end\"", nullable=false)
    private Long end = 0L;
}
