/**
 * Module containing the assertions used to evaluate Zeal values.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
module zeal.assertion {

    requires transitive zeal.logic;
    requires transitive zeal.values;

    exports io.github.libzeal.zeal.assertion;
    exports io.github.libzeal.zeal.assertion.error;
}