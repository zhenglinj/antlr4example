package com.zhenglinj.antlr4example;

import java.io.IOException;
import java.io.InputStream;

import com.zhenglinj.antlr4example.java8.*;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.TokenStream;

class Java8 {
   public static void main(String[] args) {
      System.out.println("Antlr4 Example");
      try {
         /*
          * get the input file as an InputStream
          */
         InputStream inputStream = Java8.class.getResourceAsStream("/Example.java");
         /*
          * make Lexer
          */
         Lexer lexer = new Java8Lexer(CharStreams.fromStream(inputStream));
         /*
          * get a TokenStream on the Lexer
          */
         TokenStream tokenStream = new CommonTokenStream(lexer);
         /*
          * make a Parser on the token stream
          */
         Java8Parser parser = new Java8Parser(tokenStream);
         /*
          * get the top node of the AST. This corresponds to the topmost rule of equation.q4, "equation"
          */
         Java8Parser.CompilationUnitContext context = parser.compilationUnit();
         System.out.println(context.toStringTree(parser));
      } catch (IOException e) {
         e.printStackTrace();
      }
   }
}