package youremployees.learning;

import org.springframework.data.repository.CrudRepository;

public interface EmployeeRepository extends CrudRepository<Employee, Long> {
        Employee findByName(String name);
}
