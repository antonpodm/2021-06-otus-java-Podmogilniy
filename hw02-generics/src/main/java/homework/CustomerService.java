package homework;


import java.util.AbstractMap;
import java.util.Map;
import java.util.TreeMap;

public class CustomerService {

    TreeMap<Customer, String> data = new TreeMap<>();
    //todo: 3. надо реализовать методы этого класса
    //важно подобрать подходящую Map-у, посмотрите на редко используемые методы, они тут полезны

    public Map.Entry<Customer, String> getSmallest() {
        return clone(data.firstEntry());
    }

    public Map.Entry<Customer, String> getNext(Customer customer) {
        return clone(data.higherEntry(customer));
    }

    public void add(Customer customer, String data) {
        this.data.put(customer, data);
    }

    private Map.Entry<Customer, String> clone(Map.Entry<Customer, String> entryForCopy) {
        return entryForCopy == null ?
                null :
                new AbstractMap.SimpleEntry((entryForCopy.getKey()).clone(), entryForCopy.getValue());
    }
}
