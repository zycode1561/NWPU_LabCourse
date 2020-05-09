package parser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author ZhangYun
 * @version 1.0
 * @date 2020/4/27
 */
public class Expression extends Tree {

    private static String[] strs = {"true", "false", "this", "new", "!", "("};
    private static String[] symbol = {"&&", "<", "+", "-", "*"};
    private static List<String> exps = Arrays.asList(strs);
    private static List<String> symbols = Arrays.asList(symbol);
    private String current;

    public Expression(BufferedReader bufferedReader, int line, FileWriter writer, String fileName, String curr) throws IOException {
        super("Expression", bufferedReader, line, writer, fileName);
        //语法规则
        //Expression-> Expression  ( "&&" | "<" | "+" | "-" | "*" ) Expression
        //Expression-> Expression "[" Expression "]"
        //。。。分成多个子树
        //存在递归形式，要对树进行翻译
        //对第一个子树，既有左递归也有右递归 exps{( "&&" | "<" | "+" | "-" | "*" ) Expression}
        //再进行右递归exps{ {( "&&" | "<" | "+" | "-" | "*" )} exps}

        //这里的exps代表以exps中字符串开始的和integer以及
        // identifier子树[{ {"[" Expression "]" }|{"." "length"}
        // |{"." Identifier "(" [ Expression { "," Expression } ] ")"} }]
        //同理，写出剩余子树
        //exps{"[" Expression "]" }
        //exps{"." "length"}
        //exps{"." Identifier "(" [ Expression { "," Expression } ] ")"}
        //上面三个子树和exp可以同时讨论，省略

        //Digit
        //"true"
        //"false"
        //Identifier
        //"this"
        //"new" "int" "[" Expression "]"
        //"new" Identifier "(" ")"
        //{"!"}exps
        //"(" Expression ")"

        //因为无法判断exps匹配之后是否还匹配，所以Expression统一规则：匹配之后继续往下读一位，判断
        //在Expression树递归完成后需要注意这一点
        String currentLine;
        if (curr == null) {
            //表示从其他树中进入Expression，如果不为空，表示从自身进入
            currentLine = readNext();
            String start = thirdPart(currentLine);
            if (!exps.contains(start) && !currentLine.startsWith("identifier") && !currentLine.startsWith("Digit")) {
                handleSubError(writer);
            }
        } else {
            currentLine = curr;
        }
        if (thirdPart(currentLine).equals("true") || thirdPart(currentLine).equals("false")
                || thirdPart(currentLine).equals("this") || currentLine.startsWith("identifier")
                || currentLine.startsWith("Digit")) {
            currentLine = readNext();
            currentLine = matchExps(currentLine);
        } else if (thirdPart(currentLine).equals("new")) {
            currentLine = readNext();
            if (currentLine.startsWith("identifier")) {
                currentLine = readNext();
                if (!thirdPart(currentLine).equals("(")) handleSubError(writer);
                currentLine = readNext();
                if (!thirdPart(currentLine).equals(")")) handleSubError(writer);
                currentLine = readNext();
            } else if (thirdPart(currentLine).equals("int")) {
                currentLine = readNext();
                if (!thirdPart(currentLine).equals("[")) handleSubError(writer);
                currentLine = readNext();
                if (isExpression(currentLine)) {
                    Expression t = new Expression(bf, lineNum, writer, fileName, currentLine);
                    child.add(t);
                    //更新bf读取位置和lineNum
                    this.bf = t.getBf();
                    lineNum = t.getLineNum();
                    currentLine = t.getCurrent();
                }
                if (!thirdPart(currentLine).equals("]")) handleSubError(writer);
                currentLine = readNext();
            } else {
                handleSubError(writer);
            }
        } else if (thirdPart(currentLine).equals("!")) {
            currentLine = readNext();
            while (thirdPart(currentLine).equals("!")) {
                currentLine = readNext();
            }
            if (isExpression(currentLine)) {
                Expression t = new Expression(bf, lineNum, writer, fileName, currentLine);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();
                currentLine = t.getCurrent();
            }
        } else if (thirdPart(currentLine).equals("(")) {
            currentLine = readNext();
            if (isExpression(currentLine)) {
                Expression t = new Expression(bf, lineNum, writer, fileName, currentLine);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();
                currentLine = t.getCurrent();
            }
            if (!thirdPart(currentLine).equals(")")) handleSubError(writer);
            currentLine = readNext();
            if (symbols.contains(thirdPart(currentLine))) {
                while (symbols.contains(thirdPart(currentLine))) {
                    currentLine = readNext();
                    if (isExpression(currentLine)) {
                        Expression t = new Expression(bf, lineNum, writer, fileName, currentLine);
                        child.add(t);
                        //更新bf读取位置和lineNum
                        this.bf = t.getBf();
                        lineNum = t.getLineNum();
                        currentLine = t.getCurrent();
                    }
                }
            }
        } else handleSubError(writer);

        current = currentLine;
    }

    private String matchExps(String currentLine) throws IOException {
        if (thirdPart(currentLine).equals(".") || currentLine.equals("[")) {
            while (thirdPart(currentLine).equals(".") || currentLine.equals("[")) {
                if (currentLine.equals("[")) {
                    currentLine = readNext();
                    if (isExpression(currentLine)) {
                        Expression t = new Expression(bf, lineNum, writer, fileName, currentLine);
                        child.add(t);
                        //更新bf读取位置和lineNum
                        this.bf = t.getBf();
                        lineNum = t.getLineNum();
                        currentLine = t.getCurrent();
                        if (!thirdPart(currentLine).equals("]")) {
                            handleSubError(writer);
                        }
                    }
                    currentLine = readNext();
                } else {
                    currentLine = readNext();
                    if (thirdPart(currentLine).equals("length")) {
                        currentLine = readNext();
                    } else if (currentLine.startsWith("identifier")) {
                        currentLine = readNext();
                        if (!thirdPart(currentLine).equals("(")) handleSubError(writer);
                        currentLine = readNext();
                        if (isExpression(currentLine)) { //表示匹配到Expression
                            Expression t = new Expression(bf, lineNum, writer, fileName, currentLine);
                            child.add(t);
                            //更新bf读取位置和lineNum
                            this.bf = t.getBf();
                            lineNum = t.getLineNum();
                            currentLine = t.getCurrent();
                            while (thirdPart(currentLine).equals(",")) {
                                currentLine = readNext();
                                if (isExpression(currentLine)) {
                                    Expression t2 = new Expression(bf, lineNum, writer, fileName, currentLine);
                                    child.add(t2);
                                    //更新bf读取位置和lineNum
                                    this.bf = t2.getBf();
                                    lineNum = t2.getLineNum();
                                    currentLine = t2.getCurrent();
                                } else handleSubError(writer);
                            }
                        }
                        if (!thirdPart(currentLine).equals(")")) handleSubError(writer);
                        currentLine = readNext();
                    } else {
                        handleSubError(writer);
                    }
                }
            }
        } else if (symbols.contains(thirdPart(currentLine))) { //单独处理包含右递归的树
            while (symbols.contains(thirdPart(currentLine))) {
                currentLine = readNext();
                if (isExpression(currentLine)) {
                    Expression t2 = new Expression(bf, lineNum, writer, fileName, currentLine);
                    child.add(t2);
                    //更新bf读取位置和lineNum
                    this.bf = t2.getBf();
                    lineNum = t2.getLineNum();
                    currentLine = t2.getCurrent();
                } else handleSubError(writer);
            }
        }
        return currentLine;
    }

    public String getCurrent() {
        return this.current;
    }

    public boolean isExpression(String s) {
        return exps.contains(thirdPart(s)) || s.startsWith("identifier") || s.startsWith("Digit");
    }

    private void handleSubError(FileWriter writer) throws IOException {
//        System.out.println("4>>>ERROR APPEARED IN LINE " + lineNum + " : ClassDeclaration STATEMENT INCOMPLETE" + CRLF);
        writer.write("ERROR APPEARED IN LINE " + lineNum + " : Expression STATEMENT INCOMPLETE" + CRLF);
        handleError();
    }

    private String readNext() throws IOException {
        lineNum++;
        return bf.readLine();
    }

    @Override
    public String toString(int num) {
        StringBuilder str = new StringBuilder("");
        for (int i = 0; i < num; i++) {
            str.append("\t");
        }
        str = new StringBuilder("---NodeType : " + super.type + CRLF);
        for (Tree t : child) {
            for (int i = 0; i < num; i++) {
                str.append("\t");
            }
            str.append(t.toString(num + 1));
        }
        return str.toString();
    }
}
