module zeal {

    requires java.base;
    requires zeal;

    exports io.github.libzeal.zeal.assertions;
    exports io.github.libzeal.zeal.logic;
    exports io.github.libzeal.zeal.logic.evaluation;
    exports io.github.libzeal.zeal.logic.format;
    exports io.github.libzeal.zeal.values;
}