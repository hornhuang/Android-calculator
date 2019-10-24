package com.example.a30797.myapplication;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.a30797.myapplication.compute.Calculate;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    //用于存储edit中的数
    String edit = new String();

    //用于判断括号合法性
    int bracket_flag01 = 0 ;

    /**
     * 用于匹配运算模式
     */
    int flag01  = 0 ;

    //用于查看是否反复点击同一模式按钮
    int flag02 = 0 ;

    //用于查看 前一轮是否为转换
    int flag03 = 0 ;

    //记录框
    TextView tv_save_up;
    TextView tv_save_dowm;

    //输出框
    TextView tv;

    //网格布局 按钮
    Button gri_bn[] = new Button[20];

    //网格布局
    GridLayout gridLayout;

    //定义4*4 16个按钮文本数组
    String[] chars = new String[]{
            "C","←","%","+",
            "7","8","9","--",
            "4","5","6","*",
            "1","2","3","/",
            "( )","0",".","=",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        iniView();
        iniClick();
    }

    private void iniView() {
        //设置输出框
        tv = findViewById(R.id.textview);
        //设置输出框
        tv_save_up = findViewById(R.id.textview_up);
        //设置输出框
        tv_save_dowm = findViewById(R.id.textview_down);
        gridLayout = findViewById(R.id.root);
    }

    /**
     * 点击事件
     */
    private void iniClick(){
        for( int i = 0 ; i < chars.length ; i++){
            //chat用于传递i到监听事件
            final int chat = i;
            gri_bn[i] = new Button(this);
            //设置按钮的字号大小
            gri_bn[i].setText(chars[i]);
            //设置数字按钮颜色
            gri_bn[i].setTextSize(40);
            //按钮四周空白区域
            gri_bn[i].setTextColor(Color.WHITE);
            //设置按钮背景
            gri_bn[i].setPadding(43 , 43 , 43 , 43);
            //指定按钮组所在行
            gri_bn[i].setBackgroundResource(R.drawable.shape_button_01);
            GridLayout.Spec rowSpec = GridLayout.spec(i / 4 + 2);
            //指定数组所在列
            GridLayout.Spec columeSpec = GridLayout.spec(i % 4);
            GridLayout.LayoutParams params = new GridLayout.LayoutParams(
                    rowSpec , columeSpec);
            gridLayout.addView(gri_bn[i] , params);
            //设置监听事件
            gri_bn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if( chars[chat] .equals("=") ){
                        String presentString = tv.getText().toString();
                        char endChar[] = presentString.substring(presentString.length()-1,presentString.length()).toCharArray();
                        if ( tv.getText().toString().length() == 0 ){
                            Toast.makeText(getApplicationContext(),"请输入计算式",Toast.LENGTH_SHORT).show();
                        }else if ( bracket_flag01 != 0 ){
                            Toast.makeText(getApplicationContext(),"请正确输入",Toast.LENGTH_SHORT).show();
                        }else if ( '0' > endChar[0] || '9' < endChar[0] ){
                            Toast.makeText(getApplicationContext(),"请不要以运算符结尾",Toast.LENGTH_SHORT).show();
                        }else if ( flag02 != 1 ){//防止反复点击
                            edit = tv.getText().toString();//保存文本框中内容 用于传递
                            StringBuffer stringBuffer = new StringBuffer();
                            //拼接字符串形成 # 结尾
                            stringBuffer.append(edit);
                            stringBuffer.append('#');
                            edit = stringBuffer.toString();
                            //选择操作类型
                            flag01 = 1;
                            //设置 上方存储显示栏 文字
                            tv_save_up.setText(tv_save_dowm.getText().toString());
                            tv_save_dowm.setText(tv.getText().toString());
                            //开始配对并运算
                            ChooseFlag(tv);
                            tv.requestFocus();//焦点回到文本框
                            flag02 = 1 ;
                            bracket_flag01 = 0 ;
                        }else {
                            Toast.makeText(getApplicationContext(),"请勿仿佛点击",Toast.LENGTH_SHORT).show();
                        }

                    }else if( chars[chat] == "C" ){
                        flag02 = 0 ;
                        tv.setText("");
                        bracket_flag01 = 0 ;
                    }else if( chars[chat] == "←" ){
                        if ( tv.getText().toString().length() > 0 ){//删除前判断“（ ）”
                            char array[] = tv.getText().toString().toCharArray();
                            if (array[array.length - 1 ] == '('){
                                bracket_flag01 -- ;
                            }else if ( array[array.length - 1 ] == ')' ){
                                bracket_flag01 ++ ;
                            }
                            tv.setText(tv.getText().toString().substring(0,
                                    tv.getText().toString().length() - 1));
                            flag02 = 0 ;
                        }
                    }else if( chars[chat] == "( )" ){
                        if ( tv.getText().toString().length() > 0 ){
                            char array[] = tv.getText().toString().toCharArray();
                            Log.e("789",String.valueOf(array[ array.length - 1 ]));
                            if ( ( array[array.length - 1 ] > '9' ||  array[ array.length - 1 ] < '0' )
                                    //判断前一个字符类型
                                    && array[  array.length - 1 ] != ')' ){
                                tv.append( "(" );
                                bracket_flag01 ++ ;
                                flag02 = 0 ;
                            }else if ( bracket_flag01 != 0 ) {
                                tv.append( ")" );
                                bracket_flag01 -- ;
                                flag02 = 0 ;
                            }
                        }else{
                            tv.append( "(" );
                            bracket_flag01 ++ ;
                            flag02 = 0 ;
                        }
                    }else if ( (chars[chat]).toCharArray()[0] <= '9' &&  (chars[chat]).toCharArray()[0] >= '0' ) {
                        if (flag02 == 1){
                            flag02 = 0 ;
                            tv.setText("");
                        }
                        tv.append(chars[chat]);
                    }else if ( tv.getText().toString().length() > 0 ){
                        char array[] = tv.getText().toString().toCharArray();
                        //判断前一个字符类型
                        if ( ( array[ array.length - 1 ] <= '9' &&  array[ array.length - 1 ] >= '0' )
                                || array[ array.length - 1 ] == ')' ) {
                            if( chars[chat].equals("--") ){
                                tv.append("-");
                                flag02 = 0 ;
                            }else{
                                tv.append( chars[chat] );
                                flag02 = 0 ;
                            }
                        }
                    }
                }
            });
        }
        tv_save_up.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv_save_up.getText());
                tv.requestFocus();//焦点回到文本框
            }
        });
        tv_save_dowm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv.setText(tv_save_dowm.getText());
                tv.requestFocus();//焦点回到文本框
            }
        });
    }

    /**
     * 判断当前操作
     */
    public void ChooseFlag(TextView jt){
        Calculate text = new Calculate();
        text.toStringArray(tv.getText().toString());
        text.outPutCalculate();
        try {
            text.toPostfix();
            jt.setText(text.postfix());
        }catch (Exception e){
            Toast.makeText(MainActivity.this,"请正确输入",Toast.LENGTH_SHORT).show();
        }
    }

}
