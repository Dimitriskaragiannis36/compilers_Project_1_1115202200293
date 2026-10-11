/** Έκφραση μαζί με την "προτεραιότητά" της, ώστε να μπαίνουν παρενθέσεις μόνο όπου χρειάζεται. */
class Ex {
    static final int IF = 0;    // if-else (ή ternary στη Java): χαμηλότερη προτεραιότητα
    static final int CAT = 1;   // a + b
    static final int ATOM = 2;  // literal, μεταβλητή, κλήση, reverse x, (...)

    final String s;
    final int p;

    Ex(String s, int p) { this.s = s; this.p = p; }

    /** Παρενθέσεις αν είναι if (δεν επιτρέπεται "γυμνό" ως τελεστέος). */
    static Ex wrapIf(Ex e)   { return e.p == IF ? new Ex("(" + e.s + ")", ATOM) : e; }

    /** Παρενθέσεις αν δεν είναι "ατομική" (π.χ. τελεστέος του reverse ή αποδέκτης μεθόδου). */
    static Ex wrapAtom(Ex e) { return e.p < ATOM ? new Ex("(" + e.s + ")", ATOM) : e; }

    static String indent(String s) { return "  " + s.replace("\n", "\n  "); }
}

/** Συνθήκη της πηγαίας γλώσσας:  l PREFIX|SUFFIX|EQUAL r  */
class Cond {
    static final int PREFIX = 0, SUFFIX = 1, EQUAL = 2;
    final int kind;
    final Ex l, r;

    Cond(int kind, Ex l, Ex r) { this.kind = kind; this.l = l; this.r = r; }

    private static String prefix(Ex a, Ex b) {
        return Ex.wrapIf(a).s + " prefix " + Ex.wrapIf(b).s;
    }

    private static Ex ifExpr(String cond, Ex t, Ex e) {
        return new Ex("if (" + cond + ")\n" + Ex.indent(t.s) + "\nelse\n" + Ex.indent(e.s), Ex.IF);
    }

    /** Μετάφραση του "if (cond) t else e" στο IR (μόνο prefix, reverse, if). */
    Ex toIf(Ex t, Ex e) {
        switch (kind) {
            case PREFIX:   // a prefix b  ->  αυτούσιο
                return ifExpr(prefix(l, r), t, e);
            case SUFFIX:   // a suffix b  ->  reverse a prefix reverse b
                return ifExpr("reverse " + Ex.wrapAtom(l).s + " prefix reverse " + Ex.wrapAtom(r).s, t, e);
            default:       // a = b  ->  if (a prefix b) if (b prefix a) t else e else e
                return ifExpr(prefix(l, r), ifExpr(prefix(r, l), t, e), e);
        }
    }
}