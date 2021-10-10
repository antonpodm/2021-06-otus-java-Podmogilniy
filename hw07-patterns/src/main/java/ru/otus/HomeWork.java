package ru.otus;

import ru.otus.handler.ComplexProcessor;
import ru.otus.listener.homework.HistoryListener;
import ru.otus.model.Message;
import ru.otus.processor.homework.ProcessorField11Field12ValueChanger;
import ru.otus.processor.homework.ProcessorSecondsChecker;

import java.util.List;

public class HomeWork {

    /*
     Реализовать to do:
       1. Добавить поля field11 - field13 (для field13 используйте класс ObjectForMessage)
       2. Сделать процессор, который поменяет местами значения field11 и field12
       3. Сделать процессор, который будет выбрасывать исключение в четную секунду (сделайте тест с гарантированным результатом)
            Секунда должна определяться во время выполнения.
       4. Сделать Listener для ведения истории: старое сообщение - новое (подумайте, как сделать, чтобы сообщения не портились)
     */

    public static void main(String[] args) {
        var processors = List.of(new ProcessorField11Field12ValueChanger(),
                new ProcessorSecondsChecker());

        var complexProcessor = new ComplexProcessor(processors, ex -> {
        });
        var historyListener = new HistoryListener();
        complexProcessor.addListener(historyListener);

        var message = new Message.Builder(1L)
                .field10("field10")
                .field11("field11")
                .field12("field12")
                .build();

        var result = complexProcessor.handle(message);
        System.out.println("result: " + result);
        var message2 = new Message.Builder(2L)
                .field1("field1")
                .field3("field3")
                .field12("field12")
                .build();


        result = complexProcessor.handle(message2);
        System.out.println("result2: " + result);
        System.out.println("-------------------");
        System.out.println(historyListener.findMessageById(1));
        System.out.println(historyListener.findMessageById(2));

        complexProcessor.removeListener(historyListener);
    }
}
