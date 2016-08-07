package com.example.android.hangmanfinal;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;



public class MainActivity extends AppCompatActivity {


    private GridView letters;
    private LAdapter lAdapt;

    private String[] database;
    private Random rando;
    private String currentWord;
    private LinearLayout wLayout;
    private TextView[] cViews;
    public int g;

    private ImageView[] arr;
    private int numParts=6;
    private int currentPart;
    private int numChars;
    private int numCorrect;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Resources res = getResources();
        database = res.getStringArray(R.array.words);


        rando = new Random();
        currentWord = "";

        wLayout = (LinearLayout)findViewById(R.id.word);
        letters = (GridView)findViewById(R.id.letters);


        arr = new ImageView[numParts];
        arr[0] = (ImageView)findViewById(R.id.head);
        arr[1] = (ImageView)findViewById(R.id.body);
        arr[2] = (ImageView)findViewById(R.id.arm1);
        arr[3] = (ImageView)findViewById(R.id.arm2);
        arr[4] = (ImageView)findViewById(R.id.leg1);
        arr[5] = (ImageView)findViewById(R.id.leg2);

     pop();
        lAdapt=new LAdapter(this);
        letters.setAdapter(lAdapt);
    //    play();
    }
    //this fuction displays an alert dialog box regarding the instructions of the game.
    private void pop(){
        AlertDialog.Builder start = new AlertDialog.Builder(this);
        start.setTitle("THE ADVANCED HANGMAN - INSTRUCTIONS");
        start.setMessage("The advanced hangman is an advanced version of the traditional hangman game. \nThe objective of the game is to guess the correct word within six chances.\nThis game is like a treasure hunt game. Two hints will be provided. \n\nHint 1: when the player makes two wrong guesses. This hint will be a one vague one. \n\nHint 2: when the player makes four wrong guesses. This hint will be a sort of giveaway. \n\n NOTE : The hints appear for a short period of time. Be attentive.");
        start.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        MainActivity.this.play();       //is pressed ok, play function is called.
                    }
                });
        start.show();
    }
    //this function is the main backbone of the game.
    private void play() {
        //play a new game

        g=rando.nextInt(database.length);
        String newWord = database[g];
        while(newWord.equals(currentWord))
        {
            g=rando.nextInt(database.length);   //random word is chosen diffferent from prev. word.
            newWord = database[g];
        }

        currentWord = newWord;  //update current word.


        cViews = new TextView[currentWord.length()];
        wLayout.removeAllViews();
        int i=0;
        while(i!=currentWord.length()){
            cViews[i] = new TextView(this);
            cViews[i].setText("" + currentWord.charAt(i));
            LayoutParams lp=new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
            cViews[i].setLayoutParams(lp);

            cViews[i].setGravity(Gravity.CENTER);
            cViews[i].setTextColor(Color.WHITE);        //word is present but in white color.
            cViews[i].setBackgroundResource(R.drawable.l_bg);
            //add to layout
            wLayout.addView(cViews[i]);
            i++;
        }

        lAdapt=new LAdapter(this);
        letters.setAdapter(lAdapt);     //adding letter butttons dynamically.

        currentPart=0;
        numChars=currentWord.length();
        numCorrect=0;

        for(int p = 0; p < numParts; p++) {
            arr[p].setVisibility(View.INVISIBLE);       //set all body parts to invisible
        }

      //  pop();

    }
    //function when button is pressed.
    public void lPressed(View v) {
        //user has pressed a letter to guess

        String ltr=((TextView)v).getText().toString();
        char letterChar = ltr.charAt(0);

        v.setEnabled(false);
      //  v.setBackgroundResource(R.drawable.l_down);

        int hang=0;
        int k=0;
        while(k!=currentWord.length()){
            if(currentWord.charAt(k)==letterChar){
                hang=1;
                numCorrect++;
                cViews[k].setTextColor(Color.RED);  //change color to red.
            }
            k++;
        }

        String t=currentWord;
        if(hang==1){
            if(numCorrect==numChars){

                int numLetters = letters.getChildCount();
                for (int i = 0; i < numLetters; i++) {
                    letters.getChildAt(i).setEnabled(false);
                }
                play();
                //dialog box that you won
                AlertDialog.Builder wins = new AlertDialog.Builder(this);
                wins.setTitle("CONGRATS !!!");
                wins.setMessage("You win!\n\nThe answer was : " + t);
                wins.setPositiveButton("Play Again",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.play();
                            }
                        });
                wins.setNegativeButton("Exit",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                MainActivity.this.finish();
                            }
                        });
                wins.show();
            }

        }
    else if (currentPart < numParts) {

            if(currentPart==1){     //adding hint 1

                switch(g) {
                    case 0:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Have you heard of CrANBERRies?", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Also known as city of David", Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Dalai Lama is bad (No offence) ", Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getApplicationContext(), "Hint 1 : WELL ! A makING TON", Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getApplicationContext(), "Hint 1 : According to sources, Hamsters live near dam", Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Student wish pumkin had fallen !!!", Toast.LENGTH_SHORT).show();
                        break;
                    case 6:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Think this anwer relatively !!", Toast.LENGTH_SHORT).show();
                        break;
                    case 7:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Nearnight is antonym of ...", Toast.LENGTH_SHORT).show();
                        break;
                    case 8:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Eureka !!!", Toast.LENGTH_SHORT).show();
                        break;
                    case 9:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Did you try pressing a button other than an alphabet !!!", Toast.LENGTH_SHORT).show();
                        break;
                    case 10:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Windowless !!!", Toast.LENGTH_SHORT).show();
                        break;
                    case 11:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Dora is fed up with Windows !!", Toast.LENGTH_SHORT).show();
                        break;
                    case 12:
                        Toast.makeText(getApplicationContext(), "Hint 1 : DEBI is AN eclyomologocalysticalisyisac.", Toast.LENGTH_SHORT).show();
                        break;
                    case 13:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Short form of 'generation too' !!!", Toast.LENGTH_SHORT).show();
                        break;
                    case 14:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Answer also matches with a Hindu goddess.", Toast.LENGTH_SHORT).show();
                        break;
                    case 15:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Beth bought a MAC !!", Toast.LENGTH_SHORT).show();
                        break;
                    case 16:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Let hamburger be eaten by Prince of Denmark !!", Toast.LENGTH_SHORT).show();
                        break;
                    case 17:
                        Toast.makeText(getApplicationContext(), "Hint 1 : King of vampires !!", Toast.LENGTH_SHORT).show();
                        break;
                    case 18:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Oath should be made as Hello is made !!!", Toast.LENGTH_SHORT).show();
                        break;
                    case 19:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Are you hungry. Then don't mock others !!!", Toast.LENGTH_SHORT).show();
                        break;
                    case 20:
                        Toast.makeText(getApplicationContext(), "Hint 1 : He was assassinated", Toast.LENGTH_SHORT).show();
                        break;
                    case 21:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Also known as Uncle Abe !!!", Toast.LENGTH_SHORT).show();
                        break;
                    case 22:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Is your BeltLoose?", Toast.LENGTH_SHORT).show();
                        break;
                    case 23:
                        Toast.makeText(getApplicationContext(), "Hint 1 : He was clever-and..he drank a lot of watel !!!", Toast.LENGTH_SHORT).show();
                        break;

                    case 24:
                        Toast.makeText(getApplicationContext(), "Hint 1 : Also known as Barry !!!", Toast.LENGTH_SHORT).show();
                        break;


                }
                    arr[currentPart].setVisibility(View.VISIBLE);
                currentPart++;
            }




        else if(currentPart==3){        //displaying hint 2
            if(g/5==0)
            {
                Toast.makeText(getApplicationContext(), "Hint 2 : A capital city!", Toast.LENGTH_SHORT).show();
            }
            else if(g/5==1)
            {
                Toast.makeText(getApplicationContext(), "Hint 2 : A Scientist!", Toast.LENGTH_SHORT).show();
            }
            else if(g/5==2)
            {
                Toast.makeText(getApplicationContext(), "Hint 2 : A Linux system!", Toast.LENGTH_SHORT).show();
            }
            else if(g/5==3)
            {
                Toast.makeText(getApplicationContext(), "Hint 2 : A novel!", Toast.LENGTH_SHORT).show();
            }
            else if(g/5==4)
            {
                Toast.makeText(getApplicationContext(), "Hint 2 : An American president!", Toast.LENGTH_SHORT).show();
            }
            arr[currentPart].setVisibility(View.VISIBLE);
            currentPart++;
        }
        else {
            arr[currentPart].setVisibility(View.VISIBLE);
            currentPart++;
        }
    }
        else{
            int numLetters = letters.getChildCount();
            for (int i = 0; i < numLetters; i++) {
                letters.getChildAt(i).setEnabled(false);
            }
            play();
            //dialog box displaying when u lose
            AlertDialog.Builder lose = new AlertDialog.Builder(this);
            lose.setTitle("TRY AGAIN");
            lose.setMessage("You lose!\n\nThe answer was : "+t);
            lose.setPositiveButton("Play Again",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.play();
                        }});

            lose.setNegativeButton("Exit",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            MainActivity.this.finish();
                        }});

            lose.show();
        }

    }

}
