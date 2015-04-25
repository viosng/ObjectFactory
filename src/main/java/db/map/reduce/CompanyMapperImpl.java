package db.map.reduce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

/**
 * Created by StudentDB on 09.04.2015.
 */
public class CompanyMapperImpl implements CompanyMapper {
    @Override
    public Map<String, BigDecimal> reduce(List<Employee> employeeList) {
        Map<String, List<BigDecimal>> salaries = new HashMap<>();
        for (Employee employee : employeeList) {
            if (!salaries.containsKey(employee.getCompany())) {
                List<BigDecimal> bigDecimals = new ArrayList<>();
                salaries.put(employee.getCompany(), bigDecimals);
            }
            salaries.get(employee.getCompany()).add(employee.getSalary());
        }
        Map<String, Future<BigDecimal>> reducedMap = new HashMap<>();
        for (Map.Entry<String, List<BigDecimal>> stringListEntry : salaries.entrySet()) {
            FutureTask<BigDecimal> future = new FutureTask<BigDecimal>(new Callable<BigDecimal>() {
                @Override
                public BigDecimal call() throws Exception {
                    BigDecimal sum = new BigDecimal(0);
                    for (BigDecimal bigDecimal : stringListEntry.getValue()) {
                        sum = sum.add(bigDecimal);
                    }
                    return sum;
                }
            });
            SimpleMapper.exService.execute(future);
            reducedMap.put(stringListEntry.getKey(), future);
        }
        Map<String, BigDecimal> result = new HashMap<>();
        for (Map.Entry<String, Future<BigDecimal>> stringFutureEntry : reducedMap.entrySet()) {
            try {
                result.put(stringFutureEntry.getKey(), stringFutureEntry.getValue().get());
            } catch (InterruptedException e) {
                continue;
            } catch (ExecutionException e) {
                continue;
            }
        }
        return result;
    }
}
