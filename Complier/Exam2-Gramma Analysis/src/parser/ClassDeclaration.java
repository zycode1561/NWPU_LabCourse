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
public class ClassDeclaration extends Tree {

    private String idName;

    public ClassDeclaration(BufferedReader bufferedReader, int line, FileWriter writer, String fileName, LinkedList<String> class_names) throws IOException {
        super("ClassDeclaration", bufferedReader, line, writer, fileName);
        //语法规则为
        //ClassDeclaration->"class" Identifier [ "extends" Identifier ] "{" { VarDeclaration } { MethodDeclaration } "}"
        //VarDeclaration ->Type Identifier ";"
        //MethodDeclaration->"public" Type Identifier "(" [ Type Identifier { "," Type Identifier } ] ")" "{" { VarDeclaration } { Statement } "return" Expression ";" "}"
        //构造自身节点的信息
        String currentLine = bf.readLine();
        lineNum++;
        if (currentLine == null) {
            handleSubError(writer);
        } else {
            //判断当前行是不是identifier，如果不是返回错误，如果是，判断类名是不是已经在list中
            // 如果没有在就把当前类名加载到list中，并且往下进行，如果在就返回错误
            if (!currentLine.startsWith("identifier")) {
                handleSubError(writer);
            } else {
                class_names.add(thirdPart(currentLine));
            }
            currentLine = bf.readLine();
            lineNum++;
            if (thirdPart(currentLine).equals("extends")) {
                currentLine = bf.readLine();
                lineNum++;
                if (!currentLine.startsWith("identifier")) {
                    handleSubError(writer);
                } else {
                    class_names.add(thirdPart(currentLine));
                    currentLine = bf.readLine();
                    lineNum++;
                }
            } else if (!thirdPart(currentLine).equals("{")) {
                handleSubError(writer);
            }
            //开始处理VarDeclaration和MethodDeclaration
            currentLine = bf.readLine();
            lineNum++;
//            if (thirdPart(currentLine).equals("int") || thirdPart(currentLine).equals("boolean")
//                    || currentLine.startsWith("identifier")) {
//                VarDeclaration t = new VarDeclaration(bf, lineNum, writer, fileName, currentLine);
//                child.add(t);
//                //更新bf读取位置和lineNum
//                this.bf = t.getBf();
//                lineNum = t.getLineNum();
//
//                currentLine = bf.readLine();
//                lineNum++;
//            } else {
//                handleSubError(writer);
//            }
            //有重复的话继续创建子树
            while (thirdPart(currentLine).equals("int") || thirdPart(currentLine).equals("boolean")
                    || currentLine.startsWith("identifier")) {
                VarDeclaration t = new VarDeclaration(bf, lineNum, writer, fileName, currentLine);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();

                currentLine = bf.readLine();
                lineNum++;
            }
//            if (thirdPart(currentLine).equals("public")) {
//                MethodDeclaration t = new MethodDeclaration(bf, lineNum, writer, fileName);
//                child.add(t);
//                //更新bf读取位置和lineNum
//                this.bf = t.getBf();
//                lineNum = t.getLineNum();
//
//                currentLine = bf.readLine();
//                lineNum++;
//            } else {
//                handleSubError(writer);
//            }
            while (thirdPart(currentLine).equals("public")){
                MethodDeclaration t = new MethodDeclaration(bf, lineNum, writer, fileName);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();

                currentLine = bf.readLine();
                lineNum++;
            }
            if(!thirdPart(currentLine).equals("}")){
                handleSubError(writer);
            }
        }
    }

    private void handleSubError(FileWriter writer) throws IOException {
//        System.out.println("4>>>ERROR APPEARED IN LINE " + lineNum + " : ClassDeclaration STATEMENT INCOMPLETE" + CRLF);
        writer.write("ERROR APPEARED IN LINE " + lineNum + " : ClassDeclaration STATEMENT INCOMPLETE" + CRLF);
        handleError();
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
