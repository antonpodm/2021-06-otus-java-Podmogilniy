package ru.otus;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ThreadsHomework {
    private static final Logger logger = LoggerFactory.getLogger(ThreadsHomework.class);
    private static final int MAX_THREADS = 2;

    private final int min;
    private final int max;
    private int currentValue = 0;
    private int nextId = 0;
    private Direction direction;
    private final Object object = new Object();

    public ThreadsHomework(int min, int max) {
        this(min, max, Direction.UP);
    }

    public ThreadsHomework(int min, int max, Direction direction) {
        this.min = min;
        this.max = max;
        this.direction = direction;
    }

    public static void main(String[] args) {
        ThreadsHomework homework = new ThreadsHomework(1, 10);
        for (int i = 0; i < MAX_THREADS; i++) {
            final int id = i;
            new Thread(() -> homework.action(id)).start();
        }
    }

    public void action(int id) {
        while (!Thread.currentThread().isInterrupted()) {
            synchronized (object) {
                try {
                    while (nextId != id) {
                        object.wait();
                    }
                    updateValue();
                    updateNextId();
                    logger.info("{}", currentValue);
                    sleep();
                    object.notifyAll();
                } catch (InterruptedException ex) {
                    logger.error(ex.getMessage());
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    private void updateValue() {
        if (nextId == 0) {
            if (direction.equals(Direction.UP)) {
                currentValue++;
                if (currentValue == max) {
                    direction = Direction.DOWN;
                }
            } else {
                currentValue--;
                if (currentValue == min) {
                    direction = Direction.UP;
                }
            }
        }
    }

    private void updateNextId() {
        nextId++;
        if (nextId == MAX_THREADS) {
            nextId = 0;
        }
    }

    private static void sleep() {
        try {
            Thread.sleep(1_000);
        } catch (InterruptedException ex) {
            logger.error(ex.getMessage());
            Thread.currentThread().interrupt();
        }
    }

}
