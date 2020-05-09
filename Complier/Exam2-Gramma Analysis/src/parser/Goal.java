package parser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;

/**
 * @author ZhangYun
 * @version 1.0
 * @date 2020/4/24
 */
public class Goal extends Tree {

    private String idName;
    //用于存储已经使用过的类名
    private static LinkedList<String> class_names = new LinkedList<>();

    public Goal(BufferedReader bufferedReader, int line, FileWriter writer, String fileName) throws IOException {
        //语法规则为
        // Goal -> MainClass { ClassDeclaration } EOF
        // MainClass->"class" Identifier "{" "public" "static" "void" "main" "(" "String" "[" "]" Identifier ")" "{" Statement "}" "}"
        // ClassDeclaration->"class" Identifier [ "extends" Identifier ] "{" { VarDeclaration } { MethodDeclaration } "}"
        super("Goal", bufferedReader, line, writer, fileName);
        //构造自身节点的信息
        String currentLine = bf.readLine();
        lineNum++;
        if (currentLine == null || !(currentLine.startsWith("identifier"))) {
            System.out.println("2>>>ERROR APPEARED IN LINE " + lineNum + " : Goal STATEMENT INCOMPLETE" + CRLF);
            writer.write("ERROR APPEARED IN LINE " + lineNum + " : Goal STATEMENT INCOMPLETE" + CRLF);
            handleError();
        } else {
            //词法分析结果类别为identifier时
            //获取到identifier
            //无法判断是哪个identifier
            //将第一个identifier保存.继续分析
            idName = thirdPart(currentLine);
            class_names.add(thirdPart(currentLine));
            currentLine = bf.readLine();
            lineNum++;
            if (thirdPart(currentLine).equals("{")) {
                //无法判断是哪个{
                //保存，继续分析
                idName += " {";
                currentLine = bf.readLine();
                lineNum++;
                //如果不是public
                if (!thirdPart(currentLine).equals("public")) {
                    writer.write("ERROR APPEARED IN LINE " + lineNum + " : Goal STATEMENT INCOMPLETE" + CRLF);
                    handleError();
                } else {
                    //说明匹配到MainClass
                    MainClass t = new MainClass(bf, lineNum, writer, fileName, idName);
                    child.add(t);
                    //更新bf读取位置和lineNum
                    this.bf = t.getBf();
                    lineNum = t.getLineNum();
                }
            } else {
                //如果不是{,说明出现错误，如果是extends也为错误，因为开始必须为MainClass
                writer.write("ERROR APPEARED IN LINE " + lineNum + " : Goal STATEMENT INCOMPLETE" + CRLF);
                handleError();
            }

            //MainClass处理完成后，继续往下读取
            currentLine = bf.readLine();
            lineNum++;

            if (thirdPart(currentLine).equals("class")) {
                //匹配到ClassDeclaration
                while (thirdPart(currentLine).equals("class")) {
                    ClassDeclaration t = new ClassDeclaration(bf, lineNum, writer, fileName, class_names);
                    child.add(t);
                    //更新bf读取位置和lineNum
                    this.bf = t.getBf();
                    lineNum = t.getLineNum();
                    currentLine = readNext();
                }
            }
            if (!thirdPart(currentLine).equals("EOF")) {
                handleSubError(writer);
            }
        }
    }

    private void handleSubError(FileWriter writer) throws IOException {
//        System.out.println("4>>>ERROR APPEARED IN LINE " + lineNum + " : ClassDeclaration STATEMENT INCOMPLETE" + CRLF);
        writer.write("ERROR APPEARED IN LINE " + lineNum + " : Goal STATEMENT INCOMPLETE" + CRLF);
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
