package parser;

import java.io.BufferedReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Currency;

/**
 * @author ZhangYun
 * @version 1.0
 * @date 2020/4/27
 */
public class Type extends Tree {

    //Type的类型
    private String strType;
    private String currentLine;

    public Type(BufferedReader bufferedReader, int line, FileWriter writer, String fileName, String curr) throws IOException {
        super("Type", bufferedReader, line, writer, fileName);
        //Type->"int" "[" "]"|"boolean"|"int"| Identifier
        currentLine = "";
        //因为Type全部为终结符，所以直接判断，交给Type处理，并返回给toString
        if (thirdPart(curr).equals("boolean")) {
            strType = "boolean";
            currentLine = readNext();
        } else if (curr.startsWith("identifier")) {
            strType = "Identifier";
            currentLine = readNext();
        } else if (thirdPart(curr).equals("int")) {
            //判断下一个字符是否为"["
            currentLine = readNext();
            if (!thirdPart(curr).equals("[")) {
                strType = "int";
            } else {
                strType = "int[]";
                //保证读取到Type的下一位
                currentLine = readNext();
                currentLine = readNext();
            }
        } else {
            handleSubError(writer);
        }
    }

    private void handleSubError(FileWriter writer) throws IOException {
//        System.out.println("4>>>ERROR APPEARED IN LINE " + lineNum + " : ClassDeclaration STATEMENT INCOMPLETE" + CRLF);
        writer.write("ERROR APPEARED IN LINE " + lineNum + " : Type STATEMENT INCOMPLETE" + CRLF);
        handleError();
    }

    private String readNext() throws IOException {
        lineNum++;
        return bf.readLine();
    }

    public String getCurr(){
        return currentLine;
    }
}
