package parser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ZhangYun
 * @version 1.0
 * @date 2020/4/24
 */
public class Statement extends Tree {
    public Statement(BufferedReader bufferedReader, int line, FileWriter writer, String fileName, String curr) throws IOException {
        super("Statement", bufferedReader, line, writer, fileName);
        String currentLine = curr;
        if (thirdPart(currentLine).equals("{")) {
            currentLine = readNext();
            while (thirdPart(currentLine).equals("{") || thirdPart(currentLine).equals("if") || thirdPart(currentLine).equals("while")
                    || thirdPart(currentLine).equals("System.out.println") || currentLine.startsWith("identifier")) {
                Statement t = new Statement(bf, lineNum, writer, fileName, currentLine);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();

                currentLine = readNext();
            }
            if (!thirdPart(currentLine).equals("}")) handleSubError(writer);
        } else if (thirdPart(currentLine).equals("if")) {
            currentLine = readNext();
            if (!thirdPart(currentLine).equals("(")) handleSubError(writer);
            //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
            Expression t = new Expression(bf, lineNum, writer, fileName, null);
            child.add(t);
            //更新bf读取位置和lineNum
            this.bf = t.getBf();
            lineNum = t.getLineNum();
            currentLine = t.getCurrent();
            if (!thirdPart(currentLine).equals(")")) {
                handleSubError(writer);
            }
            currentLine = readNext();
            if (isStatement(currentLine)) {
                Statement t1 = new Statement(bf, lineNum, writer, fileName, currentLine);
                child.add(t1);
                //更新bf读取位置和lineNum
                this.bf = t1.getBf();
                lineNum = t1.getLineNum();

                currentLine = readNext();
            } else handleSubError(writer);
            if (!thirdPart(currentLine).equals("else")) handleSubError(writer);
            currentLine = readNext();
            if (isStatement(currentLine)) {
                Statement t2 = new Statement(bf, lineNum, writer, fileName, currentLine);
                child.add(t2);
                //更新bf读取位置和lineNum
                this.bf = t2.getBf();
                lineNum = t2.getLineNum();
            } else handleSubError(writer);
        } else if (thirdPart(currentLine).equals("while")) {
            currentLine = readNext();
            if (!thirdPart(currentLine).equals("(")) handleSubError(writer);
            //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
            Expression t = new Expression(bf, lineNum, writer, fileName, null);
            child.add(t);
            //更新bf读取位置和lineNum
            this.bf = t.getBf();
            lineNum = t.getLineNum();
            currentLine = t.getCurrent();
            if (!thirdPart(currentLine).equals(")")) handleSubError(writer);
            currentLine = readNext();
            if (isStatement(currentLine)) {
                Statement t2 = new Statement(bf, lineNum, writer, fileName, currentLine);
                child.add(t2);
                //更新bf读取位置和lineNum
                this.bf = t2.getBf();
                lineNum = t2.getLineNum();
            } else handleSubError(writer);
        } else if (thirdPart(currentLine).equals("System.out.println")) {
            currentLine = readNext();
            if (!thirdPart(currentLine).equals("(")) handleSubError(writer);
            //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
            Expression t = new Expression(bf, lineNum, writer, fileName, null);
            child.add(t);
            //更新bf读取位置和lineNum
            this.bf = t.getBf();
            lineNum = t.getLineNum();
            currentLine = t.getCurrent();
            if (!thirdPart(currentLine).equals(")")) handleSubError(writer);
            currentLine = readNext();
            if (!thirdPart(currentLine).equals(";")) handleSubError(writer);
        } else if(thirdPart(currentLine).equals("=")) {
            if (thirdPart(currentLine).equals("=")) {
                //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
                Expression t = new Expression(bf, lineNum, writer, fileName, null);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();
                currentLine = t.getCurrent();
                if (!thirdPart(currentLine).equals(";")) handleSubError(writer);
            } else if (thirdPart(currentLine).equals("[")) {
                //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
                Expression t = new Expression(bf, lineNum, writer, fileName, null);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();
                currentLine = t.getCurrent();
                if (!thirdPart(currentLine).equals("]")) handleSubError(writer);
                currentLine = readNext();
                if (!thirdPart(currentLine).equals("=")) handleSubError(writer);
                //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
                Expression t2 = new Expression(bf, lineNum, writer, fileName, null);
                child.add(t2);
                //更新bf读取位置和lineNum
                this.bf = t2.getBf();
                lineNum = t2.getLineNum();
                currentLine = t2.getCurrent();
                if (!thirdPart(currentLine).equals(";")) handleSubError(writer);
            } else handleSubError(writer);
        }else{
            currentLine = readNext();
            if (thirdPart(currentLine).equals("=")) {
                //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
                Expression t = new Expression(bf, lineNum, writer, fileName, null);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();
                currentLine = t.getCurrent();
                if (!thirdPart(currentLine).equals(";")) handleSubError(writer);
            } else if (thirdPart(currentLine).equals("[")) {
                //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
                Expression t = new Expression(bf, lineNum, writer, fileName, null);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();
                currentLine = t.getCurrent();
                if (!thirdPart(currentLine).equals("]")) handleSubError(writer);
                currentLine = readNext();
                if (!thirdPart(currentLine).equals("=")) handleSubError(writer);
                //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
                Expression t2 = new Expression(bf, lineNum, writer, fileName, null);
                child.add(t2);
                //更新bf读取位置和lineNum
                this.bf = t2.getBf();
                lineNum = t2.getLineNum();
                currentLine = t2.getCurrent();
                if (!thirdPart(currentLine).equals(";")) handleSubError(writer);
            } else handleSubError(writer);
        }
    }


    private boolean isStatement(String s) {
        return thirdPart(s).equals("{") || thirdPart(s).equals("if") || thirdPart(s).equals("while")
                || thirdPart(s).equals("System.out.println") || s.startsWith("identifier");
    }

    private void handleSubError(FileWriter writer) throws IOException {
//        System.out.println("4>>>ERROR APPEARED IN LINE " + lineNum + " : ClassDeclaration STATEMENT INCOMPLETE" + CRLF);
        writer.write("ERROR APPEARED IN LINE " + lineNum + " : Statement STATEMENT INCOMPLETE" + CRLF);
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
