import org.jspecify.annotations.NullMarked;

@NullMarked
module zeal {

    requires static transitive org.jspecify;
    requires transitive zeal.assertion;
    requires transitive zeal.values;
}
