package org.example.springbatch.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;
import java.time.Instant;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Data
@Entity
@Table(name = "BATCH")
public class BatchEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String status;
    private String createdBy;
    private Instant createdDate = Instant.now();
    private String modifiedBy;
    private Instant modifiedDate = Instant.now();

    @Lob
    @Basic(fetch = FetchType.LAZY)
    private String additions;
}
