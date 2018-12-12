package com.example.a30797.myapplication.compute;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Stack;

public class Calculate {

    private Stack<String> num; //后缀用栈 中转后数字栈

    private Stack<String> charnum;//中转后字符栈

    private String []calculate;//存字符串数组

    private int calculateLength;//字符串数组长度

    public Calculate() {
        // TODO Auto-generated constructor stub
        num = new Stack<>(); //后缀用栈 中转后数字栈

        charnum = new Stack<>();//中转后字符栈

        calculate = new String[1000];//存字符串数组

        calculateLength = 0 ;//字符串数组长度
    }

    //转字符串数组
    public void toStringArray(String input) {
        boolean pointFalg = false;
        char charArray[] = input.toCharArray();
        double number = 0;//用于导入多位数
        int j = 0 ;//用于计入当前字符串数组的位数
        int sizeOfArray = charArray.length;
        int pointBelow =1
                ;
        for(int i = 0 ; i < sizeOfArray ; i++){
            if(charArray[i] == '('){
                calculate[j++] = "(";

            }else if(charArray[i] == ')'){
                calculate[j++] = ")";

            }else if (charArray[i] == '+') {
                calculate[j++] = "+";

            }else if (charArray[i] == '-') {
                calculate[j++] = "-";

            }else if (charArray[i] == '*') {
                calculate[j++] = "*";

            }else if (charArray[i] == '/') {
                calculate[j++] = "/";

            }else if (charArray[i] == '%') {
                calculate[j++] = "%";

            }else if (charArray[i] == '#') {
                calculate[j++] = "#";

            }else if (charArray[i] == '.') {
                System.out.println("find new . :");
                pointBelow = 1;
//				sizeOfArray -- ;
                pointFalg = true;
            }else {
                String str=String.valueOf(charArray[i]);
                if (pointFalg == false) {
                    System.out.println("1:" + number);
                    number = number * 10 + Double.parseDouble(str);
                }else {
                    System.out.println("2:" + charArray[i]);
                    number = number + Double.parseDouble(str) * Math.pow(0.1, pointBelow);
                }
                System.out.println("3:" + number + "i==" + i);
                if( (i + 1) == sizeOfArray ||( charArray[i+1] < '0' || charArray[i+1] > '9' ) && charArray[i+1] != '.'){

                    if ( (i + 1) != sizeOfArray && charArray[i+1] == ' ') {
                        i++;
                    }
                    calculate[j++] = String.valueOf(number);
                    System.out.println("number:" + number);
                    number = 0 ;
                    pointFalg = false;
                }
            }
            System.out.println("---z->" + calculate[i]);
        }
        calculateLength = j-- ;//不--会将‘#’存入
    }

    public void outPutCalculate() {
        for(int i = 0 ;  i < calculateLength ; i ++ ){
            System.out.println(calculate[i]);
        }
    }

    public void CalculateToZero() {
        for(int i = 0 ;  i < calculateLength ; i ++ ){
            calculate[i]= calculate[999] ;
        }
    }

    //中缀转后缀
    public void toPostfix() {
        // TODO Auto-generated method stub
        System.out.println("789");
        int sum = 0 ;//用于记入”（）“总个数
        int j = 0 ;//用于读到”）“时循环出栈
        String outStack = null;
        charnum.push(null);
        System.out.println(calculateLength);
        for( int i = 0 ; i < calculateLength ; i ++){
            System.out.println(calculate[i]);//-----------------------------
            if ( calculate[i].equals("(")) {
                charnum.push(calculate[i]);
                System.out.println("1-1  charpush  " + calculate[i]);//-----------------------------
                sum ++ ;
            }else if ( calculate[i].equals(")") ) {
                System.out.println("2-1  charpush  " + calculate[i]);//-----------------------------
                outStack = charnum.pop();//进入前先出一个
                System.out.println("2-1  charpush  " + outStack);//-----------------------------
                while ( !outStack.equals("(") ){
                    System.out.println("2-2  charpush  " + outStack);//-----------------------------
                    num.push(outStack);
                    outStack = charnum.pop();
                }//最后一次outStack正好接到”（“不入栈
                System.out.println("qiangxing 1 = " + outStack );
//					outStack = charnum.pop();
                System.out.println("qiangxing 2 = " + outStack );
                sum ++ ;
            }else if (calculate[i].equals("*")) {
                outStack = charnum.pop();
                charnum.push(outStack);
                System.out.println("3-2  charpush  " + outStack);//-----------------------------
                while( ( outStack == "*" || outStack == "/" || outStack == "%" ) && !(outStack == null) ){
                    System.out.println("3-1  charpush  " + outStack);//-----------------------------
                    num.push(outStack);
                    charnum.pop();//由于前第三行又将outStack存入栈中，座椅此处再次弹出
                    outStack = charnum.pop();
                    charnum.push(outStack);

                }
                System.out.println("3-3  charpush  " + outStack);//-----------------------------
                charnum.push("*");
            }else if (calculate[i].equals("%")) {
                outStack = charnum.pop();
                charnum.push(outStack);
                System.out.println("3-2  charpush  " + outStack);//-----------------------------
                while( ( outStack == "*" || outStack == "/" || outStack == "%" ) && !(outStack == null) ){
                    System.out.println("3-1  charpush  " + outStack);//-----------------------------
                    num.push(outStack);
                    charnum.pop();//由于前第三行又将outStack存入栈中，座椅此处再次弹出
                    outStack = charnum.pop();
                    charnum.push(outStack);

                }
                System.out.println("3-3  charpush  " + outStack);//-----------------------------
                charnum.push("%");
            }else if (calculate[i].equals("/")) {
                System.out.println("5-1-0  charpush  " + "1-1-1-1-1-1-1-1");//-----------------------------
                outStack = charnum.pop();
                System.out.println("5-1-1  charpush  " + "2-2-2-2-2-2-22-2");//-----------------------------
                charnum.push(outStack);
                System.out.println("4-1  charpush  " + outStack);//-----------------------------
                while( ( outStack == "*" || outStack == "/" || outStack == "%") && !(outStack == null) ){
                    System.out.println("4-2  charpush  " + outStack);//-----------------------------
                    num.push(outStack);
                    charnum.pop();//由于前第三行又将outStack存入栈中，座椅此处再次弹出
                    outStack = charnum.pop();
                    charnum.push(outStack);
                }
                System.out.println("4-3  charpush  " + outStack);//-----------------------------
                System.out.println("5-1-2  charpush  " + outStack);//-----------------------------
                charnum.push("/");
            }else if (calculate[i].equals("+")) {
                outStack = charnum.pop();
                charnum.push(outStack);
                System.out.println("5-1  charpush  " + outStack);//-----------------------------
                while( !(outStack=="(") && !(outStack == null) ){
                    System.out.println("5-2  charpush  " + outStack);//-----------------------------
                    num.push(outStack);
                    charnum.pop();
                    outStack = charnum.pop();
                    charnum.push(outStack);
                }
                System.out.println("5-3  charpush  " + outStack);//-----------------------------
                charnum.push("+");
            }else if (calculate[i].equals("-")) {
                outStack = charnum.pop();
                charnum.push(outStack);
                System.out.println("6-1  charpush  " + outStack);//-----------------------------
                while(  !(outStack=="(") && !(outStack == null)  ){
                    System.out.println("6-2  charpush  " + outStack);//-----------------------------
                    num.push(outStack);
                    charnum.pop();
                    outStack = charnum.pop();
                    charnum.push(outStack);
                }
                System.out.println("6-3  charpush  " + outStack);//-----------------------------
                charnum.push("-");
            }else {
                System.out.println("7-7    " + calculate[i]);
                num.push(calculate[i]);
            }
        }
        System.out.println("匹配结束" + outStack);
        outStack = charnum.pop();
        System.out.println("finish 1 == " + outStack);
        while ( outStack != null ) {
            num.push(outStack);
            outStack = charnum.pop();
            System.out.println("finish 2 == " + outStack);
        }
        calculateLength  = calculateLength - sum ;
        System.out.println( "0.0.0.0  charpush  " );//-----------------------------
        System.out.println("sum = " + sum + " calculate = " +
                calculateLength + "calculateLength-sum = " + (calculateLength-sum));
        System.out.println("over ~~~~~0 ");
        Stack<String> zanshi = new Stack<>();
//			num.pop();
        for(int i = 0 ; i < calculateLength ; i ++ ){
            zanshi.push(num.pop());
//				System.out.println(num.pop());
        }
        CalculateToZero();
        System.out.println("over ~~~~~1 ");
        for(int i = 0 ; i < calculateLength ;i ++ ){
            calculate[i] = zanshi.pop();
        }
        System.out.println("over ~~~~~2 ");
        for(int i = 0 ; i < calculateLength ;i ++ ){
            System.out.println(calculate[i]);
        }
        System.out.println("over ~~~~~3 ");
//			num.push("#");
    }

    //后缀计算
    public String postfix() {
        BigDecimal a  , b ;//栈中弹出的两数
        BigDecimal sum ;//求两数运算
        for (int i = 0; i < calculateLength ; i++ ) {
//				System.out.println("目前符号：" + calculate[i]);
            if (i == 0) {
                num.push(calculate[i]);
            }else if (calculate[i].equals("+") ) {
                b = new BigDecimal(num.pop());
                a = new BigDecimal(num.pop());
                sum = a.add(b) ;
                num.push(String.valueOf(sum));

            }else if (calculate[i].equals("-") ) {
                b = new BigDecimal(num.pop());
                a = new BigDecimal(num.pop());
                sum = a.subtract(b) ;
                num.push(String.valueOf(sum));

            }else if (calculate[i].equals("*") ) {
                b = new BigDecimal(num.pop());
                a = new BigDecimal(num.pop());
                sum = a.multiply(b) ;
                num.push(String.valueOf(sum));

            }else if (calculate[i].equals("/") ) {
                b = new BigDecimal(num.pop());
                a = new BigDecimal(num.pop());
                sum = a.divide(b,10,RoundingMode.HALF_UP) ;
                num.push(String.valueOf(sum));

            }else if (calculate[i].equals("%") ) {
                b = new BigDecimal(num.pop());
                a = new BigDecimal(num.pop());
                sum = a.divideAndRemainder(b)[1] ;
                num.push(String.valueOf(sum));
            }else {
                num.push(calculate[i]);
            }
        }
        return num.pop();
    }

}
