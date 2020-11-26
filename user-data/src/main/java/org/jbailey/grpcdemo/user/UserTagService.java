package org.jbailey.grpcdemo.user;

import io.grpc.stub.StreamObserver;
import org.jbailey.grpcdemo.user.tags.UserTagsGrpc;
import org.jbailey.grpcdemo.user.tags.UserTagsOuterClass;

import java.text.MessageFormat;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

public class UserTagService extends UserTagsGrpc.UserTagsImplBase {


    @Override
    public void getTag(UserTagsOuterClass.User request, StreamObserver<UserTagsOuterClass.Tag> responseObserver) {
        long startTime = System.nanoTime();
        responseObserver.onNext(getFakeTag());
        responseObserver.onCompleted();
        long endTime = System.nanoTime();

        System.out.println(MessageFormat.format("Took {0}ms", NANOSECONDS.toMillis(endTime - startTime)));
    }

    @Override
    public void getTags(UserTagsOuterClass.User request, StreamObserver<UserTagsOuterClass.Tag> responseObserver) {
        long startTime = System.nanoTime();
        // Arbitrary number in loop just to see things work
        for (int i = 0; i < 4; i++) {
            responseObserver.onNext(getFakeTag());
        }
        responseObserver.onCompleted();
        long endTime = System.nanoTime();

        System.out.println(MessageFormat.format("Took {0}ms", NANOSECONDS.toMillis(endTime - startTime)));

    }

    @Override
    public StreamObserver<UserTagsOuterClass.Tag> updateTags(StreamObserver<UserTagsOuterClass.TagSummary> responseObserver) {
        return new StreamObserver<>() {
            int tagCount;
            final long startTime = System.nanoTime();

            @Override
            public void onNext(UserTagsOuterClass.Tag value) {
                tagCount++;
                // DB update or something
            }

            @Override
            public void onError(Throwable t) {
                // log something
            }

            @Override
            public void onCompleted() {
                long milliseconds = NANOSECONDS.toMillis(System.nanoTime() - startTime);
                responseObserver.onNext(UserTagsOuterClass.TagSummary.newBuilder()
                                                                     .setUpdatedCount(tagCount)
                                                                     .setElapsedTime(milliseconds)
                                                                     .build());

                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public StreamObserver<UserTagsOuterClass.Tag> tagsFlyingEverywhere(StreamObserver<UserTagsOuterClass.Tag> responseObserver) {
        return new StreamObserver<>() {
            @Override
            public void onNext(UserTagsOuterClass.Tag value) {
                // Echo back what was received just to see things work
                responseObserver.onNext(value);
            }

            @Override
            public void onError(Throwable t) {
                // log something
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    private UserTagsOuterClass.Tag getFakeTag() {
        return UserTagsOuterClass.Tag.newBuilder()
                                     .setId(UUID.randomUUID().toString())
                                     .setName("Tag Name")
                                     .build();
    }
}
