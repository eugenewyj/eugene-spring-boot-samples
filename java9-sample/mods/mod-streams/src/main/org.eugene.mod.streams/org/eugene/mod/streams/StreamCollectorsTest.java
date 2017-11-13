package org.eugene.mod.streams;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class StreamCollectorsTest {
    public static void main(String[] args) {
        System.out.println("测试Collectors.filtering():");
        testFiltering();
        System.out.println("\n测试Collectors.flatMapping():");
        testFlatMapping();
    }

    private static void testFlatMapping() {
        Map<String, Set<List<String>>> langByDept = Employee.employees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.mapping(Employee::getSpokenLanguages, Collectors.toSet())));
        System.out.println("整个部门所有员工所说的语言：");
        System.out.println(langByDept);
        Map<String, Set<String>> langByDept2 = Employee.employees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.flatMapping(e -> e.getSpokenLanguages().stream(), Collectors.toSet())));
        System.out.println("整个部门所有员工所说的语言，通过flapMapping（）获取：");
        System.out.println(langByDept2);
    }

    private static void testFiltering() {
        Map<String, List<Employee>> empGroupByDept = Employee.employees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.toList()));
        System.out.println("雇员按照部门分组结果：");
        System.out.println(empGroupByDept);

        Map<String, List<Employee>> empSalaryGt1900GroupByDept = Employee.employees()
                .stream()
                .filter(employee -> employee.getSalary() > 1900)
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.toList()));
        System.out.println("工资大于1900员工按照部门分组：");
        System.out.println(empSalaryGt1900GroupByDept);

        Map<String, List<Employee>> empGroupedByDeptWithSalaryGt1900 = Employee.employees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.filtering(e -> e.getSalary() > 1900.00, Collectors.toList())));
        System.out.println("按照部门分分组，并且工资大于1900：");
        System.out.println(empGroupedByDeptWithSalaryGt1900);

        Map<String, List<Employee>> empByDeptWith2LangWithEn = Employee.employees()
                .stream()
                .collect(Collectors.groupingBy(Employee::getDepartment, Collectors.filtering(e -> e.getSpokenLanguages().size() >= 2
                        && e.getSpokenLanguages().contains("English"), Collectors.toList())));
        System.out.println("雇员按照部门分组，说两种语言，并且一种是英语：");
        System.out.println(empByDeptWith2LangWithEn);
    }
}
