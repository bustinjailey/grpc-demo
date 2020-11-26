package org.jbailey.grpcdemo.shared;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.jbailey.grpcdemo.user.tags.UserTagsGrpc;
import org.jbailey.grpcdemo.user.tags.UserTagsOuterClass;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.Timer;
import java.util.UUID;

import static java.util.concurrent.TimeUnit.NANOSECONDS;

/**
 * This module/application doesn't know what it wants to be.  Right now it is simply a main method which calls the
 * client from user-data.  In the real world it would be a restful API which returns JSON to a web client (or similar)
 */
public class SharedDataServer {
    public static void main(String[] args) {
        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 7000)
                                                      .usePlaintext()
                                                      .build();
        UserTagsGrpc.UserTagsBlockingStub blockingStub = UserTagsGrpc.newBlockingStub(channel);
        // UserTagsGrpc.UserTagsStub asyncStub = UserTagsGrpc.newStub(channel);

        UserTagsOuterClass.User fakeUser = UserTagsOuterClass.User.newBuilder()
                                                                  .setId(UUID.randomUUID().toString())
                                                                  .build();

        // Call each RPC multiple times to compare response times
        getSingleTag(blockingStub, fakeUser);
        getSingleTag(blockingStub, fakeUser);
        getSingleTag(blockingStub, fakeUser);
        getMultipleTags(blockingStub, fakeUser);
        getMultipleTags(blockingStub, fakeUser);
        getSingleTag(blockingStub, fakeUser);


        // TODO: Implement call using asyncStub

        channel.shutdown();
        System.exit(0);
    }

    private static void getMultipleTags(UserTagsGrpc.UserTagsBlockingStub blockingStub, UserTagsOuterClass.User fakeUser) {
        long startTime2 = System.nanoTime();
        Iterator<UserTagsOuterClass.Tag> tags = blockingStub.getTags(fakeUser);
        while (tags.hasNext()) {
            UserTagsOuterClass.Tag tag = tags.next();
            System.out.println(tag);
        }
        long endTime2 = System.nanoTime();
        System.out.println(MessageFormat.format("Getting multiple tags took {0}ms",
                                                NANOSECONDS.toMillis(endTime2 - startTime2)));
    }

    private static void getSingleTag(UserTagsGrpc.UserTagsBlockingStub blockingStub, UserTagsOuterClass.User fakeUser) {
        long startTime1 = System.nanoTime();
        System.out.println(blockingStub.getTag(fakeUser));
        long endTime1 = System.nanoTime();
        System.out.println(MessageFormat.format("Getting single tag took {0}ms",
                                                NANOSECONDS.toMillis(endTime1 - startTime1)));
    }
}
