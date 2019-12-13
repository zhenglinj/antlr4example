package com.zhenglinj.antlr4example.calculator;

import java.util.HashMap;
import java.util.Map;

public class EvalVisitor extends CalculatorBaseVisitor<Double> {
    private Map<String, Double> vars = new HashMap<>();

    // stmt : ID '=' expr NEWLINE ;
    @Override
    public Double visitAssign(CalculatorParser.AssignContext ctx) {
        String id = ctx.ID().getText();
        Double val = visit(ctx.expr());
        vars.put(id, val);
        return val;
    }

    // stmt : expr NEWLINE ;
    @Override
    public Double visitPrintExpr(CalculatorParser.PrintExprContext ctx) {
        Double value = visit(ctx.expr());
        System.out.println(value);
        return .0;
    }

    // expr : INT ;
    @Override
    public Double visitInt(CalculatorParser.IntContext ctx) {
        return Double.valueOf(ctx.INT().getText());
    }

    // expr : ID ;
    @Override
    public Double visitId(CalculatorParser.IdContext ctx) {
        String id = ctx.ID().getText();
        if (vars.containsKey(id)) return vars.get(id);
        return .0;
    }

    // expr : expr op=('*'|'/') expr ;
    @Override
    public Double visitMulDiv(CalculatorParser.MulDivContext ctx) {
        double lhs = visit(ctx.expr(0));
        double rhs = visit(ctx.expr(1));
        if (ctx.op.getType() == CalculatorParser.MUL) return lhs * rhs;
        return lhs / rhs;
    }

    // expr : expr op=('+'|'-') expr ;
    @Override
    public Double visitAddSub(CalculatorParser.AddSubContext ctx) {
        double lhs = visit(ctx.expr(0));
        double rhs = visit(ctx.expr(1));
        if (ctx.op.getType() == CalculatorParser.ADD) return lhs + rhs;
        return lhs - rhs;
    }

    // expr : '(' expr ')' ;
    @Override
    public Double visitParens(CalculatorParser.ParensContext ctx) {
        return visit(ctx.expr());
    }
}
