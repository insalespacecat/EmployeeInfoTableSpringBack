package youremployees.learning;

import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.Optional;

@RestController
@RequestMapping("/query")
@Slf4j
@CrossOrigin(origins = "*")
public class EmployeeQueryController {

    private EmployeeRepository employeeRepository;
    EmployeeQueryController(EmployeeRepository employeeRepository){
        this.employeeRepository = employeeRepository;
    }

    @GetMapping(path = "/getEmployees", produces="application/json")
    @ResponseStatus(HttpStatus.OK)
    public ArrayList<Employee> getEmployees() {
        Iterable<Employee> emps = employeeRepository.findAll();
        ArrayList<Employee> employees = new ArrayList<Employee>();
        log.info("Get query");
        emps.forEach(employees::add);
        log.info(employees.toString());
        return employees;
    }
    @PostMapping(path = "/addEmployee", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee addEmployee(@RequestBody Employee employee){
        employee.setId(null);
        log.info("Post request");
        log.info(employee.toString());
        return employeeRepository.save(employee);
    }
    @PatchMapping(path="/patch/{EmployeeId}", consumes="application/json")
    public ResponseEntity<Employee> patchEmployee(@PathVariable("EmployeeId") Long employeeId, @RequestBody Employee patch) {
        Optional<Employee> employeeOpt = employeeRepository.findById(employeeId);
        if(employeeOpt.isEmpty()) {
            return new ResponseEntity<Employee>(patch, HttpStatus.NOT_FOUND);
        }
        Employee employeeToPatch = employeeOpt.get();
        if(patch.getName() != null){
            employeeToPatch.setName(patch.getName());
        }
        if(patch.getPosition() != null){
            employeeToPatch.setPosition(patch.getPosition());

        }
        if(patch.getSalary() > 0){
            employeeToPatch.setSalary(patch.getSalary());
        }
        if(patch.getWorkplace() != null){
            employeeToPatch.setWorkplace(patch.getWorkplace());
        }
        employeeRepository.save(employeeToPatch);
        return new ResponseEntity<Employee>(employeeToPatch, HttpStatus.ACCEPTED);
    }
    @DeleteMapping(path = "/delete/{employeeId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteOrder(@PathVariable("employeeId") Long employeeId) {
        try {
            employeeRepository.deleteById(employeeId);
        } catch (EmptyResultDataAccessException ignored) {}
    }
}
