package com.example.myquizapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

//implements View.OnClickListener Xử lý sự kiện khi người dùng tương tác
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    //Khai báo các biến giao diện
    TextView total_question, question;
    Button ansA, ansB, ansC, ansD;
    Button btnSubmit;

    //Khai báo các biến tính điểm
    int score = 0;
    int TongCauHoi = QuestionAnswer.question.length;
    int viTriCauHoiHienTai = 0;
    String selectedAnswer = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Ánh xạ
        total_question = findViewById(R.id.total_question);
        question = findViewById(R.id.question);
        ansA = findViewById(R.id.ans_A);
        ansB = findViewById(R.id.ans_B);
        ansC = findViewById(R.id.ans_C);
        ansD = findViewById(R.id.ans_D);
        btnSubmit = findViewById(R.id.btnSubmit);

        //Thiết lập lắng nghe click vào tất cả các nút

        ansA.setOnClickListener(this);
        ansB.setOnClickListener(this);
        ansC.setOnClickListener(this);
        ansD.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);

        //Hiển thị tổng số câu hỏi
        total_question.setText("Tổng số câu hỏi: " + TongCauHoi);

        loadNewQuestion();

    }


    @Override
    public void onClick(View view) {
        //Đổi màu tất cả các button
        ansA.setBackgroundColor(Color.WHITE);
        ansB.setBackgroundColor(Color.WHITE);
        ansC.setBackgroundColor(Color.WHITE);
        ansD.setBackgroundColor(Color.WHITE);

        //Lay về tất cả button
        Button clickedButton = (Button) view;

        //Nếu mà button được click là nút Submit
        if (clickedButton.getId() == R.id.btnSubmit) {

            //Nếu mà Đáp án chọn là đáp án đúng thì điểm sẽ tăng lên
            if (selectedAnswer.equals(QuestionAnswer.correctAnswers[viTriCauHoiHienTai])) {
                score++;
            }
            viTriCauHoiHienTai++;
            loadNewQuestion();


        } else {
            //Nếu mà button được click là các nút đáp án
            //Lấy về nội dung của button
            selectedAnswer = clickedButton.getText().toString();
            //Đổi màu button click
            clickedButton.setBackgroundColor(Color.RED);


        }
    }

    void loadNewQuestion() {
        //Khi mà đến câu hỏi cuối cùng thì
        if (viTriCauHoiHienTai == TongCauHoi) {
            //Gọi hàm finishQuiz
            finishQuiz();
            return;
        }

        //Hiển thị nội dung câu hỏi
        question.setText(QuestionAnswer.question[viTriCauHoiHienTai]);
        //Hiển thị nội dung đáp án
        ansA.setText(QuestionAnswer.choices[viTriCauHoiHienTai][0]);
        ansB.setText(QuestionAnswer.choices[viTriCauHoiHienTai][1]);
        ansC.setText(QuestionAnswer.choices[viTriCauHoiHienTai][2]);
        ansD.setText(QuestionAnswer.choices[viTriCauHoiHienTai][3]);


    }

    //Hàm
    void finishQuiz() {
        String passStatus = "";
        //Nếu điểm thi làm được đúng hơn 50% thì qua môn
        if (score > TongCauHoi * 0.5) {
            passStatus = "Qua môn";
        } else {
            passStatus = "Trượt môn";
        }

        AlertDialog.Builder al = new AlertDialog.Builder(MainActivity.this)
                .setTitle(passStatus)
                .setMessage("Điểm của bạn là " + score + " / " + TongCauHoi)
                .setPositiveButton("Chơi lại", (dialogInterface, i) -> restartQuiz())
                .setCancelable(false);
        al.create().show();

    }

    //Tạo hàm khi muốn chơi lại
    private void restartQuiz() {
        score = 0;
        viTriCauHoiHienTai = 0;
        loadNewQuestion();
    }


}