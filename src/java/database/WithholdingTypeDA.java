package database;

import domain.WithholdingType;

import java.util.ArrayList;

public class WithholdingTypeDA {
    private static ArrayList<WithholdingType> withholdingTypes = new ArrayList<WithholdingType>(5);
    
    public static void add(WithholdingType wt) {
        withholdingTypes.add(wt);
    }

    public static ArrayList<WithholdingType> getWithholdingTypes() {
        return withholdingTypes;
    }
    
    public static void initialize() {
        WithholdingType w;
        
        w = new WithholdingType();
        w.setID(1);
        w.setDescription("Income Tax");
        w.setAmount(0.0);
        w.setRate(10.0);
        w.add();
        
        w = new WithholdingType();
        w.setID(2);
        w.setDescription("Health Insurance");
        w.setAmount(258.25);
        w.setRate(0.0);
        w.add();
    }
}