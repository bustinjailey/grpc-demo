package org.jbailey.grpcdemo.user.client;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface UserTagsClient {

    @GET("tags")
    Call<List<Tag>> getTags();
}
