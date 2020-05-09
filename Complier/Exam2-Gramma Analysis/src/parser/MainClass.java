package parser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ZhangYun
 * @version 1.0
 * @date 2020/4/24
 */
public class MainClass extends Tree {

    private String idName;

    public MainClass(BufferedReader bufferedReader, int line, FileWriter writer, String fileName, String curr) throws IOException {
        super("MainClass", bufferedReader, line, writer, fileName);
        //语法规则为
        //MainClass->"class" Identifier "{" "public" "static" "void" "main" "(" "String" "[" "]" Identifier ")" "{" Statement "}" "}"
        //Statement->"{" { Statement } "}"|"if" "(" Expression ")" Statement "else" Statement |"while" "(" Expression ")" Statement |"System.out.println" "("  Expression ")" ";"| Identifier "=" Expression ";"| Identifier "[" Expression "]" "=" Expression ";"
        //构造自身节点的信息
        String currentLine = bf.readLine();
        lineNum++;
        if (currentLine == null || !thirdPart(currentLine).equals("static")) {
            System.out.println("3>>>ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals("void")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals("main")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals("(")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals("String")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals("[")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals("]")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!currentLine.startsWith("identifier")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals(")")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals("{")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        //匹配Statement
        String s = thirdPart(currentLine);
        if (s.equals("{") || s.equals("if") || s.equals("while")
                || s.equals("System.out.println") || currentLine.startsWith("identifier")) {
            //匹配到Statement
            Statement t = new Statement(bf, lineNum, writer, fileName, currentLine);
            child.add(t);
            //更新bf读取位置和lineNum
            this.bf = t.getBf();
            lineNum = t.getLineNum();
        } else {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        //匹配完Statement
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals("}")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
        currentLine = bf.readLine();
        lineNum++;
        if (!thirdPart(currentLine).equals("}")) {
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : MainClass STATEMENT INCOMPLETE" + CRLF);
            handleError();
        }
    }

    @Override
    public String toString(int num) {
        StringBuilder str = new StringBuilder();
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
