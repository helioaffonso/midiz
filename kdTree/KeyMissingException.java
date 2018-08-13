// Key-size mismatch exception supporting KDTree class

package kdTree;

class KeyMissingException extends Exception {

    public KeyMissingException() {
	super("Key not found");
    }
}
