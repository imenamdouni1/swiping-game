package com.example.swipinggame;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.ColorStateList;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


import android.os.CountDownTimer;
import android.widget.Button;
import android.widget.TextView;
import android.content.Context;

public class MainActivity extends AppCompatActivity {
    public boolean gameEnded=false;
     int i=0;
    int nbswipes=0;
    public int counter=30;
    Button button;
    TextView textView;
    // Creating RecyclerView
    private RecyclerView recyclerView;
    // Creating a ArrayList of type Modelclass
    private List<Modelclass> barsColor;

    // Alert dialog
    AlertDialog.Builder alertDialog;
    private Adapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        i=0;
        nbswipes=0;
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ProgressBar mProgressBar;
        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
        mProgressBar.setProgress(i);
        mProgressBar.setVisibility(View.GONE);
        button= (Button) findViewById(R.id.button);
        textView= (TextView) findViewById(R.id.textView);
        textView.setText("black= left || purple= right (you have 30 seconds)");
        button.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                mProgressBar.setVisibility(View.VISIBLE);
                button.setVisibility(View.GONE);
                textView.setVisibility(View.GONE);
                bar();
                //progress bar code

                //ProgressBar mProgressBar;
                //mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
                //mProgressBar.setProgress(i);
                // end ---
                new CountDownTimer(30000, 1000){
                    public void onTick(long millisUntilFinished){
                        textView.setText(String.valueOf(counter));
                        counter--;
                        i++;
                        mProgressBar.setProgress((int)i*100/(30000/1000));

                    }
                    public  void onFinish(){
                        //textView.setTextColor();

                        //adapter.notifyDataSetChanged();
                        if (gameEnded==false)
                        {textView.setText("FINISH!!");
                            endgame();
                            alertDialog.show();
                            mProgressBar.setVisibility(View.GONE);
                        }

                    }

                }.start();
            }
        });
//HEREEE!!!!

    }

    ItemTouchHelper.SimpleCallback simpleCallback= new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull @NotNull RecyclerView recyclerView, @NonNull @NotNull RecyclerView.ViewHolder viewHolder, @NonNull @NotNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull @NotNull RecyclerView.ViewHolder viewHolder, int direction) {
            // get the position of the swiped bar
            int position = viewHolder.getPosition();
            switch (direction) {
                // Right side is for Yellow
                case ItemTouchHelper.LEFT: {
                    if ((barsColor.get(position).getColor()).equals("Red")) {
                        barsColor.remove(position);
                        adapter.notifyDataSetChanged();
                    } else {
                        endthegame();
                        adapter.notifyDataSetChanged();
                        alertDialog.show();
                        ProgressBar mProgressBar;
                        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
                        mProgressBar.setVisibility(View.GONE);
                    }
                    break;
                }
                // Left side is for Red
                case ItemTouchHelper.RIGHT: {
                    if ((barsColor.get(position).getColor()).equals("Yellow")) {
                        barsColor.remove(position);
                        adapter.notifyDataSetChanged();
                    } else {
                        endthegame();
                        adapter.notifyDataSetChanged();
                        alertDialog.show();
                        ProgressBar mProgressBar;
                        mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
                        mProgressBar.setVisibility(View.GONE);
                    }
                    break;
                }

            }
            nbswipes++;
            if(nbswipes==15)
            {
                ProgressBar mProgressBar;
                mProgressBar=(ProgressBar)findViewById(R.id.progressbar);
                mProgressBar.setVisibility(View.GONE);

                wingame();
                alertDialog.show();


            }

        }
    };

    public void wingame(){
        gameEnded=true;
        alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage("YOU WIN ! score= "+(30-counter)+" seconds ").setPositiveButton("Replay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                recreate();
            }
        }).setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Later!", Toast.LENGTH_SHORT).show();
                System.exit(0);
                //finish();

            }
        });
        alertDialog.create();
    }
    // Shows game ended dialog
    private void endthegame()
    {
        gameEnded=true;
        alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage("Oopa! Wrong side! Try Again! ").setPositiveButton("Try Again", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Try again", Toast.LENGTH_SHORT).show();
                recreate();
            }
        }).setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Later!", Toast.LENGTH_SHORT).show();
                System.exit(0);
                //finish();

            }
        });
        alertDialog.create();

    }
    private void endgame()
    {
        gameEnded=true;
        alertDialog=new AlertDialog.Builder(this);
        alertDialog.setMessage("Time is up ! ").setPositiveButton("Replay", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Try Again", Toast.LENGTH_SHORT).show();
                recreate();
            }
        }).setNegativeButton("Later", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(MainActivity.this, "Later!", Toast.LENGTH_SHORT).show();
                System.exit(0);
                //finish();

            }
        });
        alertDialog.create();
    }

    private void bar () {
        // Adding elements to the barsColor
        barsColor = new ArrayList<>();
        Random random = new Random();
        // Add 15 bars to the RecyclerView
        for (int i = 0; i < 15; i++) {
            // Generate a random number
            int n = random.nextInt(2);
            // Giving the color for the
            // bar based on the random number
            if (n == 0) {
                barsColor.add(new Modelclass("Yellow"));
            } else {
                barsColor.add(new Modelclass("Red"));
            }
        }

        // Finding the RecyclerView by it's ID
        recyclerView = findViewById(R.id.recyclerview);

        // Creating an Adapter Object
        adapter = new Adapter(this, barsColor);

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Add ItemTouchHelper to the recyclerView
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);

        adapter.notifyDataSetChanged();
    }
}

