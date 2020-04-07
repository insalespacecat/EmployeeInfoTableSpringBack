package youremployees.learning;


import lombok.*;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;


@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PRIVATE, force = true)
@Slf4j
@Entity
@Table(name = "employees")
public class Employee {
    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;
    private String name;
    private String position;
    private double salary;
    private String workplace;
}
