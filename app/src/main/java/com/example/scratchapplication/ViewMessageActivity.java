package com.example.scratchapplication;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.scratchapplication.adapter.ChatAdapter;
import com.example.scratchapplication.api.JsonApi;
import com.example.scratchapplication.api.RestClient;
import com.example.scratchapplication.model.Message;
import com.example.scratchapplication.socketio.WebSocket;
import com.google.firebase.auth.FirebaseAuth;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;

import io.socket.client.IO;
import io.socket.client.Socket;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewMessageActivity extends AppCompatActivity {
    private RecyclerView recyclerViewMessage;
    private ChatAdapter adapter;

    private EditText editTextChat;
    private Button buttonChat;
    private Socket socket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_message);
        String myId = FirebaseAuth.getInstance().getUid();

        Intent intent = getIntent();
        String idReceive = intent.getStringExtra("idReceive");
        // truyen id nguoi dung len ma
        // co phai truyen id nguoi nhận đâu
        socket = WebSocket.getInstance();
        socket.connect();
        JsonObject enterUser = new JsonObject();
        enterUser.addProperty("uId", myId);
        Gson gson = new Gson();
        String json = gson.toJson(enterUser);
        try {
            JSONObject obj = new JSONObject(json);
            socket.emit("EnterUser", obj);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        
        editTextChat = findViewById(R.id.edt_chatbox);
        buttonChat = findViewById(R.id.btn_chatbox_send);

        JsonApi service = RestClient.createService(JsonApi.class);
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("uId",FirebaseAuth.getInstance().getUid());
        jsonObject.addProperty("uIdReceive",idReceive);
        Call<JsonApi.MessagesPojo> call = service.chat(jsonObject);
        call.enqueue(new Callback<JsonApi.MessagesPojo>() {
            @Override
            public void onResponse(Call<JsonApi.MessagesPojo> call, Response<JsonApi.MessagesPojo> response) {
                if (!response.isSuccessful()){
                    Log.e("callChat", response.code()+" "+call.request().url().toString());
                    return;
                }
                recyclerViewMessage = findViewById(R.id.rv_message_list);
                recyclerViewMessage.setLayoutManager(new LinearLayoutManager(ViewMessageActivity.this));
                List<Message> messageList = response.body().getDataMessages().getMessages();
                adapter = new ChatAdapter(messageList, ViewMessageActivity.this, "");
                recyclerViewMessage.setAdapter(adapter);

                buttonChat.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String text = editTextChat.getText().toString().trim();
                        if (!text.equals("")){
                            editTextChat.setText("");
                            JsonObject data = new JsonObject();
                            data.addProperty("to",idReceive);
                            data.addProperty("from",myId);
                            data.addProperty("message", text);
                            data.addProperty("idMessage",response.body().getDataMessages().getIdMessage());
                            Gson gson = new Gson();
                            String json = gson.toJson(data);
                            try {
                                JSONObject obj = new JSONObject(json);
                                Log.e("rawJson", json);
                                socket.emit("MessageNews",obj);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });
            }

            @Override
            public void onFailure(Call<JsonApi.MessagesPojo> call, Throwable t) {
                Log.e("failChat", t.getMessage());
            }
        });
    }
}