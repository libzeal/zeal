package com.zeal.expression.subject;

import com.zeal.expression.BooleanExpression;

public interface SingleSubjectBooleanExpression<T> extends BooleanExpression {

    T subject();
}
