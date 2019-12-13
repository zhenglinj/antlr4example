package com.zhenglinj.antlr4example.calculator;

import org.antlr.v4.runtime.tree.ParseTreeProperty;

import java.util.HashMap;
import java.util.Map;

public class Evaluator extends CalculatorBaseListener {
    private Map<String, Double> vars = new HashMap<>();
    private ParseTreeProperty<Double> values = new ParseTreeProperty<>();

    // stmt : ID '=' expr NEWLINE ;
    @Override
    public void exitAssign(CalculatorParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Double val = values.get(ctx.expr());
        vars.put(id, val);
    }

    // stmt : expr NEWLINE ;
    @Override
    public void exitPrintExpr(CalculatorParser.PrintExprContext ctx) {
        System.out.println(values.get(ctx.expr()));
    }

    // expr : NUMBER ;
    @Override
    public void exitInt(CalculatorParser.IntContext ctx) {
        values.put(ctx, Double.valueOf(ctx.INT().getText()));
    }

    // expr : ID ;
    @Override
    public void exitId(CalculatorParser.IdContext ctx) {
        values.put(ctx, vars.getOrDefault(ctx.ID().getText(), .0));
    }

    // expr : expr op=('*'|'/') expr ;
    @Override
    public void exitMulDiv(CalculatorParser.MulDivContext ctx) {
        double lhs = values.get(ctx.expr(0));
        double rhs = values.get(ctx.expr(1));
        values.put(ctx, ctx.op.getType() == CalculatorParser.MUL ? lhs * rhs : lhs / rhs);
    }

    // expr : expr op=('+'|'-') expr ;
    @Override
    public void exitAddSub(CalculatorParser.AddSubContext ctx) {
        double lhs = values.get(ctx.expr(0));
        double rhs = values.get(ctx.expr(1));
        values.put(ctx, ctx.op.getType() == CalculatorParser.ADD ? lhs + rhs : lhs - rhs);
    }

    // expr : '(' expr ')' ;
    @Override
    public void exitParens(CalculatorParser.ParensContext ctx) {
        values.put(ctx, values.get(ctx.expr()));
    }
}
