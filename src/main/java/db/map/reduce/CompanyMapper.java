package db.map.reduce;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Created by StudentDB on 09.04.2015.
 */
public interface CompanyMapper {
    Map<String, BigDecimal> reduce(List<Employee> employeeList);
}
