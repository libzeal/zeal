import org.jspecify.annotations.NullMarked;

/**
 * Module combing the zeal.assertion and zeal.values modules to allow users to
 * add Zeal to a project using a single dependency.
 *
 * @author Justin Albano
 */
@NullMarked
module zeal {

    requires static transitive org.jspecify;
    requires transitive zeal.assertion;
    requires transitive zeal.values;
}
