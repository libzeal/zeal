package io.github.libzeal.zeal.expression.criteria;

/**
 * A criteria that is composed of any number of sub-criteria. The nature of the evaluation depends on the nature of the
 * specific compound criteria. For example, if the compound criteria is conjunctive, then all sub-criteria must be true
 * for the compound criteria to be true; other implementations may have different behavior.
 *
 * @param <T>
 *     The type of the subject.
 *
 * @author Justin Albano
 * @since 0.2.0
 */
public interface CompoundCriteria<T> extends Criteria<T> {

    /**
     * Appends the supplied sub-criteria to this compound criteria.
     *
     * @param criteria
     *     The criteria to append.
     */
    void append(Criteria<T> criteria);

    /**
     * Prepends the supplied sub-criteria to this compound criteria.
     *
     * @param criteria
     *     The criteria to prepend.
     */
    void prepend(Criteria<T> criteria);
}
