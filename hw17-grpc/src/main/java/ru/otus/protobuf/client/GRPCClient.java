package ru.otus.protobuf.client;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.StreamObserver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.otus.protobuf.generated.ClientRequest;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.ServerResponse;

import java.util.concurrent.CountDownLatch;

public class GRPCClient {

    private static final Logger logger = LoggerFactory.getLogger(GRPCClient.class);

    private static final String SERVER_HOST = "localhost";
    private static final int SERVER_PORT = 8080;
    private static final long CLIENT_COUNTER_VALUE = 50;
    private static final long SERVER_COUNTER_FROM = 1;
    private static final long SERVER_COUNTER_TO = 30;

    private static final Object object = new Object();
    private static long lastValue = 0;

    public static void main(String[] args) throws InterruptedException {
        ManagedChannel channel = ManagedChannelBuilder.forAddress(SERVER_HOST, SERVER_PORT)
                .usePlaintext()
                .build();

        RemoteServiceGrpc.RemoteServiceStub newStub = RemoteServiceGrpc.newStub(channel);

        CountDownLatch latch = new CountDownLatch(1);

        var clientRequest = ClientRequest
                .newBuilder()
                .setFirstValue(SERVER_COUNTER_FROM)
                .setLastValue(SERVER_COUNTER_TO)
                .build();

        newStub.getValues(clientRequest, new StreamObserver<ServerResponse>() {
            @Override
            public void onNext(ServerResponse response) {
                synchronized (object) {
                    lastValue = response.getCurrentValue();
                    logger.info("last value = {}", lastValue);
                }
            }

            @Override
            public void onError(Throwable t) {
                logger.error("onError", t);
            }

            @Override
            public void onCompleted() {
                logger.info("completed");
                latch.countDown();
            }
        });
        activateCounter();

        latch.await();

        channel.shutdown();
    }

    private static void activateCounter() {
        var currentValue = 0L;
        for (long i = 1; i <= CLIENT_COUNTER_VALUE; i++) {
            synchronized (object) {
                currentValue = currentValue + lastValue + 1;
                lastValue = 0;
                logger.info("currentValue = {}", currentValue);
            }

            try {
                Thread.sleep(1000);
            } catch (InterruptedException ignored) {
            }
        }
    }

}
