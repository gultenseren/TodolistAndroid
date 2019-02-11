package com.todolist;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.format.DateFormat;
import android.text.style.StrikethroughSpan;
import android.text.style.TtsSpan;
import android.text.style.UnderlineSpan;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.todolist.model.ListNames;
import com.todolist.model.Task;
import com.todolist.model.User;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;


public class Lists extends AppCompatActivity {
    TextView textView;
    private RequestQueue mQueue;

    Spinner spinner;
    TextView tv;

    ImageView addList, removeList, addTask, removeTask;

    ArrayList<ListNames> arraylist;
    ArrayList<Task> arraytask;
    String selected ;
    Integer selectedid;
    String name;
    Integer id;

    TableLayout table;
    RelativeLayout rl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lists);

        arraylist = new ArrayList<ListNames>();
        arraytask = new ArrayList<Task>();

        final Context context = this;

        spinner = findViewById(R.id.spin);
        table= findViewById(R.id.table);
        addList=findViewById(R.id.addList);
        removeList=findViewById(R.id.removeList);
        addTask=findViewById(R.id.addTask);

        Intent gelenCinsiyet = getIntent();
        name = gelenCinsiyet.getExtras().getString("username");
        id = gelenCinsiyet.getExtras().getInt("userid");

        mQueue = Volley.newRequestQueue(this);







        arraylist = new ArrayList<ListNames>();

        String url = "http://10.0.2.2:2222/todolistservice/listofuser/"+id;

        JsonArrayRequest request = new JsonArrayRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        if (response != null) {
                            try {
                                for (int i = 0; i<response.length(); i++) {
                                    JSONObject jo = response.getJSONObject(i);


                                    ListNames list = new ListNames();
                                    list = fromJson(jo);
                                    arraylist.add(list);




                                }
                                final ArrayList<String> contacts = new ArrayList<>();

                                for ( ListNames x : arraylist ) {


                                    contacts.add(x.list_name);
                                    // System.out.println(x.list_name);

                                }
                                selected=contacts.get(0);
                                selectedid =arraylist.get(0).list_id;


                                ArrayAdapter<String> adapter =
                                        new ArrayAdapter<String>(getApplicationContext(),  android.R.layout.simple_spinner_dropdown_item, contacts);
                                adapter.setDropDownViewResource( android.R.layout.simple_spinner_dropdown_item);

                                spinner.setAdapter(adapter);

                                spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                                    @Override
                                    public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {

                                        selected=contacts.get(position);
                                        selectedid =arraylist.get(position).list_id;
                                        arraytask=new ArrayList<Task>();
                                        System.out.println("-----------------------------------------------------" + selectedid);


                                        table.removeAllViews();
                                        arraytask = new ArrayList<Task>();

                                        String url2 = "http://10.0.2.2:2222/todolistservice/taskoflist/"+selectedid;


                                        JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                                                new Response.Listener<JSONArray>() {
                                                    @Override
                                                    public void onResponse(JSONArray response) {
                                                        if (response != null) {
                                                            try {
                                                                //  showMessage(context,"*******************");
                                                                for (int i = 0; i<response.length(); i++) {
                                                                    JSONObject jo = response.getJSONObject(i);


                                                                    Task task = new Task();
                                                                    task = fromJsonTask(jo);
                                                                    arraytask.add(task);

                                                                    final ArrayList<String> tasks = new ArrayList<>();


                                                                }


                                                                for(Task taskk : arraytask){

                                                                    // System.out.println(x.task_name);

                                                                    final TableRow row=new TableRow(context);

                                                                    row.setClickable(true);
                                                                    TableRow.LayoutParams  params1=new TableRow.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,1.0f);

                                                                    final TextView txt1=new TextView(context);
                                                                    final TextView txt2=new TextView(context);
                                                                    final CheckBox txt3=new CheckBox(context);
                                                                    final TextView txt4=new TextView(context);
                                                                    final TextView txt5=new TextView(context);

                                                                    txt1.setText(taskk.task_name);

                                                                    txt1.setFocusable(true);
                                                                    txt1.setFocusableInTouchMode(true);
                                                                    txt1.setClickable(true);










                                                                    txt2.setText(taskk.task_description);
                                                                    txt4.setText(taskk.task_deadline);

                                                                    if(taskk.task_status.equals("active")){
                                                                        txt3.setChecked(true);
                                                                        SpannableString content = new SpannableString(txt1.getText().toString());
                                                                        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                                                                        txt1.setText(content);
                                                                        SpannableString content2 = new SpannableString(txt2.getText().toString());
                                                                        content2.setSpan(new StrikethroughSpan(), 0, content2.length(), 0);
                                                                        txt2.setText(content2);
                                                                        SpannableString content3 = new SpannableString(txt4.getText().toString());
                                                                        content3.setSpan(new StrikethroughSpan(), 0, content3.length(), 0);
                                                                        txt4.setText(content3);
                                                                    }else{
                                                                        txt3.setChecked(false);
                                                                        String s1 = txt1.getText().toString();
                                                                        txt1.setText(s1);
                                                                        String s2 = txt2.getText().toString();
                                                                        txt2.setText(s2);
                                                                        String s4 = txt4.getText().toString();
                                                                        txt4.setText(s4);
                                                                    }

//----------------------------------------------------------------------------------------------------------------------------
                                                                    txt1.setOnClickListener(new View.OnClickListener() {
                                                                        @Override

                                                                        public void onClick(View widget) {

                                                                            TextView tv = (TextView) widget;

                                                                            final TextView tnametv= (TextView) row.getChildAt(0) ;
                                                                            final TextView tdestv = (TextView) row.getChildAt(1) ;
                                                                            final TextView tdeadtv = (TextView) row.getChildAt(3) ;
                                                                            String taskdes = tnametv.getText().toString();

                                                                            for(Task t : arraytask){
                                                                                if(t.task_description.equals(taskdes)){

                                                                                    int id = t.task_id;

                                                                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                                                                    alert.setTitle("Task Description :");

                                                                                    LinearLayout layout = new LinearLayout(context);
                                                                                    layout.setOrientation(LinearLayout.VERTICAL);

                                                                                    final EditText tn = new EditText(context);
                                                                                    tn.setText(t.task_description);
                                                                                    layout.addView(tn);

                                                                                    final EditText td = new EditText(context);
                                                                                    td.setText(t.task_description);
                                                                                    layout.addView(td);

                                                                                    final EditText tdl = new EditText(context);
                                                                                    tdl.setText(t.task_description);
                                                                                    layout.addView(tdl);

                                                                                    final int tid = t.task_id;
                                                                                    final int lid = t.list_id;
                                                                                    final String tnamee =t.task_name;
                                                                                    final String taskd = t.task_description;
                                                                                    final String taskdead =t.task_deadline;

                                                                                    alert.setView(layout);

                                                                                    alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                                                                        public void onClick(DialogInterface dialog, int whichButton) {


                                                                                            try {
                                                                                                RequestQueue requestQueue = Volley.newRequestQueue(context);
                                                                                                String URL = "http://...";
                                                                                                JSONObject jsonBody = new JSONObject();
                                                                                                jsonBody.put("task_id", tid);
                                                                                                jsonBody.put("list_id", lid);
                                                                                                jsonBody.put("task_tame", tnamee);
                                                                                                jsonBody.put("task_description", taskd);
                                                                                                jsonBody.put("task_deadline", taskdead);
                                                                                                jsonBody.put("task_status", "passive");
                                                                                                jsonBody.put("task_id", tid);

                                                                                                final String requestBody = jsonBody.toString();

                                                                                                StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                                                                                                    @Override
                                                                                                    public void onResponse(String response) {
                                                                                                        Log.i("VOLLEY", response);
                                                                                                        tnametv.setText(tn.getText());
                                                                                                        tdestv.setText(td.getText());
                                                                                                        tdeadtv.setText(tdl.getText());
                                                                                                    }
                                                                                                }, new Response.ErrorListener() {
                                                                                                    @Override
                                                                                                    public void onErrorResponse(VolleyError error) {
                                                                                                        Log.e("VOLLEY", error.toString());
                                                                                                    }
                                                                                                }) {
                                                                                                    @Override
                                                                                                    public String getBodyContentType() {
                                                                                                        return "application/json; charset=utf-8";
                                                                                                    }

                                                                                                    @Override
                                                                                                    public byte[] getBody() throws AuthFailureError {
                                                                                                        try {
                                                                                                            return requestBody == null ? null : requestBody.getBytes("utf-8");
                                                                                                        } catch (UnsupportedEncodingException uee) {
                                                                                                            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                                                                                            return null;
                                                                                                        }
                                                                                                    }

                                                                                                    @Override
                                                                                                    protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                                                                                        String responseString = "";
                                                                                                        if (response != null) {
                                                                                                            responseString = String.valueOf(response.statusCode);
                                                                                                            // can get more details such as response.headers
                                                                                                        }
                                                                                                        return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                                                                                                    }
                                                                                                };

                                                                                                requestQueue.add(stringRequest);
                                                                                            } catch (JSONException e) {
                                                                                                e.printStackTrace();
                                                                                            }




                                                                                        }
                                                                                    });

                                                                                    alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                                        public void onClick(DialogInterface dialog, int whichButton) {
                                                                                            // what ever you want to do with No option.
                                                                                        }
                                                                                    });

                                                                                    alert.show();


                                                                                }

                                                                            }


                                                                        }});

//------------------------------------------------------------------------------------------------------------------------------
                                                                    txt3.setOnClickListener(new View.OnClickListener() {
                                                                        @Override

                                                                        public void onClick(View v) {
                                                                            if (txt3.isChecked()) {

                                                                                SpannableString content = new SpannableString(txt1.getText().toString());
                                                                                content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                                                                                txt1.setText(content);
                                                                                SpannableString content2 = new SpannableString(txt2.getText().toString());
                                                                                content2.setSpan(new StrikethroughSpan(), 0, content2.length(), 0);
                                                                                txt2.setText(content2);
                                                                                SpannableString content3 = new SpannableString(txt4.getText().toString());
                                                                                content3.setSpan(new StrikethroughSpan(), 0, content3.length(), 0);
                                                                                txt4.setText(content3);

                                                                            }else{
                                                                                String s1 = txt1.getText().toString();
                                                                                txt1.setText(s1);
                                                                                String s2 = txt2.getText().toString();
                                                                                txt2.setText(s2);
                                                                                String s4 = txt4.getText().toString();
                                                                                txt4.setText(s4);
                                                                            }
                                                                        }
                                                                    });

                                                                    // txt4.setText(taskk.task_deadline);
                                                                    txt5.setText("X");

                                                                    txt5.setOnClickListener(new View.OnClickListener() {
                                                                        @Override

                                                                        public void onClick(View v) {
                                                                            System.out.println("---------------------------");
                                                                            System.out.println(v.getId());
                                                                        }
                                                                    });
//----------------------------------------------------------------------------------------------------------------------------
                                                                    txt1.setLayoutParams(params1);
                                                                    txt2.setLayoutParams(params1);
                                                                    txt3.setLayoutParams(params1);
                                                                    txt4.setLayoutParams(params1);
                                                                    txt5.setLayoutParams(params1);

                                                                    row.addView(txt1);
                                                                    row.addView(txt2);
                                                                    row.addView(txt3);
                                                                    row.addView(txt4);
                                                                    row.addView(txt5);



                                                                    row.getChildAt(4).setOnClickListener(new View.OnClickListener() {
                                                                        public void onClick(View widget) {
                                                                            widget.setBackgroundColor(Color.GRAY);
                                                                            //  System.out.println("Row clicked: "+row.ge );



                                                                            TextView tv = (TextView) widget;
                                                                            // TODO add check if tv.getText() instanceof Spanned
                                                                            String s= tv.getText().toString();

                                                                            TextView tname = (TextView) row.getChildAt(0) ;
                                                                            String name = tname.getText().toString();

                                                                            for(Task t : arraytask){
                                                                                if(t.task_name.equals(name)){

                                                                                    int id = t.task_id;

                                                                                    RequestQueue queue = Volley.newRequestQueue(context);
                                                                                    final String url = "http://10.0.2.2:2222/todolistservice/removetask/"+id;

                                                                                    StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                                                                                            new Response.Listener<String>()
                                                                                            {
                                                                                                @Override
                                                                                                public void onResponse(String response) {
                                                                                                    showMessage(context,"sildiiiii");
                                                                                                    table.removeView(row);
                                                                                                }
                                                                                            },
                                                                                            new Response.ErrorListener()
                                                                                            {
                                                                                                @Override
                                                                                                public void onErrorResponse(VolleyError error) {
                                                                                                    // error.

                                                                                                }
                                                                                            }
                                                                                    );
                                                                                    queue.add(deleteRequest);
                                                                                }

                                                                            }

                                                                            //   System.out.println("**** "+name);
                                                                        }
                                                                    });

                                                                    table.addView(row);


                                                                }
                                                                System.out.println("--------------------------------------------------------");
                                                                System.out.println(arraytask.toString());


                                                            } catch (JSONException e) {
                                                                e.printStackTrace();
                                                            }

                                                        }
                                                    }
                                                }, new Response.ErrorListener() {
                                            @Override
                                            public void onErrorResponse(VolleyError error) {
                                                error.printStackTrace();
                                            }
                                        });

                                        mQueue.add(request2);
                                    }

                                    @Override
                                    public void onNothingSelected(AdapterView<?> parentView) {
                                        // your code here


                                    }

                                });
                                //-----------------------------------------------------------------------------------------------------------------


                                table.removeAllViews();
                                arraytask = new ArrayList<Task>();

                                String url2 = "http://10.0.2.2:2222/todolistservice/taskoflist/"+selectedid;


                                JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                                        new Response.Listener<JSONArray>() {
                                            @Override
                                            public void onResponse(JSONArray response) {
                                                if (response != null) {
                                                    try {
                                                        //  showMessage(context,"*******************");
                                                        for (int i = 0; i<response.length(); i++) {
                                                            JSONObject jo = response.getJSONObject(i);


                                                            Task task = new Task();
                                                            task = fromJsonTask(jo);
                                                            arraytask.add(task);

                                                            final ArrayList<String> tasks = new ArrayList<>();


                                                        }


                                                        for(Task taskk : arraytask){

                                                            // System.out.println(x.task_name);

                                                            final TableRow row=new TableRow(context);

                                                            row.setClickable(true);
                                                            TableRow.LayoutParams  params1=new TableRow.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,1.0f);

                                                            final TextView txt1=new TextView(context);
                                                            final TextView txt2=new TextView(context);
                                                            final CheckBox txt3=new CheckBox(context);
                                                            final TextView txt4=new TextView(context);
                                                            final TextView txt5=new TextView(context);

                                                            txt1.setText(taskk.task_name);

                                                            txt1.setFocusable(true);
                                                            txt1.setFocusableInTouchMode(true);
                                                            txt1.setClickable(true);










                                                            txt2.setText(taskk.task_description);
                                                            txt4.setText(taskk.task_deadline);

                                                            if(taskk.task_status.equals("active")){
                                                                txt3.setChecked(true);
                                                                SpannableString content = new SpannableString(txt1.getText().toString());
                                                                content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                                                                txt1.setText(content);
                                                                SpannableString content2 = new SpannableString(txt2.getText().toString());
                                                                content2.setSpan(new StrikethroughSpan(), 0, content2.length(), 0);
                                                                txt2.setText(content2);
                                                                SpannableString content3 = new SpannableString(txt4.getText().toString());
                                                                content3.setSpan(new StrikethroughSpan(), 0, content3.length(), 0);
                                                                txt4.setText(content3);
                                                            }else{
                                                                txt3.setChecked(false);
                                                                String s1 = txt1.getText().toString();
                                                                txt1.setText(s1);
                                                                String s2 = txt2.getText().toString();
                                                                txt2.setText(s2);
                                                                String s4 = txt4.getText().toString();
                                                                txt4.setText(s4);
                                                            }

//----------------------------------------------------------------------------------------------------------------------------
                                                            txt1.setOnClickListener(new View.OnClickListener() {
                                                                @Override

                                                                public void onClick(View widget) {

                                                                    TextView tv = (TextView) widget;

                                                                    final TextView tnametv= (TextView) row.getChildAt(0) ;
                                                                    final TextView tdestv = (TextView) row.getChildAt(1) ;
                                                                    final TextView tdeadtv = (TextView) row.getChildAt(3) ;
                                                                    String taskdes = tnametv.getText().toString();

                                                                    for(Task t : arraytask){
                                                                        if(t.task_description.equals(taskdes)){

                                                                            int id = t.task_id;

                                                                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                                                                            alert.setTitle("Task Description :");

                                                                            LinearLayout layout = new LinearLayout(context);
                                                                            layout.setOrientation(LinearLayout.VERTICAL);

                                                                            final EditText tn = new EditText(context);
                                                                            tn.setText(t.task_description);
                                                                            layout.addView(tn);

                                                                            final EditText td = new EditText(context);
                                                                            td.setText(t.task_description);
                                                                            layout.addView(td);

                                                                            final EditText tdl = new EditText(context);
                                                                            tdl.setText(t.task_description);
                                                                            layout.addView(tdl);

                                                                           final int tid = t.task_id;
                                                                           final int lid = t.list_id;
                                                                           final String tnamee =t.task_name;
                                                                           final String taskd = t.task_description;
                                                                           final String taskdead =t.task_deadline;

                                                                            alert.setView(layout);

                                                                            alert.setPositiveButton("Change", new DialogInterface.OnClickListener() {
                                                                                public void onClick(DialogInterface dialog, int whichButton) {


                                                                                    try {
                                                                                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                                                                                        String URL = "http://...";
                                                                                        JSONObject jsonBody = new JSONObject();
                                                                                        jsonBody.put("task_id", tid);
                                                                                        jsonBody.put("list_id", lid);
                                                                                        jsonBody.put("task_tame", tnamee);
                                                                                        jsonBody.put("task_description", taskd);
                                                                                        jsonBody.put("task_deadline", taskdead);
                                                                                        jsonBody.put("task_status", "passive");
                                                                                        jsonBody.put("task_id", tid);

                                                                                        final String requestBody = jsonBody.toString();

                                                                                        StringRequest stringRequest = new StringRequest(Request.Method.PUT, URL, new Response.Listener<String>() {
                                                                                            @Override
                                                                                            public void onResponse(String response) {
                                                                                                Log.i("VOLLEY", response);
                                                                                                tnametv.setText(tn.getText());
                                                                                                tdestv.setText(td.getText());
                                                                                                tdeadtv.setText(tdl.getText());
                                                                                            }
                                                                                        }, new Response.ErrorListener() {
                                                                                            @Override
                                                                                            public void onErrorResponse(VolleyError error) {
                                                                                                Log.e("VOLLEY", error.toString());
                                                                                            }
                                                                                        }) {
                                                                                            @Override
                                                                                            public String getBodyContentType() {
                                                                                                return "application/json; charset=utf-8";
                                                                                            }

                                                                                            @Override
                                                                                            public byte[] getBody() throws AuthFailureError {
                                                                                                try {
                                                                                                    return requestBody == null ? null : requestBody.getBytes("utf-8");
                                                                                                } catch (UnsupportedEncodingException uee) {
                                                                                                    VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                                                                                    return null;
                                                                                                }
                                                                                            }

                                                                                            @Override
                                                                                            protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                                                                                String responseString = "";
                                                                                                if (response != null) {
                                                                                                    responseString = String.valueOf(response.statusCode);
                                                                                                    // can get more details such as response.headers
                                                                                                }
                                                                                                return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                                                                                            }
                                                                                        };

                                                                                        requestQueue.add(stringRequest);
                                                                                    } catch (JSONException e) {
                                                                                        e.printStackTrace();
                                                                                    }




                                                                                }
                                                                            });

                                                                            alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                                                                                public void onClick(DialogInterface dialog, int whichButton) {
                                                                                    // what ever you want to do with No option.
                                                                                }
                                                                            });

                                                                            alert.show();


                                                                        }

                                                                    }


                                                                }});

//------------------------------------------------------------------------------------------------------------------------------
                                                            txt3.setOnClickListener(new View.OnClickListener() {
                                                                @Override

                                                                public void onClick(View v) {
                                                                    if (txt3.isChecked()) {

                                                                        SpannableString content = new SpannableString(txt1.getText().toString());
                                                                        content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                                                                        txt1.setText(content);
                                                                        SpannableString content2 = new SpannableString(txt2.getText().toString());
                                                                        content2.setSpan(new StrikethroughSpan(), 0, content2.length(), 0);
                                                                        txt2.setText(content2);
                                                                        SpannableString content3 = new SpannableString(txt4.getText().toString());
                                                                        content3.setSpan(new StrikethroughSpan(), 0, content3.length(), 0);
                                                                        txt4.setText(content3);

                                                                    }else{
                                                                        String s1 = txt1.getText().toString();
                                                                        txt1.setText(s1);
                                                                        String s2 = txt2.getText().toString();
                                                                        txt2.setText(s2);
                                                                        String s4 = txt4.getText().toString();
                                                                        txt4.setText(s4);
                                                                    }
                                                                }
                                                            });

                                                           // txt4.setText(taskk.task_deadline);
                                                            txt5.setText("X");

                                                            txt5.setOnClickListener(new View.OnClickListener() {
                                                                @Override

                                                                public void onClick(View v) {
                                                                    System.out.println("---------------------------");
                                                                    System.out.println(v.getId());
                                                                }
                                                            });
//----------------------------------------------------------------------------------------------------------------------------
                                                            txt1.setLayoutParams(params1);
                                                            txt2.setLayoutParams(params1);
                                                            txt3.setLayoutParams(params1);
                                                            txt4.setLayoutParams(params1);
                                                            txt5.setLayoutParams(params1);

                                                            row.addView(txt1);
                                                            row.addView(txt2);
                                                            row.addView(txt3);
                                                            row.addView(txt4);
                                                            row.addView(txt5);



                                                            row.getChildAt(4).setOnClickListener(new View.OnClickListener() {
                                                                public void onClick(View widget) {
                                                                    widget.setBackgroundColor(Color.GRAY);
                                                                  //  System.out.println("Row clicked: "+row.ge );



                                                                    TextView tv = (TextView) widget;
                                                                    // TODO add check if tv.getText() instanceof Spanned
                                                                    String s= tv.getText().toString();

                                                                    TextView tname = (TextView) row.getChildAt(0) ;
                                                                    String name = tname.getText().toString();

                                                                    for(Task t : arraytask){
                                                                        if(t.task_name.equals(name)){

                                                                            int id = t.task_id;

                                                                            RequestQueue queue = Volley.newRequestQueue(context);
                                                                            final String url = "http://10.0.2.2:2222/todolistservice/removetask/"+id;

                                                                            StringRequest deleteRequest = new StringRequest(Request.Method.DELETE, url,
                                                                                    new Response.Listener<String>()
                                                                                    {
                                                                                        @Override
                                                                                        public void onResponse(String response) {
                                                                                            showMessage(context,"sildiiiii");
                                                                                            table.removeView(row);
                                                                                        }
                                                                                    },
                                                                                    new Response.ErrorListener()
                                                                                    {
                                                                                        @Override
                                                                                        public void onErrorResponse(VolleyError error) {
                                                                                            // error.

                                                                                        }
                                                                                    }
                                                                            );
                                                                            queue.add(deleteRequest);
                                                                        }

                                                                    }

                                                              //   System.out.println("**** "+name);
                                                                }
                                                            });

                                                            table.addView(row);


                                                        }
                                                        System.out.println("--------------------------------------------------------");
                                                        System.out.println(arraytask.toString());


                                                    } catch (JSONException e) {
                                                        e.printStackTrace();
                                                    }

                                                }
                                            }
                                        }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        error.printStackTrace();
                                    }
                                });

                                mQueue.add(request2);

                                //-----------------------------------------------------------------------------------------------------------------


                            } catch (JSONException e) {
                                e.printStackTrace();
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

//------------------------------------------------------------------------------------------------------------------------
        addList.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alert = new AlertDialog.Builder(context);
                alert.setTitle("Add List");

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                final EditText listname = new EditText(context);
                layout.addView(listname);


                alert.setView(layout);

                alert.setPositiveButton("Add List", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        String list_name = listname.getText().toString();

                        try {
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            final String url = "http://10.0.2.2:2222/todolistservice/createlist";

                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("user_id", id);
                            jsonBody.put("list_name", list_name);
                            final String requestBody = jsonBody.toString();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("VOLLEY", response);
                                    /******************/
                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("VOLLEY", error.toString());
                                }
                            }) {
                                @Override
                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8";
                                }

                                @Override
                                public byte[] getBody() throws AuthFailureError {
                                    try {
                                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                                    } catch (UnsupportedEncodingException uee) {
                                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                        return null;
                                    }
                                }

                                @Override
                                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                    String responseString = "";
                                    if (response != null) {
                                        responseString = String.valueOf(response.statusCode);
                                        // can get more details such as response.headers
                                    }
                                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                                }
                            };

                            requestQueue.add(stringRequest);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });

                alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        // what ever you want to do with No option.
                    }
                });

                alert.show();


            }});

//------------------------------------------------------------------------------------------------------------------------


        addTask.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {

                LinearLayout layout = new LinearLayout(context);
                layout.setOrientation(LinearLayout.VERTICAL);

                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Add Task");


                final EditText task_name = new EditText(context);
                task_name.setHint("Enter Task Name");
                layout.addView(task_name);

                final EditText task_description = new EditText(context);
                task_description.setHint("Enter Task Description");
                layout.addView(task_description);

                final DatePicker date = new DatePicker(context);
                layout.addView(date);


                builder.setView(layout);


                builder.setPositiveButton("Add Task", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String  taskname = task_name.getText().toString();
                        String  taskdes = task_description.getText().toString();
                        int  mm = date.getMonth();
                        int dd = date.getDayOfMonth();
                        int yy = date.getYear();
                        String taskdate = yy + "/" + mm + "/" + dd;


                        //---------------------------------------------------------------------------------------------------


                        try {
                            RequestQueue requestQueue = Volley.newRequestQueue(context);
                            String URL = "http://10.0.2.2:2222/todolistservice/inserttask";
                            JSONObject jsonBody = new JSONObject();
                            jsonBody.put("list_id", selectedid);
                            jsonBody.put("task_name", taskname);
                            jsonBody.put("task_description", taskdes);
                            jsonBody.put("task_status", "passive");
                            jsonBody.put("task_deadline", taskdate);
                            final String requestBody = jsonBody.toString();

                            StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                                @Override
                                public void onResponse(String response) {
                                    Log.i("VOLLEY**", response);

                                    //-----------------------------------------------------------------------------------------------------------------
                                    arraytask = null;
                                    arraytask = new ArrayList<Task>();

                                    table.removeAllViews();//******************************************************************

                                    arraylist = new ArrayList<ListNames>();
                                    String url2 = "http://10.0.2.2:2222/todolistservice/taskoflist/"+selectedid;


                                    JsonArrayRequest request2 = new JsonArrayRequest(Request.Method.GET, url2, null,
                                            new Response.Listener<JSONArray>() {
                                                @Override
                                                public void onResponse(JSONArray response) {
                                                    if (response != null) {
                                                        try {
                                                            ArrayList<Task> at = new ArrayList<Task>();

                                                            //  showMessage(context,"*******************");
                                                            for (int i = 0; i<response.length(); i++) {
                                                                JSONObject jo = response.getJSONObject(i);


                                                                Task task = new Task();
                                                                task = fromJsonTask(jo);


                                                                at.add(task);

                                                                final ArrayList<String> tasks = new ArrayList<>();


                                                            }


                                                            for(Task taskk : at){

                                                                // System.out.println(x.task_name);

                                                                TableRow row=new TableRow(context);

                                                                TableRow.LayoutParams  params1=new TableRow.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT,1.0f);

                                                                final TextView txt1=new TextView(context);
                                                                final TextView txt2=new TextView(context);
                                                                final CheckBox txt3=new CheckBox(context);
                                                                final TextView txt4=new TextView(context);
                                                                final TextView txt5=new TextView(context);

                                                                txt1.setText(taskk.task_name);
                                                                txt2.setText(taskk.task_description);
                                                                txt4.setText(taskk.task_deadline);

                                                                if(taskk.task_status.equals("active")){
                                                                    txt3.setChecked(true);
                                                                    SpannableString content = new SpannableString(txt1.getText().toString());
                                                                    content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                                                                    txt1.setText(content);
                                                                    SpannableString content2 = new SpannableString(txt2.getText().toString());
                                                                    content2.setSpan(new StrikethroughSpan(), 0, content2.length(), 0);
                                                                    txt2.setText(content2);
                                                                    SpannableString content3 = new SpannableString(txt4.getText().toString());
                                                                    content3.setSpan(new StrikethroughSpan(), 0, content3.length(), 0);
                                                                    txt4.setText(content3);
                                                                }else{
                                                                    txt3.setChecked(false);
                                                                    String s1 = txt1.getText().toString();
                                                                    txt1.setText(s1);
                                                                    String s2 = txt2.getText().toString();
                                                                    txt2.setText(s2);
                                                                    String s4 = txt4.getText().toString();
                                                                    txt4.setText(s4);
                                                                }

                                                                txt3.setOnClickListener(new View.OnClickListener() {
                                                                    @Override

                                                                    public void onClick(View v) {
                                                                        if (txt3.isChecked()) {

                                                                            SpannableString content = new SpannableString(txt1.getText().toString());
                                                                            content.setSpan(new StrikethroughSpan(), 0, content.length(), 0);
                                                                            txt1.setText(content);
                                                                            SpannableString content2 = new SpannableString(txt2.getText().toString());
                                                                            content2.setSpan(new StrikethroughSpan(), 0, content2.length(), 0);
                                                                            txt2.setText(content2);
                                                                            SpannableString content3 = new SpannableString(txt4.getText().toString());
                                                                            content3.setSpan(new StrikethroughSpan(), 0, content3.length(), 0);
                                                                            txt4.setText(content3);

                                                                        }else{
                                                                            String s1 = txt1.getText().toString();
                                                                            txt1.setText(s1);
                                                                            String s2 = txt2.getText().toString();
                                                                            txt2.setText(s2);
                                                                            String s4 = txt4.getText().toString();
                                                                            txt4.setText(s4);
                                                                        }
                                                                    }
                                                                });

                                                                // txt4.setText(taskk.task_deadline);
                                                                txt5.setText("X");

                                                                txt1.setLayoutParams(params1);
                                                                txt2.setLayoutParams(params1);
                                                                txt3.setLayoutParams(params1);
                                                                txt4.setLayoutParams(params1);
                                                                txt5.setLayoutParams(params1);

                                                                row.addView(txt1);
                                                                row.addView(txt2);
                                                                row.addView(txt3);
                                                                row.addView(txt4);
                                                                row.addView(txt5);

                                                                table.addView(row);


                                                            }
                                                            System.out.println("--------------------------------------------------------");
                                                            System.out.println(arraytask.toString());


                                                        } catch (JSONException e) {
                                                            e.printStackTrace();
                                                        }

                                                    }
                                                }
                                            }, new Response.ErrorListener() {
                                        @Override
                                        public void onErrorResponse(VolleyError error) {
                                            error.printStackTrace();
                                        }
                                    });

                                    mQueue.add(request2);
                                    //-----------------------------------------------------------------------------------------------------------------

                                }
                            }, new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Log.e("VOLLEY", error.toString());
                                }
                            }) {
                                @Override
                                public String getBodyContentType() {
                                    return "application/json; charset=utf-8";
                                }

                                @Override
                                public byte[] getBody() throws AuthFailureError {
                                    try {
                                        return requestBody == null ? null : requestBody.getBytes("utf-8");
                                    } catch (UnsupportedEncodingException uee) {
                                        VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s", requestBody, "utf-8");
                                        return null;
                                    }
                                }

                                @Override
                                protected Response<String> parseNetworkResponse(NetworkResponse response) {
                                    String responseString = "";
                                    if (response != null) {
                                        responseString = String.valueOf(response.statusCode);
                                        // can get more details such as response.headers
                                    }
                                    return Response.success(responseString, HttpHeaderParser.parseCacheHeaders(response));
                                }
                            };

                            requestQueue.add(stringRequest);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        //---------------------------------------------------------------------------------------------------
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

                builder.show();

            }
        });

//------------------------------------------------------------------------------------------------------------------------

    }


    // Decodes business json into business model object
    public  ListNames fromJson(JSONObject jsonObject) {
        ListNames b = new ListNames();
        // Deserialize json into object fields
        try {

            b.list_id= jsonObject.getInt("list_id");
            b.list_name = jsonObject.getString("list_name");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object

        return b;
    }

    public Task fromJsonTask(JSONObject jsonObject) {
        Task b = new Task();
        // Deserialize json into object fields
        try {

            b.task_id= jsonObject.getInt("task_id");
            b.task_name = jsonObject.getString("task_name");
            b.task_description = jsonObject.getString("task_description");
            b.task_status = jsonObject.getString("task_status");
            b.task_deadline = jsonObject.getString("task_deadline");
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
        // Return new object

        return b;
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






}


