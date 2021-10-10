package ru.otus.processor.homework;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import ru.otus.model.Message;
import ru.otus.processor.Processor;

import static org.assertj.core.api.Assertions.assertThat;

class ProcessorField11Field12ValueChangerTest {

    @Test
    @DisplayName("Тестируем смену значений полей 11 и 12")
    void shouldSwapFieldsValues() {
        var field11 = "field11";
        var field12 = "field12";
        var message = new Message.Builder(1L)
                .field11(field11)
                .field12(field12)
                .build();

        Processor processor = Mockito.spy(ProcessorField11Field12ValueChanger.class);

        assertThat(message.getField11()).isEqualTo(field11);
        assertThat(message.getField12()).isEqualTo(field12);

        var updatedMessage = processor.process(message);

        assertThat(updatedMessage.getField11()).isEqualTo(field12);
        assertThat(updatedMessage.getField12()).isEqualTo(field11);
    }
    @Test
    @DisplayName("исходное сообщение не должно изменяться")
    void shouldNotChangeDefaultMessage() {
        var field11 = "field11";
        var field12 = "field12";
        var message = new Message.Builder(1L)
                .field11(field11)
                .field12(field12)
                .build();

        Processor processor = Mockito.spy(ProcessorField11Field12ValueChanger.class);

        assertThat(message.getField11()).isEqualTo(field11);
        assertThat(message.getField12()).isEqualTo(field12);

        var updatedMessage = processor.process(message);

        assertThat(message.getField11()).isEqualTo(field11);
        assertThat(message.getField12()).isEqualTo(field12);
    }
}