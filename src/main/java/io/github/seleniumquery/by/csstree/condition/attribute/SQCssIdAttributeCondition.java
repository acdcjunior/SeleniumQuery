package io.github.seleniumquery.by.csstree.condition.attribute;

import io.github.seleniumquery.by.csstree.condition.SQCssCondition;

/**
 * #id
 *
 * CASE SENSITIVE!
 *
 * @author acdcjunior
 * @since 0.10.0
 */
public class SQCssIdAttributeCondition implements SQCssCondition {

    private String id;

    public SQCssIdAttributeCondition(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

}