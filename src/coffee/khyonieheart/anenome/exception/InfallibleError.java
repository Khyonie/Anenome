package coffee.khyonieheart.anenome.exception;

/**
 * Error to indicate that an operation must not fail. Should this error be thrown, it should be treated as a serious logic bug.
 */
public class InfallibleError extends Error {}
