package ru.ncedu.tdakkota.converter;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.TypeDeclaration;
import com.github.javaparser.ast.expr.ArrayCreationExpr;
import com.github.javaparser.ast.expr.ArrayInitializerExpr;
import com.github.javaparser.ast.expr.Expression;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class JavaParser implements BundleParser {
    private String getLiteral(Expression e) {
        if (!e.isStringLiteralExpr()) {
            return null;
        }

        return e.asStringLiteralExpr().getValue();
    }

    private List<Line> collectLinesFromArrayLiteral(ArrayInitializerExpr a) {
        NodeList<Expression> pairs = a.getValues();
        List<Line> lines = new ArrayList<>(pairs.size());

        for (Expression pairExpression : pairs) {
            ArrayInitializerExpr pair = pairExpression.asArrayInitializerExpr();

            String comment = null;
            if (pair.getComment().isPresent()) {
                comment = pair.getComment().get().getContent();
            }
            Optional<Expression> key = (pair.getValues().getFirst());
            Optional<Expression> value = (pair.getValues().getLast());
            if (!key.isPresent() || !value.isPresent())
                continue;

            lines.add(new Line(getLiteral(key.get()), getLiteral(value.get()), comment));
        }

        return lines;
    }

    @Override
    public List<Line> parse(InputStream s) throws IOException {
        TypeDeclaration<?> t = StaticJavaParser.parse(s).getType(0);

        List<ArrayInitializerExpr> l = t.findAll(ArrayCreationExpr.class).
                stream().
                filter(a -> a.getLevels().size() == 2 && a.getElementType().isClassOrInterfaceType()). // Search only Object[][] literals.
                map(ArrayCreationExpr::getInitializer).
                filter(Optional::isPresent).
                map(Optional::get).
                collect(Collectors.toList());

        List<Line> lines = new ArrayList<>();
        for (ArrayInitializerExpr a : l) {
            lines.addAll(collectLinesFromArrayLiteral(a));
        }

        return lines;
    }
}
