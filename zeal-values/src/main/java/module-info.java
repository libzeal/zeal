import org.jspecify.annotations.NullMarked;

/**
 * Module containing the core set of Zeal values.
 *
 * @author Justin Albano
 * @since 0.5.0
 */
@NullMarked
module zeal.values {

    requires static transitive org.jspecify;
    requires transitive zeal.logic;

    exports io.github.libzeal.zeal.values;
    exports io.github.libzeal.zeal.values.collection;
    exports io.github.libzeal.zeal.values.core;
    exports io.github.libzeal.zeal.values.core.boxed;
    exports io.github.libzeal.zeal.values.sdk;
    exports io.github.libzeal.zeal.values.sdk.sequence;
}