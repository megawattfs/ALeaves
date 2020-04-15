package com.example.aleaves;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mongodb.lang.NonNull;
import com.mongodb.stitch.android.core.Stitch;
import com.mongodb.stitch.android.core.StitchAppClient;
import com.mongodb.stitch.android.core.auth.StitchUser;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoClient;
import com.mongodb.stitch.android.services.mongodb.remote.RemoteMongoCollection;
import com.mongodb.stitch.core.auth.providers.anonymous.AnonymousCredential;
import com.mongodb.stitch.core.internal.common.BsonUtils;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateOptions;
import com.mongodb.stitch.core.services.mongodb.remote.RemoteUpdateResult;

import org.bson.Document;
import org.bson.codecs.configuration.CodecRegistries;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private StitchAppClient client;
    public static RemoteMongoCollection<LeafCapture> all_leaves;
    public static String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //Initialize app client
        Stitch.initializeDefaultAppClient("aleaves-vkpjt");
        client = Stitch.getDefaultAppClient();

        final RemoteMongoClient mongoClient = client.getServiceClient(
                RemoteMongoClient.factory, "leaf-database");

        //set up collection
        all_leaves = mongoClient
                .getDatabase(LeafCapture.LEAF_DATABASE)
                .getCollection(LeafCapture.LEAF_COLLECTION, LeafCapture.class)
                .withCodecRegistry(CodecRegistries.fromRegistries(
                        BsonUtils.DEFAULT_CODEC_REGISTRY,
                        CodecRegistries.fromCodecs(LeafCapture.codec)));

        //login
        Stitch.getDefaultAppClient().getAuth().loginWithCredential(new AnonymousCredential()).addOnCompleteListener(new OnCompleteListener<StitchUser>() {
            @Override
            public void onComplete(@NonNull final Task<StitchUser> task) {
                if (task.isSuccessful()) {
                    Log.d("stitch", "logged in anonymously");
                } else {
                    Log.e("stitch", "failed to log in anonymously", task.getException());
                }
            }
        });

        userId = "";//TODO assign user ID
    }

    public void onClickAddLeaves(View view) {
        Intent addLeavesIntent = new Intent(this, AddLeaves.class);
        startActivity(addLeavesIntent);
    }
    public void onClickViewLeaves(View view) {
        Intent viewLeavesIntent = new Intent(this, ViewLeaves.class);
        startActivity(viewLeavesIntent);
    }
    public void onClickLeafMap(View view) {
        Intent leafMapIntent = new Intent(this, LeafMap.class);
        startActivity(leafMapIntent);
    }
    public void onClickAbout(View view) {
        Intent aboutIntent = new Intent(this, About.class);
        startActivity(aboutIntent);
    }
}
