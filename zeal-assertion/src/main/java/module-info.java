import org.jspecify.annotations.NullMarked;

/**
 * Module containing the assertions used to evaluate Zeal values.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
@NullMarked
module zeal.assertion {

    requires static transitive org.jspecify;
    requires transitive zeal.logic;
    requires transitive zeal.values;

    exports io.github.libzeal.zeal.assertion;
    exports io.github.libzeal.zeal.assertion.error;
}