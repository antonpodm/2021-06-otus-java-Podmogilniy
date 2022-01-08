package ru.otus.protobuf.service;

import io.grpc.stub.StreamObserver;
import ru.otus.protobuf.generated.ClientRequest;
import ru.otus.protobuf.generated.RemoteServiceGrpc;
import ru.otus.protobuf.generated.ServerResponse;

public class RemoteServiceImpl extends RemoteServiceGrpc.RemoteServiceImplBase {

    private final ValuesService valuesService;

    public RemoteServiceImpl(ValuesService valuesService) {
        this.valuesService = valuesService;
    }

    @Override
    public void getValues(ClientRequest request, StreamObserver<ServerResponse> responseObserver) {
        var values = valuesService.getValues(request.getFirstValue(), request.getLastValue());
        values.forEach(value -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            responseObserver.onNext(response(value));
        });
        responseObserver.onCompleted();
    }

    private ServerResponse response(Long currentValue) {
        return ServerResponse.newBuilder().setCurrentValue(currentValue)
                .build();
    }
}
