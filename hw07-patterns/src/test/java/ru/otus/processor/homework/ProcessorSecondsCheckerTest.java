package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThatThrownBy;

class ProcessorSecondsCheckerTest {
    @Test
    @DisplayName("Тестируем возникновение исключения на чётной секунде")
    void shouldThrowRuntimeExceptionOnEvenSecond() {
        var field1 = "field1";
        var message = new Message.Builder(1L)
                .field1(field1)
                .build();

        Time evenSecond = () -> Instant.parse("2021-10-06T12:00:00.00Z");

        Processor processor = new ProcessorSecondsChecker(evenSecond);

        assertThatThrownBy(() -> processor.process(message)).isInstanceOf(RuntimeException.class);
    }

    @Test
    @DisplayName("Тестируем отсутствие исключения на нечётной секунде")
    void shouldNotThrowRuntimeExceptionOnOddSecond() {
        var field1 = "field1";
        var message = new Message.Builder(1L)
                .field1(field1)
                .build();

        Time evenSecond = () -> Instant.parse("2021-10-06T12:00:01.00Z");

        Processor processor = new ProcessorSecondsChecker(evenSecond);

        processor.process(message);


    }
}