package audio.learn.com.braintrainer;

import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
   private TextView timer, question, answeredCorrect,result;
   private Button optionsArr[] = new Button[4], startAgainBtn;
   private TableLayout answerkey;
   private int answer,correctlyAnswered=0,questionPlayed=0;
   private final int totalQuestions = 15;
   private CountDownTimer counter;
   private int genRandomAns(){ return (int)(Math.random() * 50 + 10);}

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        timer = findViewById(R.id.timer);
        question  = findViewById(R.id.question);
        answeredCorrect = findViewById(R.id.answered);
        result= findViewById(R.id.result);
    }

    // onClick Functions for buttons
    public void go(View view){
        view.setVisibility(View.INVISIBLE);
        startGame();
    }
    public void postOption(View view){
       int value = 0;
       try {
           value = Integer.parseInt((String) ((Button) view).getText());
       }catch(Exception e){Toast.makeText(getApplicationContext(),"Sorry, internal error", Toast.LENGTH_SHORT).show();}
        if(value==answer) ++correctlyAnswered;
        questionPlayed++;
        if (questionPlayed>=15){
            onEnd();
            return;
        }
        Log.i("Question Played",String.valueOf(questionPlayed));
        answeredCorrect.setText(correctlyAnswered+"/"+totalQuestions);
        generateAnswer();
        setOption();
    }
    public void startAgain(View view){
       startGame();
        timer.setEnabled(true);
        view.setVisibility(View.GONE);
    }

    // Helper Functions

    // Function to set the options
    private void startGame(){
        answerkey = findViewById(R.id.tableLayout);
        result.setText("");
        result.setVisibility(View.GONE);
        answerkey.setVisibility(View.VISIBLE);
        timer.setVisibility(View.VISIBLE);
        question.setVisibility(View.VISIBLE);
        answeredCorrect.setVisibility(View.VISIBLE);
        answeredCorrect.setText(correctlyAnswered+"/"+totalQuestions);
        optionsArr[0] = findViewById(R.id.op1);
        optionsArr[1] = findViewById(R.id.op2);
        optionsArr[2] = findViewById(R.id.op3);
        optionsArr[3] = findViewById(R.id.op4);
        startAgainBtn = findViewById(R.id.restart);
        startTimer();
        generateAnswer();
        setOption();
    }
    private void setOption() {
        int randomPos = (int) Math.floor(Math.random() * 4);
        for (int i = 0; i < 4; i++) {
            if (randomPos == i) optionsArr[i].setText(String.valueOf(answer));
            else optionsArr[i].setText(String.valueOf(genRandomAns()));
        }
    }
    //Timer function for start the timer
    void startTimer(){
        counter = new CountDownTimer(30*1000,1000){
            public void onTick(long millisUntilFinished){
                timer.setText(millisUntilFinished/1000+"s");
            }
            public void onFinish(){
                timer.setText("0s");
                onEnd();
            }
        };
        counter.start();
    }
    private void generateAnswer(){
        answer= genRandomAns();
        int questionFormer = (int) (Math.random()*answer);
        question.setText((answer-questionFormer) +"+"+questionFormer);
    }
    private void onEnd(){
       if (timer.getText()!="0s") timer.setEnabled(false);
       counter.cancel();
       answerkey.setVisibility(View.GONE);
       result.setVisibility(View.VISIBLE);
       result.setText("You have correctly answered "+correctlyAnswered+" out of "+totalQuestions+" questions");
       startAgainBtn.setVisibility(View.VISIBLE);
       questionPlayed = 0;
       correctlyAnswered = 0;
    }
}
