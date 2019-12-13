grammar Calculator;

stmt:   expr NEWLINE                    # printExpr
    |   ID '=' expr NEWLINE             # assign
    |   NEWLINE                         # blank
    ;

expr:   <assoc=right> expr op='^' expr  # pow
    |   expr op=('*'|'/') expr          # mulDiv
    |   expr op=('+'|'-') expr          # addSub
    |   INT                             # int
    |   ID                              # id
    |   '(' expr ')'                    # parens
    ;

MUL : '*';
DIV : '/';
ADD : '+';
SUB : '-';
ID  : Letter LetterOrDigit*;
INT : Digit Digit*;
fragment Letter: [a-zA-Z_];
fragment Digit: [0-9];
fragment LetterOrDigit: Letter | Digit;
NEWLINE: '\r'? '\n';
WS: [ \t]+ -> skip;