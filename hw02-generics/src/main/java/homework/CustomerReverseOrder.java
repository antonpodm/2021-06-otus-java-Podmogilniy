package homework;


import java.util.Stack;

public class CustomerReverseOrder {

    Stack<Customer> data = new Stack<>();
    //todo: 2. надо реализовать методы этого класса
    //надо подобрать подходящую структуру данных, тогда решение будет в "две строчки"

    public void add(Customer customer) {
        data.push(customer);
    }

    public Customer take() {
        if (!data.isEmpty()) {
            return data.pop();
        }
        return null; // это "заглушка, чтобы скомилировать"
    }
}
