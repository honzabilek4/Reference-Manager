package cz.muni.fi.pa165.referenceManager.rest;

/**
 * Represents the entry points for the API.
 * It contains all the URIs necessary for global use,
 * especially from all the controllers
 *
 * @author Lenka Smitalova
 */
public abstract class ApiUris {
    public static final String ROOT_URI_MAIN        = "/rest";
    public static final String ROOT_URI_USERS       = ROOT_URI_MAIN + "/users";
    public static final String ROOT_URI_TAGS        = ROOT_URI_MAIN + "/tags";
    public static final String ROOT_URI_REFERENCES  = ROOT_URI_MAIN + "/references";
    public static final String ROOT_URI_NOTES       = ROOT_URI_MAIN + "/notes";
    public static final String ROOT_URI_EXPORT      = ROOT_URI_MAIN + "/export";
    public static final String ROOT_URI_IMPORT      = ROOT_URI_MAIN + "/import";
}
