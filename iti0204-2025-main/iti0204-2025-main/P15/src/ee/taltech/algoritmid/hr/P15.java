package ee.taltech.algoritmid.hr;

import java.util.HashMap;
import java.util.Map;

public class P15 {

    public enum Department {
        CUSTOMER_SUPPORT, RESEARCH_AND_DEVELOPMENT, UNKNOWN;
    }

    private final DisjointSubsets disjointSubsets = new DisjointSubsets();
    private final Map<String, Department> departmentMap = new HashMap<>();

    private void ensureExists(String name) {
        try {
            disjointSubsets.find(name);
        } catch (IllegalArgumentException e) {
            disjointSubsets.addSubset(name);
        }

        if ("Priit".equals(name)) {
            departmentMap.put(name, Department.CUSTOMER_SUPPORT);
        } else if ("Liis".equals(name)) {
            departmentMap.put(name, Department.RESEARCH_AND_DEVELOPMENT);
        }
    }

    public P15() {
        // don't remove
    }

    public DisjointSubsets getDisjointSubsets() {
        return disjointSubsets;
    }

    public void talkedToEachOther(String name1, String name2) {
        ensureExists(name1);
        ensureExists(name2);

        String r1 = disjointSubsets.find(name1);
        String r2 = disjointSubsets.find(name2);

        Department dep = departmentMap.get(r1);
        if (dep == null) dep = departmentMap.get(r2);

        String newRoot = disjointSubsets.union(name1, name2);

        if (dep != null)
            departmentMap.put(newRoot, dep);
    }

    public void addPerson(String name) {
        ensureExists(name);
    }

    public void setCustomerSupport(String name) {
        ensureExists(name);
        departmentMap.put(disjointSubsets.find(name), Department.CUSTOMER_SUPPORT);
    }

    public void setResearchAndDevelopment(String name) {
        ensureExists(name);
        departmentMap.put(disjointSubsets.find(name), Department.RESEARCH_AND_DEVELOPMENT);
    }

    public Department getDepartment(String name) {
        ensureExists(name);
        return departmentMap.getOrDefault(disjointSubsets.find(name), Department.UNKNOWN);
    }
}