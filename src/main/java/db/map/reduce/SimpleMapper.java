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
public class SimpleMapper {

    public static final ExecutorService exService = Executors.newFixedThreadPool(70);

    public Map<String, BigDecimal> reduce(List<List<Employee>> employeeLists) {
        List<Future<Map<String, BigDecimal>>> salaries = new ArrayList<>();
        for (List<Employee> employees : employeeLists) {
            FutureTask<Map<String, BigDecimal>> mapFutureTask = new FutureTask<>(new Callable<Map<String, BigDecimal>>() {
                @Override
                public Map<String, BigDecimal> call() throws Exception {
                    return new CompanyMapperImpl().reduce(employees);
                }
            });

            exService.execute(mapFutureTask);
            salaries.add(mapFutureTask);
        }
        Map<String, BigDecimal> result = new HashMap<>();
        for (Future<Map<String, BigDecimal>> salaryByCompany : salaries) {
            Map<String, BigDecimal> stringBigDecimalMap;
            try {
                stringBigDecimalMap = salaryByCompany.get();
            } catch (InterruptedException e) {
                continue;
            } catch (ExecutionException e) {
                continue;
            }
            for (Map.Entry<String, BigDecimal> stringBigDecimalEntry : stringBigDecimalMap.entrySet()) {
                if (!result.containsKey(stringBigDecimalEntry.getKey())) {
                    result.put(stringBigDecimalEntry.getKey(), new BigDecimal(0));
                }
                result.put(stringBigDecimalEntry.getKey(), result.get(stringBigDecimalEntry.getKey()).add(stringBigDecimalEntry.getValue()));
            }
        }
        return result;
    }
}
