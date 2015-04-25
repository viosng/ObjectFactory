package db.map.reduce;

import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by StudentDB on 09.04.2015.
 */
public class EmployeeRepository {

    public EmployeeRepository() {
        List<List<Employee>> employeesBlocks = new ArrayList<>();
        employeesBlocks.add(Arrays.asList(new Employee("Boris", "Google", new BigDecimal(10000)), new Employee("Nadya", "Deutsche Bank", new BigDecimal(30000))));
        employeesBlocks.add(Arrays.asList(new Employee("Rodik", "Google", new BigDecimal(10000)), new Employee("Alena", "Deutsche Bank", new BigDecimal(30000)), new Employee("Denis", "Microsoft", new BigDecimal(10000))));
        employeesBlocks.add(Arrays.asList(new Employee("Sergey", "Google", new BigDecimal(10000)), new Employee("Nikolay", "Deutsche Bank", new BigDecimal(30000))));
        employeesBlocks.add(Arrays.asList(new Employee("Pasha", "Microsoft", new BigDecimal(30000))));
        Extractor<String, Employee> stringEmployeeExtractor = new Extractor<String, Employee>() {
            @Override
            public String extract(Employee employee) {
                return employee.getCompany();
            }
        };
        Extractor<BigDecimal, Employee> decimalEmployeeExtractor = new Extractor<BigDecimal, Employee>() {
            @Override
            public BigDecimal extract(Employee employee) {
                return employee.getSalary();
            }
        };
        Iterable<Employee> employees = employeesBlocks.get(0);
        Map<String, Iterable<Employee>> map = new MapperImpl<String, Employee, Extractor<String, Employee>>()
                .map(employees, stringEmployeeExtractor);
        System.out.println(map);
        Reducer<BigDecimal> sumReducer = new Reducer<BigDecimal>() {
            @Override
            public BigDecimal reduce(BigDecimal e1, BigDecimal e2) {
                return e1.add(e2);
            }

            @Override
            public BigDecimal getDefault() {
                return new BigDecimal(0);
            }
        };
        System.out.println(new BulkReducerImpl<String, Employee, BigDecimal, Reducer<BigDecimal>,
                Extractor<BigDecimal, Employee>>().reduce(map, sumReducer, decimalEmployeeExtractor));

        SimpleMapper simpleMapper = new SimpleMapper();
        List<List<Employee>> lists = createLists(50);
        long t = System.currentTimeMillis();
        System.out.println(process(lists));
        System.out.println(System.currentTimeMillis() - t);
        t = System.currentTimeMillis();
        System.out.println(simpleMapper.reduce(lists));
        System.out.println(System.currentTimeMillis() - t);
        SimpleMapper.exService.shutdown();
    }

    private final static List<String> COMPANIES = Arrays.asList("Google", "Microsoft", "Deutsche Bank");
    private static final Random RANDOM = new Random();

    private static Employee createEmployee() {
        return new Employee("a", COMPANIES.get(RANDOM.nextInt(COMPANIES.size())), new BigDecimal(RANDOM.nextInt(100000)));
    }


    public static void run(List<List<Employee>> lists) {
        new SimpleMapper().reduce(lists);
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureName() {
        run(createLists(10));
    }

    public static List<List<Employee>> createLists(int size) {
        List<List<Employee>> lists = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            List<Employee> list = new ArrayList<>();
            for (int j = 0; j < size * 1000; j++) {
                list.add(createEmployee());
            }
            lists.add(list);
        }
        return lists;
    }

    private static Map<String, BigDecimal> process(List<List<Employee>> lists) {
        Map<String, BigDecimal> res = new HashMap<>();
        for (List<Employee> list : lists) {
            for (Employee employee : list) {
                if (!res.containsKey(employee.getCompany())) {
                    res.put(employee.getCompany(), new BigDecimal(0));
                }
                res.put(employee.getCompany(), res.get(employee.getCompany()).add(employee.getSalary()));
            }
        }
        return res;
    }

    public static void main(String[] args) {
        EmployeeRepository employeeRepository = new EmployeeRepository();
    }
}
