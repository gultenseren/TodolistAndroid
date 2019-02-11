package com.todolist;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.todolist.model.ListNames;
import com.todolist.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText name, password;
    Button button_login;

    String  user_name,user_password;

    private RequestQueue mQueue;

    ArrayList<User> array;

    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        name=findViewById(R.id.user_name);
        password=findViewById(R.id.user_password);
        button_login=findViewById(R.id.button_login);

        final Context context = this;
        mQueue = Volley.newRequestQueue(this);

        user = new User();

        button_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_name = String.valueOf(name.getText());
                user_password = String.valueOf(password.getText());

                if(user_name.isEmpty() || user_password.isEmpty()){

                    showMessage(context,"Do not leave free space !");

                }else{

                    String url = "http://10.0.2.2:2222/todolistservice/user/"+user_name;

                    JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null,
                            new Response.Listener<JSONObject>() {
                                @Override
                                public void onResponse(JSONObject response) {
                                    if (response != null) {


                                        user = new User();
                                        user = fromJson(response);

                                        //   String name = response.getString(0);
                                        //  int employee_ID = response.getInt(1);


                                        if(user.getUser_name().equals(user_name) && user.getUser_password().equals(user_password)){

                                            Intent intent = new Intent(getApplicationContext(), Lists.class);
                                            intent.putExtra("userid",user.user_id);
                                            intent.putExtra("username",user_name);
                                            startActivity(intent);


                                        }else{
                                            showMessage(context, "Check user information ");
                                        }

                                    }
                                }
                            }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            error.printStackTrace();
                        }
                    });

                    mQueue.add(request);
                }


            }
        });
    }

    public void showMessage(Context context, String message){

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);


        alertDialogBuilder.setTitle("Warning");


        alertDialogBuilder
                .setMessage(message)
                .setCancelable(false)
                .setIcon(R.mipmap.ic_launcher_round)
                // Evet butonuna tıklanınca yapılacak işlemleri buraya yazıyoruz.

                // İptal butonuna tıklanınca yapılacak işlemleri buraya yazıyoruz.
                .setNegativeButton("Okey", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        // alert dialog nesnesini oluşturuyoruz
        AlertDialog alertDialog = alertDialogBuilder.create();

        // alerti gösteriyoruz
        alertDialog.show();
    }

    //-------------------------------------------------------------------------------------------------



    // Decodes business json into business model object
    public static User fromJson(JSONObject jsonObject) {
        User b = new User();
        // Deserialize json into object fields
        try {

            b.user_id = jsonObject.getInt("user_id");
            b.user_name = jsonObject.getString("user_name");
            b.user_password = jsonObject.getString("user_password");
            b.user_email = jsonObject.getString("user_email");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object
        return b;
    }
}
