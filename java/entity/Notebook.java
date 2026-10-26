package entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Notebooks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Notebook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String manufacturerName;

    @Column(nullable = false)
    private String codeOrTitle;

    @Column(nullable = false)
    private Integer pageCount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CoverType coverType;

    @Column(nullable = false)
    private String country;

    @Column(nullable = false)
    private Long circulation;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PageStyleType pageStyle;
}