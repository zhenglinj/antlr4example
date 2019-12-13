package com.zhenglinj.antlr4example;

import com.zhenglinj.antlr4example.calculator.*;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.*;

public class Calculator {
    public static void main(String[] args) throws IOException {
        System.out.println("Antlr4 Example");
        try {
            /*
             * get the input file as an InputStream
             */
            InputStream inputStream = Java8.class.getResourceAsStream("/example.txt");
            /*
             * make Lexer
             */
            Lexer lexer = new CalculatorLexer(CharStreams.fromStream(inputStream));
            /*
             * get a TokenStream on the Lexer
             */
            TokenStream tokenStream = new CommonTokenStream(lexer);
            /*
             * make a Parser on the token stream
             */
            CalculatorParser parser = new CalculatorParser(tokenStream);
            /*
             * get the top node of the AST. This corresponds to the topmost rule of equation.q4, "equation"
             */
            CalculatorParser.StmtContext context = parser.stmt();
            System.out.println(context.toStringTree(parser));

            System.out.println("Visitor:");
            EvalVisitor evalByVisitor = new EvalVisitor();
            evalByVisitor.visit(context);
            System.out.println();

            System.out.println("Listener:");
            ParseTreeWalker walker = new ParseTreeWalker();
            Evaluator evalByListener = new Evaluator();
            walker.walk(evalByListener, context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}