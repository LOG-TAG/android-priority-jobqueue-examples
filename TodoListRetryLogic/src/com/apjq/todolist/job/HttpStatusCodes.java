package com.apjq.todolist.job;

public class HttpStatusCodes {

	public static final int STATUS_CODE_SOMETHING_WRONG = 0;

	public static final int STATUS_CODE_CONTINUE = 100;

	public static final int STATUS_CODE_SWITCHING_PROTOCOLS = 101;

	/** Status code for a successful request. */
	public static final int STATUS_CODE_OK = 200;

	/**
	 * Status code for a successful request with no content information.
	 * 
	 * @since 1.11
	 */

	public static final int STATUS_CODE_CREATED = 201;

	public static final int STATUS_CODE_ACCEPTED = 202;

	public static final int STATUS_CODE_NON_AUTHORITATIVE_INFO = 203;
	public static final int STATUS_CODE_NO_CONTENT = 204;

	public static final int STATUS_RESET_CONTENT = 205;

	public static final int STATUS_CODE_PARTIAL_CONTENT = 206;

	/**
	 * Status code for a resource corresponding to any one of a set of
	 * representations.
	 */
	public static final int STATUS_CODE_MULTIPLE_CHOICES = 300;

	/** Status code for a resource that has permanently moved to a new URI. */
	public static final int STATUS_CODE_MOVED_PERMANENTLY = 301;

	/** Status code for a resource that has temporarily moved to a new URI. */
	public static final int STATUS_CODE_FOUND = 302;

	/**
	 * Status code for a resource that has moved to a new URI and should be
	 * retrieved using GET.
	 */
	public static final int STATUS_CODE_SEE_OTHER = 303;

	/**
	 * Status code for a resource that access is allowed but the document has
	 * not been modified.
	 */
	public static final int STATUS_CODE_NOT_MODIFIED = 304;

	/** Status code for a resource that has temporarily moved to a new URI. */
	public static final int STATUS_CODE_TEMPORARY_REDIRECT = 307;

	/** Status code for a request that requires user authentication. */
	public static final int STATUS_CODE_UNAUTHORIZED = 401;

	/**
	 * Status code for a server that understood the request, but is refusing to
	 * fulfill it.
	 */
	public static final int STATUS_CODE_FORBIDDEN = 403;

	/**
	 * Status code for a server that has not found anything matching the
	 * Request-URI.
	 */
	public static final int STATUS_CODE_NOT_FOUND = 404;

	public static final int STATUS_METHOD_NOT_ALLOWED = 405;

	public static final int STATUS_NOT_ACCEPTABLE = 406;

	public static final int STATUS_PROXY_AUTHENTICATION_REQUIRED = 407;

	public static final int STATUS_REQUEST_TIMEOUT = 408;

	public static final int STATUS_NOT_CONFLICT = 409;

	public static final int STATUS_GONE = 410;

	public static final int STATUS_LENGTH_REQUIRED = 411;

	public static final int STATUS_PRECONDITION_FAILED = 412;

	public static final int STATUS_REQUEST_ENTITY_TOO_LARGE = 413;

	public static final int STATUS_REQUESTURI_TOO_LONG = 414;

	public static final int STATUS_UNSUPPORTED_MEDIA_TYPE = 415;

	public static final int STATUS_REQUESTED_RANGE_NOT_SATISFIABLE = 416;

	public static final int STATUS_EXPECTATION_FAILED = 417;

	public static final int STATUS_FAILURE = 418;

	/** Status code for an internal server error. */
	public static final int STATUS_CODE_SERVER_ERROR = 500;

	/**
	 * Status code for a bad gateway.
	 * 
	 * @since 1.16
	 */
	public static final int STATUS_CODE_BAD_GATEWAY = 502;

	/** Status code for a service that is unavailable on the server. */
	public static final int STATUS_CODE_SERVICE_UNAVAILABLE = 503;

	public static final int STATUS_GATEWAY_TIMEOUT = 504;

	public static final int STATUS_HTTP_VERSION_NOT_SUPPORTED = 505;

	/**
	 * Returns whether the given HTTP response status code is a success code
	 * {@code >= 200 and < 300}.
	 */
	public static boolean isSuccess(int statusCode) {
		return statusCode >= STATUS_CODE_OK
				&& statusCode < STATUS_CODE_MULTIPLE_CHOICES;
	}

	/**
	 * Returns whether the given HTTP response status code is a redirect code
	 * {@code 301, 302, 303, 307}.
	 * 
	 * @since 1.11
	 */
	public static boolean isRedirect(int statusCode) {
		switch (statusCode) {
		case HttpStatusCodes.STATUS_CODE_MOVED_PERMANENTLY: // 301
		case HttpStatusCodes.STATUS_CODE_FOUND: // 302
		case HttpStatusCodes.STATUS_CODE_SEE_OTHER: // 303
		case HttpStatusCodes.STATUS_CODE_TEMPORARY_REDIRECT: // 307
			return true;
		default:
			return false;
		}
	}
}
