import java.util.*;

/**
 * An instance of class <code>Environment</code> maintains a
 * collection of bindings from valid identifiers to integers.
 * It supports storing and retrieving bindings, just as would
 * be expected in any dictionary.
 *
 * @author <a href="mailto:dcoore@uwimona.edu.jm">Daniel Coore</a>
 * @version 1.0
 */
public class Environment {

    Environment parent;
    HashMap<String, SMPLDataType> dictionary;

    /**
     * Create a new (empty) top level Environment.
     *
     */
    public Environment() {
        parent = null;
        dictionary = new HashMap<>();
    }
    public void setParent(Environment parent){
        this.parent=parent;
    }

    /**
     * Creates a new <code>Environment</code> instance that is
     * initialised with the given collection of bindings
     * (presented as separate arrays of names and values).
     *
     * @param ids    the collection of identifiers to be bound.
     * @param values the corresponding collection of values
     *               for the identifiers. Note that the two arrays must
     *               have the same length.
     */
    public Environment(Environment parent, String[] ids, SMPLDataType[] values) {
        this.parent = parent;
        dictionary = new HashMap<>();
        for (int i = 0; i < ids.length; i++) {
            dictionary.put(ids[i], values[i]);
        }
    }

    /**
     * Creates a new <code>Environment</code> instance that is
     * initialised with the given collection of bindings
     * (presented as separate array lists of names and values).
     *
     * @param ids    the collection of identifiers to be bound.
     * @param values the corresponding collection of values
     *               for the identifiers. Note that the two lists must
     *               have the same length.
     */
    public Environment(Environment parent, ArrayList<String> ids,
            ArrayList<? extends SMPLDataType> values) {
        this.parent = parent;
        dictionary = new HashMap<>();
        for (int i = 0; i < ids.size(); i++) {
            dictionary.put(ids.get(i), values.get(i));
        }
    }

    /**
     * Create an instance of a global environment suitable for
     * evaluating an program.
     *
     * @return the <code>Environment</code> created.
     */
    public static Environment makeGlobalEnv() {
        Environment result = new Environment();
        // add definitions for any primitive procedures or
        // constants here
        return result;
    }

    /**
     * Store a binding for the given identifier to the given
     * int within this environment.
     *
     * @param id    the name to be bound
     * @param value the value to which the name is bound.
     */
    public void put(String id, SMPLDataType value) {
        dictionary.put(id, value);
    }

    /**
     * Return the int associated with the given identifier.
     *
     * @param id the identifier.
     * @return the int associated with the identifier in
     *         this environment.
     * @exception Exception if <code>id</code> is unbound
     */
    public SMPLDataType get(String id) throws UnboundVarException {
        SMPLDataType result = dictionary.get(id);
        if (result == null)
            if (parent == null)
                throw new UnboundVarException(id);
            else
                return parent.get(id);
        else
            return result;
    }

    /**
     * Create a string representation of this environment.
     *
     * @return a string of all the names bound in this
     *         environment.
     */
    public String toString() {
        StringBuffer result = new StringBuffer();

        Iterator iter = dictionary.keySet().iterator();
        while (iter.hasNext()) {
            result = result.append(iter.next().toString());
        }
        return result.toString();
    }

}
