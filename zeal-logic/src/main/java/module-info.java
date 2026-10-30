/**
 * The logic module that contains the logical constructed used by the rest of
 * Zeal, including expressions, operators, and conditions. This logic is
 * intended for use as both a standalone logic library and the foundation for
 * the other Zeal modules, such as the assertion methods and value types.
 *
 * @author Justin Albano
 */
module zeal.logic {

    exports io.github.libzeal.zeal.logic;
    exports io.github.libzeal.zeal.logic.evaluation;
    exports io.github.libzeal.zeal.logic.evaluation.format;
    exports io.github.libzeal.zeal.logic.rationale;
    exports io.github.libzeal.zeal.logic.unary;

    exports io.github.libzeal.zeal.logic.format to zeal.values;
}