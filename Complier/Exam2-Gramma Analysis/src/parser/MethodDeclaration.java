package parser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ZhangYun
 * @version 1.0
 * @date 2020/4/24
 */
public class MethodDeclaration extends Tree {

    private int argNums;

    public MethodDeclaration(BufferedReader bufferedReader, int line, FileWriter writer, String fileName) throws IOException {
        super("MethodDeclaration", bufferedReader, line, writer, fileName);
        //语法规则
        //MethodDeclaration->"public" Type Identifier "(" [ Type Identifier { "," Type Identifier } ] ")"
        // "{" { VarDeclaration } { Statement } "return" Expression ";" "}"
        //进入时currentLine为public,读取下一位
        String currentLine = bf.readLine();
        lineNum++;
        //Type->"int" "[" "]"|"boolean"|"int"| Identifier
        if (currentLine.startsWith("identifier") || thirdPart(currentLine).equals("int")
                || thirdPart(currentLine).equals("boolean")) {
            Type t = new Type(bf, lineNum, writer, fileName, currentLine);
            child.add(t);
            //更新bf读取位置和lineNum
            this.bf = t.getBf();
            lineNum = t.getLineNum();
            currentLine = t.getCurr();
        } else {
            handleSubError(writer);
        }
        if (!currentLine.startsWith("identifier")) {
            handleSubError(writer);
        }
        currentLine = readNext();
        if (!thirdPart(currentLine).equals("(")) {
            handleSubError(writer);
        }
        currentLine = readNext();
        //如果下一位不是 ），说明有参数
        if (!thirdPart(currentLine).equals(")")) {
            //判断第一个type和identifier
            if (currentLine.startsWith("identifier") || thirdPart(currentLine).equals("int")
                    || thirdPart(currentLine).equals("boolean")) {
                Type t = new Type(bf, lineNum, writer, fileName, currentLine);
                child.add(t);
                //更新bf读取位置和lineNum
                this.bf = t.getBf();
                lineNum = t.getLineNum();
                currentLine = t.getCurr();
            } else {
                handleSubError(writer);
            }
            if (!currentLine.startsWith("identifier")) {
                handleSubError(writer);
            }
            currentLine = readNext();
            //进入多个参数的判断,{}表示至少一个参数，所以这里应该一共至少两个参数
//            if (thirdPart(currentLine).equals(",")) {
//                currentLine = readNext();
//                //判断第一个type和identifier
//                if (currentLine.startsWith("identifier") || thirdPart(currentLine).equals("int")
//                        || thirdPart(currentLine).equals("boolean")) {
//                    Type t = new Type(bf, lineNum, writer, fileName, currentLine);
//                    child.add(t);
//                    //更新bf读取位置和lineNum
//                    this.bf = t.getBf();
//                    lineNum = t.getLineNum();
//                    currentLine = t.getCurr();
//                } else {
//                    handleSubError(writer);
//                }
//                if (!currentLine.startsWith("identifier")) {
//                    handleSubError(writer);
//                }
//                currentLine = readNext();
//            } else {
//                handleSubError(writer);
//            }
            while (thirdPart(currentLine).equals(",")) {
                //当读到","时，说明下面还有参数
                //重复上面的代码，直到退出循环
                currentLine = readNext();
                //判断第一个type和identifier
                if (currentLine.startsWith("identifier") || thirdPart(currentLine).equals("int")
                        || thirdPart(currentLine).equals("boolean")) {
                    Type t = new Type(bf, lineNum, writer, fileName, currentLine);
                    child.add(t);
                    //更新bf读取位置和lineNum
                    this.bf = t.getBf();
                    lineNum = t.getLineNum();
                    currentLine = t.getCurr();
                } else {
                    handleSubError(writer);
                }
                if (!currentLine.startsWith("identifier")) {
                    handleSubError(writer);
                }
                currentLine = readNext();
            }
            if (!thirdPart(currentLine).equals(")")) {
                handleSubError(writer);
            }
            currentLine = readNext();
        } else {
            currentLine = readNext();
        }
        if (!thirdPart(currentLine).equals("{")) {
            handleSubError(writer);
        }
        currentLine = readNext();
        //处理VarDeclaration
        // Todo 这里有点小问题，如何判断二者的交叉项identifier

        if (thirdPart(currentLine).equals("int") || thirdPart(currentLine).equals("boolean")
                || currentLine.startsWith("identifier")) {
            while (thirdPart(currentLine).equals("int") || thirdPart(currentLine).equals("boolean")
                    || currentLine.startsWith("identifier")) {
                currentLine = readNext();
                if (!thirdPart(currentLine).equals("=")) {
                    //若匹配，就进入子树
                    VarDeclaration t = new VarDeclaration(bf, lineNum, writer, fileName, currentLine);
                    child.add(t);
                    //更新bf读取位置和lineNum
                    this.bf = t.getBf();
                    lineNum = t.getLineNum();

                    currentLine = readNext();
                }
            }
        }
        //tatement->"{" { Statement } "}"|"if" "(" Expression ")" Statement
        // "else" Statement |"while" "(" Expression ")" Statement |"System.out.println" "("  Expression ")" ";"| Identifier "=" Expression ";"| Identifier "[" Expression "]" "=" Expression ";"
//        if (thirdPart(currentLine).equals("{") || thirdPart(currentLine).equals("if") || thirdPart(currentLine).equals("while")
//                || thirdPart(currentLine).equals("System.out.println") || currentLine.startsWith("identifier")) {
//            Statement t = new Statement(bf, lineNum, writer, fileName, currentLine);
//            child.add(t);
//            //更新bf读取位置和lineNum
//            this.bf = t.getBf();
//            lineNum = t.getLineNum();
//
//            currentLine = readNext();
//
//        } else {
//            handleSubError(writer);
//        }

        while (thirdPart(currentLine).equals("{") || thirdPart(currentLine).equals("if") || thirdPart(currentLine).equals("while")
                || thirdPart(currentLine).equals("System.out.println") || currentLine.startsWith("identifier") || thirdPart(currentLine).equals("=")) {
            Statement t = new Statement(bf, lineNum, writer, fileName, currentLine);
            child.add(t);
            //更新bf读取位置和lineNum
            this.bf = t.getBf();
            lineNum = t.getLineNum();

            currentLine = readNext();
        }

        if (!thirdPart(currentLine).equals("return")) {
            handleSubError(writer);
        }
        //Expression判断条件略多，而且是必须存在的所以进入子树之后再判断
        Expression t = new Expression(bf, lineNum, writer, fileName, null);
        child.add(t);
        //更新bf读取位置和lineNum
        this.bf = t.getBf();
        lineNum = t.getLineNum();
        currentLine = t.getCurrent();
        if (!thirdPart(currentLine).equals(";")) {
            handleSubError(writer);
        }
        currentLine = readNext();
        if (!thirdPart(currentLine).equals("}")) {
            handleSubError(writer);
        }

    }


    private void handleSubError(FileWriter writer) throws IOException {
//        System.out.println("4>>>ERROR APPEARED IN LINE " + lineNum + " : ClassDeclaration STATEMENT INCOMPLETE" + CRLF);
        writer.write("ERROR APPEARED IN LINE " + lineNum + " : MethodDeclaration STATEMENT INCOMPLETE" + CRLF);
        handleError();
    }

    private String readNext() throws IOException {
        lineNum++;
        return bf.readLine();
    }
}
