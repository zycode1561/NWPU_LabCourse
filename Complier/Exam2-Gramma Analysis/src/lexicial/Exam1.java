package lexicial;

import com.sun.org.apache.xalan.internal.xsltc.compiler.util.Util;

import java.io.*;
import java.util.ArrayList;

/**
 * 词法分析程序
 *
 * @author zhangyun
 */
public class Exam1 {
    private static ArrayList<String> keyword;// 关键字
    private static ArrayList<String> assign;// 专用符号
    private static int[] state;// 状态
    private static String CRLF = "\r";//换行符，针对不同系统提高灵活性

    private static void init() {
        // 关键字
        keyword = new ArrayList<>();
        keyword.add("class");
        keyword.add("public");
        keyword.add("static");
        keyword.add("void");
        keyword.add("main");
        keyword.add("String");
        keyword.add("extends");
        keyword.add("return");
        keyword.add("int");
        keyword.add("boolean");
        keyword.add("if");
        keyword.add("else");
        keyword.add("while");
        keyword.add("System.out.println");
        keyword.add("length");
        keyword.add("true");
        keyword.add("false");
        keyword.add("this");
        keyword.add("new");
        // 专用符号
        assign = new ArrayList<>();
        assign.add("[");
        assign.add("]");
        assign.add("{");
        assign.add("}");
        assign.add("(");
        assign.add(")");
        assign.add(",");
        assign.add(";");
        assign.add("=");
        assign.add("&&");
        assign.add("<");
        assign.add("+");
        assign.add("-");
        assign.add("*");
        assign.add(".");
        assign.add("!");
        // 根据状态转换图得知有4个状态
        state = new int[4];
    }

    /**
     * @param c char
     * @return if char is digit
     */
    private static boolean isDigit(char c) {
        if (c >= '0' && c <= '9')
            return true;
        return false;
    }

    /**
     * @param c char
     * @return if char is letter
     */
    private static boolean isLetter(char c) {
        if ((c >= 'A' && c <= 'Z') || (c >= 'a' && c <= 'z'))
            return true;
        return false;
    }

    /**
     * @param c char
     * @return if it is char or digit
     */
    private static boolean isLetterOrDigit(char c) {
        return isDigit(c) || isLetter(c);
    }

    public static void main(String[] args) throws IOException {
        init();
        System.out.println("Please input the filename:");
        BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
        String input = "";
        FileWriter writer = new FileWriter("./tokenOut.txt");
        FileWriter errorWriter = new FileWriter("./error.txt");
        //获取输入文件名称
        input = bf.readLine();
        File file = new File(input);
        FileReader filereader = new FileReader(file);
        StringBuffer buffer = new StringBuffer();
        // 向tokenOut.txt写，换行根据系统不同修改CRLF即可
//        writer.write("read file :" + input + CRLF);
        BufferedReader reader = new BufferedReader(filereader);

        //行号
        int LineNum = 0;
        String currentLine = "";

        //读取数据
        while ((currentLine = reader.readLine()) != null) {
            System.out.println(currentLine);
            //读取后行号自增
            LineNum++;
            //转换为字符数组
            char[] charArr = currentLine.toCharArray();
            //遍历字符数组
            int i = 0;
            //i等于currentLine的长度时终止循环
            while (i < currentLine.length()) {
                //开始时状态为0
                int state = 0;
                //state为5时代表达到终止状态，结束循环
                while (state != 4) {
                    switch (state) {
                        //定义一个报错状态，出现错误后到达报错状态，之后前往结束状态，终止循环
                        case -1:
                            System.out.println("4");
                            writer.write("error: an error in line : " + LineNum + CRLF);
                            errorWriter.write("       ^ at line : " + LineNum + CRLF);
                            //每行只报一次错误，之后的可以不考虑，所以将i赋为字符串长度，跳到下一行
                            i = currentLine.length();
                            state = 4;
                        //初始状态0
                        case 0:
                            //末尾如果是空格，i++之后还会跳到这里来，需要换行了
                            if(i==currentLine.length()) {
                                state=4;
                                break;
                            }

                            if(i < currentLine.length()){
                                // 打印到控制台的信息为状态跳转的过程
                                // 读取到空格和制表符后，仍然在状态0
                                if (charArr[i] == ' ' || charArr[i] == '\t') {
                                    // start state
                                    System.out.print("0 -> ");
                                    state = 0;
                                }else if(charArr[i] == '&'){
                                    if (i + 1 < currentLine.length() && charArr[i + 1] == '&') {
                                        // 判断是否为两个&，如果是，最长匹配，返回
                                        i++;
                                        writer.write("assign -> &&"+CRLF);
                                        System.out.println("4");
                                        state = 4;
                                    }else {
                                        System.out.print("-1 -> ");
                                        writer.write("error type : unknown symbol\r\n");
                                        errorWriter.write("error type : unknown symbol\r\n");
                                        state = -1;
                                    }
//                                    i++;
                                }else if(isDigit(charArr[i])){
                                    //state num
                                    System.out.print("1 ->");
                                    // 因为后面还有可能有数字，所以不换行
                                    buffer.append("Digit -> ").append(charArr[i]);
                                    state = 1;
                                }else if(isLetter(charArr[i])){
                                    //state IDIN
                                    System.out.print("3 ->");
                                    // 因为后面还有可能有字母，所以不换行
                                    buffer.append(charArr[i]);
                                    state = 2;
                                }else if (charArr[i] == ';' || charArr[i] == '{'
                                        || charArr[i] == '}'|| charArr[i] == '['
                                        || charArr[i] == ']'|| charArr[i] == '('
                                        || charArr[i] == ')'|| charArr[i] == ','
                                        || charArr[i] == '='|| charArr[i] == '<'
                                        || charArr[i] == '+'|| charArr[i] == '-'
                                        || charArr[i] == '*'|| charArr[i] == '.'|| charArr[i] == '!') {
                                    writer.write("assign -> " + charArr[i] + CRLF);
                                    System.out.println("4");
                                    state = 4;
                                }else{
                                    System.out.print("-1 -> ");
                                    writer.write("error type : unknown symbol\r\n");
                                    errorWriter.write("error type : unknown symbol\r\n");
                                    state = -1;
                                }
                                i++;
                            }else
                                break;
                            break;
                        case 1:
                            if(i < currentLine.length()){
                                if (isDigit(charArr[i])) {
                                    buffer.append(charArr[i]);
                                    System.out.print("1 -> ");
                                    state = 1;
                                    //到结尾了，直接返回
                                    if (i == currentLine.length() - 1) {
                                        buffer.append(CRLF);
                                        writer.write("IntegerLiteral -> " + String.valueOf(buffer));
                                        //清空buffer
                                        buffer = new StringBuffer();
                                        state = 4;
                                    }
                                }else{
                                        // 后面不是数字，则将前面数字输出，并完成
                                        buffer.append(CRLF);
                                        writer.write(String.valueOf(buffer));
                                        writer.flush();
                                        buffer = new StringBuffer();
                                        System.out.println("4");
                                        state = 4;
                                        // 因为后面有i++,所以现在回滚前一个字符,判断当前字符
                                        i--;
                                    }
                                    i++;
                                }else
                                    break;
                            break;
                        case 2:
                            if(i < currentLine.length()){
                                if (charArr[i] == '_') {
                                    buffer.append("_");
                                    System.out.print("3 -> ");
                                    state = 3;
                                }else if (charArr[i] == '.' && i + 11 < currentLine.length() && charArr[i + 1] == 'o'
                                        && charArr[i + 2] == 'u'&& charArr[i + 3] == 't'
                                        && charArr[i + 4] == '.'&& charArr[i + 5] == 'p'
                                        && charArr[i + 6] == 'r'&& charArr[i + 7] == 'i'
                                        && charArr[i + 8] == 'n'&& charArr[i + 9] == 't'
                                        && charArr[i + 10] == 'l'&& charArr[i + 11] == 'n'){
                                    String word = String.valueOf(buffer);
                                    if(word.equals("System")){
                                            writer.write("assign -> System.out.println"+CRLF);
                                            buffer = new StringBuffer();
                                            System.out.println("4");
                                            state = 4;
                                            i = i + 11;
                                    }

                                }
                                else if (isLetterOrDigit(charArr[i])) {
                                    buffer.append(charArr[i]);
                                    System.out.println(charArr[i]);
                                    System.out.print("2 -> ");
                                    state = 2;

                                    // 判断当前字符串是否已经结束。因为没有\0作为标志位，所以当最后为identifier时，会阻塞在状态2
                                    if (i == currentLine.length() - 1) {
                                        String word = String.valueOf(buffer);
                                        boolean isKeyWord = false;
                                        for (String s : keyword) {
                                            if (s.equals(word)) {
                                                isKeyWord = true;
                                                writer.write(s+" -> " + buffer + CRLF);
                                                break;
                                            }
                                        }

                                        if (!isKeyWord) {
                                            writer.write("identifier -> " + buffer + CRLF);
                                        }

                                        buffer = new StringBuffer();
                                        System.out.println("4 -> currentLine finish");
                                        state = 4;
                                    }
                                } else {
                                    // 根据最长匹配，认定前文字符为正确字符，所以将token输出
                                    String word = String.valueOf(buffer);
                                    boolean isKeyWord = false;
                                    for (String s : keyword) {
                                        if (s.equals(word)) {
                                            isKeyWord = true;
                                            writer.write(s+" -> " + buffer + CRLF);
                                            break;
                                        }
                                    }
                                    if (!isKeyWord) {
                                        writer.write("identifier -> " + buffer + CRLF);
                                    }
                                    buffer = new StringBuffer();
                                    System.out.println(4);
                                    state = 4;
                                    // 因为后面有i++,所以现在回滚前一个字符,判断当前字符
                                    i--;
                                }
                                i++;
                            }else
                                break;
                            break;
                        case 3:
                            if(i < currentLine.length()){
                                if (isLetterOrDigit(charArr[i])) {
                                    buffer.append(charArr[i]);
                                    System.out.print("2 -> ");
                                    state = 2;
                                    // 判断当前字符串是否已经结束。因为没有\0作为标志位，所以当最后为identifier_X时，会阻塞在状态2
                                    if (i == currentLine.length() - 1) {
                                        buffer.append('\n');
                                        writer.write("identifier -> " + String.valueOf(buffer));
                                        buffer = new StringBuffer();
                                        System.out.println("4 -> currentLine finish");
                                        state = 4;
                                    }
                                } else {
                                    //下划线后面只能是字母
                                    buffer = new StringBuffer();
                                    System.out.print("-1 -> ");
                                    writer.write(
                                            "error type : there must be at least one letter or digit behind underline"+CRLF);
                                    errorWriter.write(
                                            "error type : there must be at least one letter or digit behind underline"+CRLF);
                                    state = -1;
                                }
                                i++;
                            }else
                                break;
                            break;
                        case 4:
                            break;
                    }
                }
            }
        }
        writer.write("EOF -> EOF" + CRLF);
        writer.close();
        errorWriter.close();
        filereader.close();
        reader.close();
        bf.close();
    }
}
