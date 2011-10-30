/**
 * A simple class that overrides checkPermission in SecurityManager
 * in order to block calls to System.exit.
 */

public class ExitSecurityManager extends SecurityManager
{
	public void checkPermission(java.security.Permission permission)
	{
		String permissionName = permission.getName();
		if (permissionName.contains("exitVM"))
		{
			throw new SecurityException("Calls to System.exit are disallowed!");
		}
	}
}