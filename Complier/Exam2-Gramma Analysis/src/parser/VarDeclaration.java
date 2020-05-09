package parser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author ZhangYun
 * @version 1.0
 * @date 2020/4/24
 */
public class VarDeclaration extends Tree {


    //Type的类型
    private String strType;

    public VarDeclaration(BufferedReader bufferedReader, int line, FileWriter writer, String fileName, String curr) throws IOException {
        super("VarDeclaration", bufferedReader, line, writer, fileName);
        //语法规则为
        //VarDeclaration ->Type Identifier ";"
        //Type->"int" "[" "]"|"boolean"|"int"| Identifier
        String currentLine = "";
        //因为Type全部为终结符，所以直接判断，交给Type处理，并返回给toString
        if (thirdPart(curr).equals("boolean")) {
            strType = "boolean";
            currentLine = readNext();
            if (!currentLine.startsWith("identifier")) {
                handleSubError(writer);
            }
            currentLine = readNext();
            if (!thirdPart(currentLine).equals(";")) {
                handleSubError(writer);
            }
        } else if (curr.startsWith("identifier")) {
            strType = "Identifier";
            currentLine = readNext();
            //处理完Type，开始处理后面的
            if (!currentLine.startsWith("identifier")) {
                if (!thirdPart(currentLine).equals(";")) {
                    handleSubError(writer);
                }
            } else {
                currentLine = readNext();
                if (!thirdPart(currentLine).equals(";")) {
                    handleSubError(writer);
                }
            }
        } else if (thirdPart(curr).equals("int")) {
            //判断下一个字符是否为"["
            currentLine = readNext();
            if (!thirdPart(currentLine).equals("[")) {
                strType = "int";
                if (!currentLine.startsWith("identifier")) {
                    handleSubError(writer);
                }
                currentLine = readNext();
                if (!thirdPart(currentLine).equals(";")) {
                    handleSubError(writer);
                }
            } else {
                strType = "int[]";
                //保证读取到Type的下一位
                currentLine = readNext();
                currentLine = readNext();
                if (!currentLine.startsWith("identifier")) {
                    handleSubError(writer);
                }
                currentLine = readNext();
                if (!thirdPart(currentLine).equals(";")) {
                    handleSubError(writer);
                }
            }
        } else if (thirdPart(curr).equals("[")) {
            strType = "int[]";
            //保证读取到Type的下一位
            currentLine = readNext();
            currentLine = readNext();
            if (!currentLine.startsWith("identifier")) {
                handleSubError(writer);
            }
            currentLine = readNext();
            if (!thirdPart(currentLine).equals(";")) {
                handleSubError(writer);
            }

        } else {
            handleSubError(writer);
        }
    }

    private void handleSubError(FileWriter writer) throws IOException {
//        System.out.println("4>>>ERROR APPEARED IN LINE " + lineNum + " : ClassDeclaration STATEMENT INCOMPLETE" + CRLF);
        writer.write("ERROR APPEARED IN LINE " + lineNum + " : VarDeclaration STATEMENT INCOMPLETE" + CRLF);
        handleError();
    }

    private String readNext() throws IOException {
        lineNum++;
        return bf.readLine();
    }

    @Override
    public String toString(int num) {
        StringBuilder str = new StringBuilder();
        for (int i = 0; i < num; i++) {
            str.append("\t");
        }
        str = new StringBuilder("---NodeType : " + super.type + CRLF);
        for (int i = 0; i < num; i++) {
            str.append("\t");
        }
        str.append("Type : ").append(strType).append(CRLF);
        for (Tree t : child) {
            for (int i = 0; i < num; i++) {
                str.append("\t");
            }
            str.append(t.toString(num + 1)).append(CRLF);
        }
        return str.toString();
    }
}
